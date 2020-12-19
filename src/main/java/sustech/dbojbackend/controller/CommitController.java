package sustech.dbojbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sustech.dbojbackend.annotatin.needToken;
import sustech.dbojbackend.exception.globalException;
import sustech.dbojbackend.model.CommitResultType;
import sustech.dbojbackend.model.SqlLanguage;
import sustech.dbojbackend.model.State;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.CommitLog;
import sustech.dbojbackend.model.data.Question;
import sustech.dbojbackend.model.data.QuestionBuild;
import sustech.dbojbackend.model.data.QuestionDetail;
import sustech.dbojbackend.model.request.CommitCreateQuestion;
import sustech.dbojbackend.model.request.CommitDeleteRequest;
import sustech.dbojbackend.model.request.CommitQuery;
import sustech.dbojbackend.model.request.CommitUpdateQuestion;
import sustech.dbojbackend.model.request.JudgeRequest;
import sustech.dbojbackend.model.response.CommitQueryResponse;
import sustech.dbojbackend.model.response.JudgeSystemResultResponse;
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionDetailRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/commit")
public class CommitController {
    @Resource
    UserRepository userRepository;
    @Resource
    CommitLogRepository commitLogRepository;

    @Resource
    QuestionRepository questionRepository;

    @Resource
    QuestionDetailRepository questionDetailRepository;

    @Resource
    QuestionBuildRepository questionBuildRepository;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/history")
    @needToken(UserLevel.NORMAL_USER)
    public List<CommitLog> commithistory(@RequestParam String username) {
        var users = userRepository.findByUserName(username);
        if (users.isEmpty()) {
            throw new globalException.NotFoundException("Wrong UserName");
        }
        var user = users.get(0);
        return commitLogRepository.findByUserId(user.getId());
    }

    @GetMapping("/history/{order}")
    @needToken(UserLevel.NORMAL_USER)
    public List<CommitLog> commithistory(@PathVariable(value = "order") Long questionOrder,
                                         @RequestParam String username) {
        var users = userRepository.findByUserName(username);
        if (users.isEmpty()) {
            throw new globalException.NotFoundException("Wrong UserName");
        }
        var user = users.get(0);
        return commitLogRepository.findByUserIdAndQuestionOrder(user.getId(), questionOrder);
    }


    @Modifying
    @PostMapping("/query/{quesid}")
    @needToken(UserLevel.NORMAL_USER)
    public CommitQueryResponse commitQuery(@PathVariable("quesid") Long useless
            , @RequestBody CommitQuery cqo) {
        var questionList = questionRepository.findByProgramOrder(cqo.getQuestionId());
        if (questionList.isEmpty()) {
            throw new globalException.NotFoundException("program number not found");
        }
        var questionCommit = questionList.get(0);
        var questionDetailList = questionDetailRepository.findByProgramOrderAndLanguage(cqo.getQuestionId(), cqo.getLanguage());
        if (questionDetailList.isEmpty()) {
            throw new globalException.NotFoundException("program number not found");
        }
        var questionDetail = questionDetailList.get(0);

        var questionBuildList = questionBuildRepository.findByProgramOrderAndLanguageAndTableOrder(
                cqo.getQuestionId(),
                cqo.getLanguage(),
                (long) cqo.getTestOrRun());
        if (questionBuildList.isEmpty()) {
            throw new globalException.NotFoundException("program number not found");
        }
        var questionbuild = questionBuildList.get(0);
        JudgeRequest correct = new JudgeRequest();
        JudgeRequest commit = new JudgeRequest();
        correct.setLanguage(cqo.getLanguage());
        commit.setLanguage(cqo.getLanguage());
        correct.setLimitTime(questionDetail.getCputime());
        commit.setLimitTime(questionDetail.getCputime());
        correct.setLimitMemory(questionDetail.getMemory());
        commit.setLimitMemory(questionDetail.getMemory());
        correct.setCreateTable(questionbuild.getBuildScript());
        //.replace('\n', ' '));
        commit.setCreateTable(questionbuild.getBuildScript());
        //.replace('\n', ' '));
        correct.setSearchTable(questionDetail.getCorrectScript());
        commit.setSearchTable(cqo.getCommitCode());
        String url = "http://127.0.0.1:11223";
        String[] correctResults = new String[2];
        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(() -> {
            correctResults[0] = restTemplate.postForObject(url, correct, String.class);
        }));
        threads.add(new Thread(() -> {
            correctResults[1] = restTemplate.postForObject(url, commit, String.class);
        }));
        try {
            threads.get(0).start();
            threads.get(1).start();
            threads.get(0).join();
            threads.get(1).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(correctResults[0]);
        System.out.println(correctResults[1]);
        ObjectMapper mapper = new ObjectMapper();
        JudgeSystemResultResponse result1 = null;
        JudgeSystemResultResponse result2 = null;
        try {
            result1 = mapper.readValue(correctResults[0], JudgeSystemResultResponse.class);
            result2 = mapper.readValue(correctResults[1], JudgeSystemResultResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        CommitLog commitLog;
        Long userId = userRepository.findByUserName(cqo.getUsername()).get(0).getId();
        assert result1 != null;
        assert result2 != null;
        System.out.println(result1.getData().equals(result2.getData()));
        if (Objects.equals(result1.getData(), result2.getData())) {
            commitLog = new CommitLog(userId, cqo.getQuestionId(), cqo.getCommitCode(), cqo.getLanguage(), CommitResultType.AC);
        } else {
            commitLog = new CommitLog(userId, cqo.getQuestionId(), cqo.getCommitCode(), cqo.getLanguage(), CommitResultType.WA);
        }
        commitLogRepository.save(commitLog);
        return new CommitQueryResponse(cqo.getQuestionId(), commitLog);
    }

    @Modifying
    @PostMapping("/create")
    @needToken(UserLevel.ADMIN)
    public Question CommitCreateQuestion(@RequestBody CommitCreateQuestion ccq) {
        var question = new Question(ccq.getName(), ccq.getDescription(), ccq.getDDL());
        try {
            return questionRepository.save(question);
        } catch (Exception e) {
            throw new globalException.ForbiddenException("unknown error ");
        }
    }

    @Modifying
    @PostMapping("/update")
    @needToken(UserLevel.ADMIN)
    public State commitUpdate(@RequestBody CommitUpdateQuestion request) {
        Long id = request.getProgramOrder();
        if (questionRepository.findByProgramOrder(id).isEmpty()) {
            throw new globalException.NotFoundException("can not update to null");
        }
        questionDetailRepository.save(new QuestionDetail(
                request.getProgramOrder(), request.getCorrectCommand(), request.getLanguage(),
                request.getCputime(), request.getMomory()
        ));
        try {
            for (var s : request.getGroup()) {
                QuestionBuild questionBuild = new QuestionBuild();
                questionBuild.setProgramOrder(request.getProgramOrder());
                questionBuild.setBuildScript(s);
                questionBuild.setLanguage(request.getLanguage());
                questionBuildRepository.save(questionBuild);
            }
            return State.SUCCESS;
        } catch (Exception e) {
            throw new globalException.ForbiddenException("some error happen during update to questionBuild");
        }
    }

    @GetMapping("/update")
    @needToken(UserLevel.ADMIN)
    public CommitUpdateQuestion commitUpdateGetInformation(@RequestBody CommitUpdateQuestion request) {
        // this one use to sync the informations
        Long programOrder = request.getProgramOrder();
        SqlLanguage language = request.getLanguage();
        var details = questionDetailRepository.findByProgramOrderAndLanguage(programOrder, language);
        if (details.isEmpty()) {
            return request;
        }
        String correctCommand = details.get(0).getCorrectScript();
        request.setCorrectCommand(correctCommand);
        var builds = questionBuildRepository.findByProgramOrderAndLanguage(programOrder, language);
        if (builds.isEmpty()) {
            return request;
        }
        for (var parameter : builds) {
            request.getGroup().add(parameter.getBuildScript());
        }
        return request;
    }

    @Modifying
    @DeleteMapping("/delete")
    @needToken(UserLevel.ADMIN)
    public State commitDelete(@RequestBody CommitDeleteRequest request) {
        try {
            questionDetailRepository.deleteById(request.getQuestionOrder());
            questionBuildRepository.deleteByProgramOrder(request.getQuestionOrder());
            questionRepository.deleteById(request.getQuestionOrder());
            return State.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw new globalException.ForbiddenException("Error in delete question");
        }
    }


}
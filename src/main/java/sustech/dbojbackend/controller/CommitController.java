package sustech.dbojbackend.controller;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import sustech.dbojbackend.model.response.CommitQueryResponse;
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionDetailRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

import java.util.List;

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

    @Modifying
    @PostMapping("/query")
    @needToken(UserLevel.NORMAL_USER)
    public CommitQueryResponse commitQuery(@RequestBody CommitQuery cqo) {
        List<Question> questionList;
        questionList = questionRepository.findByProgramOrder(cqo.getQuestionId());
        if (questionList.isEmpty()) {
            // error state
        }
        /**
         *
         * TODO deal with the judge system
         * */
        var commitLog = new CommitLog(1L, cqo.getQuestionId(), cqo.getCommitCode(), cqo.getLanguage(), CommitResultType.AC);
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

    @PostMapping("/update")
    @needToken(UserLevel.ADMIN)
    public State commitUpdate(@RequestBody CommitUpdateQuestion request) {
        Long id = request.getProgramOrder();
        if (questionRepository.findByProgramOrder(id).isEmpty()) {
            throw new globalException.NotFoundException("can not update to null");
        }
        questionDetailRepository.save(new QuestionDetail(request.getProgramOrder(), request.getCorrectCommand(), request.getLanguage()));
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
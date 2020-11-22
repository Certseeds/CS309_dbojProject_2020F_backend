package sustech.dbojbackend.controller;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.*;
import sustech.dbojbackend.model.State;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.*;
import sustech.dbojbackend.model.request.CommitQuery;
import sustech.dbojbackend.model.request.CommitUpdateQuestion;
import sustech.dbojbackend.model.request.CommitUpdateRequest;
import sustech.dbojbackend.model.request.CommitDeleteRequest;
import sustech.dbojbackend.model.response.CommitQueryResponse;
import sustech.dbojbackend.repository.*;
import sustech.dbojbackend.service.Token;

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
    public CommitQueryResponse commitQuery(@RequestBody CommitQuery cqo) {
        Token token = new Token(userRepository);
        User user;
        try {
            user = token.checkToken(cqo.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        List<Question> questionList;
        try {
            questionList = questionRepository.findByProgramOrder(cqo.getQuestionId());
        } catch (Exception e) {
            throw new RuntimeException("not exist question");
        }
        if (questionList.size() == 0) {
            throw new RuntimeException("not exist question");
        }
        Question question = questionList.get(0);
        if (question.getLanguage() != cqo.getLanguage()) {
            throw new RuntimeException("language not match");
        }

        CommitLog commitLog = new CommitLog();
        commitLog.setUserId(user.getId());
        commitLog.setQuestionOrder(cqo.getQuestionId());
        commitLog.setCommitCode(cqo.getCommitCode());
        commitLog.setLanguage(cqo.getLanguage());
        commitLogRepository.save(commitLog);
        return new CommitQueryResponse(cqo.getQuestionId(), commitLog);
    }

    @PostMapping("/update")
    public State commitUpdate(@RequestBody CommitUpdateRequest request) {
        Token token = new Token(userRepository);
        User user;
        try {
            user = token.checkToken(request.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (user.getLevel() != UserLevel.ADMIN)
            throw new RuntimeException("must be admin");


        try {
            CommitUpdateQuestion commitUpdateQuestion = request.getCommitUpdateQuestion();

            Question question = new Question();
            question.setName(commitUpdateQuestion.getName());
            question.setDescription(commitUpdateQuestion.getDescription());
            question.setLanguage(commitUpdateQuestion.getLanguage());
            question.setDeadline(commitUpdateQuestion.getDDL());
            questionRepository.save(question);

            QuestionDetail questionDetail = new QuestionDetail(question.getProgramOrder(), commitUpdateQuestion.getCorrectCommand());
            questionDetailRepository.save(questionDetail);

            for (String s : commitUpdateQuestion.getGroup()) {
                QuestionBuild questionBuild = new QuestionBuild();
                questionBuild.setProgramOrder(question.getProgramOrder());
                questionBuild.setBuildScript(s);
                questionBuild.setLanguage(commitUpdateQuestion.getLanguage());
                questionBuildRepository.save(questionBuild);
            }
            return State.SUCCESS;
        } catch (Exception e) {
            return State.FAILED;
        }
    }

    @Modifying
    @DeleteMapping("/delete")
    public State commitDelete(@RequestBody CommitDeleteRequest request) {
        Token token = new Token(userRepository);
        User user;
        try {
            user = token.checkToken(request.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (user.getLevel() != UserLevel.ADMIN)
            throw new RuntimeException("must be admin");
        try {
            questionDetailRepository.deleteById(request.getQuestionOrder());
            questionBuildRepository.deleteByProgramOrder(request.getQuestionOrder());
            questionRepository.deleteById(request.getQuestionOrder());
            return State.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return State.FAILED;
        }
    }


}
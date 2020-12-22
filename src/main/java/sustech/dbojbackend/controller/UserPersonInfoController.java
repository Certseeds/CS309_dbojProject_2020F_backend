package sustech.dbojbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sustech.dbojbackend.annotatin.needToken;
import sustech.dbojbackend.exception.globalException;
import sustech.dbojbackend.model.CommitResultType;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.response.StatisticsAnalysisResponse;
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionDetailRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

import java.util.HashMap;

@RestController
public class UserPersonInfoController {
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

    @GetMapping("/persona/{username}/{questionid}")
    @needToken(UserLevel.NORMAL_USER)
    public StatisticsAnalysisResponse commithistory(@PathVariable("username") String username, @PathVariable("questionid") Long qid) {
        if (userRepository.findByUserName(username).isEmpty()) {
            throw new globalException.NotFoundException("username Do not exist");
        } else if (questionRepository.findByProgramOrder(qid).isEmpty()) {
            throw new globalException.NotFoundException("ProgramOrder do not exist");
        }
        var userid = userRepository.findByUserName(username).get(0).getId();
        var commitLog = commitLogRepository.findByUserIdAndQuestionOrder(userid, qid);
        Long submission = (long) commitLog.size();
        var hashmap = new HashMap<CommitResultType, Long>();
        for (var x : CommitResultType.values()) {
            hashmap.put(x, commitLog.stream().filter(cl -> cl.getState() == x).count());
        }
        return new StatisticsAnalysisResponse(submission, hashmap);
    }

    @GetMapping("/persona/all/all")
    public StatisticsAnalysisResponse commithistoryOfAllUsers() {
        var commitLog = commitLogRepository.findAll();
        Long submission = (long) commitLog.size();
        var hashmap = new HashMap<CommitResultType, Long>();
        for (var x : CommitResultType.values()) {
            hashmap.put(x, commitLog.stream().filter(cl -> cl.getState() == x).count());
        }
        return new StatisticsAnalysisResponse(submission, hashmap);
    }
}

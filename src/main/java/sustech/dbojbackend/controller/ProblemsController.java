package sustech.dbojbackend.controller;

import org.springframework.web.bind.annotation.*;
import sustech.dbojbackend.model.data.Question;
import sustech.dbojbackend.model.response.ProblemsListResponse;
import sustech.dbojbackend.model.response.QuestionHead;
import sustech.dbojbackend.repository.QuestionRepository;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemsController {
    @Resource
    QuestionRepository questionRepository;

    @GetMapping("/details/{question_order}")
    public Question problemsDetail(@PathVariable String question_order) {
        List<Question> questions = questionRepository.findByProgramOrder(Long.parseLong(question_order));
        return questions.get(0);
    }

    @GetMapping("/list")
    public ProblemsListResponse problemsList() {
        List<Question> questions = questionRepository.findAll();
        ProblemsListResponse problemsListResponse=new ProblemsListResponse();
        for (Question q : questions) {
            problemsListResponse.getQuestionHeads().add(new QuestionHead(q.getProgramOrder(),q.getName(),q.getDescription(),q.getLanguage(),q.getDeadline()));
        }
        return problemsListResponse;
    }
}

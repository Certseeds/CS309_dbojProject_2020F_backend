package sustech.dbojbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.data.Question;
import sustech.dbojbackend.model.response.ProblemsListResponse;
import sustech.dbojbackend.repository.QuestionRepository;

import javax.annotation.Resource;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemsController {
    @Resource
    QuestionRepository questionRepository;

    @GetMapping("/details/{question_order}")
    public Question problemsDetail(@PathVariable("question_order") String question_order) {
        List<Question> questions = questionRepository.findByProgramOrder(Long.parseLong(question_order));
        return questions.get(0);
    }

    @GetMapping("/list")
    public ProblemsListResponse problemsList() {
        List<Question> questions = questionRepository.findAll();
        return new ProblemsListResponse(questions);
    }
}

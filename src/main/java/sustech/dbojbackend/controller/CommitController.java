package sustech.dbojbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.dao.CommitLog;
import sustech.dbojbackend.model.dto.CommitQueryObject;

@RestController
@RequestMapping("/commit")
public class CommitController {
    @PostMapping("/query")
    public CommitLog commitQuery(@RequestBody CommitQueryObject cqo) {
        return new CommitLog();
    }

    @GetMapping("/query/{id}")
    public CommitLog commitQuery2(@PathVariable("id") int cqo) {
        return new CommitLog();
    }

}
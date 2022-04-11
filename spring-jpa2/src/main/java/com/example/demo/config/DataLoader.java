package com.example.demo.config;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    private final CommentRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var comment = new Comment();
        comment.setContent("こんにちは");
        repository.save(comment);

        var testComment = new Comment();
        testComment.setContent("テストコメント");
        repository.save(testComment);
    }
}

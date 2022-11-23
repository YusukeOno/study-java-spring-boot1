package com.example.todolist.repository;

import com.example.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByTitleLike(String title);

    List<Todo> findByImportance(Integer importance);

    List<Todo> findByUrgency(Integer urgency);

    List<Todo> findByDeadlineBetweenOrderByDeadlineAsc(Date from, Date to);

    List<Todo> findByDeadlineGreaterThanEqualOrderByDeadlineAsc(Date from);

    List<Todo> findByDeadlineLessThanEqualOrderByDeadlineAsc(Date to);

    List<Todo> findByDone(String done);
}

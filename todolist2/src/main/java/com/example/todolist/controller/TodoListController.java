package com.example.todolist.controller;

import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class TodoListController {

    private final TodoRepository todoRepository;
    private final TodoService todoService;

    @GetMapping("/todo")
    public ModelAndView showTodoList(ModelAndView modelAndView) {

        modelAndView.setViewName("todoList");
        List<Todo> todoList = todoRepository.findAll();
        modelAndView.addObject("todoList", todoList);
        return modelAndView;
    }

    @GetMapping("/todo/create")
    public ModelAndView createTodoAndView(ModelAndView modelAndView) {
        modelAndView.setViewName("todoForm");
        modelAndView.addObject("todoData", new TodoData());
        return modelAndView;
    }

    @PostMapping("/todo/create")
    public ModelAndView createTodo(@ModelAttribute @Validated TodoData todoData,
                                   BindingResult result,
                                   ModelAndView modelAndView) {
        // エラーチェック
        boolean isValid = todoService.isValid(todoData, result);
        if (!result.hasErrors() && isValid) {
            // エラーなし
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return showTodoList(modelAndView);
        } else {
            // エラーあり
            modelAndView.setViewName("todoForm");
            return modelAndView;
        }
    }

    @PostMapping("/todo/cancel")
    public String cancel() {
        return "redirect:/todo";
    }
}

package com.example.demo.controller;

import com.example.demo.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameController {

    @Autowired
    HttpSession session;

    @GetMapping("/")
    public String index() {

        // sessionクリア
        session.invalidate();

        // 答えを作ってSessionに格納
        Random random = new Random();
        int answer = random.nextInt(100) + 1;
        session.setAttribute("answer", answer);
        System.out.println("answer=" + answer);
        return "game";
    }

    @PostMapping("/challenge")
    public ModelAndView challenge(@RequestParam("number") int number,
                                  ModelAndView modelAndView) {
        // セッションから答えを取得
        int answer = (Integer) session.getAttribute("answer");

        // ユーザーの回答履歴を取得
        @SuppressWarnings("unchecked")
        List<History> histories = (List<History>) session.getAttribute("histories");
        if (histories == null) {
            histories = new ArrayList<>();
            session.setAttribute("histories", histories);

        }

        // 判定→回答履歴追加
        if (answer < number) {
            histories.add(new History(histories.size() + 1, number, "もっと小さいです"));

        } else if (answer == number) {
            histories.add(new History(histories.size() + 1, number, "正解です！"));

        } else {
            histories.add(new History(histories.size() + 1, number, "もっと大きいです"));
        }

        modelAndView.setViewName("game");
        modelAndView.addObject("histories", histories);
        return modelAndView;
    }
}

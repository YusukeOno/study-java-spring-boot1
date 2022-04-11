package com.example.demo.controller;

import com.example.demo.model.SiteUser;
import com.example.demo.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("登録エラーがある時、エラー表示することを期待する")
    void whenThereIsRegistrationError_expectToSeeErrors() throws Exception {

        mockMvc
                // リクエストを実行する
                .perform(
                        // HTTPのPOSTリクエストを使用する
                        post("/register")
                                // 入力の属性を設定する
                                .flashAttr("user", new SiteUser())
                                // CSRFトークンを自動挿入する
                                .with(csrf())
                )
                // エラーがあることを検証する
                .andExpect(model().hasErrors())
                // 指定したHTMLを表示しているかを検証する
                .andExpect(view().name("register"));
    }

    @Test
    @DisplayName("管理者ユーザーとして登録する時、成功することを期待する")
    void whenRegisteringAsAdminUser_expectToSucceed() throws Exception {
        var user = new SiteUser();
        user.setUsername("管理者ユーザ");
        user.setPassword("password");
        user.setEmail("admin@example.com");
        user.setGender(0);
        user.setAdmin(true);
        user.setRole(Role.ADMIN.name());

        mockMvc.perform(
                    post("/register")
                            .flashAttr("user", user)
                            .with(csrf())
                )
                // エラーがないことを検証する
                .andExpect(model().hasNoErrors())
                // 指定したURLに、リダイレクトすることを検証する
                .andExpect(redirectedUrl("/login?register"))
                // ステータスコードが、302であることを検証する
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("管理者ユーザでログイン時、ユーザー一覧を表示することを期待する")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void whenLoggedInAsAdminUser_expectToSeeListOfUsers() throws Exception {

        mockMvc.perform(get("/admin/list"))
                // ステータスコードが、200であることを検証する
                .andExpect(status().isOk())
                // HTMLの表示内容に、指定した文字列を含んでいるか検証する
                .andExpect(content().string(containsString("ユーザ一覧")))
                .andExpect(view().name("list"));
    }
}
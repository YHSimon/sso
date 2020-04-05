package com.yh_simon.handler;


import com.yh_simon.db.MockDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@Slf4j
public class ServerController {
    /**
     * 第一登录验证
     *
     * @param redirectUrl
     * @param session
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/checkToken")
    public String checkToken(String redirectUrl, HttpSession session, Model model, HttpServletRequest request) {
        String token = (String) session.getServletContext().getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            model.addAttribute("redirectUrl", redirectUrl);
            return "login";
        } else {
            //验证token
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getValue().equals(token)) {
                    log.info("验证通过"+redirectUrl);
                    return "redirect:" + redirectUrl + "?token=" + token;
                }
            }
        }
        model.addAttribute("redirectUrl'", redirectUrl);
        return "login";
    }

    @PostMapping("/login")
    public String login(String username,
                        String password,
                        String redirectUrl,
                        HttpSession session,
                        Model model,HttpServletRequest request) {
        if ("admin".equals(username) && "123456".equals(password)) {
            //1.创建token
            String token = UUID.randomUUID().toString();
            log.info("token创建成功！token={}", token);
            //2.token保存到全局会话中
            session.getServletContext().setAttribute("token", token);
            //3.token保存到数据库
            MockDB.tokenSet.add(token);
            //4.返回客户端
            return "redirect:" + redirectUrl + "?token=" + token;
        } else {
            log.error("用户名或密码错误! username={},password={}", username, password);
            model.addAttribute("redirectUrl", redirectUrl);
            return "login";
        }
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verifyToken(String token, String clientLogoutUrl) {
        if (MockDB.tokenSet.contains(token)) {
            Set<String> set = MockDB.clientLogoutUrlMap.get(token);
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(clientLogoutUrl);
            MockDB.clientLogoutUrlMap.put(token, set);
            return "true";
        }
        return "false";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}


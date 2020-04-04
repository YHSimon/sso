package com.yh_simon.handler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yh_simon.util.SSOClientUtil;

import javax.servlet.http.HttpSession;

@Controller
public class TMallController {

    @GetMapping("/tmall")
    public String index(Model model){
        model.addAttribute("serverLogoutUrl", SSOClientUtil.getServerLogoutUrl());
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/tmall";
    }

}

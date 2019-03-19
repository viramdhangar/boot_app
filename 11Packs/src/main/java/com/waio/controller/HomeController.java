package com.waio.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@GetMapping("/")
    public ModelAndView hello() {
		return new ModelAndView("welcome");
    }

	@GetMapping("/striker")
    public String helloStriker(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name, HttpServletRequest request) {
        model.addAttribute("name", name);
        model.addAttribute("url", ""+request.getContextPath()+"/download/file/app-debug.apk");
        return "welcome";
    }
}
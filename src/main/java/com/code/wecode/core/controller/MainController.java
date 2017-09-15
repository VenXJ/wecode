package com.code.wecode.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.code.wecode.core.mapper.CodeUserMapper;
import com.code.wecode.core.model.CodeUser;

@Controller
public class MainController {
	@Autowired
	private CodeUserMapper codeUserMapper;

	@RequestMapping("/")
	public ModelAndView index(Model model) {
		return new ModelAndView("/login");
	}

	@RequestMapping("/user")
	public String test(HttpServletRequest request, @Param("userName") String userName,
			@Param("passWord") String passWord, Model model) {
		CodeUser user = codeUserMapper.selectByUnionKey(userName);
		if(null!=user){
			if(passWord.equals(user.getPassword())){
				model.addAttribute("userName", userName);
				return "index";
			}
		}
		return "/login";
	}
}

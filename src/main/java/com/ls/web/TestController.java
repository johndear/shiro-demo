package com.ls.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController{

	@RequestMapping(value="/admin/test")
	public String getName(HttpServletRequest request, HttpSession session){
		try {
			System.out.println("---------------- controller session id 1 ----------------" + request.getSession(false).getId());
		} catch (final Exception e) {
            System.out.println("123 Error invalidating session." + e.getMessage());
        }
		System.out.println("---------------- controller session id 2 ----------------" + session.getId());
		
		return "hello...";
	}
}

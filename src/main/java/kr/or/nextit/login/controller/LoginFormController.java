package kr.or.nextit.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.nextit.web.servlet.Controller;

public class LoginFormController implements Controller{
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		/*String referer = request.getHeader("Referer");
		System.out.println("referer : " + referer);
		
		HttpSession session = request.getSession();
		session.setAttribute("previewPage", referer);*/
		
		return "login/loginForm";
	}

}




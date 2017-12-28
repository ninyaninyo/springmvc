package kr.or.nextit.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/sample.do")
public class SimpleController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String result = "";
		
		// 클라이언트 요청 분석
		String type = request.getParameter("cmd");
		
		// 비즈니스 로직 처리
		if("greeting".equals(type)) {
			result = "안녕하세요.";
		} else if("date".equals(type)) {
			result = new Date().toString();
		}
		
		// 요청 결과를 request, session 속성 저장.
		request.setAttribute("result", result);
		
		// 뷰 페이지로 forward 또는 redirect
		RequestDispatcher dispatcher = request.getRequestDispatcher("/sample.jsp");
		dispatcher.forward(request, response);
		
	}
}









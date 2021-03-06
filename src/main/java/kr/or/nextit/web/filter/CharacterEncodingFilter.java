package kr.or.nextit.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter{
	
	private String encoding = "";
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		// 필터 초기화 작업
		encoding = config.getInitParameter("encoding");
		if(encoding == null) {
			encoding = "utf-8";
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1. 실제 로직, 필터 작업.	(전처리)	
		request.setCharacterEncoding(encoding);
		// 2. 실제 기능
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		// 3. 필터에서 사용한 자원해제. (후처리)
	}

}






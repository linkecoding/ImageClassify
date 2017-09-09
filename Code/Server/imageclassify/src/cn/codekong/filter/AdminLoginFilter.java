package cn.codekong.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.codekong.config.Constant;

public class AdminLoginFilter implements Filter {
	public static final String LOGIN_PAGE = "/ImageClassify/admin/login.jsp";

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String currentURL = request.getRequestURI();
		HttpSession session = request.getSession(false);

		if (!LOGIN_PAGE.equals(currentURL)) {
			if (session != null && session.getAttribute(Constant.USERNAME) != null && !"".equals(session.getAttribute(Constant.USERNAME))) {
				// 这里表示正确，会去寻找下一个链，如果不存在，则进行正常的页面跳转
				filterChain.doFilter(request, response);
				return;
			} else {
				response.sendRedirect(LOGIN_PAGE);
				return;
			}
		} else {
			// 这里表示如果当前页面是登陆页面，跳转到登陆页面
			filterChain.doFilter(request, response);
			return;
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
package pt.onept.mei.is1920.mybay.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SessionFilter", urlPatterns = {"*.xhtml"})
public class SessionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

	private SessionFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		try {
			logger.info("Filtering Request");
			logger.debug("Filtering: request " + request.toString() + " " + response.toString());
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			HttpSession httpSession = httpServletRequest.getSession(false);

			String requestURI = httpServletRequest.getRequestURI();

			if (
					requestURI.contains("/login.xhtml") ||
							requestURI.contains("/signup.xhtml") ||
							(httpSession != null && httpSession.getAttribute("email") != null)
			) {
				logger.debug("Chaining filters for " + requestURI);
				chain.doFilter(request, response);

			}
			else {
				logger.debug("Request for " + requestURI + " not accepted");
				httpServletResponse.sendRedirect("/myBay");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

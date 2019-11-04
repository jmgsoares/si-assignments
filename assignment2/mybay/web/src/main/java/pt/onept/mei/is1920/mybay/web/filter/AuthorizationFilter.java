package pt.onept.mei.is1920.mybay.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {
    private AuthorizationFilter(){}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);

            String reqURI = req.getRequestURI();

            if (reqURI.contains("/login.xhtml") || reqURI.contains("/signup.xhtml") || (ses != null && ses.getAttribute("email") != null))
                chain.doFilter(request, response);
            else
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}

package pt.onept.mei.is1920.mybay.web;

import pt.onept.mei.is1920.mybay.data.type.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));

        /*user = UserDAO.login(user);

        if (user.isValid()) {

            HttpSession session = request.getSession(true);
            session.setAttribute("currentSessionUser",user);
            response.sendRedirect("userLogged.jsp"); //logged-in page
        }

        else
            response.sendRedirect("invalidLogin.jsp"); //error page*/
    }

}

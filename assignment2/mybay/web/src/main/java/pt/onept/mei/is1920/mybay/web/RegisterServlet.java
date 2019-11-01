package pt.onept.mei.is1920.mybay.web;

import pt.onept.mei.is1920.mybay.data.type.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setCountry(request.getParameter("country"));
    }
}

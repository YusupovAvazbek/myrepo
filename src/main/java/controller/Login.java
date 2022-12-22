package controller;

import model.User;
import service.DbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        DbService dbService=new DbService();
        User user = dbService.LoginUser(username, password);
        PrintWriter printWriter=resp.getWriter();

        if(user==null){
            printWriter.write("<h1>username or password incorrect</h1>");
        } else {
            Cookie cookie=new Cookie("appAuth",user.getUsername());
            cookie.setMaxAge(60*15);
            resp.addCookie(cookie);
            resp.sendRedirect("/cabinet");
        }

    }
}

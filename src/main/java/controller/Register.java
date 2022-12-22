package controller;

import model.Result;
import model.User;
import service.DbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(value = "/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register1.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String prePassword = req.getParameter("prePassword");
        PrintWriter printWriter = resp.getWriter();
        if(username.equals("") && password.equals("") && prePassword.equals("")){
            printWriter.write("Maydonlar bo`sh bo`lmasligi kerak");
        }else if(password.equals(prePassword)){
            DbService dbService=new DbService();
            User user=new User(username,password);
            Result result = dbService.registerUser(user);
            if(result.isSuccess()){
                printWriter.write("<h1 color='green'>" + result.getMessage() + "</h1>");
            } else {
                printWriter.write("<h1 color='red'>" + result.getMessage() + "</h1>");
            }
        } else {
            printWriter.write("<h1> color='red'>"+"password and prepassword not equals"+"</h1>");
        }
    }
}

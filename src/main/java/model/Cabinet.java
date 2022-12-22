package model;

import service.DbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(value = "/cabinet")
public class Cabinet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        Cookie [] cookies= req.getCookies();
        String username="";
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("appAuth")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        DbService dbService=new DbService();
        User user = dbService.LoadUser(username);
        if (user==null){
            Cookie cookie=new Cookie("appAuth","");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        }else {
            printWriter.write("<h1>Welcome Cabinet</h1>");
        }

    }
}

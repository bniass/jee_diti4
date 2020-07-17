package controller;

import model.Utilisateur;
import service.IUser;
import service.UserDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    @EJB
    private IUser userDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("username");
        String pwd = request.getParameter("password");

        Utilisateur u = userDAO.findUser(login, pwd);

        if(u != null){
            response.sendRedirect("medecin?action=add");
        }
        else{
            request.setAttribute("errorLogin", "Login ou password incorrect");
            getServletContext().getRequestDispatcher("/WEB-INF/index.jsp")
                    .forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("info", "Bienvenu");
        getServletContext().getRequestDispatcher("/WEB-INF/index.jsp")
                .forward(request, response);
    }
}

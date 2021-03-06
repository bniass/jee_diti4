package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Medecin;
import model.Specialite;
import service.IMedecin;
import service.IService;
import service.ISpecialite;
import service.MedecinDAO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MedecinServlet", urlPatterns = "/medecin")
public class MedecinServlet extends HttpServlet {
    @EJB
    private IMedecin medecinEJB;
    @EJB
    private IService serviceEJB;
    @EJB
    private ISpecialite specialiteEJB;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IMedecin im = new MedecinDAO();
        String action = request.getParameter("action");
        if(action != null) {
            Medecin medecin = null;
            if(action.equals("add") || action.equals("update")) {
                if (action.equals("add")) {
                    medecin = new Medecin();
                } else {
                    int medecin_id = Integer.parseInt(request.getParameter("medecinid"));
                    medecin = medecinEJB.findById(medecin_id);
                }
                medecin.setAdresse(request.getParameter("adresse"));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    medecin.setDatenaissance(df.parse(request.getParameter("datenais")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                medecin.setEmail(request.getParameter("email"));
                medecin.setNom(request.getParameter("nom"));
                medecin.setPrenom(request.getParameter("prenom"));
                medecin.setTel(request.getParameter("tel"));
            }
            switch (action) {
                case "add":
                    try {
                        int service_id = Integer.parseInt(request.getParameter("service"));
                        medecin.setService(serviceEJB.findById(service_id));
                        String[] spectialites = request.getParameterValues("specialite");
                        List<Specialite> lesSpecialites = new ArrayList<>();
                        for (String sp : spectialites) {
                            lesSpecialites.add(specialiteEJB.findById(Integer.parseInt(sp)));
                        }
                        medecin.setSpecialites(lesSpecialites);
                        medecinEJB.save(medecin);
                        response.sendRedirect("medecin?action=add");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "update":
                    medecinEJB.save(medecin);
                    response.sendRedirect("medecin?action=add");
                    break;
                case "transfert":
                    int medecin_id = Integer.parseInt(request.getParameter("medecinid"));
                    medecin = medecinEJB.findById(medecin_id);
                    int service_id = Integer.parseInt(request.getParameter("service"));
                    medecin.setService(serviceEJB.findById(service_id));
                    medecinEJB.save(medecin);
                    response.sendRedirect("medecin?action=add");
                    break;
                case "managespecialite":
                    medecin_id = Integer.parseInt(request.getParameter("medecinid"));
                    medecin = medecinEJB.findById(medecin_id);
                    String[] spectialites = request.getParameterValues("specialite");
                    List<Specialite> lesSpecialites = new ArrayList<>();
                    for (String sp : spectialites) {
                        lesSpecialites.add(specialiteEJB.findById(Integer.parseInt(sp)));
                    }
                    medecin.setSpecialites(lesSpecialites);
                    medecinEJB.save(medecin);
                    response.sendRedirect("medecin?action=add");
                    break;
                case "delete":
                    medecin_id = Integer.parseInt(request.getParameter("idmedecin"));
                    medecin = medecinEJB.findById(medecin_id);
                    medecinEJB.delete(medecin);
                    response.sendRedirect("medecin?action=add");
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action != null){
            switch (action){
                case "add" :
                    request.setAttribute("services", serviceEJB.findAll());
                    request.setAttribute("medecins", medecinEJB.findAll());
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp")
                            .forward(request, response);
                    break;
                case "findspecialites" :
                    int service_id = Integer.parseInt(request.getParameter("service_id"));
                    List<Specialite> specialites = specialiteEJB.findSpecialiteByServiceId(service_id);
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonData = mapper.writeValueAsString(specialites);
                    response.getWriter().println(jsonData);
                    break;
                case "findemploye" :
                    int emp_id = Integer.parseInt(request.getParameter("emp_id"));
                    Medecin medecin = medecinEJB.findById(emp_id);
                    mapper = new ObjectMapper();
                    jsonData = mapper.writeValueAsString(medecin);
                    response.getWriter().println(jsonData);
                    break;
            }
        }
    }
}

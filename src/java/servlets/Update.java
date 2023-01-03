/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Clients;
import repositories.AdressService;
import repositories.ClientService;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@WebServlet(name = "Update", urlPatterns = {"/update"})
public class Update extends HttpServlet {
    @EJB
    ClientService clientService;
    @EJB
    AdressService adressService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String idString = request.getParameter("id");
        int id = Integer.valueOf(idString);
        Clients client = clientService.findById(id);
        String clientname = client.getClientname();
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Update</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Update</h1>");
            out.println("<form action=\"update\" method=\"POST\">");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\" />");
            out.println("<input type=\"text\" name=\"clientname\" value=\"" + clientname + "\" /><br>Разрешены символы: а-я, А-Я,-,.<br>");
            out.println("<h5>Физлицо или юрлицо?</h5>");
            out.println("<p>");
            out.println("<input type=\"radio\" value=\"физлицо\" checked name=\"clienttype\"/>физлицо");
            out.println("</p>");
            out.println("<p>");
            out.println("<input type=\"radio\" value=\"юрлицо\" name=\"clienttype\"/>юрлицо");
            out.println("</p>");
            out.println("<p>");
            out.println("<input type=\"submit\" value=\"update\" formaction=\"update\" formmethod=\"POST\" />");
            out.println("</p>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String idStr = Objects.toString(request.getParameter("id"), "");
        int id = Integer.valueOf(idStr);
        String clientname = Objects.toString(request.getParameter("clientname"), "");
        String clienttype = Objects.toString(request.getParameter("clienttype"), "");
        Clients client = clientService.findById(id);
        client.setClientname(clientname);
        client.setClienttype(clienttype);
        //            client.setRegdate(regdate);
        clientService.updateClient(client);
        response.sendRedirect("http://localhost:8080/J200_Lab1/viewlist");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

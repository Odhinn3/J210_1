
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Adresses;
import models.Clients;
import repositories.ClientService;
import repositories.AdressService;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@WebServlet(name = "ViewList", urlPatterns = {"/viewlist"})
public class ViewList extends HttpServlet {
    
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        List<Clients> clients = clientService.findAllClients();
        String text = request.getParameter("search");
        System.out.println(text);
        clients = filterList(clientService.findAllClients(), text);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Clients table</title> <meta charset=\"UTF-8\">");
//            out.println("<link href=\"URL_адрес_CSS_файла\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("</head>");
            out.println("<body>");
//            out.println("<h1>Client table " + request.getContextPath() + "</h1>");
            out.println("<form action=\"viewlist\" method=\"GET\">");
            out.println("<input type=\"text\" name=\"search\" value=\"" + text + "\" />");
            out.println("<input type=\"submit\" value=\"Search\" /><br><br>");
            out.println("</form>");
            out.println("<form action=\"\" method=\"POST\">");
            out.println("<input type=\"submit\" value=\"Create new client\" />");
            out.println("</form>");
            out.println("<div>");
            out.println("<table border=\"2\">");
            out.println("<caption><b>Clients</b></caption>");   
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th colspan=\"7\" >Clients</th>");
            out.println("<th colspan=\"7\" >Network gears</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th colspan=\"3\" ></th>");
            out.println("<th>id</th>");
            out.println("<th>clientname</th>");
            out.println("<th>clienttype</th>");
            out.println("<th>regdate</th>");
            out.println("<th>adressid</th>");
            out.println("<th>ip</th>");
            out.println("<th>mac</th>");
            out.println("<th>model</th>");
            out.println("<th>localaddress</th>");
            out.println("<th></th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("</div>");
            out.println("<tbody>");
            if(!clients.isEmpty()){
                for(Clients client : clients){
                    Integer id = client.getClientid();
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    out.println("<tr>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" +  "<a href=\"http://localhost:8080/J200_Lab1/createadress?id=" + id + "\">Create adress</a>" + "</td>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" +  "<a href=\"http://localhost:8080/J200_Lab1/update?id=" + id + "\">Update</a>" + "</td>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" +  "<a href=\"http://localhost:8080/J200_Lab1/delete?id=" + id + "\">Delete</a>" + "</td>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" + id + "</td>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" + client.getClientname() + "</td>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" + client.getClienttype() + "</td>");
                    out.println("<td rowspan=" + client.getAdressesList().size() + ">" + sdf.format(client.getRegdate()) + "</td>");
                    if(!client.getAdressesList().isEmpty()){
                        for(Adresses adress : client.getAdressesList()){
                            int adressid = adress.getAdressid();
                            out.println("<td>" + adressid + "</td>");
                            out.println("<td>" + adress.getIp() + "</td>");
                            out.println("<td>" + adress.getMac() + "</td>");
                            out.println("<td>" + adress.getModel() + "</td>");
                            out.println("<td>" + adress.getLocationadress() + "</td>");
                            out.println("<td>" + "<a href=\"http://localhost:8080/J200_Lab1/updateadress?id=" + adressid + "\">Update adress</a>" + "</td>");
                            out.println("<td>" + "<a href=\"http://localhost:8080/J200_Lab1/deleteadress?id=" + adressid + "\">Delete adress</a>" + "</td>");
                            out.println("</tr>");
                        } 
                    } else {
                        out.println("<td colspan = \"6\"></td>");
                    } out.println("</tr>");
                    out.println("</div>");
                } 
            }
            out.println("</tbody>");
            out.println("</table>");  
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
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
        response.sendRedirect("http://localhost:8080/J200_Lab1/create");
    }
    
    private List<Clients> filterList(List<Clients> clients, String text){
        if(clients==null||clients.isEmpty()||text==null||text.isEmpty()){
            return clients;
        }
        System.out.println("filtering coll");
        clients = clients.stream().filter(e ->
                (e.getClientname().toLowerCase().contains(text.toLowerCase())) ||
                (e.getClienttype().toLowerCase().contains(text.toLowerCase()))
        ).collect(Collectors.toList());
    return clients;     
    }
}

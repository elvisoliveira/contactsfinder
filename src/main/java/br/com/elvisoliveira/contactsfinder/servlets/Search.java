package br.com.elvisoliveira.contactsfinder.servlets;

import br.com.elvisoliveira.contactsfinder.model.Telelistas;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Search extends HttpServlet
{
    private ArrayList<HashMap> contacts;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String name = request.getParameter("name");

        // @TODO: validation od inputted field
        // cannot be empty
        // cannot be longer than c characters
        
        String orgm = "0";
        String cod_localidade = "31000";
        String atividade = "";
        String nome = URLEncoder.encode(name, "ISO-8859-1");
        String uf_busca = "mg";
        String imagex = "0";
        String imagey = "0";

        String url = String.format("http://www.telelistas.net/templates/resultado_busca.aspx?q=&orgm=%s&cod_localidade=%s&atividade=%s&nome=%s&uf_busca=%s&image.x=%s&image.y=%s", orgm, cod_localidade, atividade, nome, uf_busca, imagex, imagey);

        Telelistas lista;
        try
        {
            lista = new Telelistas();
            lista.getPage(url);
            contacts = lista.getContacts();
            lista.close();
        } catch (Exception ex)
        {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }

        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // encoding
        response.setContentType("text/html;charset=UTF-8");

        // parameters
        request.setAttribute("contacts", contacts);

        // dispaycher JSP/JSTL display
        RequestDispatcher view = request.getRequestDispatcher("/contactsfinder.jsp");
        view.forward(request, response);
    }
    
    // @TODO: Servlet "Approval" with a list of already registred contacts in local
    // database to approval, or rejection. 
    // if approved, mark the status in the local database and send to 
    // contactsmanager insertion waiting list
    // if rejected, mark the status in the local database
    
    // @TODO: Servlet "List" with the  blacklisted and whitelisted names, 
    // interect the new insertions wth the approved or rejected names
    
    // @TODO: Servlet "Machine", that automatically gets the last appoved oendent name
    // in the white list and mmake the search automatically.
}

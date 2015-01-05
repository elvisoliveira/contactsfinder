package br.com.elvisoliveira.contactsfinder.servlets;

import br.com.elvisoliveira.contactsfinder.model.Telelistas;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Busca extends HttpServlet
{

    private String baseUrl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String orgm = "0";
        String cod_localidade = "31000";
        String atividade = "";
        String nome = URLEncoder.encode("jesse", "ISO-8859-1");
        String uf_busca = "mg";
        String imagex = "0";
        String imagey = "0";

        String url = String.format("http://www.telelistas.net/templates/resultado_busca.aspx?q=&orgm=%s&cod_localidade=%s&atividade=%s&nome=%s&uf_busca=%s&image.x=%s&image.y=%s", orgm, cod_localidade, atividade, nome, uf_busca, imagex, imagey);

        Telelistas lista = new Telelistas();
        lista.getPage(url);
        ArrayList<HashMap> contacts = lista.getContacts();
        lista.close();

        request.setAttribute("contacts", contacts);
        RequestDispatcher view = request.getRequestDispatcher("/contactsfinder.jsp");
        view.forward(request, response);
    }

}

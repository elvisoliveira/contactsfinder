package br.com.elvisoliveira.contactsfinder.servlets;

import br.com.elvisoliveira.contactsfinder.dao.ContactsModel;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Contacts extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ContactsModel contact = new ContactsModel();
        contact.getAll();
    }

}

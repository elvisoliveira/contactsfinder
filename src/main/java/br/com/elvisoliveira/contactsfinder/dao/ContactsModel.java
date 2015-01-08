package br.com.elvisoliveira.contactsfinder.dao;

import br.com.elvisoliveira.contactsfinder.beans.ContactBean;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsModel
{

    private String message;

    public void getAll()
    {
        try
        {
            SQLite db = new SQLite();
            try (ResultSet rs = db.stm.executeQuery("SELECT * FROM contacts"))
            {
                if (!rs.next())
                {
                    System.out.println("No records found");
                }
                else
                {
                    do
                    {
                        System.out.println(rs.getString("name"));
                    }
                    while (rs.next());
                }
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Boolean setContact(ContactBean contact)
    {
        try
        {
            String sql = "INSERT INTO \"contacts\" (\"con_id\", "
                    + " \"con_name\", "
                    + " \"con_address\", "
                    + " \"con_city\", "
                    + " \"con_province\") "
                    + " \"con_status\") "
                    + " \"con_approval\") "
                    + " VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"); ";

            String query = String.format(sql,
                    contact.getId(),
                    contact.getName(),
                    contact.getAddress(),
                    contact.getCity(),
                    contact.getProvince(),
                    contact.getStatus(),
                    contact.getApproval());

            SQLite db = new SQLite();

            Integer rs = db.stm.executeUpdate(query);

            return true;
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            this.setMessage(ex.getMessage());
            return false;
        }
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}

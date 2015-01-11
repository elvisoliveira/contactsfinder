package br.com.elvisoliveira.contactsfinder.dao;

import java.sql.*;

public class SQLite
{

    private final Connection conn;
    public final Statement stm;

    public SQLite() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.sqlite.JDBC");

        String OS = System.getProperty("os.name");
        String connection;

        if (OS.startsWith("Windows"))
        {
            connection = "jdbc:sqlite:c:/database.db";
        }
        else
        {
            connection = "jdbc:sqlite:/database.db";
        }

        this.conn = DriverManager.getConnection(connection);

        this.stm = this.conn.createStatement();
    }
}

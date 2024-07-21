package com.platinum.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String direccion = request.getParameter("direccion");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Cuentas_clientes", "root", "password");

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO persona (rut, nombre, apellido, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, request.getParameter("rut"));
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, direccion);
            ps.setString(5, correo);
            ps.setString(6, telefono);

            int i = ps.executeUpdate();

            if (i > 0) {
                out.println("Registration Successful!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Database connection error!");
        } finally {
            out.close();
        }
    }
}

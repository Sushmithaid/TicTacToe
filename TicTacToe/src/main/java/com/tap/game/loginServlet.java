package com.tap.game;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tap.game.connector_factory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/log-in")
public class loginServlet extends HttpServlet
{
	private Connection con;
	private PreparedStatement pstmt;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
	    String email = req.getParameter("email");
	    String password = req.getParameter("password");

	    try 
	    {
	        con = connector_factory.requestConnection();
	        
	        // Check if email and password exist in DB
	        String query = "select * from signup where email = ? and password = ?";
	        pstmt = con.prepareStatement(query);
	        pstmt.setString(1, email);
	        pstmt.setString(2, password);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("Login Success for: " + rs.getString("email"));
	            resp.sendRedirect("home.jsp");
	        }

	        else 
	        {
	        	System.out.println("Login Failed - No matching user");
	            // ❌ Login failed -> stay on login page with error
	            req.setAttribute("errorMessage", "Invalid Email or Password!");
	            req.getRequestDispatcher("login.jsp").forward(req, resp);
	        }

	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}

	

}
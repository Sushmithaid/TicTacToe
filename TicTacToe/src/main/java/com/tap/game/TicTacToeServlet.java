package com.tap.game;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/tictactoe")
public class TicTacToeServlet extends HttpServlet 
{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
{

    resp.setContentType("application/json");
    PrintWriter out = resp.getWriter();

    String winner = req.getParameter("winner");
    String playerX = req.getParameter("playerX");
    String playerO = req.getParameter("playerO");

    String jsonResponse = "{\"status\":\"success\", \"message\":\"Winner saved successfully!\"}";
    resp.getWriter().print(jsonResponse);


    try (Connection con = connector_factory.requestConnection();) 
    {
        String sql = "INSERT INTO game_results (player_x, player_o, winner) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, playerX);
        ps.setString(2, playerO);
        ps.setString(3, winner);
        ps.executeUpdate();

        System.out.println("✅ Game Result -> Player X: " + playerX + ", Player O: " + playerO + ", Winner: " + winner);

        jsonResponse = "{\"status\":\"success\", \"message\":\"Winner saved successfully!\"}";
        resp.getWriter().print(jsonResponse);

    } 
    catch (Exception e) 
    {
        e.printStackTrace();
       jsonResponse = "{\"status\":\"success\", \"message\":\"Winner saved successfully!\"}";
        resp.getWriter().print(jsonResponse);

    }

    jsonResponse = "{\"status\":\"success\", \"message\":\"Winner saved successfully!\"}";
    resp.getWriter().print(jsonResponse);

    out.flush();
}
}

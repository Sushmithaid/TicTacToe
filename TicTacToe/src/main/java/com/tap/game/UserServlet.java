package com.tap.game;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.tap.game.connector_factory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sign-up")
public class UserServlet extends HttpServlet {
    private Connection con;
    private PreparedStatement pstmt;
    private int res;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Password and confirm password validation
        if (!password.equals(confirmPassword)) {
            // Send JavaScript alert and redirect back to signup page
            resp.setContentType("text/html");
            resp.getWriter().println("<script type='text/javascript'>");
            resp.getWriter().println("alert('Passwords do not match! Please try again.');");
            resp.getWriter().println("window.location.href='signup.jsp';");
            resp.getWriter().println("</script>");
            return;
        }

        try {
            con = connector_factory.requestConnection();
            String insert = "INSERT INTO signup(username, email, password, confirmPassword) VALUES(?, ?, ?, ?)";
            pstmt = con.prepareStatement(insert);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, confirmPassword);

            res = pstmt.executeUpdate();

            if (res > 0) {
                HttpSession session = req.getSession();
                session.setAttribute("userEmail", email);
                session.setAttribute("userName", username); // corrected — should store username, not password
                resp.sendRedirect("login.jsp");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

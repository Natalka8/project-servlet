package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RestartServlet", value = "/restart")
public class RestartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Secure session disability
        HttpSession session = req.getSession(false);
        if (session != null) {
            try {
                session.invalidate();
            } catch (IllegalStateException e) {
                // The session was already invalid - we continue
                System.out.println("Session already invalidated");
            }
        }

        // We always redirect you to the start page.
        resp.sendRedirect(req.getContextPath() + "/tic-tac-toe/start");
    }
}

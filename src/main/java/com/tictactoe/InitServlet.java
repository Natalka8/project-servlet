package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Creating a new session
        HttpSession currentSession = req.getSession(true);

        // Creating a playing field
        Field field = new Field();
        Map<Integer, Sign> fieldData = field.getField();

        // Getting a list of field values
        List<Sign> data = field.getFieldData();

        // Adding field parameters to the session (it will be necessary to store the state between requests)
        currentSession.setAttribute("field", field);
        // and field values sorted by index (needed for drawing tic-tac-toe)
        currentSession.setAttribute("data", data);

        // Redirecting the request to the index page.jsp via the server
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
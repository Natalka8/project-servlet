package com.tictactoe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();

        try {
            Field field = extractField(currentSession);
            int index = getSelectedIndex(req);

            // 1. Checking the validity of the move
            if (!isValidMove(field, index)) {
                redirectToIndex(req, resp);
                return;
            }

            // 2. Player's move (cross)
            field.getField().put(index, Sign.CROSS);

            // 3. Checking the victory of the cross
            if (checkWinAndRedirect(currentSession, field, resp)) {
                return;
            }

            // 4.  Checking the draw
            if (isDraw(field)) {
                handleDraw(currentSession, field, resp);
                return;
            }

            // 5. Computer stroke (zero)
            int emptyIndex = field.getEmptyFieldIndex();
            if (emptyIndex >= 0) {
                field.getField().put(emptyIndex, Sign.NOUGHT);

                // 6. Checking the Zero's victory
                if (checkWinAndRedirect(currentSession, field, resp)) {
                    return;
                }
            }

            // 7. Updating the game status
            updateGameState(currentSession, field);
            redirectToIndex(req, resp);

        } catch (RuntimeException e) {
            handleSessionError(currentSession, req, resp);
        }
    }

    // Basic gaming techniques
    private boolean isValidMove(Field field, int index) {
        return index >= 0 && index < 9 && field.getField().get(index) == Sign.EMPTY;
    }

    private boolean checkWinAndRedirect(HttpSession session, Field field,
                                        HttpServletResponse resp) throws IOException {
        Sign winner = field.checkWin();
        if (winner != Sign.EMPTY) {
            session.setAttribute("winner", winner);
            updateGameState(session, field);
            resp.sendRedirect(session.getServletContext().getContextPath() + "/index.jsp");
            return true;
        }
        return false;
    }

    private boolean isDraw(Field field) {
        return field.getEmptyFieldIndex() == -1;
    }

    // Game State processing
    private void handleDraw(HttpSession session, Field field,
                            HttpServletResponse resp) throws IOException {
        session.setAttribute("draw", true);
        updateGameState(session, field);
        resp.sendRedirect(session.getServletContext().getContextPath() + "/index.jsp");
    }

    private void updateGameState(HttpSession session, Field field) {
        session.setAttribute("data", field.getFieldData());
        session.setAttribute("field", field);
    }

    // Auxiliary methods
    private void redirectToIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    private void handleSessionError(HttpSession session, HttpServletRequest req,
                                    HttpServletResponse resp) throws IOException {
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/start");
    }

    private int getSelectedIndex(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("click"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Field extractField(HttpSession session) {
        Object field = session.getAttribute("field");
        if (!(field instanceof Field)) {
            session.invalidate();
            throw new RuntimeException("Invalid session state");
        }
        return (Field) field;
    }
}
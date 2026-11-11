package com.mycompany.gestionmemoire.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/themes/review")
public class ThemeReviewServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!SessionUtil.connecter(
        request, response, SessionUtil.TEACHER, SessionUtil.PROGRAM_MANAGER, SessionUtil.ADMIN)) {
      return;
    }

    request.setAttribute("themes", ThemeStore.depuis(getServletContext()).listerEnAttente());
    request.getRequestDispatcher("/WEB-INF/views/review-themes.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    if (!SessionUtil.connecter(
        request, response, SessionUtil.TEACHER, SessionUtil.PROGRAM_MANAGER, SessionUtil.ADMIN)) {
      return;
    }

    long id;
    try {
      id = Long.parseLong(request.getParameter("themeId"));
    } catch (RuntimeException exception) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    String decision = request.getParameter("action");
    String note = valeur(request.getParameter("note"));
    if (!ThemeStore.depuis(getServletContext()).decider(id, decision, note)) {
      response.sendError(HttpServletResponse.SC_CONFLICT, "La proposition n’est plus en attente.");
      return;
    }

    request.getSession(false).setAttribute("message", "Décision enregistrée en mémoire.");
    response.sendRedirect(request.getContextPath() + "/themes/review");
  }

  private String valeur(String texte) {
    if (texte == null) {
      return "";
    }
    return texte.trim();
  }
}

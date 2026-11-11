package com.mycompany.gestionmemoire.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final Set<String> ROLES_AUTORISES =
      Set.of(
          SessionUtil.STUDENT, SessionUtil.TEACHER, SessionUtil.PROGRAM_MANAGER, SessionUtil.ADMIN);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String nom = valeur(request.getParameter("name"));
    String role = valeur(request.getParameter("role")).toUpperCase(Locale.ROOT);

    if (nom.isBlank() || nom.length() > 160 || !ROLES_AUTORISES.contains(role)) {
      request.setAttribute("erreur", "Saisissez un nom et sélectionnez un rôle.");
      request.setAttribute("nom", nom);
      doGet(request, response);
      return;
    }

    HttpSession ancienneSession = request.getSession(false);
    if (ancienneSession != null) {
      ancienneSession.invalidate();
    }

    HttpSession session = request.getSession(true);
    session.setAttribute("userName", nom);
    session.setAttribute("role", role);
    session.setAttribute("demoLogin", Boolean.TRUE);
    response.sendRedirect(request.getContextPath() + "/dashboard");
  }

  private String valeur(String texte) {
    return texte == null ? "" : texte.trim();
  }
}

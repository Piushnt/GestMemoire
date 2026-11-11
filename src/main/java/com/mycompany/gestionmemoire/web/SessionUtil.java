package com.mycompany.gestionmemoire.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public final class SessionUtil {
  public static final String ADMIN = "ADMIN";
  public static final String PROGRAM_MANAGER = "PROGRAM_MANAGER";
  public static final String TEACHER = "TEACHER";
  public static final String STUDENT = "STUDENT";

  private SessionUtil() {}

  public static boolean aRole(HttpServletRequest request, String role) {
    HttpSession session = request.getSession(false);
    return session != null && role.equals(session.getAttribute("role"));
  }

  public static boolean connecter(
      HttpServletRequest request, HttpServletResponse response, String... roles)
      throws IOException {
    HttpSession session = request.getSession(false);
    if (session == null
        || !Boolean.TRUE.equals(session.getAttribute("demoLogin"))
        || session.getAttribute("userName") == null) {
      response.sendRedirect(request.getContextPath() + "/login");
      return false;
    }

    String roleActuel = (String) session.getAttribute("role");
    for (String role : roles) {
      if (role.equals(roleActuel)) {
        return true;
      }
    }

    response.sendError(HttpServletResponse.SC_FORBIDDEN);
    return false;
  }
}

package com.mycompany.gestionmemoire.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionUtil {
    public static final String ADMIN = "ADMIN";
    public static final String PROGRAM_MANAGER = "PROGRAM_MANAGER";
    public static final String TEACHER = "TEACHER";
    public static final String STUDENT = "STUDENT";

    private SessionUtil() {
    }

    public static boolean aRole(HttpServletRequest request, String role) {
        HttpSession session = request.getSession(false);
        return session != null && role.equals(session.getAttribute("role"));
    }

    public static boolean connecter(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, String... roles) throws java.io.IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        String roleActuel = (String) session.getAttribute("role");
        for (String role : roles) {
            if (role.equals(roleActuel)) {
                return true;
            }
        }
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
}

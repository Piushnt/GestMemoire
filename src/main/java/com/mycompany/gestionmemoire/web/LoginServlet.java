package com.mycompany.gestionmemoire.web;

import com.mycompany.entities.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  @PersistenceContext(unitName = "my_persistence_unit")
  private EntityManager em;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null) {
      String message = (String) session.getAttribute("message");
      if (message != null) {
        request.setAttribute("message", message);
        session.removeAttribute("message");
      }
    }
    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String email = request.getParameter("email");
    String motDePasse = request.getParameter("password");
    if (email == null || motDePasse == null || email.isBlank() || motDePasse.isBlank()) {
      request.setAttribute("erreur", "Saisissez votre adresse e-mail et votre mot de passe.");
      doGet(request, response);
      return;
    }
    List<Users> resultats =
        em.createNamedQuery("Users.findByEmail", Users.class)
            .setParameter("email", email.trim().toLowerCase())
            .getResultList();
    if (resultats.isEmpty()) {
      request.setAttribute("erreur", "Identifiants incorrects ou compte inactif.");
      doGet(request, response);
      return;
    }
    Users user = resultats.get(0);
    String hash = user.getPasswordHash();
    boolean motDePasseValide = false;
    try {
      motDePasseValide = user.getActive() && hash != null && BCrypt.checkpw(motDePasse, hash);
    } catch (IllegalArgumentException exception) {
      motDePasseValide = false;
    }
    if (!motDePasseValide || !roleConnu(user.getRole())) {
      request.setAttribute("erreur", "Identifiants incorrects ou compte inactif.");
      doGet(request, response);
      return;
    }
    HttpSession ancienneSession = request.getSession(false);
    if (ancienneSession != null) {
      ancienneSession.invalidate();
    }
    HttpSession session = request.getSession(true);
    session.setAttribute("userId", user.getId());
    session.setAttribute("userName", user.getFullName());
    session.setAttribute("role", user.getRole().toUpperCase());
    session.setAttribute(
        "programId", user.getProgramId() == null ? null : user.getProgramId().getId());
    response.sendRedirect(request.getContextPath() + "/dashboard");
  }

  private boolean roleConnu(String role) {
    if (role == null) {
      return false;
    }
    String valeur = role.toUpperCase();
    return valeur.equals(SessionUtil.ADMIN)
        || valeur.equals(SessionUtil.PROGRAM_MANAGER)
        || valeur.equals(SessionUtil.TEACHER)
        || valeur.equals(SessionUtil.STUDENT);
  }
}

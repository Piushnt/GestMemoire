package com.mycompany.gestionmemoire.web;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
  @EJB private RegistrationService registrationService;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String nom = valeur(request.getParameter("fullName"));
    String email = valeur(request.getParameter("email"));
    String motDePasse = request.getParameter("password");
    String role = request.getParameter("role");
    if (nom.isBlank()
        || nom.length() > 160
        || email.isBlank()
        || email.length() > 254
        || !emailValide(email)
        || !RegistrationService.motDePasseValide(motDePasse)
        || !SessionUtil.STUDENT.equals(role)) {
      request.setAttribute(
          "erreur",
          "Vérifiez les champs : nom, e-mail valide, rôle étudiant et mot de passe d’au moins 8"
              + " caractères.");
      request.setAttribute("fullName", nom);
      request.setAttribute("email", email);
      doGet(request, response);
      return;
    }
    try {
      if (!registrationService.enregistrerEtudiant(nom, email, motDePasse)) {
        request.setAttribute("erreur", "Cette adresse e-mail est déjà utilisée.");
        request.setAttribute("fullName", nom);
        request.setAttribute("email", email);
        doGet(request, response);
        return;
      }
    } catch (EJBException exception) {
      request.setAttribute(
          "erreur",
          "L’inscription est indisponible pour le moment. Vérifiez la connexion à la base de"
              + " données.");
      request.setAttribute("fullName", nom);
      request.setAttribute("email", email);
      doGet(request, response);
      return;
    }
    request
        .getSession(true)
        .setAttribute(
            "message", "Votre compte étudiant est créé. Vous pouvez maintenant vous connecter.");
    response.sendRedirect(request.getContextPath() + "/login");
  }

  private String valeur(String texte) {
    return texte == null ? "" : texte.trim();
  }

  private boolean emailValide(String email) {
    int arobase = email.indexOf('@');
    return arobase > 0
        && arobase == email.lastIndexOf('@')
        && email.indexOf('.', arobase) > arobase + 1
        && !email.endsWith(".");
  }
}

package com.mycompany.gestionmemoire.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/themes/proposer")
public class ThemeProposalServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (!SessionUtil.connecter(request, response, SessionUtil.STUDENT)) {
      return;
    }
    request.getRequestDispatcher("/WEB-INF/views/proposer-theme.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    if (!SessionUtil.connecter(request, response, SessionUtil.STUDENT)) {
      return;
    }

    String title = valeur(request.getParameter("title"));
    String description = valeur(request.getParameter("description"));
    String keywords = valeur(request.getParameter("keywords"));
    Set<String> distinctKeywords = JaccardUtil.motsCles(keywords);

    if (title.isBlank()
        || title.length() > 180
        || description.isBlank()
        || description.length() > 4000
        || distinctKeywords.size() < 3
        || distinctKeywords.size() > 5) {
      request.setAttribute(
          "erreur", "Saisissez un titre, une description et de 3 à 5 mots-clés distincts.");
      request.setAttribute("title", title);
      request.setAttribute("description", description);
      request.setAttribute("keywords", keywords);
      doGet(request, response);
      return;
    }

    String comparisonText = title + " " + description + " " + keywords;
    ThemeProposal proposal =
        ThemeStore.depuis(getServletContext())
            .ajouter(
                title,
                description,
                keywords,
                comparisonText,
                (String) request.getSession(false).getAttribute("userName"));
    request
        .getSession(false)
        .setAttribute(
            "message",
            "Proposition "
                + proposal.getId()
                + " conservée en mémoire. Similarité maximale : "
                + String.format(java.util.Locale.ROOT, "%.2f", proposal.getSimilarityPercent())
                + " %. Statut : "
                + proposal.getStatus()
                + ".");
    response.sendRedirect(request.getContextPath() + "/themes/proposer");
  }

  private String valeur(String texte) {
    return texte == null ? "" : texte.trim();
  }
}

package com.mycompany.gestionmemoire.web;

import com.mycompany.entities.AcademicYears;
import com.mycompany.entities.Themes;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/themes/proposer")
public class ThemeProposalServlet extends HttpServlet {
  private static final double SEUIL_SIMILARITE = 40.0;

  @PersistenceContext(unitName = "my_persistence_unit")
  private EntityManager em;

  @EJB private ThemeService themeService;

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
    String titre = valeur(request.getParameter("title"));
    String description = valeur(request.getParameter("description"));
    String motsCles = valeur(request.getParameter("keywords"));
    Set<String> mots = JaccardUtil.motsCles(motsCles);
    if (titre.isBlank()
        || titre.length() > 180
        || description.isBlank()
        || mots.size() < 3
        || mots.size() > 5) {
      request.setAttribute(
          "erreur",
          "Le titre (180 caractères maximum), la description et 3 à 5 mots-clés distincts sont"
              + " obligatoires.");
      doGet(request, response);
      return;
    }
    if (Boolean.TRUE.equals(request.getSession(false).getAttribute("demoLogin"))) {
      List<String> themesExemples =
          new ArrayList<>(
              List.of(
                  "java programmation web sécurité",
                  "application mobile gestion données",
                  "intelligence artificielle apprentissage automatique"));
      Object themesSession = request.getSession(false).getAttribute("demoThemes");
      if (themesSession instanceof List<?>) {
        for (Object theme : (List<?>) themesSession) {
          if (theme instanceof String) {
            themesExemples.add((String) theme);
          }
        }
      }

      double similariteMax = 0.0;
      for (String themeExistant : themesExemples) {
        similariteMax = Math.max(similariteMax, JaccardUtil.calculer(motsCles, themeExistant));
      }

      List<String> themesSessionModifies = new ArrayList<>();
      Object themesPrecedents = request.getSession(false).getAttribute("demoThemes");
      if (themesPrecedents instanceof List<?>) {
        for (Object theme : (List<?>) themesPrecedents) {
          if (theme instanceof String) {
            themesSessionModifies.add((String) theme);
          }
        }
      }
      themesSessionModifies.add(String.join(", ", mots));
      request.getSession(false).setAttribute("demoThemes", themesSessionModifies);

      BigDecimal similarite = BigDecimal.valueOf(similariteMax).setScale(2, RoundingMode.HALF_UP);
      String statut = similariteMax >= SEUIL_SIMILARITE ? "ARBITRATION" : "SUBMITTED";
      request
          .getSession(false)
          .setAttribute(
              "message",
              "Mode simulation — indice de Jaccard maximal : "
                  + similarite.toPlainString()
                  + " %. Statut indicatif : "
                  + statut
                  + ". Rien n’a été enregistré dans la base.");
      response.sendRedirect(request.getContextPath() + "/themes/proposer");
      return;
    }
    Long programId = (Long) request.getSession(false).getAttribute("programId");
    if (programId == null) {
      request.setAttribute("erreur", "Aucune filière n’est associée à votre compte.");
      doGet(request, response);
      return;
    }
    List<AcademicYears> annees =
        em.createQuery("SELECT a FROM AcademicYears a WHERE a.current = true", AcademicYears.class)
            .setMaxResults(1)
            .getResultList();
    if (annees.isEmpty()) {
      request.setAttribute("erreur", "Aucune année académique active n’est configurée.");
      doGet(request, response);
      return;
    }
    List<Themes> themesExistants =
        em.createQuery("SELECT t FROM Themes t WHERE t.programId.id = :programId", Themes.class)
            .setParameter("programId", programId)
            .getResultList();
    double similariteMax = 0.0;
    for (Themes theme : themesExistants) {
      similariteMax = Math.max(similariteMax, JaccardUtil.calculer(motsCles, theme.getKeywords()));
    }
    String statut = similariteMax >= SEUIL_SIMILARITE ? "ARBITRATION" : "SUBMITTED";
    Long userId = (Long) request.getSession(false).getAttribute("userId");
    BigDecimal similarite = BigDecimal.valueOf(similariteMax).setScale(2, RoundingMode.HALF_UP);
    themeService.enregistrer(
        titre,
        description,
        String.join(", ", mots),
        similarite,
        statut,
        userId,
        programId,
        annees.get(0).getId());
    request
        .getSession(false)
        .setAttribute(
            "message",
            "Proposition enregistrée : "
                + statut
                + " (similarité maximale "
                + similarite.toPlainString()
                + " %).");
    response.sendRedirect(request.getContextPath() + "/themes/proposer");
  }

  private String valeur(String texte) {
    return texte == null ? "" : texte.trim();
  }
}

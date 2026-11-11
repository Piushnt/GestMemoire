package com.mycompany.gestionmemoire.web;

import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class ThemeStore {
  public static final String CONTEXT_ATTRIBUTE = ThemeStore.class.getName();
  private static final double SEUIL_ARBITRAGE = 40.0;

  private final List<ThemeProposal> proposals = new ArrayList<>();
  private long nextId = 1;

  public static ThemeStore depuis(ServletContext context) {
    return (ThemeStore) context.getAttribute(CONTEXT_ATTRIBUTE);
  }

  public synchronized ThemeProposal ajouter(
      String title, String description, String keywords, String comparisonText, String ownerName) {
    double maxSimilarity = 0.0;
    for (ThemeProposal existing : proposals) {
      maxSimilarity =
          Math.max(
              maxSimilarity, JaccardUtil.calculer(comparisonText, existing.getComparisonText()));
    }

    String status = maxSimilarity >= SEUIL_ARBITRAGE ? "ARBITRATION" : "SUBMITTED";
    ThemeProposal proposal =
        new ThemeProposal(
            nextId++,
            title,
            description,
            keywords,
            comparisonText,
            ownerName,
            maxSimilarity,
            status);
    proposals.add(proposal);
    return proposal;
  }

  public synchronized List<ThemeProposal> listerEnAttente() {
    List<ThemeProposal> pending = new ArrayList<>();
    for (ThemeProposal proposal : proposals) {
      if ("SUBMITTED".equals(proposal.getStatus()) || "ARBITRATION".equals(proposal.getStatus())) {
        pending.add(proposal);
      }
    }
    return pending;
  }

  public synchronized boolean decider(long id, String decision, String note) {
    if (!"APPROVED".equals(decision) && !"REJECTED".equals(decision)) {
      return false;
    }
    for (ThemeProposal proposal : proposals) {
      if (proposal.getId() == id
          && ("SUBMITTED".equals(proposal.getStatus())
              || "ARBITRATION".equals(proposal.getStatus()))) {
        proposal.setStatus(decision);
        proposal.setDecisionNote(note == null ? "" : note);
        return true;
      }
    }
    return false;
  }
}

package com.mycompany.gestionmemoire.web;

import com.mycompany.entities.AcademicYears;
import com.mycompany.entities.Programs;
import com.mycompany.entities.Themes;
import com.mycompany.entities.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;

@Stateless
public class ThemeService {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public void enregistrer(String titre, String description, String motsCles, BigDecimal similarite,
            String statut, Long userId, Long programId, Long academicYearId) {
        Themes theme = new Themes();
        theme.setTitle(titre);
        theme.setDescription(description);
        theme.setKeywords(motsCles);
        theme.setSimilarityPercent(similarite);
        theme.setStatus(statut);
        theme.setLocked(false);
        theme.setSubmittedAt(new Date());
        theme.setVersion(1);
        theme.setOwnerId(em.getReference(Users.class, userId));
        theme.setProgramId(em.getReference(Programs.class, programId));
        theme.setAcademicYearId(em.getReference(AcademicYears.class, academicYearId));
        em.persist(theme);
    }

    public boolean enregistrerDecision(Long themeId, String action, String note, Long reviewerId, Long programId) {
        Themes theme = em.find(Themes.class, themeId);
        if (theme == null || !("SUBMITTED".equals(theme.getStatus()) || "ARBITRATION".equals(theme.getStatus()))) {
            return false;
        }
        if (programId != null && !programId.equals(theme.getProgramId().getId())) {
            return false;
        }
        if (!("APPROVED".equals(action) || "REJECTED".equals(action))) {
            return false;
        }
        theme.setStatus(action);
        theme.setReviewNote(note);
        theme.setReviewedAt(new Date());
        theme.setReviewedBy(em.getReference(Users.class, reviewerId));
        em.merge(theme);
        return true;
    }
}

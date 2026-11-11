package com.mycompany.gestionmemoire.web;

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
import java.util.List;

@WebServlet("/themes/review")
public class ThemeReviewServlet extends HttpServlet {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    @EJB
    private ThemeService themeService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtil.connecter(request, response, SessionUtil.PROGRAM_MANAGER, SessionUtil.ADMIN)) {
            return;
        }
        if (SessionUtil.aRole(request, SessionUtil.PROGRAM_MANAGER)) {
            Long programId = (Long) request.getSession(false).getAttribute("programId");
            if (programId == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            List<Themes> themes = em.createQuery("SELECT t FROM Themes t JOIN FETCH t.programId WHERE t.programId.id = :programId AND t.status IN ('SUBMITTED', 'ARBITRATION') ORDER BY t.submittedAt", Themes.class)
                    .setParameter("programId", programId).getResultList();
            request.setAttribute("themes", themes);
        } else {
            List<Themes> themes = em.createQuery("SELECT t FROM Themes t JOIN FETCH t.programId WHERE t.status IN ('SUBMITTED', 'ARBITRATION') ORDER BY t.submittedAt", Themes.class)
                    .getResultList();
            request.setAttribute("themes", themes);
        }
        request.getRequestDispatcher("/WEB-INF/views/review-themes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!SessionUtil.connecter(request, response, SessionUtil.PROGRAM_MANAGER, SessionUtil.ADMIN)) {
            return;
        }
        Long id;
        try {
            id = Long.valueOf(request.getParameter("themeId"));
        } catch (RuntimeException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Themes theme = em.find(Themes.class, id);
        if (theme == null || !(theme.getStatus().equals("SUBMITTED") || theme.getStatus().equals("ARBITRATION"))) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (SessionUtil.aRole(request, SessionUtil.PROGRAM_MANAGER)) {
            Long programId = (Long) request.getSession(false).getAttribute("programId");
            if (programId == null || !programId.equals(theme.getProgramId().getId())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        String action = request.getParameter("action");
        if (!"APPROVED".equals(action) && !"REJECTED".equals(action)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Long userId = (Long) request.getSession(false).getAttribute("userId");
        Long reviewerProgramId = SessionUtil.aRole(request, SessionUtil.PROGRAM_MANAGER)
                ? (Long) request.getSession(false).getAttribute("programId") : null;
        String note = request.getParameter("note") == null ? "" : request.getParameter("note").trim();
        if (!themeService.enregistrerDecision(id, action, note, userId, reviewerProgramId)) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        request.getSession(false).setAttribute("message", "La décision a été enregistrée.");
        response.sendRedirect(request.getContextPath() + "/themes/review");
    }
}

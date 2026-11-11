<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.mycompany.gestionmemoire.web.ThemeProposal" %>
<%!
    private String echapper(String texte) {
        if (texte == null) {
            return "";
        }
        return texte.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<%
    List<ThemeProposal> themes = (List<ThemeProposal>) request.getAttribute("themes");
    String message = (String) session.getAttribute("message");
    if (message != null) {
        session.removeAttribute("message");
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Validation des thèmes | UATM-GS</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/app.css">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="<%= request.getContextPath() %>/dashboard">
            <span class="brand-mark">U</span>
            <span>UATM<span class="brand-muted">-GS</span></span>
        </a>
        <nav class="top-nav">
            <a class="nav-link" href="<%= request.getContextPath() %>/dashboard">Vue d’ensemble</a>
            <a class="nav-link active" href="<%= request.getContextPath() %>/themes/review">Validation des thèmes</a>
        </nav>
        <form method="post" action="<%= request.getContextPath() %>/logout">
            <button class="button button-quiet" type="submit">Quitter</button>
        </form>
    </header>

    <main class="page-container">
        <a class="back-link" href="<%= request.getContextPath() %>/dashboard">← Retour au tableau de bord</a>
        <section class="page-title">
            <span class="eyebrow">ESPACE DE DÉCISION</span>
            <h1>Propositions à examiner</h1>
            <p class="muted">Les propositions sont partagées en mémoire pendant l’exécution de l’application.</p>
        </section>

        <% if (message != null) { %>
            <div class="alert alert-success" role="status"><%= echapper(message) %></div>
        <% } %>
        <% if (themes == null || themes.isEmpty()) { %>
            <section class="empty-state">
                <span class="empty-icon">✓</span>
                <h2>Aucune proposition en attente</h2>
                <p>Les nouvelles propositions étudiantes apparaîtront ici.</p>
            </section>
        <% } else { %>
            <section class="review-list">
                <% for (ThemeProposal theme : themes) { %>
                    <article class="review-card">
                        <div class="review-head">
                            <div>
                                <span class="eyebrow">THÈME #<%= theme.getId() %></span>
                                <h2><%= echapper(theme.getTitle()) %></h2>
                            </div>
                            <span class="status-pill"><%= echapper(theme.getStatus()) %></span>
                        </div>
                        <p class="review-description"><%= echapper(theme.getDescription()) %></p>
                        <div class="review-meta">
                            <span><strong>Étudiant :</strong> <%= echapper(theme.getOwnerName()) %></span>
                            <span><strong>Mots-clés :</strong> <%= echapper(theme.getKeywords()) %></span>
                            <span><strong>Similarité maximale :</strong> <%= String.format(java.util.Locale.ROOT, "%.2f", theme.getSimilarityPercent()) %> %</span>
                        </div>
                        <form method="post" action="<%= request.getContextPath() %>/themes/review" class="review-form">
                            <input type="hidden" name="themeId" value="<%= theme.getId() %>">
                            <label for="note-<%= theme.getId() %>">Note de décision (facultative)</label>
                            <input id="note-<%= theme.getId() %>" name="note" type="text" maxlength="500" placeholder="Ajouter une remarque à l’étudiant">
                            <div class="form-actions">
                                <button class="button button-danger" type="submit" name="action" value="REJECTED">Rejeter</button>
                                <button class="button button-primary" type="submit" name="action" value="APPROVED">Valider</button>
                            </div>
                        </form>
                    </article>
                <% } %>
            </section>
        <% } %>

        <p class="page-foot">UATM-GS <span>•</span> Gestion des soutenances et des mémoires</p>
    </main>
</body>
</html>

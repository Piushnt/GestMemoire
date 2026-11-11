<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String nom = (String) session.getAttribute("userName");
    String role = (String) session.getAttribute("role");
    String nomAffiche = nom == null
            ? "Utilisateur"
            : nom.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    String roleAffiche = role == null ? "Compte" : role.replace('_', ' ');
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tableau de bord | UATM-GS</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/app.css">
</head>
<body>
    <header class="topbar">
        <a class="brand" href="<%= request.getContextPath() %>/dashboard">
            <span class="brand-mark">U</span>
            <span>UATM<span class="brand-muted">-GS</span></span>
        </a>

        <nav class="top-nav">
            <a class="nav-link active" href="<%= request.getContextPath() %>/dashboard">
                Vue d’ensemble
            </a>
            <% if ("STUDENT".equals(role)) { %>
                <a class="nav-link" href="<%= request.getContextPath() %>/themes/proposer">
                    Proposer un thème
                </a>
            <% } %>
            <% if ("TEACHER".equals(role) || "PROGRAM_MANAGER".equals(role) || "ADMIN".equals(role)) { %>
                <a class="nav-link" href="<%= request.getContextPath() %>/themes/review">
                    Validation des thèmes
                </a>
            <% } %>
        </nav>

        <div class="profile">
            <span class="avatar">
                <%= nomAffiche.isEmpty() ? "U" : nomAffiche.substring(0, 1).toUpperCase() %>
            </span>
            <span class="profile-text">
                <strong><%= nomAffiche %></strong>
                <small><%= roleAffiche %></small>
            </span>
            <form method="post" action="<%= request.getContextPath() %>/logout">
                <button class="button button-quiet" type="submit">Quitter</button>
            </form>
        </div>
    </header>

    <main class="page-container">
        <section class="welcome-row">
            <div>
                <span class="eyebrow">ESPACE DE TRAVAIL</span>
                <h1>Bonjour, <%= nomAffiche %></h1>
                <p class="muted">Retrouvez ici les actions adaptées à votre profil.</p>
            </div>
            <span class="role-pill"><%= roleAffiche %></span>
        </section>

        <section class="stat-grid">
            <article class="stat-card stat-highlight">
                <span class="stat-icon">01</span>
                <p>Votre rôle</p>
                <strong><%= roleAffiche %></strong>
                <small>Accès selon votre profil</small>
            </article>
            <article class="stat-card">
                <span class="stat-icon">02</span>
                <p>Thèmes</p>
                <strong>Suivi simplifié</strong>
                <small>Propositions et décisions</small>
            </article>
            <article class="stat-card">
                <span class="stat-icon">03</span>
                <p>Parcours</p>
                <strong>Un espace central</strong>
                <small>Mémoires et soutenances</small>
            </article>
        </section>

        <section class="section-heading">
            <div>
                <span class="eyebrow">RACCOURCIS</span>
                <h2>Vos prochaines actions</h2>
            </div>
        </section>

        <section class="action-grid">
            <% if ("STUDENT".equals(role)) { %>
                <a class="action-card" href="<%= request.getContextPath() %>/themes/proposer">
                    <span class="action-icon">＋</span>
                    <div>
                        <h3>Proposer un thème</h3>
                        <p>Soumettez votre idée et vérifiez sa similarité avec les sujets existants.</p>
                    </div>
                    <span class="action-arrow">→</span>
                </a>
            <% } %>

            <% if ("TEACHER".equals(role) || "PROGRAM_MANAGER".equals(role) || "ADMIN".equals(role)) { %>
                <a class="action-card" href="<%= request.getContextPath() %>/themes/review">
                    <span class="action-icon">✓</span>
                    <div>
                        <h3>Examiner les propositions</h3>
                        <p>Consultez les thèmes en attente et enregistrez une décision.</p>
                    </div>
                    <span class="action-arrow">→</span>
                </a>
            <% } %>

            <% if ("TEACHER".equals(role)) { %>
                <article class="action-card action-muted">
                    <span class="action-icon">◎</span>
                    <div>
                        <h3>Espace enseignant</h3>
                        <p>Consultez la validation des thèmes pour examiner les propositions étudiantes.</p>
                    </div>
                </article>
            <% } %>

            <% if ("ADMIN".equals(role)) { %>
                <article class="action-card action-muted">
                    <span class="action-icon">⚙</span>
                    <div>
                        <h3>Administration</h3>
                        <p>Les paramètres et la gestion des comptes seront ajoutés ensuite.</p>
                    </div>
                </article>
            <% } %>
        </section>

        <p class="page-foot">
            UATM-GS <span>•</span> Gestion des soutenances et des mémoires
        </p>
    </main>
</body>
</html>

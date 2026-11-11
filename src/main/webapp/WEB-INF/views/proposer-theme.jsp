<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    String erreur = (String) request.getAttribute("erreur");
    String message = (String) session.getAttribute("message");
    String titre = echapper((String) request.getAttribute("title"));
    String description = echapper((String) request.getAttribute("description"));
    String motsCles = echapper((String) request.getAttribute("keywords"));
    if (message != null) {
        session.removeAttribute("message");
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Proposer un thème | UATM-GS</title>
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
            <a class="nav-link active" href="<%= request.getContextPath() %>/themes/proposer">Proposer un thème</a>
        </nav>
        <form method="post" action="<%= request.getContextPath() %>/logout">
            <button class="button button-quiet" type="submit">Quitter</button>
        </form>
    </header>

    <main class="page-container narrow-container">
        <a class="back-link" href="<%= request.getContextPath() %>/dashboard">← Retour au tableau de bord</a>
        <section class="page-title">
            <span class="eyebrow">NOUVELLE PROPOSITION</span>
            <h1>Donnez forme à votre sujet</h1>
            <p class="muted">
                Les propositions sont partagées en mémoire pendant l’exécution de l’application.
                Le titre, la description et les mots-clés sont comparés par Jaccard.
            </p>
        </section>

        <% if (erreur != null) { %>
            <div class="alert alert-error" role="alert"><%= echapper(erreur) %></div>
        <% } %>
        <% if (message != null) { %>
            <div class="alert alert-success" role="status"><%= echapper(message) %></div>
        <% } %>

        <section class="form-card">
            <form method="post" action="<%= request.getContextPath() %>/themes/proposer" class="stack-form">
                <label for="title">Titre du thème <span class="required">*</span></label>
                <input id="title" name="title" type="text" maxlength="180" required
                       value="<%= titre %>" placeholder="Ex. Analyse des pratiques de cybersécurité">

                <label for="description">Description du projet <span class="required">*</span></label>
                <textarea id="description" name="description" rows="6" maxlength="4000" required
                          placeholder="Présentez le contexte, la problématique et l’objectif général."><%= description %></textarea>

                <label for="keywords">Mots-clés <span class="required">*</span></label>
                <input id="keywords" name="keywords" type="text" required value="<%= motsCles %>"
                       placeholder="cybersécurité, données, PME" aria-describedby="keywords-help">
                <small id="keywords-help" class="field-help">
                    Saisissez 3 à 5 mots-clés distincts, séparés par des virgules.
                </small>

                <div class="info-box">
                    <span class="info-mark">i</span>
                    <p>
                        Le score maximal est calculé contre les propositions déjà présentes dans le magasin
                        partagé. À partir de 40 %, le statut devient « ARBITRATION ». Les données sont en
                        mémoire et seront effacées au redémarrage de l’application.
                    </p>
                </div>

                <div class="form-actions">
                    <a class="button button-secondary" href="<%= request.getContextPath() %>/dashboard">Annuler</a>
                    <button class="button button-primary" type="submit">
                        Enregistrer et comparer <span aria-hidden="true">→</span>
                    </button>
                </div>
            </form>
        </section>

        <p class="page-foot">UATM-GS <span>•</span> Gestion des soutenances et des mémoires</p>
    </main>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    private String echapper(String texte) {
        if (texte == null) return "";
        return texte.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    String erreur = (String) request.getAttribute("erreur");
    String nom = (String) request.getAttribute("fullName");
    String email = (String) request.getAttribute("email");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Créer un compte | UATM-GS</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/app.css">
</head>
<body class="login-page">
    <main class="login-shell register-shell">
        <section class="login-brand">
            <a class="brand brand-light" href="<%= request.getContextPath() %>/login"><span class="brand-mark">U</span><span>UATM<span class="brand-muted">-GS</span></span></a>
            <div class="brand-copy"><span class="eyebrow">REJOIGNEZ UATM-GS</span><h1>Votre parcours de mémoire commence ici.</h1><p>Créez votre espace étudiant pour proposer un thème et suivre les étapes de votre mémoire.</p></div>
            <p class="brand-foot">Gestion des soutenances et des mémoires</p>
        </section>
        <section class="login-panel register-panel">
            <div class="login-form-wrap">
                <span class="eyebrow">NOUVEAU COMPTE</span><h2>Inscription</h2><p class="muted">Renseignez vos informations pour créer votre espace étudiant.</p>
                <% if (erreur != null) { %><div class="alert alert-error" role="alert"><%= echapper(erreur) %></div><% } %>
                <form method="post" action="<%= request.getContextPath() %>/register" class="stack-form">
                    <label for="fullName">Nom complet</label><input id="fullName" name="fullName" type="text" required maxlength="160" autocomplete="name" value="<%= echapper(nom) %>" placeholder="Prénom et nom">
                    <label for="email">Adresse e-mail</label><input id="email" name="email" type="email" required maxlength="254" autocomplete="email" value="<%= echapper(email) %>" placeholder="nom@uatm.edu">
                    <label for="password">Mot de passe</label><input id="password" name="password" type="password" required minlength="8" maxlength="72" autocomplete="new-password" aria-describedby="password-help"><small id="password-help" class="field-help">Au moins 8 caractères. Choisissez un mot de passe que vous n’utilisez pas ailleurs.</small>
                    <label for="role">Rôle</label><select id="role" name="role" required><option value="STUDENT" selected>Étudiant</option></select>
                    <button class="button button-primary button-wide" type="submit">Créer mon compte <span aria-hidden="true">→</span></button>
                </form>
                <p class="register-link">Vous avez déjà un compte ? <a href="<%= request.getContextPath() %>/login">Se connecter</a></p>
                <p class="form-note">Les comptes enseignant, responsable et administrateur sont créés par l’administration.</p>
            </div>
        </section>
    </main>
</body>
</html>

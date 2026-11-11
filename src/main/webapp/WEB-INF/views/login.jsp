<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String erreur = (String) request.getAttribute("erreur");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Connexion | UATM-GS</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/app.css">
</head>
<body class="login-page">
    <main class="login-shell">
        <section class="login-brand">
            <a class="brand brand-light" href="<%= request.getContextPath() %>/login">
                <span class="brand-mark">U</span>
                <span>UATM<span class="brand-muted">-GS</span></span>
            </a>

            <div class="brand-copy">
                <span class="eyebrow">ESPACE ACADÉMIQUE</span>
                <h1>De l’idée à la soutenance, tout au même endroit.</h1>
                <p>
                    Suivez les propositions de thèmes, les mémoires et les étapes clés de votre parcours
                    universitaire.
                </p>
            </div>

            <p class="brand-foot">Gestion des soutenances et des mémoires</p>
        </section>

        <section class="login-panel">
            <div class="login-form-wrap">
                <span class="eyebrow">BON RETOUR</span>
                <h2>Connexion</h2>
                <p class="muted">Accédez à votre espace avec votre compte institutionnel.</p>

                <% if (message != null) { %>
                    <div class="alert alert-success" role="status"><%= message %></div>
                <% } %>
                <% if (erreur != null) { %>
                    <div class="alert alert-error" role="alert"><%= erreur %></div>
                <% } %>

                <form method="post"
                      action="<%= request.getContextPath() %>/login"
                      class="stack-form">
                    <label for="email">Adresse e-mail</label>
                    <input id="email"
                           name="email"
                           type="email"
                           autocomplete="username"
                           required
                           maxlength="254"
                           placeholder="nom@uatm.edu">

                    <label for="password">Mot de passe</label>
                    <input id="password"
                           name="password"
                           type="password"
                           autocomplete="current-password"
                           required>

                    <button class="button button-primary button-wide" type="submit">
                        Se connecter <span aria-hidden="true">→</span>
                    </button>
                </form>

                <p class="register-link">
                    Vous n’avez pas de compte ?
                    <a href="<%= request.getContextPath() %>/register">Créer un compte</a>
                </p>
                <p class="form-note">
                    L’accès est réservé aux comptes actifs enregistrés dans le système.
                </p>
            </div>
        </section>
    </main>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String erreur = (String) request.getAttribute("erreur");
    String nom = (String) request.getAttribute("nom");
    String nomEchappe = nom == null ? "" : nom.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
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
                <p>Explorez les propositions de thèmes et la comparaison par indice de Jaccard.</p>
            </div>
            <p class="brand-foot">Mode démonstration — aucun compte ni mot de passe requis</p>
        </section>

        <section class="login-panel">
            <div class="login-form-wrap">
                <span class="eyebrow">ACCÈS RAPIDE</span>
                <h2>Connexion simulée</h2>
                <p class="muted">Saisissez un nom et choisissez le profil à présenter.</p>

                <% if (erreur != null) { %>
                    <div class="alert alert-error" role="alert"><%= erreur %></div>
                <% } %>

                <form method="post" action="<%= request.getContextPath() %>/login" class="stack-form">
                    <label for="name">Nom à afficher</label>
                    <input id="name" name="name" type="text" autocomplete="name" required maxlength="160" value="<%= nomEchappe %>" placeholder="Ex. Amina Dupont">

                    <label for="role">Espace</label>
                    <select id="role" name="role" required>
                        <option value="STUDENT">Étudiant</option>
                        <option value="TEACHER">Encadreur</option>
                        <option value="PROGRAM_MANAGER">Responsable de filière</option>
                        <option value="ADMIN">Administrateur</option>
                    </select>

                    <button class="button button-primary button-wide" type="submit">
                        Entrer dans l’espace <span aria-hidden="true">→</span>
                    </button>
                </form>
                <p class="form-note">
                    Démonstration uniquement : le nom et le rôle ne sont pas vérifiés et ne doivent pas
                    être utilisés pour protéger une application en production.
                </p>
            </div>
        </section>
    </main>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String erreur = (String) request.getAttribute("erreur");
    String message = (String) session.getAttribute("message");
    if (message != null) {
        session.removeAttribute("message");
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Proposer un thème | UATM-GS</title><link rel="stylesheet" href="<%= request.getContextPath() %>/css/app.css"></head>
<body>
<header class="topbar"><a class="brand" href="<%= request.getContextPath() %>/dashboard"><span class="brand-mark">U</span><span>UATM<span class="brand-muted">-GS</span></span></a><nav class="top-nav"><a class="nav-link" href="<%= request.getContextPath() %>/dashboard">Vue d’ensemble</a><a class="nav-link active" href="<%= request.getContextPath() %>/themes/proposer">Proposer un thème</a></nav><form method="post" action="<%= request.getContextPath() %>/logout"><button class="button button-quiet" type="submit">Quitter</button></form></header>
<main class="page-container narrow-container"><a class="back-link" href="<%= request.getContextPath() %>/dashboard">← Retour au tableau de bord</a><section class="page-title"><span class="eyebrow">NOUVELLE PROPOSITION</span><h1>Donnez forme à votre sujet</h1><p class="muted">Présentez votre idée clairement. Les mots-clés servent à repérer les sujets proches.</p></section>
<% if (erreur != null) { %><div class="alert alert-error" role="alert"><%= erreur %></div><% } %><% if (message != null) { %><div class="alert alert-success" role="status"><%= message %></div><% } %>
<section class="form-card"><form method="post" action="<%= request.getContextPath() %>/themes/proposer" class="stack-form">
<label for="title">Titre du thème <span class="required">*</span></label><input id="title" name="title" type="text" maxlength="180" required placeholder="Ex. Analyse des pratiques de cybersécurité">
<label for="description">Description du projet <span class="required">*</span></label><textarea id="description" name="description" rows="6" required placeholder="Présentez le contexte, la problématique et l’objectif général."></textarea>
<label for="keywords">Mots-clés <span class="required">*</span></label><input id="keywords" name="keywords" type="text" required placeholder="cybersécurité, données, PME" aria-describedby="keywords-help"><small id="keywords-help" class="field-help">Saisissez 3 à 5 mots-clés distincts, séparés par des virgules.</small>
<div class="info-box"><span class="info-mark">i</span><p>Les thèmes existants de votre filière sont comparés avec l’indice de Jaccard. À partir de 40 % de similarité, la proposition est transmise en arbitrage.</p></div>
<div class="form-actions"><a class="button button-secondary" href="<%= request.getContextPath() %>/dashboard">Annuler</a><button class="button button-primary" type="submit">Envoyer la proposition <span aria-hidden="true">→</span></button></div>
</form></section><p class="page-foot">UATM-GS <span>•</span> Gestion des soutenances et des mémoires</p></main>
</body>
</html>

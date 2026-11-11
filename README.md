# UATM-GS — Gestion des soutenances et des mémoires

Prototype Jakarta EE / JSP pour présenter les espaces par rôle et la comparaison de sujets avec l’indice de Jaccard.

## Connexion de démonstration

La route `/login` n’interroge plus `Users`, Supabase ni une table d’authentification. L’utilisateur saisit un nom et choisit un espace (Étudiant, Encadreur, Responsable de filière ou Administrateur). `LoginServlet` vérifie seulement que ces deux champs sont fournis et que le rôle appartient à la liste connue, puis les conserve dans la session HTTP (`userName`, `role`, `demoLogin`). Le tableau de bord adapte son contenu à ces attributs. La déconnexion invalide la session.

C’est une simulation d’interface, pas une authentification : toute personne peut choisir le profil Administrateur. Ne pas utiliser ce mode pour protéger des données réelles ni exposer l’application sur Internet. L’inscription et la dépendance BCrypt précédemment ajoutées ont été retirées.

## Comparaison Jaccard en mode démo

`JaccardUtil` normalise les mots (minuscules, accents), crée des ensembles sans doublons et calcule : **100 × intersection / union**. `ThemeProposalServlet` réutilise cette classe. Quand la session porte `demoLogin=true`, les mots-clés sont comparés à quelques sujets d’exemple ainsi qu’aux propositions précédentes de la même session. Aucun accès à `EntityManager`, aucune écriture Supabase et aucune validation persistante ne sont effectués dans ce chemin. Le résultat précise qu’il s’agit d’une simulation.

Les thèmes de la session sont temporaires : ils disparaissent à la déconnexion ou à l’expiration de la session et ne sont pas partagés entre utilisateurs. Le seuil indicatif de 40 % classe le résultat en `ARBITRATION` ou `SUBMITTED` pour la démonstration seulement. Les rôles Encadreur/Administrateur voient un écran vide explicite en mode simulation ; les décisions sont désactivées.

## Autres fichiers backend

- `LoginServlet.java` : crée la session de démonstration et redirige vers `/dashboard`.
- `SessionUtil.java` : contrôle la présence d’une session de démonstration et le rôle demandé.
- `JaccardUtil.java` : normalisation des mots-clés et calcul de l’indice.
- `ThemeProposalServlet.java` : validation des mots-clés et calcul de similarité ; chemin simulé sans base.
- `ThemeReviewServlet.java` : écran de revue et refus des décisions simulées.
- `LogoutServlet.java` : invalide la session.
- `persistence.xml` et `scripts/configure-supabase-datasource.cli` : configuration PostgreSQL/Supabase conservée pour les fonctionnalités JPA réelles du projet.

## Comparer des thèmes sans erreurs de base

Pour une soutenance ou un prototype hors ligne, la solution la plus simple est le catalogue d’exemples en mémoire utilisé dans le chemin simulé. Une approche plus propre si le projet grandit consiste à séparer la comparaison pure (`JaccardUtil`) de l’accès aux données : un petit `ThemeRepository` fournit soit une liste en mémoire pour les tests et la démo, soit des thèmes PostgreSQL via JPA. On teste ainsi l’algorithme sans démarrer Supabase et on garde une seule implémentation Jaccard. Pour un usage réel, il faut tout de même configurer et tester la datasource ; capturer une erreur SQL puis continuer silencieusement masquerait un défaut de configuration.

## Compilation

Depuis la racine du projet :

```text
mvn clean package
```

Le WAR nécessite WildFly/Jakarta EE. Les entités et façades générées dans `com.mycompany.entities` n’ont pas été modifiées. La simulation de login ne nécessite pas Supabase, mais les anciens chemins JPA du projet et toute persistance réelle dépendent toujours de `java:/jdbc/SupabaseDS`.

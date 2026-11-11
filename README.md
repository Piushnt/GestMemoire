# UATM-GS — prototype académique en mémoire

Application de démonstration construite avec des Servlets, JSP et Java standard. Les fonctionnalités de connexion, proposition, comparaison et décision ne nécessitent ni datasource, ni Supabase, ni JPA, ni EJB.

## Parcours

1. **Connexion simulée** (`/login`) : l’utilisateur saisit un nom et sélectionne un espace. `LoginServlet` crée une `HttpSession` contenant `userName`, `role` et `demoLogin`, puis redirige vers `/dashboard`. Aucun compte, mot de passe ou enregistrement de base n’est vérifié.
2. **Navigation par rôle** : `DashboardServlet` vérifie la session. Le tableau de bord affiche « Bonjour, [nom] » et montre les raccourcis étudiant ou encadreur/administrateur correspondant au rôle.
3. **Proposition de thème** (`/themes/proposer`) : l’étudiant saisit un titre, une description et trois à cinq mots-clés distincts. La servlet forme un texte de comparaison, demande au magasin partagé de calculer le meilleur score et ajoute la proposition.
4. **Décision** (`/themes/review`) : un encadreur, responsable ou administrateur voit les propositions en attente et peut les valider ou les rejeter. La décision change l’état en mémoire.
5. **Déconnexion** (`/logout`) : invalide la session HTTP.

## Structure Java

| Fichier | Responsabilité |
|---|---|
| `LoginServlet.java` | Connexion simulée, validation du rôle et création de session. |
| `SessionUtil.java` | Vérification de la session et des rôles autorisés par route. |
| `DashboardServlet.java` | Protège le tableau de bord. |
| `LogoutServlet.java` | Ferme la session. |
| `JaccardUtil.java` | Normalise les chaînes, extrait les tokens et calcule l’indice de Jaccard. |
| `ThemeProposal.java` | Modèle Java simple d’une proposition et de son état. |
| `ThemeStore.java` | Liste partagée dans l’application ; sérialise l’ajout, le calcul de similarité et les décisions avec des méthodes synchronisées. |
| `ThemeStoreListener.java` | Crée le magasin lors du démarrage de l’application Web. |
| `ThemeProposalServlet.java` | Valide et ajoute les propositions au magasin mémoire. |
| `ThemeReviewServlet.java` | Liste et décide les propositions en attente dans le même magasin. |

Les vues restent sous `src/main/webapp/WEB-INF/views` et les styles dans `src/main/webapp/css/app.css`. Les entités/facades générées, EJB de persistance, configuration JPA, ressource REST et classes compilées suivies précédemment ont été retirés : aucune injection `EntityManager` ou `@EJB` ne reste dans le parcours applicatif.

## Indice de Jaccard

`JaccardUtil.motsCles` met le texte en minuscules, retire les accents et sépare les tokens aux frontières de ponctuation ou d’espaces. Les doublons sont supprimés par un `Set`. Pour deux thèmes A et B :

**J(A,B) = 100 × |tokens(A) ∩ tokens(B)| / |tokens(A) ∪ tokens(B)|**

Le système compare le texte combiné (titre, description, mots-clés) de la nouvelle proposition à toutes celles du magasin partagé et conserve le maximum. À partir de **40 %**, le statut indicatif est `ARBITRATION`; autrement il est `SUBMITTED`. Ce seuil est une règle de démonstration, pas une décision académique automatique.

## Démarrer

Compiler le WAR avec Maven :

```text
mvn clean package
```

Déployer `target/GestionMemoire-1.0-SNAPSHOT.war` sur un serveur compatible Servlet 6.0 (par exemple une version WildFly compatible Jakarta EE 10). Le parcours décrit ici n’exige aucun pilote JDBC ni configuration WildFly de datasource.

## Limites à connaître

- La connexion est volontairement simulée : chacun peut sélectionner Administrateur. Elle ne doit jamais protéger des données réelles.
- Le magasin est partagé par les sessions de la même instance de l’application, mais il n’est pas durable. Un redémarrage, redéploiement ou arrêt du serveur efface toutes les propositions et décisions.
- Plusieurs instances de serveur ne partagent pas ce magasin mémoire.
- Pour conserver les données durablement plus tard, l’approche conseillée est de garder `JaccardUtil` pur et de brancher un dépôt de données séparé, avec une implémentation mémoire pour les tests et une implémentation PostgreSQL/JPA pour la production. Il faudra alors réintroduire et tester la datasource explicitement ; la version actuelle est conçue pour la soutenance hors base.

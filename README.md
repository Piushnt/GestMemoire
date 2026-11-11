# UATM-GS — gestion des mémoires et soutenances

Ce dépôt conserve la structure initiale NetBeans, notamment les entités JPA, les façades EJB, les ressources générées, `pom.xml`, `persistence.xml` et la page d’accueil. Ces fichiers générés/configuration ne sont pas supprimés ni modifiés par la logique mémoire décrite ici.

## Parcours simple ajouté

- **Connexion simulée** (`/login`) : saisie d’un nom et sélection d’un rôle. `LoginServlet` enregistre `userName`, `role` et `demoLogin` dans `HttpSession`, puis ouvre le tableau de bord. `SessionUtil` vérifie la session et les rôles des routes.
- **Tableau de bord** (`/dashboard`) : affiche le nom (« Bonjour, [nom] ») et les raccourcis selon le rôle.
- **Proposition** (`/themes/proposer`) : l’étudiant saisit un titre, une description et de trois à cinq mots-clés. `ThemeProposalServlet` compare le texte avec les propositions existantes et crée une proposition dans le magasin mémoire partagé.
- **Revue** (`/themes/review`) : les rôles encadreur, responsable de filière et administrateur consultent les propositions en attente et peuvent les valider ou les rejeter.
- **Déconnexion** (`/logout`) : invalide la session HTTP.

## Fichiers ajoutés pour le flux mémoire

| Fichier | Fonction |
|---|---|
| `LoginServlet.java`, `SessionUtil.java` | Session simulée et contrôle des rôles. |
| `DashboardServlet.java`, `LogoutServlet.java` | Tableau de bord protégé et fin de session. |
| `JaccardUtil.java` | Normalisation des mots et calcul pur Java de l’indice de Jaccard. |
| `ThemeProposal.java` | Modèle simple, distinct des entités JPA générées. |
| `ThemeStore.java`, `ThemeStoreListener.java` | Liste partagée et synchronisée, initialisée dans le contexte Web. |
| `ThemeProposalServlet.java`, `ThemeReviewServlet.java` | Ajout, comparaison et décisions sans injection JPA/EJB. |
| `WEB-INF/views/*.jsp` utilisés par ces routes | Formulaires et affichage du nom, des rôles, scores et statuts. |

Les propositions sont partagées entre les sessions de la même instance serveur, mais disparaissent au redémarrage ou redéploiement. L’indice de Jaccard est **100 × intersection / union** des tokens du titre, de la description et des mots-clés. Les accents et la ponctuation sont normalisés ; le seuil indicatif de 40 % donne le statut `ARBITRATION`, sinon `SUBMITTED`. Il s’agit d’une aide pédagogique, pas d’une décision académique automatisée.

## Préserver la structure de base

Les sources sous `src/main/java/com/mycompany/entities`, les façades, les classes REST générées, `pom.xml`, `persistence.xml` et `index.html` sont conservés à leur version initiale suivie dans Git. Le flux de proposition/revue décrit ci-dessus n’injecte ni `EntityManager` ni `ThemeService`; le `ThemeService` ajouté précédemment et le script datasource ajouté précédemment ont été retirés parce qu’ils n’interviennent pas dans ce flux.

Les beans et la configuration JPA originaux restent toutefois présents. Leur démarrage dépend toujours de la configuration serveur initiale du projet ; je n’ai ni modifié cette configuration ni testé WildFly. Ce changement garantit uniquement que la logique ajoutée de proposition, comparaison et revue ne lance pas de requêtes JPA.

## Sécurité et build

La connexion est une simulation : le rôle Administrateur est librement sélectionnable. Ne pas utiliser cette authentification pour protéger des données réelles ni l’exposer en production.

Construire le WAR depuis la racine :

```text
mvn clean package
```

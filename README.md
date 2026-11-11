# UATM-GS — Gestion des soutenances et des mémoires

Cette première étape ajoute l’authentification par session, une navigation adaptée aux rôles, la proposition de thèmes avec comparaison Jaccard et un espace de décision pour le responsable de filière. Les entités et façades générées dans `com.mycompany.entities` restent inchangées.

## Fichiers ajoutés ou adaptés

| Fichier | Rôle |
|---|---|
| `src/main/java/com/mycompany/gestionmemoire/web/LoginServlet.java` | Affiche la connexion, montre le message après inscription et vérifie l’adresse, l’état du compte, son rôle et le mot de passe BCrypt. |
| `src/main/java/com/mycompany/gestionmemoire/web/RegisterServlet.java` | Reçoit le formulaire, valide les champs côté serveur et impose le rôle étudiant. |
| `src/main/java/com/mycompany/gestionmemoire/web/RegistrationService.java` | Vérifie l’adresse e-mail en base, crée une empreinte BCrypt et enregistre l’utilisateur dans une transaction JPA gérée par l’EJB. |
| `src/main/java/com/mycompany/gestionmemoire/web/LogoutServlet.java` | Invalide la session sur requête POST, puis retourne à la connexion. |
| `src/main/java/com/mycompany/gestionmemoire/web/DashboardServlet.java` | Refuse l’accès sans session et transmet la requête à la vue du tableau de bord. |
| `src/main/java/com/mycompany/gestionmemoire/web/SessionUtil.java` | Centralise les quatre noms de rôles et les contrôles de session côté serveur. Le contrôle des rôles dans l’interface ne remplace pas ce contrôle. |
| `src/main/java/com/mycompany/gestionmemoire/web/JaccardUtil.java` | Transforme les mots-clés en ensembles insensibles à la casse et aux accents, puis calcule le pourcentage Jaccard. |
| `src/main/java/com/mycompany/gestionmemoire/web/ThemeProposalServlet.java` | Réserve l’accès aux étudiants, valide les données et compare les thèmes de la même filière. |
| `src/main/java/com/mycompany/gestionmemoire/web/ThemeReviewServlet.java` | Affiche les décisions en attente et accepte validation/rejet seulement pour un administrateur ou un responsable autorisé dans sa filière. |
| `src/main/java/com/mycompany/gestionmemoire/web/ThemeService.java` | EJB stateless qui réalise les écritures JPA dans les transactions gérées par le serveur et revérifie la décision sur l’enregistrement chargé en base. |
| `src/main/webapp/WEB-INF/views/login.jsp` | Formulaire de connexion. Il est placé sous `WEB-INF` pour qu’il soit affiché via la servlet et non directement par URL. |
| `src/main/webapp/WEB-INF/views/register.jsp` | Formulaire d’inscription : nom, e-mail, mot de passe et rôle étudiant. |
| `src/main/webapp/WEB-INF/views/dashboard.jsp` | Tableau de bord avec raccourcis conditionnés par le rôle en session. |
| `src/main/webapp/WEB-INF/views/proposer-theme.jsp` | Formulaire étudiant exigeant un titre, une description et de trois à cinq mots-clés. |
| `src/main/webapp/WEB-INF/views/review-themes.jsp` | Tableau de décision et formulaire de validation/rejet. |
| `src/main/webapp/css/app.css` | Style commun externe et responsive. Aucun style CSS n’est embarqué dans les JSP. |
| `src/main/resources/META-INF/persistence.xml` | Relie explicitement l’unité JTA à la datasource PostgreSQL JNDI `java:/jdbc/SupabaseDS` et interdit la génération de schéma Hibernate. |
| `scripts/configure-supabase-datasource.cli` | Crée la datasource dans WildFly, puis teste la connexion avant déploiement du WAR. |
| `pom.xml` | Ajout de jBCrypt, utilisé pour vérifier des empreintes BCrypt sans stocker ni comparer de mot de passe en clair. |

## Explication du fonctionnement

### Connexion et rôles

1. Le navigateur envoie l’adresse e-mail et le mot de passe par POST à `/login`.
2. `LoginServlet` fixe l’encodage UTF-8 avant de lire le formulaire, puis cherche l’utilisateur à l’aide de la requête nommée `Users.findByEmail` déjà fournie par l’entité.
3. La connexion n’est acceptée que si l’utilisateur est actif, possède l’un des rôles `ADMIN`, `PROGRAM_MANAGER`, `TEACHER`, `STUDENT` et si son mot de passe correspond à l’empreinte BCrypt.
4. L’ancienne session est invalidée avant la création d’une nouvelle session. Cette étape évite de réutiliser l’identifiant de session précédent après connexion.
5. La session ne conserve que les informations utiles à l’application : `userId`, `userName`, `role` et `programId`.
6. `SessionUtil.connecter` contrôle côté serveur l’existence de la session et les rôles autorisés avant que chaque servlet protègée exécute sa page ou son action. Les liens affichés dans les JSP sont seulement une adaptation d’interface ; ils ne constituent pas une autorisation.
7. La déconnexion se fait en POST et invalide complètement la session HTTP.

### Indice de Jaccard

`JaccardUtil.motsCles` met les mots en minuscules, retire les accents, sépare les mots sur les virgules, points-virgules et espaces, puis élimine les doublons avec un ensemble. La comparaison suit la formule : **100 × taille de l’intersection / taille de l’union**. Par exemple, `{java, web, sécurité}` et `{java, web, données}` donnent une intersection de 2 éléments et une union de 4, soit 50 %.

La servlet compare les mots-clés du sujet soumis à chaque sujet de la même filière et conserve le score le plus élevé. À partir de 40 %, le statut est `ARBITRATION` ; sinon il est `SUBMITTED`. Le score en pourcentage est conservé dans le champ existant `similarity_percent`. La comparaison est une aide à la détection de proximité, pas une décision académique automatisée.

### Enregistrement et décision

Le formulaire étudiant ne choisit pas lui-même son identifiant d’utilisateur ou sa filière : ces informations viennent de la session après authentification. La proposition est rattachée à l’étudiant connecté, à sa filière et à l’année académique marquée `current = true`. Les champs requis (`ownerId`, `programId`, `academicYearId`, date, version et statut) sont renseignés dans `ThemeService`. Cet EJB réalise la sauvegarde dans une transaction gérée par le conteneur, ce qui est nécessaire pour appeler `persist`.

Le responsable ne reçoit que les sujets de sa filière pour lesquels le statut est `SUBMITTED` ou `ARBITRATION`. Lors d’un POST, la servlet recharge le thème par identifiant, revérifie son statut et compare sa filière à celle de la session avant d’accepter `APPROVED` ou `REJECTED`. Un administrateur peut examiner les thèmes de toutes les filières. Une décision enregistre aussi l’utilisateur responsable, la date et la note éventuelle.

## Configuration indispensable avant exécution

`src/main/resources/META-INF/persistence.xml` déclare l’unité `my_persistence_unit` en mode JTA et référence explicitement `java:/jdbc/SupabaseDS`. Le dialecte est PostgreSQL et `hibernate.hbm2ddl.auto=none` empêche Hibernate de créer ou modifier les tables. Cette référence JNDI ne crée pas la datasource dans WildFly. L’erreur `jboss.naming.context.java.jdbc.SupabaseDS` signifie que le service de datasource ou son binding JNDI n’est pas installé sous ce nom, ou que son pilote PostgreSQL manque.

Un `jboss-web.xml` ne peut pas créer le service absent ; il ne ferait que déclarer une référence côté application. Il ne faut pas contourner le problème avec H2 : l’application a besoin de sa datasource PostgreSQL pour démarrer correctement. Le dépôt inclut maintenant `scripts/configure-supabase-datasource.cli`, qui crée précisément ce binding et teste la connexion. Le pilote JDBC PostgreSQL doit d’abord être installé dans WildFly et enregistré avec le nom `postgresql` (ou le nom du pilote indiqué dans le script doit être adapté).

Sous Windows, définissez les variables dans l’environnement du processus WildFly avant de le démarrer ou de le redémarrer : `SUPABASE_HOST`, `SUPABASE_PORT`, `SUPABASE_DB`, `SUPABASE_USER` et `SUPABASE_PASSWORD`. Utilisez les paramètres fournis dans Supabase → **Connect** ; le script utilise TLS (`sslmode=require`). Le port usuel de connexion directe est `5432`, mais utilisez le port fourni si vous passez par le pooler Supabase.

Après le démarrage de WildFly, ouvrez une deuxième fenêtre PowerShell à la racine du projet et lancez :

```powershell
& "$env:WILDFLY_HOME\bin\jboss-cli.bat" --connect --file="scripts\configure-supabase-datasource.cli"
```

La commande doit terminer le test de connexion avec succès avant le déploiement du WAR. Si `$env:WILDFLY_HOME` n’est pas défini, remplacez-le par le chemin du dossier d’installation de WildFly. Si le WAR avait déjà échoué au déploiement à cause de la datasource manquante, redéployez-le après la réussite du test. Si `SupabaseDS` existe déjà, ne relancez pas `data-source add` : vérifiez dans la console d’administration que son JNDI est `java:/jdbc/SupabaseDS`, que le pilote et les identifiants sont corrects, puis testez la connexion. La dépendance `jakarta.jakartaee-api` ne fournit pas le pilote PostgreSQL.

La colonne `users.password_hash` doit contenir une empreinte BCrypt reconnue par jBCrypt, généralement au format `$2a$...`. L’inscription appelle `BCrypt.hashpw` avec un sel aléatoire et un coût de 12 pour que le mot de passe nouvellement créé soit compatible avec `LoginServlet`. Le stockage en clair ou avec `String.hashCode()` a été écarté : ce ne serait ni suffisamment sûr ni compatible avec la vérification BCrypt du login. Les comptes Supabase Auth ne deviennent pas automatiquement des comptes utilisables par cette authentification.

La colonne `users.role` doit contenir les valeurs exactes `ADMIN`, `PROGRAM_MANAGER`, `TEACHER` ou `STUDENT` (la casse est normalisée à la lecture). Une auto-inscription publique ne doit pas laisser l’utilisateur se promouvoir : le formulaire propose le rôle `STUDENT` et le service serveur impose cette même valeur même si le POST est falsifié. Les rôles du personnel doivent être attribués par un administrateur. Les étudiants doivent être associés à `program_id` avant de proposer un thème ; l’inscription simple ne choisit pas de filière. Une année académique doit également être marquée comme courante.

### Inscription simple

`RegisterServlet` lit `fullName`, `email`, `password` et `role` en UTF-8, vérifie les tailles, l’adresse, un mot de passe d’au moins huit caractères et la valeur de rôle. `RegistrationService`, un EJB stateless, refait la règle du rôle, normalise l’adresse en minuscules, refuse un e-mail déjà présent puis renseigne `fullName`, `email`, `passwordHash`, `role`, `active`, le compteur d’échecs et les dates obligatoires de l’entité `Users`. Après l’enregistrement, le navigateur est redirigé vers `/login` et affiche un message flash. La page ne demande pas de filière : un administrateur doit associer l’étudiant à un programme avant que celui-ci puisse proposer un thème.

## Compilation et intégration NetBeans / WildFly

1. Ouvrir le projet comme projet Maven dans NetBeans et laisser Maven télécharger jBCrypt.
2. Installer le pilote PostgreSQL, démarrer WildFly avec les variables d’environnement Supabase, puis exécuter le script CLI décrit plus haut.
3. Vérifier dans Supabase que la table `users` correspond aux entités générées et que les comptes ont les champs obligatoires.
4. Compiler avec `mvn clean package`, puis déployer le WAR sur un serveur compatible Jakarta EE 11 et Java 17.
5. Ouvrir `/register` pour créer un compte étudiant ou `/login` pour se connecter.

## Limites connues de cette première étape

La compilation Maven (`mvn package -DskipTests`) a réussi après ces changements. Le serveur et les accès Supabase ne sont pas disponibles dans le dépôt ; la connexion réelle à la base et le déploiement WildFly n’ont donc pas pu être vérifiés ici. La gestion des échecs de connexion (compteur/verrouillage), la protection CSRF, l’administration des comptes, le dépôt PDF, les jurys, les notes et les écrans complets de l’enseignant restent à développer. Les opérations métier doivent être testées sur une base de test avant mise en service.

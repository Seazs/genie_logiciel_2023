
# Répartition des tâches

- Membres de l'équipe: Alexandre A., Alexandre B., Romain, Nicolas, Philippe, Brenno, Wassim, Lina, Ismail et Raphaël.
- Vélocité: 28

# Itération 1 - 28 points

## Histoire 1 - 6 points

Tâche 1 : Création de la classe utilisateur – 2 points

- Alexandre A. et Wassim
- Description : Création de la classe utilisateur et GestionnaireUtilisateur.

Tâche 2 : Interface liste de paquets – 2 points

- Alexandre B et Lina
- Description : cette tâche impliquait la conception de la vue de la liste de paquets.

Tâche 3 : Interface connexion – 2 points

- Brenno et Ismail
- Description : Implémentation du menu de connexion de l'utilisateur dans l'application

## Histoire 2 - 12 points

Tâche 1 : Stockage des Utilisateurs et Paquets – 4 points

- Alexandre A. et Ismail
- Description : Cette tâche consistait à implémenter un système de stockage des utilisateurs et de leurs paquets associés. La méthode choisie a été de créer un dossier pour chaque utilisateur contenant tous les fichiers "paquets" qui lui sont associés. 

Tâche 2: Fusion des branches– 2 points

- Nicolas et Alexandre B.
- Description : Cette tâche avait pour but de fusionner la plupart des branches présentes dans le main, ainsi que de régler les conflits associés à cette fusion.

Tâche 3 : Création de Paquets– 2 points

- Nicolas et Romain
- Description : Cette tâche comprenait la création d'une classe représentant le paquet de cartes.

Tâche 4 : Sauvegarde du paquet dans un fichier – 2 points

- Romain et Nicolas
- Description : Tâche consistant à sauvegarder/charger les paquets de l'utilisateur depuis un fichier contenu dans un dossier propre à l'utilisateur.

Tâche 5 : Ajout des cartes dans paquet et sauvegarde – 2 points

- Romain et Raphaël
- Description : Ajout et suppression d’une carte du paquet. Sauvegarde du paquet dans un fichier. Tests et javadoc qui vont avec.

## Histoire 3 - 6 points

Tâche 1 : Création de cartes – 2 points

- Raphaël et Philippe
- Description : cette tâche consiste tout ce qui concerne la création des cartes et modification de ces dernières. D’un point de vue programmation (pas de l’affichage). Des tests et de la javadoc qui va avec.

Tâche 2 : Affichage des cartes – 4 points

- Manque de temps, reporté à l’itération 2.

## Histoire 23 - 4 points

Tâche 1 : Apprentissage Java et interface graphique - 2 points

- Lina et Alexandre B
- Description : cette tâche consistait à apprendre Java et JavaFX

Tâche 2 : Mise en place de JavaFX – 2 points

- Brenno et Ismail
- Description : Configuration nécessaire pour l'utilisation de JavaFX pour la première partie du projet.


# Itération 2 - 28 points

## Histoire 2 - 6 points

Tâche 1 : Déconnexion utilisateur – 2 points

- Lina et Alexandre A.
- Description : cette tâche impliquait de mettre en place un code dans le gestionnaire utilisateur pour gérer la logique de déconnexion, ainsi que dans la vue de l’application pour permettre aux utilisateurs d’initier la déconnexion.

Tâche 2 : Édition des paquets et refont de l'interface graphique – 2 points

- Phillipe M et Brenno
- Description : Implémentation de l'interface graphique du menu d'édition d'un paquet ainsi que, modification de l'interface graphique du menu de paquet de carte.

Tâche 3 : Filtre des catégories – 2 points

- Raphaël et Alexandre B.
- Description : Modification du code pour permettre l’implémentation de plusieurs catégories par paquet. Tant au niveau du code actif que de la sauvegarde et de l’affichage. Ajout d’un filtre en JavaFX permettant de trier les paquets avec un stream selon leurs catégories.

## Histoire 4 - 5 points

Tâche 1 : Session d’étude (interface comprise) – 2,5 points

- Alexandre A et Lina
- Description : Faire en sorte que lorsqu'on choisit d'étudier un paquet, les cartes s'affichent aléatoirement et des boutons de connaissance apparaissent ("très mauvais","mauvais","moyen","bon","très bon").

Tâche 2 : Score des cartes et sauvegarde du score (+ interface) – 2,5 points

- Ismail et Romain
- Description : Implémentation d’un score de 1 à 5 pour chaque carte décrivant sa maitrise Et gestion de la sauvegarde de ce score dans le document texte.

## Histoire 5 - 6 points

Tâche 1 : gestion de l’affichage des cartes – 6 points

- Romain et Ismail
- Description : Permet de montrer avec une plus grande probabilité les cartes qui ne sont pas encore maitrisées (les cartes avec un score de maitrise bas).

## Histoire 8 - 9 points

Tâche 1 : Interface des types de cartes – 4 points

- Wassim et Nicolas
- Description: Créer une interface pour les textes à trous et les choix multiples. Il doit être possible d’éditer les cartes directement via l’interface.

Tâche 2 : Types de cartes en eux même – 5 points

- Raphaël et Alexandre B
- Description : Créer et éditer différents types de cartes (textes à trou, questions choix multiples) dans le code et sauvegarde de ces dernières. Fonctions qui permet de set et de get les infos des textes à trous et qcm.


## Histoire 24 - 2 points

Tâche 1 : MVC – 1 point

- Wassim et Nicolas
- Description : Refactoring du MVC pour suivre la structure imposée.

Tâche 2 : Gestion des exceptions – 1 points

- Nicolas et Wassim
- Description : Trouver un moyen pour gérer les différentes exceptions qui peuvent survenir lors de l’utilisation de l’application.


# Itération 3 - 28 points


## Histoire 8 - 4 points

Tâche 1 : Terminer l'implémentation des cartes de type(QCM et texte a trou) - 4 points

- Ismail et Alexandre A.
- Description : Lier toutes les différentes parties afin de l'histoire de pouvoir créer, modifier et jouer tout type de carte. Adaptation du code en même temps.

## Histoire 10 - 10 points

Tâche 1 : Création du serveur / protocole de communication - 4 points

- Raphaël et Wassim
- Description : Creér un serveur et gérer la connexion au serveur(pas à l'application) et recevoir/envoyer des infos au serveur.

Tâche 2 : Login / register / envoi de paquets - 3 points 

- Lina et Nico
- Description : Envoie du nom et du mdp par le client et check de la validité par le serveur. Envoie des paquets de l'utilisateurs à l'application.

Tâche 3 : Gérer la database du serveur - 2 points

- Brenno et Raph
- Description : Création des fichiers nécessaire au stockage des données de l'utilisateur.

Tâche 4 : Implémentation de l'option en ligne ou hors ligne - 1 points

- Wassim et Nico
- Description: Création d'un choix au menu de connexion qui détermine si l'applis est en mode en ligne ou hors ligne.

# Histoire 11 - 10 points

Tâche 1 : Gestion du stockage des paquets dans le store - 2 points

- Brenno et Ismail
- Description : Création des fichiers nécéssaire au stockage des paquets contenu dans le store

Tâche 2 : Implémentation de l'affichage du store - 2 points

- Wassim et Romain
- Description : Créer l'affichage qui récupere tous les paquets se trouvant en ligne avec le moyen les télécharger (Frontend)

Tâche 3 : Implémentation de l'affichage de upload - 2 points

- Romain et Alexandre A.
- Description : Créer l'affichage qui permet de upload un fichier sur le store

Tâche 4 : Créer le protocole de communaction store et client - 4 points

- Alexandre B. et Philippe
- Description : Créer le protocole permettant d'envoyer un paquet sur le store ainsi que de recevoir la liste de tous les paquets du store. 

## Histoire 14 - 4 points 

Tâche 1 : Création de l'interface audio pour chaque type de carte (Frontend) - 1 points

- Lina et Romain
- Description : Créer l'interface nécessaire pour la lecture audio.(Frontend)

Tâche 2 : Implémentation de la lecture audio (Back end) - 3 points

- Philippe et Alexandre B.
- Description : Implémenter la lecture des cartes avec la manière dont chaque cartes sera lue.
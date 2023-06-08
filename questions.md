# Question 1
Comment peut-on améliorer le code suivant : ulb.infof307.g12.view.paquets.CardStudyViewController#showGoodTypeCard ?

# Question 2
Bonne couverture de tests mais tout le modèle n'est pas testé. Pourquoi ? Avez-vous pratiqué le TestFirst ?

# Question 3
A quoi servent vos DTOs ?

# Question 4
La classe ulb.infof307.g12.view.paquets.EditionViewController est grande. Comment refactoriser ?

# Question 5
ulb.infof307.g12.view.profiles.ProfileViewController#onChangePasswordButtonClick : Est-ce correct au niveau du MVC ? Justifiez.

# Question 6
D'après ce que je vois, les PaquetDTO sont créés depuis la méthode ulb.infof307.g12.model.Paquet#getDTO. Dans PaquetDTO, on peut retrouver le Paquet associé à ce DTO en allant chercher une liste. N'y a-t-il donc pas de lien entre PaquetDTO et le Paquet qui le crée ? Expliquez cette partie de votre architecture.

# Question 7
Pourquoi avez vous dû définir des classes Card, CardQcm, CardTt, Paquet et STATUS différentes pour le serveur et pour le client ?

# Question 8
Discutez de la gestion des exceptions dans la méthode com.ulb.infof307.g12.server.dao.UserDataAccessService#updateUser.

# Question 9
src/main/controller/javafx/paquets/EditionController.savePaquet (ligne 57 à 60) quel est le problème ici et comment pourrait on améliorer le code ?

# Question 10
La classe src/main/controller/connexion/MenuPrincipal semble avoir plusieurs responsabilités. Comment pouvez vous améliorer ce code ?
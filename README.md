# m2-eserv-projet-spring
Java EE Projet
Makles Dimitri
Verlyck Jerome

Execution

Depuis la racine du repertoire ServicesBlog , executer la commande 
		mvn spring-boot:run
Afin de lancer les services

Depuis la racine du repertoire Blog , executer la commande
		mvn spring-boot:run
Afin de lancer le blog

Exemple de commande:

http://localhost:8000/api/posts/  -> Permet de recuperer tout les posts

http://localhost:8000/api/users/  -> Permet de recuperer tout les utilisateurs

http://localhost:8000/api/users/{name}  -> Permet de recuperer l'utilisateur dont le nom
est passé dans l'url

http://localhost:8000/api/posts/list/{auteur}  -> Permet de recuperer tout les posts d'un
auteur passé dans l'url

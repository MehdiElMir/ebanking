# Architecture JEE Spring Angular Spring Security JWT
## Architecture
![Architecture](/captures/Architecture.png)
### **La couche de services web** 
Elle est généralement responsable de la logique métier de notre application. Elle expose des fonctionnalités via des interfaces web standardisées telles que RESTful APIs ou SOAP. Cette couche traite les requêtes provenant des clients (interface utilisateur, applications mobiles, etc.) et interagit avec la couche d'accès aux données pour récupérer ou stocker des informations.
### **La couche DAO** 
Elle est responsable de l'accès direct à la base de données ou à d'autres sources de données. Elle abstrait la logique d'accès aux données de la couche métier. Cela permet de centraliser la gestion des requêtes SQL ou d'autres mécanismes d'accès aux données, facilitant ainsi la maintenance et l'évolutivité.
## Conception de Base de donnée
![Diagramme de classe](/captures/ClassDiagram.png)
## Fonctionnalités de l'application
- La gestion des clients (saisie, ajout, suppression, édition, recherche , etc.)
- La gestion des comptes (Ajout des comptes, recherche et administration des comptes)
- Pour chaque client, compte, opération enregistrée, il faut enregistrer avec l'enregistrement l'identifiant de l'utilisateur authentifié qui a effectué l'opération
- Gestion des comptes et des mot de passes des utilisateurs avec la possibilité qu'un utilisateur change son mot de passe
- Partie Dashboard : En utilisant ChartJS (ng-chart), quelques graphiques et des statistiques utiles pour les prises de décision
## Quelques Captures 
### Login
![login](/captures/login.png)
### Dashboard
![Dashboard](/captures/Dashboard.png)
### Gestion des customers
![Customers](/captures/customers.png)
### Gestion des comptes
![Accounts](/captures/accounts.png)
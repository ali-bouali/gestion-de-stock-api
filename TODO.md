##Le reste a faire pour l'API Gestion de Stock
**️✔** Done  
**x** Todo
###Commande client: 
* Modifier la quantite **️✔**
* Modifier le client **️✔**
* Ajouter un etat de dommande client **️✔**
    * En cours de preparation
    * Commande validee
    * Commande livree
* Modifier un article **️✔**
* Supprimer un article  **️✔**
* Chercher les lignes de commande client par commande **️✔**

#### Commande fournisseur:
* Modifier la quantite **️✔**
* Modifier le client **️✔**
* Modifier un article **️✔**
* Supprimer un article **️✔**
* Chercher les lignes de commande fournisseur par commande **️✔**
* Ajouter un etat de dommande client **️✔**
    * En cours de preparation
    * Commande validee
    * Commande livree

#### Article
* Chercher l'historique d'un article **️✔**
    * Historique des ventes **️✔**
    * Historique des commandes client **️✔**
    * Historique des commandes fournisseurs **️✔**
* Chercher les articles par categorie **️✔**
  
#### Controleur Photo
* Developper le controleur des photos **️✔**

#### Mouvements de stock:
* Calculer le stock reel de chaque article **️✔**
* Consuter les mouvements du stock de chaque article **️✔**
* Effectuer une entree du stock **️✔**
  * Commande fournisseur
* Effectuer une sortie du stock **️✔**
  * Commande client
  * Vente
* Effectuer une correction de stock **️✔**
    * Correction +
    * Correction -
    
#### Utilisateur
* Reinitialiser mot de passe **️✔**

#### Validators
* Finaliser les validateurs **️✔**

#### Statistiques
* Les articles les plus vendus **x**
* Les clients les plus fideles **x**
* Statistiques des ventes par periode --> Date de debut .. Date de fin **x**

#### Les methodes de suppression
* Ajouter les verification pour chaque methode de suppression de l'API **x**

### Module de mailing
* Creer un module d'envoi des emails
* Envoyer des emails pour:
    * Creation du compte entreprise
        * Envoyer un email a l'utilisateur
    * Validation de l'@ email d'un utilisateur 
    * Creation d'un nouvel utilisateur
    * Commande client --> Validee / Livree
    * Commande fournisseur --> Validee
    * Demande de reinitialisation de mot de passe
    * Mot de passe changee

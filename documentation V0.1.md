# Projet Java – Gestion des Emplois du Temps

## Etat actuel de l’implémentation

### Architecture

- JavaFX pour l’interface graphique
- MVC + DAO pour une séparation claire des responsabilités
- FXML pour décrire les vues
- Supabase PostgreSQL comme base de données distante
- Java 21 avec `module-info.java`

---

## Structure du projet

```
src/
└── main/
    ├── java/
    │   └── org/example/projet_emplois/
    │       ├── controller/
    │       │   ├── LoginController.java
    │       │   ├── MainController.java
    │       │   ├── AdminDashboardController.java
    │       │   ├── CoursTabController.java
    │       │   └── RoomTabController.java
    │       ├── dao/
    │       │   ├── UserDAO.java
    │       │   ├── CourseDAO.java
    │       │   ├── RoomDAO.java
    │       │   └── EquipmentDAO.java
    │       ├── model/
    │       │   ├── User (abstract)
    │       │   ├── Admin, Student, Teacher
    │       │   ├── Course.java
    │       │   ├── Room.java
    │       │   └── Equipment.java
    │       └── util/
    │           └── Database.java
    └── resources/
        └── org/example/projet_emplois/
            ├── views/
            │   ├── login.fxml
            │   ├── main.fxml
            │   ├── admin-dashboard.fxml
            │   ├── students-tab.fxml
            │   ├── cours-tab.fxml
            │   └── rooms-tab.fxml
            └── styles/
                └── style.css
```

---

## Fonctionnement

### Authentification

- `login.fxml` : formulaire email + mot de passe
- `LoginController.java` :
  - vérifie les identifiants via `UserDAO`
  - redirige selon le rôle (`admin-dashboard.fxml`, etc.)
  - utilise `MainController` pour afficher dynamiquement le tableau de bord

---

### Navigation

- `MainController` centralise le `BorderPane` de la scène principale
- Utilisation de `TabPane` pour chaque fonctionnalité (cours, salles, emplois du temps, etc.)
- Les vues sont chargées dynamiquement via `fx:include` dans `admin-dashboard.fxml`

---

## Interface Utilisateur

- Design moderne avec `style.css`
- Champs, boutons et composants stylisés
- Composants dynamiques :
  - TableView éditables
  - ComboBox contextuelles
  - Feedback utilisateur via `Label`

---

## Fonctionnalités Implémentées

### Pour l'administrateur :

- Gestion des cours
  - Ajout, modification, suppression
  - Affectation des enseignants

- Gestion des enseignants
  - Récupération via `UserDAO` (filtrés par rôle)

- Gestion des salles
  - Ajout / suppression
  - Attribution d’équipements (avec quantité)
  - Visualisation des équipements affectés

- Gestion des équipements
  - Ajout / suppression des types d’équipements

---

## Prochaines Étapes

- Ajouter la gestion des emplois du temps avec `ScheduleController`
- Implémenter le planning visuel par étudiant / enseignant
- Gérer les notifications en fonction des conflits ou mises à jour
- Ajouter des filtres de recherche et tri dynamique dans les tableaux
- Sécuriser les accès et données sensibles

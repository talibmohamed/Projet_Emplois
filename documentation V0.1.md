# Projet Java - Gestion des Emplois du Temps

## État actuel de l’implémentation

### Architecture

- **JavaFX** (UI)
- **MVC + DAO**
- **FXML** pour les vues
- **Supabase PostgreSQL** comme base de données distante
- **Java 21** avec `module-info.java`

---

## Structure du projet

```
src/
└── main/
    ├── java/
    │   └── org/example/projet_emplois/
    │       ├── controller/
    │       │   ├── LoginController.java
    │       │   └── MainController.java
    │       ├── dao/
    │       │   └── UserDAO.java
    │       ├── model/
    │       │   ├── User (abstract)
    │       │   ├── Admin, Student, Teacher
    │       └── util/
    │           └── Database.java
    └── resources/
        └── org/example/projet_emplois/
            ├── views/
            │   ├── main.fxml
            │   ├── login.fxml
            │   ├── admin-dashboard.fxml
            │   ├── student-dashboard.fxml
            │   ├── teacher-dashboard.fxml
            └── styles/
                └── style.css
```

---

## Fonctionnement

### Authentification

- `login.fxml` contient le formulaire de connexion (email + mot de passe)
- `LoginController.java` :
  - récupère les identifiants saisis
  - vérifie l’utilisateur via `UserDAO`
  - charge la vue appropriée (`admin-dashboard.fxml`, etc.) dans `MainController`

---

### Navigation

- `MainController` gère le contenu central de l'application à l’aide d’un `BorderPane`
- Une seule **fenêtre principale** utilisée tout au long de la session
- L’interface est mise à jour via `mainController.loadDashboard(role)`

---

### Interface Utilisateur

- UI stylisée via `style.css`
- Formulaire de connexion centré et compact
- Champs de saisie et bouton personnalisés
- Structure extensible pour ajouter des onglets dans les dashboards (via `TabPane`)

---

## 🗓️ Prochaines Étapes

- Créer les `DashboardController` pour chaque rôle
- Ajouter des vues `TabPane` selon les fonctionnalités
- Implémenter les fonctions côté admin (gestion des enseignants, salles, etc.)
- Gérer les conflits et notifications côté enseignant/étudiant

---

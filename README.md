# Projet Gestion des Emplois du Temps

## Table des matières

1. [Présentation](#présentation)
2. [Fonctionnalités](#fonctionnalités)
3. [Prérequis](#prérequis)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Structure du projet](#structure-du-projet)
7. [Utilisation](#utilisation)
8. [Bases de données](#bases-de-données)
9. [Architecture](#architecture)
10. [Contribuer](#contribuer)
11. [Licence](#licence)

---

## Présentation

Ce projet est une application de gestion des emplois du temps pour un établissement éducatif (école, université, etc.), développée en **JavaFX** avec une architecture **MVC**. Il permet de créer, modifier et consulter des plannings de cours, de gérer les salles, les utilisateurs et d’envoyer des notifications en cas de conflits ou de changements.

## Fonctionnalités

* **Authentification** par rôles (Admin, Enseignant, Étudiant)
* **Gestion des emplois du temps**  : création, modification, suppression de cours planifiés
* **Gestion des salles** : ajout, modification, consultation de la disponibilité
* **Gestion des comptes utilisateurs** : création et édition des Admin, Enseignant, Étudiant
* **Détection et résolution de conflits** d’horaires (salles ou enseignants doublés)
* **Notifications** : envoi et réception de messages entre utilisateurs
* **Consultation personnalisée** : vue du planning pour chaque rôle avec filtres, impression/export

## Prérequis

* Java JDK 17 ou supérieur
* JavaFX 21.0.3 (ou plus récent)
* Maven 3.6+
* Base de données SQL (MySQL, PostgreSQL ou SQLite)

## Installation

1. **Cloner le dépôt**

   ```bash
   git clone https://github.com/votre-utilisateur/projet-emploi-temps.git
   cd projet-emploi-temps
   ```
2. **Compiler et exécuter** avec Maven :

   ```bash
   mvn clean javafx:run
   ```

## Configuration

1. **Configurer la base de données** dans `src/main/resources/config.properties` :

   ```properties
   db.url=jdbc:postgresql://localhost:5432/emplois
   db.user=mon_user
   db.password=mon_mot_de_passe
   ```
2. **Initialiser les tables** (exécuter `schema.sql` fourni dans `db/` ou via votre outil favori)
3. Relancer l’application.

## Structure du projet

```
src/
├── main/
│   ├── java/org/example/projet_emplois/
│   │   ├── controller/   # Contrôleurs JavaFX (Login, Admin, Teacher, Student)
│   │   ├── dao/          # Data Access Objects (UserDAO, CourseDAO...)
│   │   ├── model/        # Entités métier (User, Course, Room, Schedule...)
│   │   └── util/         # Utilitaires (Database.java)
│   └── resources/org/example/projet_emplois/
│       ├── views/        # Fichiers FXML
│       └── styles/       # Fichiers CSS
└── db/
    └── schema.sql        # Script de création des tables
```

## Utilisation

1. **Démarrer l’application** et se connecter en tant qu’Admin :

    * Email : `simo@example.com`
    * Mot de passe : `simo123`
2. **Naviguer** entre les onglets pour gérer les emplois du temps, les salles et les utilisateurs.
3. **Se connecter** en tant qu’enseignant ou étudiant pour consulter votre planning et recevoir les notifications.

## Bases de données

Le projet définit les tables suivantes :

* `users(id, name, email, password, role)`
* `rooms(id, name, capacity, equipment[] )`
* `courses(id, name, type, teacher_id)`
* `timeslots(id, day, start_time, end_time)`
* `schedule(id, course_id, room_id, timeslot_id)`
* `enrollment(id, student_id, course_id)`
* `notifications(id, user_id, message, created_at)`

## Architecture

* **JavaFX + FXML** pour l’interface graphique
* **MVC** (Modèle-View-Controller) pour séparer vues, contrôleurs et logique métier
* **DAO** pour la couche d’accès aux données (JDBC)
* **Modularité** : différents packages pour chaque responsabilité

## Contribuer

?

## Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.
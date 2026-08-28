# Backend — Momentum

Backend de l'application de suivi de championnats sportifs, développé avec **Java et Spring**.

L'objectif du backend est de fournir une API centralisée permettant au frontend et aux différentes fonctionnalités de l'application d'accéder aux données sportives, aux utilisateurs, aux statistiques, aux classements, aux événements en direct et au système de Fantasy League.

L'architecture est conçue pour être **générique et évolutive**, afin de permettre l'intégration progressive de nouvelles disciplines sportives sans nécessiter une refonte complète du système.

---

## Sommaire

* [Présentation](#-présentation)
* [Objectifs du backend](#-objectifs-du-backend)
* [Fonctionnalités](#-fonctionnalités)
* [Architecture](#-architecture)
* [Modèle métier](#-modèle-métier)
* [Technologies](#-technologies)
* [Organisation du projet](#-organisation-du-projet)
* [API REST](#-api-rest)
* [Gestion des données sportives](#-gestion-des-données-sportives)
* [Temps réel](#-temps-réel)
* [Fantasy League](#-fantasy-league)
* [Authentification et sécurité](#-authentification-et-sécurité)
* [Validation et gestion des erreurs](#-validation-et-gestion-des-erreurs)
* [Tests](#-tests)
* [Qualité du code](#-qualité-du-code)
* [Configuration](#-configuration)
* [Lancement du projet](#-lancement-du-projet)
* [CI/CD](#-cicd)
* [Monitoring et observabilité](#-monitoring-et-observabilité)
* [Performance et scalabilité](#-performance-et-scalabilité)
* [Numérique responsable](#-numérique-responsable)
* [Risques techniques](#-risques-techniques)
* [Documentation](#-documentation)
* [Évolutions prévues](#-évolutions-prévues)

---

# Présentation

L'application permet de centraliser le suivi de plusieurs disciplines sportives au sein d'une seule plateforme.

Le backend constitue le cœur métier de la solution. Il assure notamment :

* la gestion des sports et compétitions ;
* la gestion des saisons ;
* la gestion des équipes et des joueurs ;
* la gestion des matchs et événements sportifs ;
* la récupération et la normalisation des données sportives ;
* la gestion des classements ;
* la gestion des statistiques ;
* la gestion des utilisateurs ;
* la gestion des favoris ;
* la gestion des notifications ;
* la gestion de la Fantasy League ;
* l'exposition des données au frontend via des API ;
* la gestion des données en temps réel.

Le projet doit initialement supporter le **football et le basketball**, tout en conservant une architecture permettant d'intégrer ultérieurement d'autres sports.

---

# Objectifs du backend

Le backend poursuit plusieurs objectifs principaux.

### Architecture générique

Le modèle métier repose sur une logique permettant de représenter différents sports à partir d'entités communes :

```text
Sport
  ↓
Compétition
  ↓
Saison
  ↓
Événement sportif
  ↓
Participant
```

Cette abstraction doit permettre de gérer aussi bien les sports collectifs que les futurs sports individuels.

### Performance

Le backend doit être capable de gérer un volume important de données sportives et des mises à jour fréquentes lors des événements en direct.

L'objectif fonctionnel du projet est notamment de permettre une expérience avec un temps de chargement inférieur à **2 secondes** et une disponibilité cible d'au moins **99 %**.

### Évolutivité

L'architecture doit permettre :

* l'ajout de nouvelles disciplines ;
* l'ajout de nouvelles compétitions ;
* l'ajout de nouveaux formats de compétition ;
* l'évolution du système Fantasy ;
* l'intégration de nouvelles sources de données ;
* l'intégration progressive de fonctionnalités d'IA.

### Sécurité

Le backend doit intégrer les principes de sécurité applicative et prendre en compte notamment :

* la protection des données personnelles ;
* le RGPD ;
* la sécurisation des API ;
* la validation des données entrantes ;
* la protection contre les vulnérabilités applicatives.

---

# Fonctionnalités

## Sports et compétitions

Le backend permet de gérer :

* les sports ;
* les genres ;
* les compétitions/championnats ;
* les saisons ;
* les phases de compétition ;
* les journées ;
* les événements sportifs.

L'objectif est de conserver un modèle suffisamment générique pour accueillir de nouveaux sports.

---

## Résultats

Le backend fournit les données nécessaires à l'affichage :

* des matchs à venir ;
* des matchs en cours ;
* des matchs terminés ;
* des scores ;
* du statut d'un événement ;
* de la chronologie des événements ;
* des statistiques de match ;
* des participants et compositions.

Les résultats historiques peuvent être filtrés selon différents critères :

* saison ;
* journée ;
* phase ;
* équipe ;
* joueur ;
* domicile/extérieur.

---

## Classements

Le backend doit permettre de récupérer :

* les classements généraux ;
* les classements spécifiques à un sport ;
* les classements individuels ;
* les meilleurs buteurs/scoreurs ;
* les meilleurs passeurs ;
* les rankings ;
* les statistiques par poste ou rôle ;
* les classements des saisons précédentes.

Les données doivent être historisées afin de permettre des comparaisons entre plusieurs saisons.

---

## Utilisateurs

Le backend prend en charge :

* la création d'un compte ;
* l'authentification ;
* les préférences utilisateur ;
* les sports suivis ;
* les championnats favoris ;
* les équipes suivies ;
* les joueurs suivis ;
* les préférences de notifications ;
* les contextes favoris.

---

## Notifications

Le système doit permettre de gérer différentes catégories de notifications :

### Sportives

* début de match ;
* fin de match ;
* but ;
* blessure ;
* événement important ;
* record.

### Fantasy

* points gagnés ;
* joueur absent ou blessé ;
* rappel de composition ;
* deadline ;
* événements ayant un impact sur l'équipe Fantasy.

Les préférences de notification sont configurables par l'utilisateur.

---

# Architecture

Le backend suit une architecture en couches permettant de séparer les responsabilités.

```text
┌───────────────────────────────┐
│           Frontend            │
└───────────────┬───────────────┘
                │
                │ HTTP / REST
                ▼
┌───────────────────────────────┐
│       Controllers / API       │
├───────────────────────────────┤
│          Services             │
├───────────────────────────────┤
│          Domain /             │
│       Business Logic          │
├───────────────────────────────┤
│        Repositories           │
├───────────────────────────────┤
│        Persistence            │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│          Database             │
└───────────────────────────────┘

                ▲
                │
┌───────────────┴───────────────┐
│     External Sports APIs      │
└───────────────────────────────┘
```

Cette séparation permet de limiter le couplage entre les différentes parties de l'application et facilite les tests et l'évolution du projet.

Le référentiel demande notamment que l'architecture réponde aux cas d'utilisation, aux contraintes techniques, réglementaires et de sécurité identifiées.

---

# Modèle métier

Le modèle métier repose sur des entités génériques.

Une représentation simplifiée peut être décrite ainsi :

```text
Sport
 │
 └── Competition
       │
       └── Season
             │
             ├── Phase
             │
             └── Event
                   │
                   ├── Participant
                   ├── Statistics
                   └── Timeline
```

Pour les sports collectifs :

```text
Competition
    │
    └── Team
          │
          ├── Player
          └── Staff
```

Pour les sports individuels :

```text
Competition
    │
    └── Player / Athlete
          │
          └── Event
```

Cette conception répond à l'un des principaux risques identifiés dans le projet : la complexité de l'architecture multi-sports. Une mauvaise abstraction pourrait entraîner une forte dette technique et rendre l'évolution du backend difficile.

---

# Technologies

| Technologie                  | Utilisation                      |
| ---------------------------- | -------------------------------- |
| **Java**                     | Langage principal                |
| **Spring Boot**              | Framework backend                |
| **Spring Web**               | API REST                         |
| **Spring Data JPA**          | Accès aux données                |
| **Hibernate**                | ORM                              |
| **[SGBD à préciser]**        | Persistance des données          |
| **Maven / Gradle**           | Gestion des dépendances et build |
| **JUnit**                    | Tests unitaires                  |
| **[Outil de CI à préciser]** | Intégration continue             |
| **Git**                      | Gestion de versions              |

> Les technologies non précisées dans les documents du projet sont volontairement indiquées comme « à préciser ».

---

# Organisation du projet

Une organisation recommandée du backend :

```text
src/
├── main/
│   ├── java/
│   │   └── .../
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── mapper/
│   │       ├── exception/
│   │       ├── security/
│   │       ├── configuration/
│   │       └── integration/
│   │
│   └── resources/
│       ├── application.yml
│       └── db/
│
└── test/
    └── java/
```

### Responsabilités

**Controller**

Expose les endpoints HTTP et reçoit les requêtes du frontend.

**Service**

Contient la logique métier.

**Repository**

Gère l'accès aux données.

**Entity**

Représente les objets persistés en base.

**DTO**

Permet de contrôler les données échangées avec l'extérieur.

**Mapper**

Assure la conversion entre les entités et les DTO.

**Security**

Centralise les mécanismes d'authentification et d'autorisation.

**Integration**

Regroupe les composants permettant de communiquer avec les fournisseurs externes de données sportives.

---

# API REST

Le backend expose une API REST consommée par le frontend.

Exemples d'endpoints :

```text
/api/sports
/api/competitions
/api/seasons
/api/events
/api/teams
/api/players
/api/rankings
/api/statistics
/api/users
/api/favorites
/api/notifications
/api/fantasy
```

Exemple :

```http
GET /api/competitions/{competitionId}/seasons
```

Permet de récupérer les saisons associées à une compétition.

Exemple :

```http
GET /api/events/{eventId}
```

Permet de récupérer le détail d'un événement sportif.

---

# Gestion des données sportives

Les données sportives peuvent provenir de fournisseurs externes.

Le backend joue alors le rôle d'intermédiaire entre les sources externes et l'application.

```text
        Fournisseur externe
                │
                ▼
        Integration Layer
                │
                ▼
       Normalisation données
                │
                ▼
          Domain Model
                │
        ┌───────┴───────┐
        ▼               ▼
    Database          Cache
        │               │
        └───────┬───────┘
                ▼
              API
                │
                ▼
            Frontend
```

Cette couche d'intégration permet également de limiter la dépendance directe du domaine métier vis-à-vis d'un fournisseur particulier.

La dépendance aux API externes constitue toutefois un risque important identifié dans l'analyse des risques : indisponibilité, données incorrectes ou coûts imprévus.

---

# Temps réel

Les résultats sportifs doivent pouvoir être actualisés automatiquement sans nécessiter un rafraîchissement manuel de l'application.

Le backend doit donc pouvoir gérer :

* les événements en direct ;
* les changements de score ;
* les changements de statut ;
* les événements importants ;
* la chronologie du match.

Une architecture de communication temps réel pourra être utilisée pour transmettre les mises à jour au frontend.

Cette partie représente un enjeu important du projet puisque la gestion des données live peut provoquer de la latence et une surcharge serveur.

---

# Fantasy League

Le backend contient la logique métier de la Fantasy League.

Il doit notamment gérer :

* la création d'une équipe Fantasy ;
* le choix du championnat ;
* le budget virtuel ;
* la sélection des joueurs ;
* les contraintes de composition ;
* les transferts ;
* les capitaines ;
* les remplaçants ;
* les deadlines ;
* le scoring ;
* les bonus/malus ;
* les ligues privées ;
* les ligues publiques ;
* l'historique des performances.

Le scoring doit être suffisamment configurable pour pouvoir évoluer selon le sport.

```text
Performance réelle
        │
        ▼
   Statistiques
        │
        ▼
  Règles de scoring
        │
        ▼
 Points Fantasy
        │
        ▼
 Classement joueur
        │
        ▼
 Classement ligue
```

La Fantasy constitue également un risque métier important : la complexité des règles peut provoquer des erreurs logiques ou un déséquilibre du jeu.

---

# Authentification et sécurité

La sécurité est intégrée dès la conception du backend.

Les principaux objectifs sont :

* protéger les données personnelles ;
* sécuriser les endpoints ;
* contrôler les droits d'accès ;
* valider les données entrantes ;
* éviter l'exposition de données sensibles ;
* protéger les secrets de configuration ;
* limiter les risques liés aux entrées utilisateur.

Le backend doit notamment prendre en compte les recommandations de sécurité applicative telles que celles de l'**OWASP**, ainsi que les exigences liées au **RGPD**.

Le référentiel demande explicitement que le développement backend respecte les normes de sécurisation et les contraintes réglementaires, notamment OWASP et RGPD.

---

# Validation et gestion des erreurs

Les données reçues par l'API doivent être validées avant leur traitement.

Le backend doit retourner des réponses HTTP cohérentes.

Exemple :

```json
{
  "status": 400,
  "message": "Invalid request",
  "timestamp": "2026-08-28T15:00:00Z"
}
```

Une gestion centralisée des exceptions permet d'éviter de dupliquer la logique de gestion des erreurs dans chaque contrôleur.

Les erreurs doivent également être suffisamment documentées pour faciliter leur diagnostic.

---

# Tests

Les tests sont intégrés au cycle de développement.

Le plan de tests doit couvrir notamment :

### Tests unitaires

Vérification des composants isolés :

* services ;
* règles Fantasy ;
* calculs statistiques ;
* validation ;
* logique métier.

### Tests d'intégration

Vérification de l'interaction entre :

* API ;
* services ;
* repositories ;
* base de données ;
* composants externes.

### Tests API

Vérification des endpoints :

```text
GET
POST
PUT
PATCH
DELETE
```

### Tests de sécurité

Vérification notamment :

* authentification ;
* autorisation ;
* validation des entrées ;
* accès aux ressources protégées.

Le référentiel demande la mise en place de tests unitaires, d'intégration et de sécurité de manière itérative pendant le développement.

---

# Qualité du code

Le backend doit suivre des règles permettant de maintenir une base de code stable et évolutive.

Principes appliqués :

* séparation des responsabilités ;
* faible couplage ;
* forte cohésion ;
* nommage explicite ;
* validation des entrées ;
* gestion centralisée des erreurs ;
* tests automatisés ;
* revue de code ;
* contrôle de version avec Git.

Les indicateurs de qualité peuvent notamment inclure :

* couverture de tests ;
* nombre de bugs ;
* dette technique ;
* vulnérabilités détectées ;
* temps de build ;
* temps de réponse API.

Cette démarche répond aux exigences du référentiel concernant l'intégrité du code, les indicateurs de qualité et l'intégration de la sécurité dès le cycle de développement.

---

# Configuration

Les paramètres sensibles ne doivent pas être directement inscrits dans le code source.

Exemple :

```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}

sports:
  api:
    url: ${SPORTS_API_URL}
    key: ${SPORTS_API_KEY}
```

Les environnements peuvent être séparés :

```text
application.yml
application-dev.yml
application-test.yml
application-prod.yml
```

Les secrets doivent être fournis par l'environnement d'exécution ou un système dédié de gestion des secrets.

---

# Lancement du projet

## Prérequis

* Java **[version à préciser]**
* Maven ou Gradle
* **[SGBD à préciser]**
* Git

## Cloner le repository

```bash
git clone <URL_DU_REPOSITORY>
cd <NOM_DU_BACKEND>
```

## Configurer les variables d'environnement

Exemple :

```bash
DATABASE_URL=...
DATABASE_USERNAME=...
DATABASE_PASSWORD=...
SPORTS_API_URL=...
SPORTS_API_KEY=...
```

## Lancer l'application

Avec Maven :

```bash
./mvnw spring-boot:run
```

Ou avec Gradle :

```bash
./gradlew bootRun
```

## Exécuter les tests

Maven :

```bash
./mvnw test
```

Gradle :

```bash
./gradlew test
```

---

# CI/CD

Le backend doit être intégré dans une démarche **Agile / DevOps**.

Le pipeline doit permettre d'automatiser au minimum :

```text
Push
  │
  ▼
Build
  │
  ▼
Tests
  │
  ▼
Analyse qualité
  │
  ▼
Analyse sécurité
  │
  ▼
Package
  │
  ▼
Déploiement
```

L'intégration continue permet de détecter rapidement les erreurs introduites dans le code et de garantir la stabilité de la branche principale.

Le référentiel demande notamment un dépôt de code partagé, une gestion des branches, des builds automatisés et l'exécution automatique des tests.

---

# Monitoring et observabilité

Le backend doit pouvoir être surveillé afin de détecter rapidement les problèmes.

Les indicateurs importants sont notamment :

* disponibilité de l'application ;
* temps de réponse API ;
* taux d'erreur ;
* consommation CPU ;
* consommation mémoire ;
* nombre de requêtes ;
* état de la base de données ;
* état des intégrations externes ;
* erreurs lors de la récupération des données sportives.

Cette surveillance est particulièrement importante pour les données en temps réel et la montée en charge.

Le projet identifie en effet la scalabilité comme un risque majeur en raison de l'augmentation potentielle du nombre d'utilisateurs et du volume de données temps réel.

---

# Performance et scalabilité

Plusieurs stratégies peuvent être utilisées pour limiter la charge :

### Cache

Les données peu volatiles peuvent être mises en cache :

```text
API
 │
 ▼
Cache ─────► Database
 │
 ▼
Response
```

Exemples :

* informations des compétitions ;
* anciennes saisons ;
* classements ;
* statistiques historiques.

### Pagination

Les données volumineuses doivent être paginées afin d'éviter de charger inutilement de grandes quantités de données.

### Indexation

Les champs fréquemment utilisés dans les recherches et filtres doivent être correctement indexés.

### Optimisation des requêtes

Il faut limiter :

* les requêtes inutiles ;
* les chargements excessifs ;
* les appels externes redondants ;
* les traitements coûteux.

Ces optimisations répondent également à l'objectif de numérique responsable du projet : limiter la consommation de ressources grâce au caching et à l'optimisation des traitements.

---

# Numérique responsable

Le backend prend en compte les principes de développement numérique responsable.

Les principales actions envisagées sont :

* optimisation des requêtes SQL ;
* limitation des appels externes inutiles ;
* utilisation du cache ;
* pagination ;
* traitement efficace des données ;
* limitation des traitements redondants ;
* utilisation raisonnée des ressources serveur ;
* nettoyage et archivage des données lorsque pertinent.

Le référentiel demande explicitement que les choix d'implémentation permettent une utilisation efficace des ressources et minimisent l'impact environnemental.

---

# Risques techniques

Les principaux risques identifiés pour le backend sont :

| Risque                                  | Probabilité     | Impact     |
| --------------------------------------- | --------------- | ---------- |
| Architecture multi-sports trop complexe | Très probable   | Très grave |
| Performance / temps réel                | Presque certain | Très grave |
| Dépendance aux API externes             | Possible        | Très grave |
| Qualité des données                     | Très probable   | Grave      |
| Complexité Fantasy League               | Très probable   | Grave      |
| Surcharge des notifications             | Très probable   | Modéré     |
| Scalabilité                             | Possible        | Très grave |
| Charge globale du projet                | Presque certain | Très grave |
| Intégration de l'IA                     | Possible        | Modéré     |

Ces risques proviennent de l'analyse des risques du projet.

### Mesures de réduction

**Architecture multi-sports**

→ utiliser des modèles métier génériques et éviter les dépendances spécifiques à un seul sport.

**Temps réel**

→ limiter les traitements inutiles, mettre en place du cache et surveiller les performances.

**API externes**

→ isoler les intégrations externes dans une couche dédiée afin de limiter leur impact sur le domaine métier.

**Qualité des données**

→ mettre en place des validations et une normalisation des données.

**Fantasy**

→ isoler les règles métier et mettre en place une couverture de tests importante.

**Scalabilité**

→ surveiller les métriques, optimiser les requêtes et prévoir une architecture pouvant évoluer.

---

# Documentation

La documentation backend doit permettre à un développeur de comprendre rapidement :

* l'architecture ;
* les composants ;
* les fonctionnalités ;
* le modèle métier ;
* les API ;
* la structure de la base de données ;
* la sécurité ;
* les procédures de déploiement ;
* les procédures de tests ;
* les configurations nécessaires.

La documentation peut être complétée par :

```text
README.md
│
├── Architecture
├── API
├── Database
├── Security
├── Deployment
├── Testing
└── Troubleshooting
```

Des diagrammes UML ou BPMN peuvent également être utilisés pour représenter les composants et processus importants.

Le référentiel demande que la documentation technique décrive notamment la solution, les fonctionnalités, la structure des bases de données et le schéma général de sécurité, avec une représentation standardisée lorsque nécessaire.

---

# Évolutions prévues

L'architecture du backend doit permettre d'accueillir progressivement les évolutions prévues dans le projet.

### Nouveaux sports

Après le football et le basketball :

* handball ;
* tennis ;
* autres disciplines.

L'objectif est de ne pas avoir à reconstruire le cœur du backend pour chaque nouvelle discipline.

### Intelligence artificielle

À terme, le backend pourra intégrer des services permettant :

* de recommander des joueurs pour la Fantasy ;
* d'analyser les performances ;
* de prédire les performances d'une équipe Fantasy ;
* de comparer plusieurs saisons ;
* de générer des recommandations personnalisées.

L'intégration de l'IA est toutefois identifiée comme un risque spécifique et doit donc être introduite progressivement.

### Contenu éditorial

Une évolution du backend pourra également gérer :

* newsletters personnalisées ;
* analyses ;
* résumés ;
* contenus liés aux équipes et joueurs suivis.

---

# Relation avec le référentiel de certification

Le développement du backend permet de couvrir directement plusieurs compétences du référentiel **Expert en Architecture et Développement Logiciel**.

| Compétence                     | Application dans le backend                        |
| ------------------------------ | -------------------------------------------------- |
| **C2.1** Architecture          | Architecture générique multi-sports                |
| **C2.2** Intégrité du code     | Git, CI, tests, qualité et sécurité                |
| **C2.4** Développement backend | Java, Spring, API, base de données                 |
| **C2.5** Données massives      | Gestion de volumes importants de données sportives |
| **C3.1** Intégration continue  | Build et tests automatisés                         |
| **C3.2** Tests automatisés     | Tests unitaires, intégration et sécurité           |
| **C3.3** Surveillance          | Monitoring et suivi des performances               |
| **C3.4** Déploiement continu   | Automatisation du déploiement                      |
| **C3.5** Opérations continues  | Alertes, supervision et amélioration continue      |
| **C3.6** Documentation         | README, API, architecture et sécurité              |

Le référentiel précise notamment que le développement backend doit gérer le serveur, les bases de données, les API de liaison avec le frontend et les choix technologiques, tout en respectant les standards de sécurité et les contraintes réglementaires.

---

# Contribution

Avant toute contribution :

1. Créer une branche dédiée.
2. Implémenter la fonctionnalité.
3. Ajouter ou mettre à jour les tests.
4. Vérifier la qualité du code.
5. Vérifier les impacts sur les API.
6. Vérifier les impacts sur les données.
7. Créer une Pull Request.
8. Faire valider les changements avant intégration dans la branche principale.

Exemple :

```bash
git checkout -b feature/nom-de-la-feature
```

Puis :

```bash
git add .
git commit -m "feat: ajout de ..."
git push origin feature/nom-de-la-feature
```

---

# Licence

Projet réalisé dans le cadre du parcours de certification **Expert en Architecture et Développement Logiciel**.

La licence définitive du projet est **à préciser**.



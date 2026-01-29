Projet déjà existant:
Edition 01- Révision 01
Date : 22/09/22 - page 2/11
1. Introduction 3
1.1. Objet Du Document 3
1.2. Objectifs Du Projet 3
1.3. Contenu Du Document 3
1.4. Convention 3
2. Exigences Fonctionnelles Et Opérationnelles 4
2.1. Définitions préalables 4
2.2. Généralités et missions du système 4
2.3. Gestion des boutiques 4
2.4. Gestion des produits 6
2.5. Gestion des catégories 7
2.6. Rôle des utilisateurs (Facultatif) 7
3. Exigences Techniques 8
3.1. Architecture 8
3.2. APIs 8
3.3. Technologies 9
4. Exigences Du Projet 9
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 3/11
1. Introduction
1.1. Objet Du Document
Le présent document décrit le contexte et le périmètre du projet proposé aux étudiants du
Master « Génie de l’Informatique Logicielle » pour l’année universitaire 2022-2023 de l’UE
Full Stack. Ce document sert de référence pour présenter l'ensemble des exigences
fonctionnelles et de qualités associées au projet. Il définit également la liste des fournitures
relatives au projet.
1.2. Objectifs Du Projet
Ce projet est un système qui permet la gestion de boutiques de produits et de catégories
associées aux produits.
1.3. Contenu Du Document
La première partie du document est consacrée à une description générale du projet et à la
définition d’un certain nombre de notions relatives aux fonctionnalités ou aux technologies à
mettre en œuvre dans le projet.
La lecture de ces définitions constitue un prérequis indispensable pour la bonne
compréhension de la suite du document.
Chacune des fonctions de ce projet fait l’objet d’une description dans laquelle sont
exprimées les exigences à satisfaire. Ce document définit ensuite les exigences techniques
applicables à la conception et au développement du projet. Enfin, le document définit les
exigences de management qui devront être respectées pour la conduite du projet.
1.4. Convention
Le présent document a été rédigé en respectant les règles suivantes :
● Les exigences sont référencées selon le format suivant :
○ la lettre « E » pour « exigence » suivie d’un tiret bas « _ »,
○ trois lettres pour codifier la catégorie fonctionnelle de l'exigence, suivies d'un
tiret bas « _ »,
○ un numéro d'incrément de 10 en 10.
● Les codes utilisés au deuxième point sont les suivants:
○ BTQ pour les exigences liées à la gestion des boutiques
○ PRD pour les exigences liées à la gestions des produits
○ CAT pour les exigences liées à la gestion des catégories
○ ARC pour les exigences liées à l’architecture du système
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 4/11
○ API pour les exigences de réalisations des API
○ TEC pour les exigences techniques de réalisation du système
○ PRO pour les exigences du projet
○ ROL pour les exigences liées aux rôles des utilisateurs
2. Exigences Fonctionnelles Et Opérationnelles
2.1. Définitions préalables
Dans la suite du document, les définitions ci-après seront utilisées pour la formulation des
exigences :
● Application Programming Interface (API) : Interface de programmation permettant
d’accéder à des fonctions, des procédures ou des classes d’objets mises à
disposition par un composant logiciel.
● Simple Page Application (SPA) : Application Web exécutée dans un navigateur web
et dont la navigation est pas gérée de façon autonome (non gérée par un serveur)
● CRUD (Create, Read, Update, Delete) : Désigne les quatre opérations de base pour
la persistance des données, création, lecture, mise à jour, suppression.
● Continuous Integration / Continuous Delivery (CI/CD) : Système permettant
l'intégration et la livraison continue de composants logiciels
● IHM (Interface Homme Machine) : Composants logiciel permettant aux utilisateur
d’interagir avec le système
● SSO (Single Sign On) : Système de gestion centralisé des identités, de
l’authentification et des droits.
2.2. Généralités et missions du système
Le système doit permettre la gestion de boutiques de produits et de catégories associées
aux produits.
Les fonctionnalités devront être développées au travers d’une IHM dédiées et accessibles
au travers d’une application web (SPA) reposant sur des APIs web.
2.3. Gestion des boutiques
Le système de gestion des boutiques permet d’effectuer des opérations comme la recherche
et le tri selon différents critères, la consultation, la modification ou la suppression des
boutiques.
Les exigences ci-après sont applicables au système de gestion des boutiques.
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 5/11
E_BTQ_10 Le système permet aux utilisateurs de créer une nouvelle boutique.
Lors de la création d’une boutique, les informations minimum à renseigner sont :
● Le nom
● Les horaires d’ouverture pour les jours de la semaine
● Si la boutique est en congé
E_BTQ_20 Le système permet aux utilisateurs de sélectionner une boutique
existante et de modifier les champs renseignés lors de la création de la boutique tels que
définis par E_BTQ_10.
E_BTQ_30 Le système permet aux utilisateurs de supprimer une boutique.
E_BTQ_40 Le système permet aux utilisateurs d’associer un produit à une seule
boutique.
E_BTQ_50 Le système permet d’effectuer une recherche paginé sur toutes les
boutiques qui sont présente en affichant :
● Leur nom
● Leur date de création
● Le nombre de produits
● Le nombre de catégorie distincte qui sont associées aux produits de la
boutique
● Si la boutique est en congé
E_BTQ_60 Le système permet d’effectuer une recherche sur les boutiques avec
un ou une combinaison de filtres qui sont :
● Si la boutique est en congé
● Date de création
○ Après une date précise
○ Avant une date précise
○ Entre deux dates précises
E_BTQ_70 Le système permet d’effectuer une recherche sur les boutiques en
triant par :
● Le nom
● La date de création
● Le nombre de produits
E_BTQ_80 Le système permet d’afficher le détail d’une boutique en affichant les
champs et les entités tels que définis par E_BTQ_10, E_BTQ_40 et E_BTQ_50
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 6/11
2.4. Gestion des produits
La gestion des produits permet d’effectuer des opérations comme la recherche et le tri selon
différents critères, la consultation, la modification ou la suppression des produits.
Les exigences ci-après sont applicables au système de gestion des produits :
E_PRD_10 Le système permet aux utilisateurs de créer un nouveau produit. Lors
de la création d’un produit, les informations minimum à renseigner sont :
● Le nom
● Le prix
E_PRD_20 Le système permet aux utilisateurs de sélectionner un produit existant
et de modifier les champs renseignés lors de la création du produit tels que définis par
E_PRD_10.
E_PRD_30 Le système permet aux utilisateurs de sélectionner un produit existant
et d’ajouter une description.
E_PRD_40 Le système permet aux utilisateurs de supprimer un produit.
E_PRD_50 Le système permet aux utilisateurs d’associer une ou plusieurs
catégories à un produit.
E_PRD_60 Le système permet d’effectuer une recherche paginé sur l’ensemble
des produits appartenant à une boutique en affichant :
● Le nom
● Le prix
● Les catégories qui sont associées
● La description
E_PRD_70 Le système permet de filtrer les produits d’une boutique qui
appartiennent à une catégorie.
E_PRD_80 Le système permet aux utilisateurs d’internationaliser le nom et la
description du produit.
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 7/11
2.5. Gestion des catégories
Le système de gestion des catégories permet d’effectuer des opérations comme la
recherche et le tri selon différents critères, la consultation, la modification ou la suppression
des catégories.
E_CAT_10 Le système permet aux utilisateurs de créer une nouvelle catégorie.
Lors de la création d’une catégorie, les informations minimum à renseigner sont :
● Le nom
E_CAT_20 Le système permet aux utilisateurs de sélectionner une catégorie
existante et de modifier le champ renseigné lors de la création de la catégorie tels que
définis par E_CAT_10.
E_CAT_30 Le système permet aux utilisateurs de supprimer une catégorie.
E_CAT_40 Le système permet aux utilisateurs d’associer une catégorie à un ou
plusieurs produits.
E_CAT_50 Le système permet d’effectuer une recherche paginée sur toutes les
catégories qui sont présentes en affichant :
● Le nom
E_CAT_70 Le système permet d’afficher le détail d’une catégorie en affichant le
champ tel que défini par E_CAT_10.
2.6. Rôle des utilisateurs (Facultatif)
Les exigences ci-après sont applicables à la définition des rôles de l'application et à
l’authentification sur le système.
E_ROL_10 Le projet définit trois rôles principaux :
● un rôle anonyme : utilisateur qui est uniquement en lecture seule sur le projet
● un rôle administrateur : utilisateur ayant accès à toutes les fonctionnalités du
projet
● un rôle de vendeur-livreur : utilisateur qui possède toutes les fonctionnalités
liée à une seule boutique
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 8/11
E_ROL_20 L’accès à toutes les fonctionnalités du projet (gestion des produits,
boutiques, catégories) n’est possible que pour les utilisateurs possédant le rôle
d’administrateur.
E_ROL_30 L’accès aux fonctionnalités liées à une seule boutique n’est possible
que pour les utilisateurs possédant le rôle de vendeur-livreur.
E_ROL_40 Le rôle de vendeur-livreur permet d’effectuer des opérations de CRUD
sur sa boutique
E_ROL_50 Le rôle de vendeur-livreur permet d’effectuer des opérations de CRUD
sur tous les produits de sa boutique.
E_ROL_60 Le rôle de vendeur-livreur ne permet pas d'effectuer des opérations de
modification sur les catégories.
3. Exigences Techniques
3.1. Architecture
Le système est basé sur une API REST.
E_ARC_10 Le système est composé d’un client et d’un serveur.
E_ARC_20 Le serveur dispose de son propre système de gestion de données.
E_ARC_30 Les échanges client-serveur doivent être uniformes. Par exemple, si
les échanges entre le client et le serveur sont en JSON, alors tous les échanges doivent être
en JSON.
3.2. APIs
Le projet doit pouvoir être intégré facilement. Pour se faire, les différentes fonctionnalités
mises en œuvre doivent être accessibles au travers d’APIs web.
E_API_10 Le système met à disposition une API REST permettant d’accéder
aux fonctionnalités de gestion des boutiques détaillées en 2.3.
E_API_20 Le système met à disposition une API REST permettant d’accéder
aux gestion des produits détaillées en 2.4.
E_API_30 Le système met à disposition une API REST permettant d’accéder
aux gestion des catégories en 2.5.
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 9/11
E_API_40 Le système met à disposition des APIs sans état (RESTFull) afin de
permettre leur redondance et leur mise à l'échelle.
3.3. Technologies
E_TEC_10 Le serveur doit être développé en s’appuyant sur un framework.
E_TEC_20 Le client doit être développé en s’appuyant sur un framework.
E_TEC_30 Toute opération sur l'IHM du système doit se faire en moins de 5
secondes (affichage d'une page, etc..).
E_TEC_40 Facultatif La sécurisation du serveur développé, la gestion du login et des droits
doivent se faire en intégrant un SSO Open Source.
4. Exigences Du Projet
Pour cette UE une “tenue” de projet est obligatoire.
E_PRO_10 Des outils de gestion du code source, de configuration doivent être
mis en place. Ils intégrent :
● Une gestion du code source Git
● Une utilisation d’un workflow Git
● Une configuration docker qui permet de lancer le client, le serveur et la base
de données
E_PRO_20 Lors du lancement des conteneurs docker en local, le projet devra
alimenter le système de données d’exemple.
● Au moins 10 boutiques
● Au moins 100 produits
● Au moins 10 catégories
E_PRO_30 Le serveur doit tourner sur le port 8080.
E_PRO_30 Le client doit tourner sur le port 4200.
E_PRO_40 Facultatif Une installation client-serveur sur un serveur distant est possible,
dans ce cas le lien pour accéder à ce serveur doit être fourni dans la documentation du
projet.
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 10/11
E_PRO_50 Lors du développement du projet, du CI/CD doit être mis en place
pour toutes les tâches répétitives. Par exemple, lors d’un hotfix avec git flow une fois que le
code est sur la branche main, il faut penser à mettre à jour la branche develop.
E_PRO_60 Chaque personne participant au projet doit rédiger un document
descriptif de ses travaux personnels au sein du projet listant ses principales contributions et
en insistant fortement sur les principales difficultés rencontrées, et/ou les modes de
résolution associés.
E_PRO_70 Lors du développement, les commits git du projet doivent utiliser la
convention angular pour les commits .1
E_PRO_80 Lors du développement, le code doit être homogène, respecter les
conventions de code et l’agencement des fichiers comme recommandé par les différents
framework que vous allez utiliser.
E_PRO_90 Le serveur doit avoir une gestion des exceptions et des erreurs
permettant de prévenir le client des éventuels problèmes dans les appels API.
E_PRO_100 Le serveur doit éviter de solliciter la base de données avec plusieurs
appels successifs si un seul appel peut suffire. Par exemple, faire 10 appels différents pour
rechercher les 10 premiers produits d’une boutique, dans ce cas, un seul appel peut suffire.
E_PRO_110 Le système doit être conçu de sorte à ce qu’il soit facile à faire
évoluer. Par exemple, l’ajout d’une entité ou d’un champ dans une entité, ne doit pas
remettre en cause toute la structure du système.
E_PRO_120 Le système doit s’appuyer doit s’appuyer au maximum sur des
technologies existantes (APIs externes, librairies/framework, algorithmes.
E_PRO_130 Le client doit avoir une gestion des exceptions et des erreurs qui
permettent de prévenir l’utilisateur de sa mauvaise utilisation du système.
E_MAN_140 Une documentation complète de votre API doit être disponible et
fournie dans la documentation du projet .2
E_PRO_150 Les participants du projet doivent expliquer succintement dans la
documentation du projet le choix de la base de données qui va être utilisée.
E_PRO_160 Le projet sera rendu au format tar.gz. Le nom du dossier sera le nom
des participants du projet, il contiendra :
● Le code source et le git du client3
● Le code source et le git du serveur
3 Les dépôts git du projet pourront être séparés ou non.
2 A titre d’exemple : https://petstore.swagger.io/
1 https://github.com/angular/angular/blob/main/CONTRIBUTING.md#commit
Projet UE Full Stack- Master II GIL 2022/2023
Edition 01- Révision 01
Date : 22/09/22 - page 11/11
● Un rapport par participant du projet comme décrit pour E_PRO_60
● La configuration du docker
● La documentation du projet
● Et toute autre documentation que vous jugerez utile.
**********
projet à faire:
Edition 01- Révision 01
Date : 24/10/24 - page 1/7
UE Full Stack
TP3 UE Full Stack
TP3 UE Full Stack- Master II GIL 2024/2025
Edition 01- Révision 01
Date : 24/10/24 - page 2/7
1. Introduction 3
1.1. Objet Du Document 3
1.2. Objectifs Du Projet 3
1.3. Contenu Du Document 3
1.4. Convention 3
2. Exigences Fonctionnelles Et Opérationnelles 4
2.1. Généralités et missions du système 4
2.2. Document de référence 4
3. Exigences Techniques 4
3.1. Architecture 4
3.2. Technologies 4
4. Exigences Du Projet 4
TP3 UE Full Stack- Master II GIL 2024/2025
Edition 01- Révision 01
Date : 24/10/24 - page 3/7
1. Introduction
1.1. Objet Du Document
Le présent document décrit le contexte et le périmètre du TP3 proposé aux étudiants du
Master « Génie de l’Informatique Logicielle » pour l’année universitaire 2024-2025 de l’UE
Full Stack. Ce document sert de référence pour présenter l'ensemble des exigences
fonctionnelles et de qualités associées au TP. Il définit également la liste des fournitures
relatives au projet.
1.2. Objectifs Du Projet
Ce projet est une refonte d’une application déjà existante qui permet la gestion de
boutiques, de produits et de catégories associées aux produits.
Le document qui définit les exigences de réalisation est fourni avec ce document et est
intitulé “Projet UE Full Stack”.
1.3. Contenu Du Document
La première partie du document est consacrée à une description générale du projet et à la
définition d’un certain nombre de notions relatives aux fonctionnalités ou aux technologies à
mettre en œuvre dans le projet.
La lecture de ces définitions constitue un prérequis indispensable pour la bonne
compréhension de la suite du document.
Chacune des fonctions de ce TP fait l’objet d’une description dans laquelle sont exprimées
les exigences à satisfaire. Ce document définit ensuite les exigences techniques applicables
à la conception et au développement du projet. Enfin, le document définit les exigences de
management qui devront être respectées pour la conduite du projet.
1.4. Convention
Le présent document a été rédigé en respectant les règles suivantes :
● Les exigences sont référencées selon le format suivant :
○ la lettre « E » pour « exigence » suivie d’un tiret bas « _ »,
○ trois lettres pour codifier la catégorie fonctionnelle de l'exigence, suivies d'un
tiret bas « _ »,
○ un numéro d'incrément de 10 en 10.
● Les codes utilisés au deuxième point sont les suivants:
○ BTQ pour les exigences liées à la gestion des boutiques
○ PRD pour les exigences liées à la gestion des produits
TP3 UE Full Stack- Master II GIL 2024/2025
Edition 01- Révision 01
Date : 24/10/24 - page 4/7
○ FIX pour les exigences liées aux bug de l’application
○ AME pour les exigences liées à l’amélioration du projet
○ ARC pour les exigences liées à l’architecture du système
○ TEC pour les exigences techniques de réalisation du système
○ PRO pour les exigences du projet
2. Exigences Fonctionnelles Et Opérationnelles
2.1. Définitions préalables
Dans la suite du document, les définitions ci-après seront utilisées pour la formulation des
exigences :
● Long-term support (LTS) : Version spécifique d'un logiciel dont le support est assuré
pour une période de temps plus longue que la normale
2.2. Généralités et missions du système
Le système doit permettre la gestion de boutiques, de produits et de catégories.
Les fonctionnalités devront être développées au travers d’une IHM dédiées et accessibles
au travers d’une application web (SPA) reposant sur des APIs web.
2.3. Document de référence
Ce document reprend toutes les exigences du document fourni avec ce TP et est intitulé
“Projet UE Full Stack”, les exigences supplémentaires concernant le TP3 seront décrites
ci-dessous.
2.4. Gestion des boutiques
Le système de gestion des boutiques permet d’effectuer des opérations comme la recherche
et le tri selon différents critères, la consultation, la modification ou la suppression des
boutiques.
Les exigences ci-après sont applicables au système de gestion des boutiques.
E_BTQ_50 Le système permet d’effectuer une recherche paginé sur toutes les
boutiques qui sont présente en affichant :
● Leur nom
● Leur date de création
TP3 UE Full Stack- Master II GIL 2024/2025
Edition 01- Révision 01
Date : 24/10/24 - page 5/7
● Le nombre de produits
● Le nombre de catégorie distincte qui sont associées aux produits de la boutique1
● Si la boutique est en congé
E_BTQ_65 Le système permet d’effectuer une recherche plein texte via la base
de donnée Elasticsearch sur les boutiques avec un ou une combinaison de filtres qui sont :
● Si la boutique est en congé
● Date de création
○ Après une date précise
○ Avant une date précise
○ Entre deux dates précises
2.5. Gestion des produits
La gestion des produits permet d’effectuer des opérations comme la recherche et le tri selon
différents critères, la consultation, la modification ou la suppression des produits.
Les exigences ci-après sont applicables au système de gestion des produits :
E_PRD_15 Le système doit gérer dans son système de gestion de données les
prix des produits en centimes. (i.e. si dans le système de gestion de donnée le prix du
produit est de 1234, alors sur le client il doit être affiché 12,34€)
2.6. Bug de l’application
E_FIX_10 Le système vérifie que les horaires des boutiques n’entre pas en
conflit. Par exemple, on ne peut pas ajouter un horaire pour une boutique le lundi de 8h à
17h et le lundi de 15h à 19h.
2.7. Amélioration du projet
E_AME_10 L’existant pourra être modifiée dans le but de fournir une application
maintenable, lisible et/ou cohérente (i.e. nom de variable, fonction, modèle, ...).
E_AME_20 Des index pourront être présents en base de données pour permettre
une recherche plus rapide si la base venait à être conséquente .2
2 Une bonne pratique est d’ajouter un index sur chaque foreign key en base.
1 Donnée non présente dans le projet modifié de cette année
TP3 UE Full Stack- Master II GIL 2024/2025
Edition 01- Révision 01
Date : 24/10/24 - page 6/7
E_AME_30 Le client du système doit être responsive.
3. Exigences Techniques
3.1. Architecture
Le système est basé sur une API REST.
E_ARC_40 Le système utilise une base de données Elasticsearch en plus de son
système de gestion données initial.
E_ARC_50 La base de données Elasticsearch du système utilise le port 9200.
E_ARC_60 La base de données Postgresql sera initialisée tel que défini dans le
fichier `docker-compose.yml` dans le dossier `shop-server`.
3.2. Technologies
E_TEC_50 Le serveur doit être mis à jour avec la dernière version LTS du
framework disponible.
E_TEC_60 Le client doit être mis à jour avec la dernière version LTS du
framework disponible.
E_TEC_70 Les librairies du serveur utilisées doivent être mise à jour avec la
dernière version LTS compatible avec le framework dans lequel elles sont utilisées.
E_TEC_80 Les librairies du client utilisées doivent être mise à jour avec la
dernière version LTS compatible avec le framework dans lequel elles sont utilisées.
E_TEC_90 Les boutiques déjà présentes dans le système de gestion de données
initial doivent aussi être présentes dans la base de données Elasticsearch au lancement de
l’application ou par tout autre moyen expliqué dans la documentation fourni lors du rendu du
projet.
E_TEC_100 Lors de la mise à jour du système, une interruption du serveur est
autorisée.
4. Exigences Du Projet
E_PRO_170 Des outils de configuration doivent être mis en place. Ils intégrent :
TP3 UE Full Stack- Master II GIL 2024/2025
Edition 01- Révision 01
Date : 24/10/24 - page 7/7
● Une configuration docker qui permet de lancer le client. En plus de l’exigence
E_PRO_10
E_PRO_180 Le client doit tourner sur le port 4200.
E_PRO_190 Le client doit avoir une gestion des exceptions et des erreurs qui
permettent de prévenir l’utilisateur de sa mauvaise utilisation du système.
E_PRO_200 Une documentation de mise à jour du système doit être disponible et
fournie dans la documentation du projet .3
E_PRO_210 Le projet sera rendu au format tar.gz. Le nom du dossier sera le nom
du binôme ou de l’étudiant qui aura réalisé ce projet, il contiendra :
● Le code source et le git du client
● Le code source et le git du serveur
● Un rapport par participant du projet comme décrit pour E_PRO_60
● La configuration du docker
● La documentation du projet
● Et toute autre documentation que vous jugerez utile.
important: le prof a dit:
Pour noter ce projet, je vais lancer le docker compose avec les fichiers initiaux, puis avec la
documentation que vous m’aurez fourni je procéderais à la mise à jour du client, du serveur et des
bases de données.
******
client et serveur:
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3$ ls
docker-compose.yml  README.md  shop-client  shop-server
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3$ 
docker-compose.yml:
version: "3.9"

services:
  db:
    container_name: shop-db
    image: postgres:15
    hostname: "psql_docker"
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: "1234"
      PGDATA: /data/postgres
    volumes:
      - postgres:/data/postgres
      - ./shop-server/sql/create_tables.sql:/docker-entrypoint-initdb.d/create_tables.sql
      - ./shop-server/sql/fill_tables.sql:/docker-entrypoint-initdb.d/fill_tables.sql
    networks:
      - postgres
    
  elasticsearch:
    container_name: shop-elasticsearch
    image: docker.elastic.co/elasticsearch/elasticsearch:7.17.25
    hostname: "elasticsearch_docker"
    environment:
      discovery.type: single-node
    ports:
      - "9200:9200"
    networks:
      - postgres

  api:
    depends_on:
      - "db"
      - "elasticsearch"
    container_name: shop-server
    build: ./shop-server
    ports:
      - "8080:8080"
    networks:
      - postgres
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=1234
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - ES_URL=elasticsearch:9200

  front:
    container_name: shop-client
    build: ./shop-client
    ports:
      - "4200:4200"

networks:
  postgres:
    driver: bridge

volumes:
  postgres:
**********
shop-client:
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client$ ls
docker-compose.yml  package.json       public     src
Dockerfile          package-lock.json  README.md  tsconfig.json
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client$ 
docker-compose.yml:
version: '3.9'

services:
    front:
        container_name: shop-client
        build: .
        ports:
            - '4200:4200'
        restart: always
Dockerfile:
# Use a lighter version of Node as a parent image
FROM node:19-alpine

# Set the working directory to /app
WORKDIR /app

# copy package.json into the container at /app
COPY package.json /app/package.json

# install dependencies
RUN npm install

# Copy the current directory contents into the container at /app
COPY . /app

# Expose port
EXPOSE 4200

# Run the app when the container launches
CMD ["npm", "start"]
.env:
REACT_APP_API=http://localhost:8080/api/v1
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client$ ls
docker-compose.yml  package.json       public     src
Dockerfile          package-lock.json  README.md  tsconfig.json
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client$ cd src
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client/src$ ls
App.tsx  components  index.tsx  react-app-env.d.ts  services  utils
assets   context     pages      routes              types
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client/src$ cd ./components/
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client/src/components$ ls
ActionButtons.tsx  index.ts    ProductCard.tsx     ShopProducts.tsx
CategoryCard.tsx   Layout.tsx  SelectPaginate.tsx  SwitchLanguage.tsx
Filters.tsx        Loader.tsx  ShopCard.tsx        Toaster.tsx
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-client/src/components$ 
************
shop-server:
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server$ ls
Dockerfile  mvnw  mvnw.cmd  pom.xml  README.md  shop-app.iml  sql  src  target
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server$ 
Dockerfile:
FROM openjdk:11
ADD target/shop-app-0.0.1-SNAPSHOT.jar shop-app.jar
ENTRYPOINT ["java","-jar","shop-app.jar"]
EXPOSE 8080
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server$ ls
Dockerfile  mvnw  mvnw.cmd  pom.xml  README.md  shop-app.iml  sql  src  target
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server$ cd ./src/main/java/fr/fullstack//shopapp/
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/src/main/java/fr/fullstack/shopapp$ ls
config      exception  repository  ShopAppApplication.java  validation
controller  model      service     util
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/src/main/java/fr/fullstack/shopapp$ cd ./controller/
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/src/main/java/fr/fullstack/shopapp/controller$ ls
CategoryController.java  ProductController.java  ShopController.java
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/src/main/resources$ ls
application.properties
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/src/main/resources$ 
application.properties:
# port
server.port=8080
# database
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=1234
# jpa
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.search.backend.protocol=http
spring.jpa.properties.hibernate.search.backend.hosts=${ES_URL}
# Fix Postgres JPA Error:
# Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
# error message
server.error.include-message=always
logging.level.org.hibernate.search.query=TRACE
logging.level.org.apache.http=TRACE
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/sql$ ls
create_tables.sql  fill_tables.sql
rami@portable-rami:~/root/M2 GIL/Fullstack/TP 3/TP3/TP3/shop-server/sql$ 
********
quelles sont les technologies que je dois utiliser pour faire le projet à faire ? et je dois faire quoi en premier lieu ?
# Shop serveur

## Fonctionnalités

- API REST pour boutiques, produits, catégories
- Recherche avancée avec Elasticsearch
- Documentation Swagger

## Lancer l'application avec mvn

A la racine du répertoire, il faut exécuter : `mvn spring-boot:run`.

L'application est disponible ici : http://localhost:8080.

## Lancer l'application avec Docker

A la racine du répertoire, il faut exécuter : `docker compose up`.

L'application est disponible ici : http://localhost:8080.

## Swagger

Une documentation swagger est disponible.
Toutes les routes (boutiques, produits, catégories) sont documentées.

## Recherche Elasticsearch

Le backend utilise Hibernate Search pour indexer les boutiques dans Elasticsearch.
Vous pouvez interroger Elasticsearch directement ou utiliser Kibana (voir README principal).

## Workflow GitFlow

Ce projet suit le workflow GitFlow :

main : production
develop : développement
feature/\* : nouvelles fonctionnalités

## Documentation générale

Voir le README principal pour plus d'informations sur l'architecture, l'installation et les technologies.

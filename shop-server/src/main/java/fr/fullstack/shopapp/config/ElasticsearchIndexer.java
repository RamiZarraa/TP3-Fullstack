package fr.fullstack.shopapp.config;

import fr.fullstack.shopapp.model.Shop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ElasticsearchIndexer {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchIndexer.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Indexation automatique de toutes les boutiques au démarrage de l'application
     * Cette méthode s'exécute de manière asynchrone après le démarrage complet de
     * Spring Boot
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    @Async
    public void reindexOnStartup() {
        try {
            // Attendre 5 secondes pour s'assurer qu'Elasticsearch est prêt
            logger.info("⏳ Attente de 5 secondes avant l'indexation...");
            Thread.sleep(5000);

            logger.info("🔄 Début de l'indexation automatique Elasticsearch...");

            var searchSession = Search.session(entityManager);
            searchSession.massIndexer(Shop.class)
                    .batchSizeToLoadObjects(25)
                    .threadsToLoadObjects(1)
                    .startAndWait();

            logger.info("✅ Indexation Elasticsearch terminée avec succès !");
        } catch (InterruptedException e) {
            logger.error("❌ Erreur lors de l'indexation Elasticsearch : {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("❌ Erreur inattendue lors de l'indexation : {}", e.getMessage(), e);
        }
    }
}
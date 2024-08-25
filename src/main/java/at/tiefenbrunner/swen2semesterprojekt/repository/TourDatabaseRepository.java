package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@Log4j2
public class TourDatabaseRepository implements TourRepository {

    private final EntityManagerFactory entityManagerFactory;
    private final ConfigService configService;

    public TourDatabaseRepository(ConfigService configService) {
        this.configService = configService;

        Properties dbConfig = new Properties();
        dbConfig.setProperty("jakarta.persistence.jdbc.driver", configService.getConfigValue("db.driver"));
        dbConfig.setProperty("jakarta.persistence.jdbc.url", configService.getConfigValue("db.url"));
        dbConfig.setProperty("jakarta.persistence.jdbc.user", configService.getConfigValue("db.username"));
        dbConfig.setProperty("jakarta.persistence.jdbc.password", configService.getConfigValue("db.password"));

        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate", dbConfig);
    }

    @Override
    public Tour saveTour(Tour tour) {
        return (tour.getId() == null) ?
                insertTour(tour) :
                updateTour(tour);
    }

    private Tour insertTour(Tour newTour) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(newTour);
            entityManager.getTransaction().commit();
        }
        return newTour;
    }

    private Tour updateTour(Tour updatedTour) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(updatedTour);
            transaction.commit();
        }
        return updatedTour;
    }

    @Override
    public void deleteTour(UUID id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Tour tour = entityManager.find(Tour.class, id);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(tour);
            transaction.commit();
        }
    }

    @Override
    public List<Tour> findAllTours() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        CriteriaQuery<Tour> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(all).getResultList();
        }
    }

    @Override
    public Optional<Tour> findTourById(UUID id) {
        Tour tour;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            tour = entityManager.find(Tour.class, id);
        }
        return (tour == null) ? Optional.empty() : Optional.of(tour);
    }

    @Override
    public List<Tour> queryTours(String searchTerm) {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> query = builder.createQuery(Tour.class);
        Root<Tour> root = query.from(Tour.class);

        searchTerm = searchTerm.toLowerCase();

        Predicate termPredicate =
                builder.or(
                        builder.like(
                                builder.lower(root.get("name")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("description")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("from")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("to")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("tourType")), "%" + searchTerm + "%" // TODO: Add converted localization to search in future
                        ),
                        builder.equal(
                                root.get("distanceM").as(String.class), searchTerm
                        ),
                        builder.equal(
                                root.get("estimatedTime").as(String.class), searchTerm // TODO: Check how this works
                        )
                );
        query.where(termPredicate);

        // TODO: Add logs search

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public TourLog saveTourLog(TourLog tourLog) {
        return (tourLog.getId() == null) ?
                insertTourLog(tourLog) :
                updateTourLog(tourLog);
    }

    private TourLog insertTourLog(TourLog newTourLog) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(newTourLog);
            entityManager.getTransaction().commit();
        }
        return newTourLog;
    }

    private TourLog updateTourLog(TourLog updatedTourLog) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(updatedTourLog);
            transaction.commit();
        }
        return updatedTourLog;
    }

    @Override
    public void deleteTourLog(UUID id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TourLog tourLog = entityManager.find(TourLog.class, id);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(tourLog);
            transaction.commit();
        }
    }

    @Override
    public Optional<TourLog> findTourLogById(UUID id) {
        TourLog tourLog;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            tourLog = entityManager.find(TourLog.class, id);
        }
        return (tourLog == null) ? Optional.empty() : Optional.of(tourLog);
    }

    @Override
    public List<TourLog> findTourLogsByTourId(UUID id) {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourLog> query = builder.createQuery(TourLog.class);
        Root<TourLog> root = query.from(TourLog.class);

        Predicate termPredicate = builder.equal(root.get("tour").get("id"), id);
        query.where(termPredicate);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }
}

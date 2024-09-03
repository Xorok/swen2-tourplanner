package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourPoint;
import at.tiefenbrunner.swen2semesterprojekt.service.ConfigService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
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

    @Override
    public int deleteAllTours() {
        // TODO: Fix Foreign Key constraint error
        int deleteCount = 0;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<Tour> delete = builder.createCriteriaDelete(Tour.class);
            delete.from(Tour.class);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            deleteCount = entityManager.createQuery(delete).executeUpdate();
            transaction.commit();
        }
        return deleteCount;
    }

    @Override
    public void insertAllTours(List<Tour> entities) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            for (Tour tour : entities) {
                entityManager.merge(tour);
            }
            entityManager.getTransaction().commit();
        }
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
        criteriaQuery.select(root)
                .orderBy(criteriaBuilder.desc(root.get("id")));

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(criteriaQuery).getResultList();
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
    public List<Tour> fullTextTourSearch(String searchTerm) {
        // Get the CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> cq = cb.createQuery(Tour.class);

        // Define the root for Tour
        Root<Tour> tour = cq.from(Tour.class);

        // Join with TourLog (LEFT JOIN)
        Join<Tour, TourLog> tourLog = tour.join("tourLogs", JoinType.LEFT);

        // Define lowercase search term
        String searchTermLc = searchTerm.toLowerCase();
        // Define search term with wildcards
        String wildcardSearchTerm = "%" + searchTermLc + "%";

        // Create predicates for the Tour fields
        Predicate p1 = cb.like(cb.lower(tour.get("name")), wildcardSearchTerm);
        Predicate p2 = cb.like(cb.lower(tour.get("description")), wildcardSearchTerm);
        Predicate p3 = cb.like(cb.lower(tour.get("tourType")), wildcardSearchTerm); // TODO: Add converted localization to search in future
        Predicate p4 = cb.equal(tour.get("distanceM").as(String.class), searchTermLc);
        Predicate p5 = cb.equal(tour.get("estimatedTime").as(String.class), searchTermLc); // TODO: Check how this works

        // Create predicates for the TourLog fields
        Predicate p6 = cb.like(tourLog.get("dateTime").as(String.class), wildcardSearchTerm); // TODO: Check how this works
        Predicate p7 = cb.like(cb.lower(tourLog.get("comment")), wildcardSearchTerm);
        Predicate p8 = cb.equal(tourLog.get("totalDistanceM").as(String.class), searchTermLc);
        Predicate p9 = cb.equal(tourLog.get("totalTime").as(String.class), searchTermLc); // TODO: Check how this works
        Predicate p10 = cb.like(cb.lower(tourLog.get("difficulty")), wildcardSearchTerm); // TODO: Add converted localization to search in future
        Predicate p11 = cb.equal(tourLog.get("rating").as(String.class), searchTermLc);

        // Combine all predicates using OR
        Predicate finalPredicate = cb.or(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);

        // Set the WHERE clause
        cq.where(finalPredicate);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public void saveTourPoints(List<TourPoint> tourPoints) {
        if (tourPoints == null || tourPoints.isEmpty()) {
            throw new IllegalArgumentException("TourPoints is null or empty!");
        }
        Tour tour = tourPoints.getFirst().getTour();
        if (tour == null || tour.getId() == null) {
            throw new IllegalArgumentException("TourPoints doesn't have a valid Tour!");
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            // Delete all current points
            deleteAllTourPoints(tour.getId());

            // Convert and save new points
            for (TourPoint tourPoint : tourPoints) {
                entityManager.persist(tourPoint);
                entityManager.flush();
                entityManager.clear();
            }

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public int deleteAllTourPoints(UUID tourId) {
        int deleteCount = 0;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<TourPoint> delete = builder.createCriteriaDelete(TourPoint.class);
            Root<TourPoint> root = delete.from(TourPoint.class);

            delete.where(
                    builder.equal(
                            root.get("tour").get("id"),
                            tourId
                    )
            );

            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            deleteCount = entityManager.createQuery(delete).executeUpdate();
            transaction.commit();
        }
        return deleteCount;
    }

    @Override
    public List<TourPoint> findRouteByTourId(UUID id) {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourPoint> query = builder.createQuery(TourPoint.class);
        Root<TourPoint> root = query.from(TourPoint.class);

        Predicate termPredicate = builder.equal(root.get("tour").get("id"), id);
        query.where(termPredicate);

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

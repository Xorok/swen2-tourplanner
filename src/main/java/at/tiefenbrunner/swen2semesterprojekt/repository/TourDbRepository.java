package at.tiefenbrunner.swen2semesterprojekt.repository;


import at.tiefenbrunner.swen2semesterprojekt.repository.entities.Tour;
import at.tiefenbrunner.swen2semesterprojekt.repository.entities.TourLog;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourDbRepository implements TourRepository {

    private final EntityManagerFactory entityManagerFactory;

    public TourDbRepository() {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("hibernate");
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
    public Tour saveTour(Tour tour) {
        return (tour.getId() == null) ?
                insert(tour) :
                update(tour);
    }

    private Tour update(Tour updatedTour) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(updatedTour);
            transaction.commit();
        }
        return updatedTour;
    }

    private Tour insert(Tour newTour) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(newTour);
            transaction.commit();
        }
        return newTour;
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
    public Optional<Tour> findTourById(UUID id) {
        Tour tour;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            tour = entityManager.find(Tour.class, id);
        }
        return Optional.of(tour);
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
                                builder.lower(root.get("t_name")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("t_desc")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("t_from")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("t_to")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("t_tt_type")), "%" + searchTerm + "%" // TODO: Add converted localization to search in future
                        ),
                        builder.like(
                                builder.lower(root.get("t_distance_m")), "%" + searchTerm + "%"
                        ),
                        builder.like(
                                builder.lower(root.get("t_time_min")), "%" + searchTerm + "%"
                        )
                );
        query.where(termPredicate);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public List<TourLog> findLogsByTourId(UUID id) {
        CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourLog> query = builder.createQuery(TourLog.class);
        Root<TourLog> root = query.from(TourLog.class);

        Predicate termPredicate = builder.equal(root.get("tl_t_tour"), id);
        query.where(termPredicate);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }
}

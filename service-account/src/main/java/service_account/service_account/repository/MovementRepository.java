package service_account.service_account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service_account.service_account.model.Movement;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, UUID> {

    @Query("SELECT m " +
            "FROM Movement m WHERE m.date BETWEEN :dateFrom AND :dateTo AND m.account.clientId = :clientId")
    List<Movement> findMovementsByClientIdAndDateRange(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("clientId") UUID clientId);
}
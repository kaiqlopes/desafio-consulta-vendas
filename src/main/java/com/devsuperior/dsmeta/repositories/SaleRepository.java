package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT sa.id AS id, sa.date AS date, sa.amount AS amount, s.name AS sellerName FROM tb_sales AS sa " +
            "INNER JOIN tb_seller AS s ON sa.seller_id = s.id " +
            "WHERE date BETWEEN :initialDate AND :finalDate " +
            "AND LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "ORDER BY sa.date")
    List<SaleReportProjection> getReport(@Param("initialDate") LocalDate initialDate, @Param("finalDate") LocalDate finalDate, @Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT s.name AS sellerName, SUM(sa.amount) AS total FROM tb_sales AS sa " +
            "INNER JOIN tb_seller AS s ON sa.seller_id = s.id " +
            "WHERE sa.date BETWEEN :initialDate AND :finalDate " +
            "GROUP BY s.name " +
            "ORDER BY s.name")
    List<SaleSummaryProjection> getSummary(@Param("initialDate") LocalDate initialDate, @Param("finalDate") LocalDate finalDate);
}

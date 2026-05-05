package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

/**
 * @author David Oliva Huelamo
 *
 */
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

  @Query("""
        SELECT COUNT(l) > 0 FROM Loan l
        WHERE l.game.id = :gameId
        AND l.startDate <= :endDate
        AND l.endDate >= :startDate
        AND (:loanId IS NULL OR l.id <> :loanId)
    """)
  boolean existsGameOverlap(Long gameId, LocalDate startDate, LocalDate endDate, Long loanId);

  @Query("""
        SELECT COUNT(l) FROM Loan l
        WHERE l.client.id = :clientId
        AND l.startDate <= :endDate
        AND l.endDate >= :startDate
        AND (:loanId IS NULL OR l.id <> :loanId)
    """)
  long countClientLoans(Long clientId, LocalDate startDate, LocalDate endDate, Long loanId);

  @Query("""
        SELECT l FROM Loan l
        WHERE (:clientId IS NULL OR l.client.id = :clientId)
          AND (:gameId IS NULL OR l.game.id = :gameId)
          AND (
               :date IS NULL OR
               (l.startDate <= :date AND l.endDate >= :date)
          )
    """)
  Page<Loan> findByFilters(Long clientId, Long gameId, LocalDate date, Pageable pageable);

}

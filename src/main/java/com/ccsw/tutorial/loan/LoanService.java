package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

/**
 * @author David Oliva Huelamo
 *
 */
public interface LoanService {

  List<Loan> findAll();

  Page<Loan> findAll(int page, int size, Long clientId, Long gameId, LocalDate date);

  Loan get(Long id);

  void save(Long id, LoanDto dto);

  void delete(Long id);
}

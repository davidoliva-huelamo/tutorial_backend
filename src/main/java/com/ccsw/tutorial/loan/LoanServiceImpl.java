package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author David Oliva Huelamo
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

  private static final int MAX_DAYS = 14;

  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private ClientService clientService;

  @Autowired
  private GameService gameService;

  @Override
  public Page<Loan> findAll(int page, int size, Long clientId, Long gameId, LocalDate date) {

    Pageable pageable = PageRequest.of(page, size);

    return loanRepository.findByFilters(clientId, gameId, date, pageable);
  }

  @Override
  public List<Loan> findAll() {
    return loanRepository.findAll();
  }

  @Override
  public Loan get(Long id) {
    return loanRepository.findById(id).orElse(null);
  }

  @Override
  public void save(Long id, LoanDto dto) {

    if (dto.getEndDate().isBefore(dto.getStartDate())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin no puede ser anterior a la fecha inicio");
    }

    long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
    if (days > MAX_DAYS) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El préstamo no puede superar los 14 días");
    }

    if (loanRepository.existsGameOverlap(dto.getGameId(), dto.getStartDate(), dto.getEndDate(), id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El juego ya está prestado en ese periodo");
    }

    if (loanRepository.countClientLoans(dto.getClientId(), dto.getStartDate(), dto.getEndDate(), id) >= 2) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no puede tener más de 2 préstamos");
    }

    Loan loan = (id == null) ? new Loan() : get(id);

    loan.setClient(clientService.get(dto.getClientId()));
    loan.setGame(gameService.get(dto.getGameId()));
    loan.setStartDate(dto.getStartDate());
    loan.setEndDate(dto.getEndDate());

    loanRepository.save(loan);
  }

  @Override
  public void delete(Long id) {
    loanRepository.deleteById(id);
  }

}

package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author David Oliva Huelamo
 *
 */
@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanController {

  @Autowired
  LoanService loanService;

  @Autowired
  ModelMapper mapper;

  @GetMapping("")
  public Page<LoanDto> findAll(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) Long clientId, @RequestParam(required = false) Long gameId,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return loanService.findAll(page, size, clientId, gameId, date).map(this::mapToDto);
  }

  @PostMapping("")
  public void create(@RequestBody LoanDto dto) {
    loanService.save(null, dto);
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody LoanDto dto) {
    loanService.save(id, dto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    loanService.delete(id);
  }

  private LoanDto mapToDto(Loan loan) {
    LoanDto dto = new LoanDto();

    dto.setId(loan.getId());

    dto.setClientId(loan.getClient().getId());
    dto.setClientName(loan.getClient().getName());

    dto.setGameId(loan.getGame().getId());
    dto.setGameName(loan.getGame().getTitle());

    dto.setStartDate(loan.getStartDate());
    dto.setEndDate(loan.getEndDate());

    return dto;
  }

}

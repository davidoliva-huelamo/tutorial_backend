package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;

/**
 * @author David Oliva Huelamo
 *
 */
public class LoanDto {

  private Long id;
  private Long clientId;
  private String clientName;

  private Long gameId;
  private String gameName;

  private LocalDate startDate;
  private LocalDate endDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getGameId() {
    return gameId;
  }

  public void setGameId(Long gameId) {
    this.gameId = gameId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public String getGameName() {
    return gameName;
  }
}

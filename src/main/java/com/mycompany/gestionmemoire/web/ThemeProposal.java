package com.mycompany.gestionmemoire.web;

public class ThemeProposal {
  private final long id;
  private final String title;
  private final String description;
  private final String keywords;
  private final String comparisonText;
  private final String ownerName;
  private final double similarityPercent;
  private String status;
  private String decisionNote = "";

  public ThemeProposal(
      long id,
      String title,
      String description,
      String keywords,
      String comparisonText,
      String ownerName,
      double similarityPercent,
      String status) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.keywords = keywords;
    this.comparisonText = comparisonText;
    this.ownerName = ownerName;
    this.similarityPercent = similarityPercent;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getKeywords() {
    return keywords;
  }

  public String getComparisonText() {
    return comparisonText;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public double getSimilarityPercent() {
    return similarityPercent;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDecisionNote() {
    return decisionNote;
  }

  public void setDecisionNote(String decisionNote) {
    this.decisionNote = decisionNote;
  }
}

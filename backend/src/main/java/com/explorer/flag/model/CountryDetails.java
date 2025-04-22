package com.explorer.flag.model;

public class CountryDetails {
  private String name;
  private String capital;
  private int population;
  private String flag;

  public CountryDetails(String name, String capital, int population, String flag) {
    this.name = name;
    this.capital = capital;
    this.population = population;
    this.flag = flag;
  }

  public String getName() { return name; }
  public String getCapital() { return capital; }
  public int getPopulation() { return population; }
  public String getFlag() { return flag; }
}

package com.explorer.flag.model;


/**
 * Country model used in the /countries endpoint
 */

public class Country {

  public Country(String name, String flag) {
    this.name = name;
    this.flag = flag;
  }

  private String name;
  private String flag;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }
}

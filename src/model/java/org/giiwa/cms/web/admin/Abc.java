package org.giiwa.cms.web.admin;

public class Abc {

  public static <T extends Number> T get() {
    Object o = new Integer(1);
    return (T) o;
  }

  public static void main(String[] args) {
    Object o = get();
    System.out.println(o);
  }
}

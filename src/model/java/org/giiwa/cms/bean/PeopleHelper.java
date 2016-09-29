package org.giiwa.cms.bean;

import org.giiwa.core.bean.Helper.W;

public class PeopleHelper {

  long uid;

  public PeopleHelper(long uid) {
    this.uid = uid;
  }

  public String get(String name) {
    People p = People.load(W.create("uid", uid).and("name", name));
    if (p != null) {
      return p.val;
    }
    return name;
  }
}

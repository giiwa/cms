package org.giiwa.cms.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;

@Table(name = "cms_people")
public class People extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "uid")
  long                      uid;

  @Column(name = "name")
  String                    name;

  @Column(name = "val")
  String                    val;

  public static People load(W q) {
    return Helper.load(q, People.class);
  }

}

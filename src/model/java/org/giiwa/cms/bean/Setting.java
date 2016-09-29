package org.giiwa.cms.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;

@Table(name = "cms_setting")
public class Setting extends Bean {

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

  
  public String getVal() {
    return val;
  }


  public static Setting load(W q) {
    return Helper.load(q, Setting.class);
  }

}

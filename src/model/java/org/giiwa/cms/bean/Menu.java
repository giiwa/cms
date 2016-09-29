package org.giiwa.cms.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Table;

@Table(name = "cms_menu")
public class Menu extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "uid")
  long                      uid;

  @Column(name = "seq")
  int                       seq;

  @Column(name = "title")
  String                    title;

  @Column(name = "url")
  String                    url;

}

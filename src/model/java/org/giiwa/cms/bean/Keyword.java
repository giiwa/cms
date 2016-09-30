package org.giiwa.cms.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Table;

@Table(name = "cms_keyword")
public class Keyword extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "uid")
  long                      uid;
  @Column(name = "word")
  String                    word;
  @Column(name = "count")
  long                      count;

  public static void repair(long uid) {

  }

}

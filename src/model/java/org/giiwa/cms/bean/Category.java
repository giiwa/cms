package org.giiwa.cms.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;

@Table(name = "cms_menu")
public class Category extends Bean {

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

  public static Object load(W q, int s, int n) {
    Beans<Category> bs = Helper.load(q, s, n, Category.class);
    if (bs != null) {
      return bs.getList();
    }
    return null;
  }

}

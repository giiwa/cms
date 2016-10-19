package org.giiwa.cms.bean;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;

@Table(name = "tbl_test")
public class TestBean extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "id")
  long                      id;

  @Column(name = "name")
  String                    name;

  public static long create(V v) {
    long id = UID.next("testbean.id");
    try {
      while (Helper.exists(id, TestBean.class)) {
        id = UID.next("testbean.id");
      }
      if (Helper.insert(v.set("id", id), TestBean.class) > 0) {
        return id;
      }
    } catch (Exception e) {
      log.error(v.toString(), e);
    }
    return -1;
  }

  public static TestBean load(long id) {
    return Helper.load(id, TestBean.class);
  }

  public static TestBean load(W q) {
    return Helper.load(q, TestBean.class);
  }

  public static Beans<TestBean> load(W q, int offset, int limit) {
    return Helper.load(q, offset, limit, TestBean.class);
  }

  public static void delete(long id) {
    Helper.delete(id, TestBean.class);
  }

  public static int update(long id, V v) {
    return Helper.update(id, v, TestBean.class);
  }

  public static int update(W q, V v) {
    return Helper.update(q, v, TestBean.class);
  }

}

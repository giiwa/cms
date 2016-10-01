package org.giiwa.cms.bean;

import java.util.List;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.task.Task;

@Table(name = "cms_tag")
public class Tag extends Bean {

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

  public static void repair(final long uid) {
    new Task() {
      @Override
      public void onExecute() {

      }

    }.schedule(0);
  }

  public static List<Tag> load(W q, int s, int n) {
    Beans<Tag> bs = Helper.load(q, s, n, Tag.class);
    return bs == null ? null : bs.getList();
  }

}

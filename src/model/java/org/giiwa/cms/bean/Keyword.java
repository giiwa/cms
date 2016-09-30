package org.giiwa.cms.bean;

import java.util.List;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.task.Task;

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

  public static void repair(final long uid) {
    new Task() {
      @Override
      public void onExecute() {

      }

    }.schedule(0);
  }

  public static List<Keyword> load(W q, int s, int n) {
    Beans<Keyword> bs = Helper.load(q, s, n, Keyword.class);
    return bs == null ? null : bs.getList();
  }

}

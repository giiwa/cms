package org.giiwa.cms.bean;

import java.sql.SQLException;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;

@Table(name = "gi_article")
public class Article extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = X.ID)
  long                      id;

  @Column(name = "folderid")
  long                      folderid;

  @Column(name = "title")
  String                    title;

  @Column(name = "content")
  String                    content;

  @Column(name = "seq")
  int                       seq;

  private Folder            folder_obj;

  public Folder getFolder_obj() {
    if (folder_obj == null) {
      folder_obj = Folder.load(folderid);
    }
    return folder_obj;
  }

  public static Beans<Article> load(W q, int s, int n) {
    return Helper.load(q, s, n, Article.class);
  }

  public static void delete(long id) {
    Helper.delete(id, Article.class);
  }

  public static long create(V v) {
    try {
      long id = UID.next("article.id");
      while (exists(id)) {
        id = UID.next("article.id");
      }
      Helper.insert(v.set(X.ID, id), Article.class);
      return id;
    } catch (Exception e) {
      log.error(v.toString(), e);
    }
    return 0;
  }

  public static boolean exists(long id) throws SQLException {
    return Helper.exists(id, Article.class);
  }

  public static int update(long id, V v) {

    return Helper.update(id, v, Article.class);

  }

  public static Article load(long id) {
    return Helper.load(id, Article.class);
  }

}

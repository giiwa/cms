package org.giiwa.cms.bean;

import java.sql.SQLException;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;
import org.giiwa.core.bean.X;
import org.giiwa.framework.bean.User;

@Table(name = "gi_comment")
public class Comment extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "uid")
  long                      uid;
  @Column(name = "aid")
  long                      aid;
  @Column(name = "content")
  String                    content;

  private User              user_obj;

  public User getUser_obj() {
    if (user_obj == null) {
      user_obj = User.loadById(uid);
    }
    return user_obj;
  }

  public long getUid() {
    return uid;
  }

  public long getAid() {
    return aid;
  }

  public String getContent() {
    return content;
  }

  public static long create(V v) {
    try {
      long id = UID.next("comment.id");
      while (exists(id)) {
        id = UID.next("comment.id");
      }

      Helper.insert(v.set(X.ID, id), Comment.class);

      return id;
    } catch (Exception e) {
      log.error(v.toString(), e);
    }
    return -1;
  }

  private static boolean exists(long id) throws SQLException {
    return Helper.exists(id, Comment.class);
  }

  public static Beans<Comment> load(W q, int s, int n) {
    return Helper.load(q, s, n, Comment.class);
  }

}

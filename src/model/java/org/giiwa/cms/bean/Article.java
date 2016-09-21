package org.giiwa.cms.bean;

import java.sql.SQLException;
import java.util.List;

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

  @Column(name = "reads")
  long                      reads;

  @Column(name = "likes")
  long                      likes;

  @Column(name = "commentable")
  String                    commentable;

  private Comment           lastcomment;

  public Comment getLastcomment() {
    if (lastcomment == null) {
      lastcomment = Helper.load(W.create("aid", id).sort("created", -1), Comment.class);
    }
    return lastcomment;
  }

  public long getReads() {
    return reads;
  }

  public long getLikes() {
    return likes;
  }

  private List<Comment> comments;

  public List<Comment> getComments() {
    if (comments == null) {
      Beans<Comment> bs = Comment.load(W.create("aid", id), 0, 10);
      comments = bs == null ? null : bs.getList();
    }
    return comments;
  }

  public String getCommentable() {
    return commentable;
  }

  public long getId() {
    return id;
  }

  public long getFolderid() {
    return folderid;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public int getSeq() {
    return seq;
  }

  private Folder folder_obj;

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

  @Table(name = "gi_article_like")
  public static class Like extends Bean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static long up(long aid, String sid, long uid) {
      try {
        if (uid > 0) {
          if (exists(W.create("aid", aid).and("uid", uid))) {
            return -1;
          }
        } else {
          if (exists(W.create("aid", aid).and("sid", sid))) {
            return -1;
          }
        }

        Helper.insert(V.create("aid", aid).set("uid", uid).set("sid", sid), Like.class);

        long count = Helper.count(W.create("aid", aid), Like.class);

        Article.update(aid, V.create("likes", count));

        return count;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }

      return -1;
    }

    public static boolean exists(W q) throws SQLException {
      return Helper.exists(q, Like.class);
    }
  }

  @Table(name = "gi_article_read")
  public static class Read extends Bean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static long up(long aid, String sid, long uid) {
      try {
        if (uid > 0) {
          if (exists(W.create("aid", aid).and("uid", uid))) {
            return -1;
          }
        } else {
          if (exists(W.create("aid", aid).and("sid", sid))) {
            return -1;
          }
        }

        Helper.insert(V.create("aid", aid).set("uid", uid).set("sid", sid), Read.class);

        long count = Helper.count(W.create("aid", aid), Read.class);
        Article.update(aid, V.create("reads", count));
        return count;

      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }

      return -1;
    }

    public static boolean exists(W q) throws SQLException {
      return Helper.exists(q, Read.class);
    }
  }

}

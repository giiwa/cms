/*
 * Copyright 2015 JIHU, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.giiwa.cms.bean;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.X;
import org.giiwa.framework.bean.User;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;

@Table(name = "cms_article")
public class Article extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = X.ID)
  long                      id;

  @Column(name = "folderid")
  long                      folderid;

  @Column(name = "keywords")
  String                    keywords;

  @Column(name = "category")
  String                    category;

  @Column(name = "uid")
  long                      uid;

  @Column(name = "title")
  String                    title;

  @Column(name = "img")
  String                    img;

  @Column(name = "text")
  String                    text;

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

  public String getCategory() {
    return category;
  }

  public String getImg() {
    return img;
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

  public String getText() {
    return text;
  }

  public int getSeq() {
    return seq;
  }

  private Set<String> keywords_obj;

  public Set<String> getKeywords_obj() {
    if (keywords_obj == null && !X.isEmpty(keywords)) {
      keywords_obj = new TreeSet<String>();
      String[] ss = keywords.split("[,; ，； ]");
      for (String s : ss) {
        if (!X.isEmpty(s)) {
          keywords_obj.add(s);
        }
      }
    }
    return keywords_obj;
  }

  public String getKeywords() {
    return keywords;
  }

  private User user_obj;

  public User getUser_obj() {
    if (user_obj == null) {
      user_obj = User.loadById(uid);
    }
    return user_obj;
  }

  public long getUid() {
    return uid;
  }

  private Folder folder_obj;

  public Folder getFolder_obj() {
    if (folder_obj == null) {
      folder_obj = Folder.load(folderid);
    }
    return folder_obj;
  }

  /**
   * Load.
   *
   * @param q
   *          the q
   * @param s
   *          the s
   * @param n
   *          the n
   * @return the beans
   */
  public static Beans<Article> load(W q, int s, int n) {
    return Helper.load(q, s, n, Article.class);
  }

  /**
   * Delete.
   *
   * @param id
   *          the id
   */
  public static void delete(long id) {
    Helper.delete(id, Article.class);
  }

  /**
   * Creates the.
   *
   * @param v
   *          the v
   * @return the long
   */
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

  /**
   * Exists.
   *
   * @param id
   *          the id
   * @return true, if successful
   * @throws SQLException
   *           the SQL exception
   */
  public static boolean exists(long id) throws SQLException {
    return Helper.exists(id, Article.class);
  }

  /**
   * Update.
   *
   * @param id
   *          the id
   * @param v
   *          the v
   * @return the int
   */
  public static int update(long id, V v) {

    return Helper.update(id, v, Article.class);

  }

  /**
   * Load.
   *
   * @param id
   *          the id
   * @return the article
   */
  public static Article load(long id) {
    return Helper.load(id, Article.class);
  }

  @Table(name = "gi_article_like")
  public static class Like extends Bean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Up.
     *
     * @param aid
     *          the aid
     * @param sid
     *          the sid
     * @param uid
     *          the uid
     * @return the long
     */
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

    /**
     * Exists.
     *
     * @param q
     *          the q
     * @return true, if successful
     * @throws SQLException
     *           the SQL exception
     */
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

    /**
     * Up.
     *
     * @param aid
     *          the aid
     * @param sid
     *          the sid
     * @param uid
     *          the uid
     * @return the long
     */
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

    /**
     * Exists.
     *
     * @param q
     *          the q
     * @return true, if successful
     * @throws SQLException
     *           the SQL exception
     */
    public static boolean exists(W q) throws SQLException {
      return Helper.exists(q, Read.class);
    }
  }

  public static List<Article> load(W q, int n) {
    Beans<Article> bs = Article.load(q, 0, n);
    return bs == null ? null : bs.getList();
  }

}

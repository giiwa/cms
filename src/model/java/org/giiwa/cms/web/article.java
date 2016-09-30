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
package org.giiwa.cms.web;

import org.giiwa.cms.bean.Article;
import org.giiwa.cms.bean.Category;
import org.giiwa.cms.bean.Comment;
import org.giiwa.cms.bean.Folder;
import org.giiwa.cms.bean.SettingHelper;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.json.JSON;
import org.giiwa.framework.bean.User;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

// TODO: Auto-generated Javadoc
/**
 * web api: /demo
 * 
 * @author joe
 * 
 */
public class article extends Model {

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.framework.web.Model#onGet()
   */
  @Path()
  public void onGet() {
    long id = this.getLong("id");
    Article a = Article.load(id);
    User u = this.getUser();
    this.set("me", u);

    if (u != null && method.isPost() && X.isSame("on", a.getCommentable())) {
      String content = this.getString("content");
      Comment c = a.getLastcomment();
      if (c == null || u.getId() != c.getUid() || !X.isSame(content, c.getContent())) {
        Comment.create(V.create("aid", id).set("uid", u.getId()).set("content", content));
      }
    }

    Article.Read.up(id, sid(), u == null ? -1 : u.getId());

    this.set("a", a);

    this.set("user", a.getUser_obj());
    this.set("helper", new SettingHelper(a.getUid()));
    _usage(a.getUid());

    this.show("/cms/article.html");
  }

  private void _usage(long uid) {
    
    this.set("cates", Category.load(W.create("uid", uid).sort("seq", 1), 0, 100));
    this.set("latest", Article.load(W.create("uid", uid).sort("created", -1), 5));
    this.set("hotest", Article.load(W.create("uid", uid).sort("updated", -1), 5));
    this.set("categories", Category.load(W.create("uid", uid).sort("title", 1), 0, 5));
    
  }

  /**
   * Folder.
   */
  @Path(path = "folder")
  public void folder() {
    JSON jo = JSON.create();

    int s = this.getInt("s");
    int n = this.getInt("n", 20);
    long parentid = this.getLong("parentid");
    W q = W.create("parentid", parentid).sort("seq", 1).sort("name", 1);
    Beans<Folder> bs = Folder.load(q, s, n);

    if (bs != null) {
      jo.put("list", bs.getList());
    }
    jo.put("s", s);
    jo.put("n", n);
    jo.put("total", Helper.count(q, Folder.class));
    jo.put(X.STATE, 200);

    this.response(jo);
  }

  /**
   * List.
   */
  @Path(path = "list")
  public void list() {
    JSON jo = JSON.create();

    int s = this.getInt("s");
    int n = this.getInt("n", 20);
    long folderid = this.getLong("folderid");
    W q = W.create("folderid", folderid).sort("seq", 1).sort("updated", -1);
    Beans<Article> bs = Article.load(q, s, n);

    if (bs != null) {
      jo.put("list", bs.getList());
    }
    jo.put("s", s);
    jo.put("n", n);
    jo.put("folderid", folderid);
    jo.put("total", Helper.count(q, Article.class));
    jo.put(X.STATE, 200);

    this.response(jo);
  }

  /**
   * Comments.
   */
  @Path(path = "comments")
  public void comments() {
    JSON jo = JSON.create();

    int s = this.getInt("s");
    int n = this.getInt("n", 20);
    long aid = this.getLong("aid");
    W q = W.create("aid", aid).sort("updated", 1);
    Beans<Comment> bs = Comment.load(q, s, n);

    if (bs != null) {
      jo.put("list", bs.getList());
    }
    jo.put("s", s);
    jo.put("n", n);
    jo.put("aid", aid);
    jo.put("total", Helper.count(q, Comment.class));
    jo.put(X.STATE, 200);

    this.response(jo);
  }

  /**
   * Gets the.
   */
  @Path(path = "get")
  public void get() {
    JSON jo = JSON.create();

    long id = this.getLong("id");
    Article a = Article.load(id);
    if (a != null) {
      jo.put("data", a);
      jo.put("id", id);
      jo.put(X.STATE, 200);
    } else {
      jo.put(X.MESSAGE, "id missed");
      jo.put(X.STATE, 201);
    }

    this.response(jo);
  }

  /**
   * Read.
   */
  @Path(path = "read")
  public void read() {
    JSON jo = JSON.create();

    long id = this.getLong("id");
    User u = this.getUser();
    long count = Article.Read.up(id, sid(), u == null ? -1 : u.getId());
    if (count > 0) {
      Article.update(id, V.create("reads", count));
      jo.put("reads", count);
      jo.put("id", id);
      jo.put(X.STATE, 200);
    } else {
      jo.put("id", id);
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "already read");
    }

    this.response(jo);
  }

  /**
   * Like.
   */
  @Path(path = "like")
  public void like() {
    JSON jo = JSON.create();

    long id = this.getLong("id");
    User u = this.getUser();
    long count = Article.Like.up(id, sid(), u == null ? -1 : u.getId());
    if (count > 0) {
      jo.put("likes", count);
      jo.put("id", id);
      jo.put(X.STATE, 200);
    } else {
      jo.put("id", id);
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, "already like");
    }

    this.response(jo);
  }

}

package org.giiwa.cms.web;

import org.giiwa.cms.bean.Article;
import org.giiwa.cms.bean.Folder;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.json.JSON;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

/**
 * web api: /demo
 * 
 * @author joe
 * 
 */
public class article extends Model {

  @Path()
  public void onGet() {
    long id = this.getLong("id");
    Article a = Article.load(id);
    this.set("a", a);
    this.show("/cms/article.html");
  }

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

  @Path(path = "read")
  public void read() {
    JSON jo = JSON.create();

    long id = this.getLong("id");
    Article.update(id, V.create());

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

}

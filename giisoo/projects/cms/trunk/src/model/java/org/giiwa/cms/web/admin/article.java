package org.giiwa.cms.web.admin;

import org.giiwa.cms.bean.Article;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class article extends Model {

  @Path(login = true, access = "access.cms.admin")
  public void onGet() {
    int s = this.getInt("s");
    int n = this.getInt("n", 20, "number.per.page");

    W q = W.create();
    long folderid = this.getLong("folderid");
    if (folderid > 0) {
      q.and("folderid", folderid);
      this.set("folderid", folderid);
    }

    String title = this.getString("title");

    if (!X.isEmpty(title) && X.isEmpty(path)) {
      q.and("title", title, W.OP_LIKE);
      this.set("title", title);
    }
    Beans<Article> bs = Article.load(q, s, n);
    this.set(bs, s, n);

    this.show("/admin/article.index.html");
  }

  @Path(path = "delete", login = true, access = "access.cms.admin")
  public void delete() {
    long id = this.getLong("id");
    Article.delete(id);
    JSON jo = new JSON();
    jo.put(X.STATE, 200);
    this.response(jo);
  }

  @Path(path = "create", login = true, access = "access.cms.admin")
  public void create() {
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "title");
      v.set("seq", this.getInt("seq"));
      v.set("folderid", this.getLong("folderid"));
      v.set("content", this.getHtml("content"));
      long id = Article.create(v);

      this.set(X.MESSAGE, lang.get("create.success"));
      onGet();
      return;
    }

    long folderid = this.getLong("folderid");
    this.set("folderid", folderid);
    this.show("/admin/article.create.html");
  }

  @Path(path = "edit", login = true, access = "access.cms.admin")
  public void edit() {
    long id = this.getLong("id");
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "title");
      v.set("seq", this.getInt("seq"));
      v.set("content", this.getHtml("content"));
      Article.update(id, v);

      this.set(X.MESSAGE, lang.get("save.success"));
      onGet();
      return;
    }

    Article d = Article.load(id);
    this.set(d.getJSON());
    this.set("id", id);
    this.show("/admin/article.edit.html");
  }

}

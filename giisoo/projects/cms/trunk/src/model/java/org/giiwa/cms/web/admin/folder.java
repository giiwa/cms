package org.giiwa.cms.web.admin;

import org.giiwa.cms.bean.Folder;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class folder extends Model {

  @Path(login = true, access = "access.cms.admin")
  public void onGet() {
    int s = this.getInt("s");
    int n = this.getInt("n", 20, "number.per.page");

    W q = W.create();
    String name = this.getString("name");

    if (!X.isEmpty(name) && X.isEmpty(path)) {
      q.and("name", name, W.OP_LIKE);
      this.set("name", name);
    }
    Beans<Folder> bs = Folder.load(q, s, n);
    this.set(bs, s, n);

    this.show("/admin/folder.index.html");
  }

  @Path(path = "delete", login = true, access = "access.cms.admin")
  public void delete() {
    long id = this.getLong("id");
    Folder.delete(id);
    JSON jo = new JSON();
    jo.put(X.STATE, 200);
    this.response(jo);
  }

  @Path(path = "create", login = true, access = "access.cms.admin")
  public void create() {
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "name");
      v.set("seq", this.getInt("seq"));
      v.set("commentable", X.isSame("on", this.getString("commentable")) ? "on" : "off");
      v.set("parentid", this.getLong("parentid"));
      v.set("content", this.getHtml("content"));
      long id = Folder.create(v);

      this.set(X.MESSAGE, lang.get("create.success"));
      onGet();
      return;
    }

    long parentid = this.getLong("parentid");
    this.set("parentid", parentid);
    this.show("/admin/folder.create.html");
  }

  @Path(path = "edit", login = true, access = "access.cms.admin")
  public void edit() {
    long id = this.getLong("id");
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "name");
      v.set("seq", this.getInt("seq"));
      v.set("commentable", X.isSame("on", this.getString("commentable")) ? "on" : "off");
      v.set("content", this.getHtml("content"));
      Folder.update(id, v);

      this.set(X.MESSAGE, lang.get("save.success"));
      onGet();
      return;
    }

    Folder d = Folder.load(id);
    this.set(d.getJSON());
    this.set("id", id);
    this.show("/admin/folder.edit.html");
  }

}

package org.giiwa.cms.web.cms;

import org.giiwa.cms.bean.Category;
import org.giiwa.cms.bean.SettingHelper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class setting extends Model {

  @Path(path = "base", login = true)
  public void base() {
    SettingHelper h = new SettingHelper(login.getId());
    if (method.isPost()) {
      String logo = this.getString("logo");
      h.set("logo", logo);
    }

    this.set("cates", Category.load(W.create("uid", login.getId()).sort("seq", 1), 0, 100));
    this.set("helper", h);
    this.show("/cms/setting/base.html");
  }

  @Path(path = "category", login = true)
  public void category() {
    SettingHelper h = new SettingHelper(login.getId());
    if (method.isPost()) {
      String category = this.getString("category");
      String[] ss = category.split("[, ]");
      for (String s : ss) {
        Category.create(V.create("uid", login.getId()).set("title", s));
      }
    }

    this.set("cates", Category.load(W.create("uid", login.getId()).sort("seq", 1), 0, 100));
    this.set("helper", h);
    this.show("/cms/setting/category.html");
  }

  @Path(path = "splash", login = true)
  public void splash() {
    SettingHelper h = new SettingHelper(login.getId());
    if (method.isPost()) {
      String splash = this.getString("splash");
      h.set("splash", splash);
    }

    this.set("cates", Category.load(W.create("uid", login.getId()).sort("seq", 1), 0, 100));
    this.set("helper", h);
    this.show("/cms/setting/base.html");
  }

}

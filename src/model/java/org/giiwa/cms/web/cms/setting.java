package org.giiwa.cms.web.cms;

import org.giiwa.cms.bean.SettingHelper;
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

    this.set("helper", h);
    this.show("/cms/setting/base.html");
  }

}

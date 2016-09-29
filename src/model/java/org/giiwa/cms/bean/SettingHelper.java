package org.giiwa.cms.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;

public class SettingHelper {

  static Log log = LogFactory.getLog(SettingHelper.class);

  long       uid;

  public SettingHelper(long uid) {
    this.uid = uid;
  }

  public String get(String name) {

    Setting p = cache.get(name);
    if (p == null) {
      p = Setting.load(W.create("uid", uid).and("name", name));
      cache.put(name, p);
    }
    if (p != null) {
      return p.getVal();
    }
    return name;
  }

  Map<String, Setting> cache = new HashMap<String, Setting>();

  public void set(String name, String val) {
    W q = W.create("uid", uid).and("name", name);
    try {
      if (Helper.exists(q, Setting.class)) {
        // update
        Helper.update(q, V.create("val", val), Setting.class);
      } else {
        Helper.insert(V.create("uid", uid).set("name", name).set("val", val), Setting.class);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}

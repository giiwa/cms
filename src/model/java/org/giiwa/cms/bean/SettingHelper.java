package org.giiwa.cms.bean;

import java.util.HashMap;
import java.util.Map;

import org.giiwa.core.bean.Helper.W;

public class SettingHelper {

  long uid;

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
      return p.val;
    }
    return name;
  }

  Map<String, Setting> cache = new HashMap<String, Setting>();

}

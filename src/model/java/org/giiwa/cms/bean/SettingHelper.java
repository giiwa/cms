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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;

// TODO: Auto-generated Javadoc
public class SettingHelper {

  static Log log = LogFactory.getLog(SettingHelper.class);

  long       uid;

  /**
   * Instantiates a new setting helper.
   *
   * @param uid
   *          the uid
   */
  public SettingHelper(long uid) {
    this.uid = uid;
  }

  /**
   * Gets the.
   *
   * @param name
   *          the name
   * @return the string
   */
  public String get(String name) {

    Setting p = cache.get(name);
    if (p == null) {
      p = Setting.load(W.create("uid", uid).and("name", name));
      cache.put(name, p);
    }
    if (p != null) {
      return p.getVal();
    }
    return null;
  }

  Map<String, Setting> cache = new HashMap<String, Setting>();

  /**
   * Sets the.
   *
   * @param name
   *          the name
   * @param val
   *          the val
   */
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

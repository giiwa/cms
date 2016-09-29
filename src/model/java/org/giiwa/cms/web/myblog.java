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

import java.util.HashMap;
import java.util.Map;

import org.giiwa.cms.bean.Article;
import org.giiwa.cms.bean.Category;
import org.giiwa.cms.bean.Setting;
import org.giiwa.cms.bean.SettingHelper;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.bean.User;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class myblog extends Model {

  @Path()
  public void onGet() {
    User u = this.getUser();
    this.set("me", u);

    long uid = this.getLong("uid");
    if (uid <= 0) {
      uid = X.toLong(this.path, 0);
    }

    u = User.load(uid);

    if (u == null) {
      this.notfound();
    } else {
      this.set("helper", new SettingHelper(uid));
      this.set("user", u);
      int s = this.getInt("s");
      int n = this.getInt("n", 20, "number.per.page");
      W q = W.create(); // .and("uid", uid);
      Beans<Article> bs = Article.load(q.sort("created", -1), s, n);
      this.set(bs, s, n);

      this.set("cates", Category.load(W.create("uid", uid).sort("seq", 1), 0, 100));

      this.show("/cms/myblog.home.html");
    }
  }

}

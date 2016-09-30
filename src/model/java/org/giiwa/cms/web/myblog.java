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

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.giiwa.cms.bean.Article;
import org.giiwa.cms.bean.Category;
import org.giiwa.cms.bean.Keyword;
import org.giiwa.cms.bean.SettingHelper;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.bean.User;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;
import org.giiwa.tinyse.se.SE;

public class myblog extends Model {

  @Path()
  public void onGet() {
    User user = this.getUser();
    this.set("me", user);

    long uid = this.getLong("uid");
    if (uid < 0) {
      uid = X.toLong(this.path, 0);
    }

    user = User.load(uid);

    if (user == null) {
      this.notfound();
    } else {
      this.set("user", user);
      this.set("helper", new SettingHelper(uid));

      _usage(uid);

      int s = this.getInt("s");
      int n = this.getInt("n", 20, "number.per.page");
      Beans<Article> bs = null;
      String wd = this.getString("wd");
      if (X.isEmpty(wd)) {
        W q = W.create(); // .and("uid", uid);
        bs = Article.load(q.sort("created", -1), s, n);
      } else {
        // search
        Query q = SE.parse(wd, new String[] { "title", "text" });

        TopDocs docs = SE.search("article", q);
        ScoreDoc[] dd = docs.scoreDocs;
        bs = new Beans<Article>();
        bs.setTotal(docs.totalHits);
        List<Article> list = new ArrayList<Article>();
        bs.setList(list);

        int min = s + n;
        int i = s;
        while (i < min && i < dd.length) {

          ScoreDoc d = dd[i];
          long id = X.toLong(SE.get(d.doc), -1);
          if (id > -1) {
            Article e = Article.load(id);
            // SE.highlight();
            String s1 = SE.highlight(d.doc, "title", q, null);
            if (s1 != null) {
              e.set("title", s1);
            }

            s1 = SE.highlight(d.doc, "text", q, null);
            if (s1 != null) {
              e.set("text", s1);
            }

            list.add(e);
          }
          i++;
          if (list.size() >= n) {
            break;
          }
        }

        this.set("wd", wd);

      }

      this.set(bs, s, n);

      this.show("/cms/myblog.home.html");
    }
  }

  private void _usage(long uid) {

    this.set("cates", Category.load(W.create("uid", uid).sort("seq", 1), 0, 100));
    this.set("latest", Article.load(W.create("uid", uid).sort("created", -1), 5));
    this.set("hotest", Article.load(W.create("uid", uid).sort("updated", -1), 5));
    this.set("categories", Category.load(W.create("uid", uid).sort("title", 1), 0, 5));
    this.set("keywords", Keyword.load(W.create("uid", uid).sort("count", -1), 0, 5));

  }

}

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
package org.giiwa.cms.web.admin;

import java.util.List;

import org.giiwa.cms.bean.Article;
import org.giiwa.cms.bean.Folder;
import org.giiwa.core.base.Html;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;
import org.jsoup.nodes.Element;

// TODO: Auto-generated Javadoc
public class article extends Model {

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.framework.web.Model#onGet()
   */
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

    Beans<Folder> b1 = Folder.load(W.create().sort("name", 1), 0, 1000);
    if (b1 != null) {
      this.set("folders", b1.getList());
    }

    this.show("/admin/article.index.html");
  }

  /**
   * Delete.
   */
  @Path(path = "delete", login = true, access = "access.cms.admin")
  public void delete() {
    long id = this.getLong("id");
    Article.delete(id);
    JSON jo = new JSON();
    jo.put(X.STATE, 200);
    this.response(jo);
  }

  /**
   * Creates the.
   */
  @Path(path = "create", login = true, access = "access.cms.admin")
  public void create() {
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "title", "keywords", "category");
      v.set("seq", this.getInt("seq"));
      v.set("commentable", X.isSame("on", this.getString("commentable")) ? "on" : "off");
      v.set("folderid", this.getLong("folderid"));
      String content = this.getHtml("content");
      v.set("content", content);
      Html h = Html.create(content);
      v.set("text", h.text());
      List<Element> list = h.getTags("img");
      if (list != null && list.size() > 0) {
        v.set("img", list.get(0).attr("src"));
      }
      long id = Article.create(v);

      this.set(X.MESSAGE, lang.get("create.success"));
      onGet();
      return;
    }

    long folderid = this.getLong("folderid");
    Folder f = Folder.load(folderid);
    this.set("commentable", f == null ? "off" : f.getCommentable());
    this.set("folderid", folderid);
    this.show("/admin/article.create.html");
  }

  /**
   * Edits the.
   */
  @Path(path = "edit", login = true, access = "access.cms.admin")
  public void edit() {
    long id = this.getLong("id");
    if (method.isPost()) {
      JSON jo = this.getJSON();
      V v = V.create().copy(jo, "title");
      v.set("seq", this.getInt("seq"));
      v.set("commentable", X.isSame("on", this.getString("commentable")) ? "on" : "off");
      v.set("content", this.getHtml("content"));
      Article.update(id, v);

      this.set(X.MESSAGE, lang.get("save.success"));
      onGet();
      return;
    }

    Article d = Article.load(id);
    this.set(d.getJSON());
    this.set("id", id);
    this.set("folderid", d.getFolderid());
    this.show("/admin/article.edit.html");
  }

}

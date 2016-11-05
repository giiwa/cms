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

import java.io.File;

import org.giiwa.cms.bean.Folder;
import org.giiwa.core.base.Http;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

// TODO: Auto-generated Javadoc
public class folder extends Model {

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
    String name = this.getString("name");

    if (!X.isEmpty(name) && X.isEmpty(path)) {
      q.and("name", name, W.OP_LIKE);
      this.set("name", name);
    }
    Beans<Folder> bs = Folder.load(q, s, n);
    this.set(bs, s, n);

    this.show("/admin/folder.index.html");
  }

  /**
   * Delete.
   */
  @Path(path = "delete", login = true, access = "access.cms.admin")
  public void delete() {
    long id = this.getLong("id");
    Folder.delete(id);
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

  /**
   * Edits the.
   */
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

  public static void main(String[] args) {
    String url = "http://joe.mac:8080/repo/cbnvy2grz15rv/1.%20Scene%201.mp3";
    File f = new File("/Users/wujun/d/temp/f2.mp3");
    JSON head = JSON.create();
    head.put("Range", "bytes=4415488-");
    int len = Http.download(url, head, f);

    System.out.println("done, len=" + len);
  }

}

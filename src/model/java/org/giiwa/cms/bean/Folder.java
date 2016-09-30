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

import java.sql.SQLException;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.bean.UID;
import org.giiwa.core.bean.X;

// TODO: Auto-generated Javadoc
@Table(name = "cms_folder")
public class Folder extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "parentid")
  long                      parentid;

  @Column(name = X.ID)
  long                      id;

  @Column(name = "name")
  String                    name;

  @Column(name = "seq")
  int                       seq;

  @Column(name = "commentable")
  String                    commentable;

  @Column(name = "content")
  String                    content;

  public String getCommentable() {
    return commentable;
  }

  private Folder            parent_obj;

  public Folder getParent_obj() {
    if (parent_obj == null) {
      parent_obj = Folder.load(parentid);
    }
    return parent_obj;
  }

  public long getParentid() {
    return parentid;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getSeq() {
    return seq;
  }

  public String getContent() {
    return content;
  }

  /**
   * Load.
   *
   * @param q
   *          the q
   * @param s
   *          the s
   * @param n
   *          the n
   * @return the beans
   */
  public static Beans<Folder> load(W q, int s, int n) {
    // TODO Auto-generated method stub
    return Helper.load(q, s, n, Folder.class);
  }

  /**
   * Load.
   *
   * @param id
   *          the id
   * @return the folder
   */
  public static Folder load(long id) {
    // TODO Auto-generated method stub
    return Helper.load(id, Folder.class);
  }

  /**
   * Delete.
   *
   * @param id
   *          the id
   */
  public static void delete(long id) {
    // TODO Auto-generated method stub
    Helper.delete(id, Folder.class);
  }

  /**
   * Creates the.
   *
   * @param v
   *          the v
   * @return the long
   */
  public static long create(V v) {
    try {
      long id = UID.next("folder.id");
      while (exists(id)) {
        id = UID.next("folder.id");
      }
      Helper.insert(v.set(X.ID, id), Folder.class);
      return id;
    } catch (Exception e) {
      log.error(v.toString(), e);
      return -1;
    }
  }

  /**
   * Exists.
   *
   * @param id
   *          the id
   * @return true, if successful
   * @throws SQLException
   *           the SQL exception
   */
  public static boolean exists(long id) throws SQLException {
    return Helper.exists(id, Folder.class);
  }

  /**
   * Update.
   *
   * @param id
   *          the id
   * @param v
   *          the v
   * @return the int
   */
  public static int update(long id, V v) {
    return Helper.update(id, v, Folder.class);
  }

}

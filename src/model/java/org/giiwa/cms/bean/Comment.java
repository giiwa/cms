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
import org.giiwa.framework.bean.User;

// TODO: Auto-generated Javadoc
@Table(name = "gi_comment")
public class Comment extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "uid")
  long                      uid;
  @Column(name = "aid")
  long                      aid;
  @Column(name = "content")
  String                    content;

  private User              user_obj;

  public User getUser_obj() {
    if (user_obj == null) {
      user_obj = User.loadById(uid);
    }
    return user_obj;
  }

  public long getUid() {
    return uid;
  }

  public long getAid() {
    return aid;
  }

  public String getContent() {
    return content;
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
      long id = UID.next("comment.id");
      while (exists(id)) {
        id = UID.next("comment.id");
      }

      Helper.insert(v.set(X.ID, id), Comment.class);

      return id;
    } catch (Exception e) {
      log.error(v.toString(), e);
    }
    return -1;
  }

  private static boolean exists(long id) throws SQLException {
    return Helper.exists(id, Comment.class);
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
  public static Beans<Comment> load(W q, int s, int n) {
    return Helper.load(q, s, n, Comment.class);
  }

}

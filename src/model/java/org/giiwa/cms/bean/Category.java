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

import java.util.List;

import org.giiwa.core.bean.Bean;
import org.giiwa.core.bean.Beans;
import org.giiwa.core.bean.Column;
import org.giiwa.core.bean.Helper;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.Table;
import org.giiwa.core.task.Task;

// TODO: Auto-generated Javadoc
@Table(name = "cms_category")
public class Category extends Bean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Column(name = "uid")
  long                      uid;

  @Column(name = "seq")
  int                       seq;

  @Column(name = "title")
  String                    title;

  @Column(name = "url")
  String                    url;

  @Column(name = "count")
  long                      count;

  /**
   * Load.
   *
   * @param q
   *          the q
   * @param s
   *          the s
   * @param n
   *          the n
   * @return the object
   */
  public static List<Category> load(W q, int s, int n) {
    Beans<Category> bs = Helper.load(q, s, n, Category.class);
    if (bs != null) {
      return bs.getList();
    }
    return null;
  }

  /**
   * Creates the.
   *
   * @param v
   *          the v
   */
  public static void create(V v) {
    Helper.insert(v, Category.class);
  }

  public static void repair(final long uid) {
    new Task() {
      @Override
      public void onExecute() {
        
      }

    }.schedule(0);
  }

}

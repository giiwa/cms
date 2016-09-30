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

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.giiwa.framework.web.Model;
import org.giiwa.core.bean.Helper.V;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.bean.X;
import org.giiwa.tinyse.se.SE.Indexer;

// TODO: Auto-generated Javadoc
public class ArticleIndexer implements Indexer {

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.tinyse.se.SE.Indexer#bad(java.lang.Object, long)
   */
  @Override
  public void bad(Object id, long flag) {
    // TODO Auto-generated method stub
    String node = Model.node();
    Article.update(X.toLong(id, -1), V.create("index_flag", flag).set("index_state", "bad").set("updated", V.ignore));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.tinyse.se.SE.Indexer#done(java.lang.Object, long)
   */
  @Override
  public void done(Object id, long flag) {
    // TODO Auto-generated method stub
    String node = Model.node();
    Article.update(X.toLong(id, -1), V.create("index_flag", flag).set("index_state", "done").set("updated", V.ignore));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.tinyse.se.SE.Indexer#load(java.lang.Object)
   */
  @Override
  public Document load(Object id) {
    // TODO Auto-generated method stub
    Article c = Article.load(X.toLong(id, -1));
    if (c != null) {
      Document d = new Document();
      if (!X.isEmpty(c.getTitle()))
        d.add(new TextField("title", c.getTitle(), Store.YES));
      if (!X.isEmpty(c.getText()))
        d.add(new TextField("text", c.getText(), Store.YES));

      d.add(new LongField("uid", c.getUid(), Store.YES));

      return d;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.tinyse.se.SE.Indexer#next(long)
   */
  @Override
  public Object next(long flag) {
    // TODO Auto-generated method stub
    String node = Model.node();
    Article c = Article.load(W.create().and("index_flag", flag, W.OP_NEQ).sort(X.ID, 1));
    return c == null ? null : c.getId();
  }

}

package org.giiwa.cms.web;

import org.apache.commons.fileupload.FileItem;
import org.giiwa.cms.bean.TestBean;
import org.giiwa.core.bean.X;
import org.giiwa.core.bean.Helper.W;
import org.giiwa.core.task.Task;
import org.giiwa.framework.web.Model;
import org.giiwa.framework.web.Path;

public class Test extends Model {

  @Path()
  public void onGet() {

  }

  /**
   * web api: /Test/demo
   */
  @Path(path = "demo")
  public void demo() {
    /**
     * the request may: /Test/demo?name=xxxx&l1=122&i1=1, and posted a file in
     */
    String name = this.getString("name");

    long l1 = this.getLong("l1");

    int i1 = this.getInt("i1");

    FileItem f1 = this.getFile("f1");
    
    this.set("name", "123");
    TestBean t = TestBean.load(W.create("name", "1"));
    this.set("t", t);

    this.show("/cms/test.html");

  }

  /**
   * web api: /Test/do1
   */
  @Path(path = "do1", login = true, access = "access.cms.do1", method = Model.METHOD_GET
      | Model.METHOD_POST, log = Model.METHOD_POST | Model.METHOD_GET)
  public void do1() {
    
    Task t = new Task(){

      @Override
      public void onExecute() {
        // TODO do something
        
      }

      @Override
      public void onFinish() {
        this.schedule(X.AMINUTE);
      }
      
      
    };
    
    t.schedule(10);

    t.schedule("02:00");

  }

}

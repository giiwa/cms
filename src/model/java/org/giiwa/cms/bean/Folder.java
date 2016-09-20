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

@Table(name = "gi_folder")
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

  @Column(name = "content")
  String                    content;

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

  public static Beans<Folder> load(W q, int s, int n) {
    // TODO Auto-generated method stub
    return Helper.load(q, s, n, Folder.class);
  }

  public static Folder load(long id) {
    // TODO Auto-generated method stub
    return Helper.load(id, Folder.class);
  }

  public static void delete(long id) {
    // TODO Auto-generated method stub
    Helper.delete(id, Folder.class);
  }

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

  public static boolean exists(long id) throws SQLException {
    return Helper.exists(id, Folder.class);
  }

  public static int update(long id, V v) {
    return Helper.update(id, v, Folder.class);
  }

}

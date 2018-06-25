package ${package};

import com.yinhai.sysframework.app.domain.BaseDomain;
import com.yinhai.sysframework.app.domain.Key;

public class ${domainName} extends BaseDomain {
    <#list columns as col>
     /** ${col.comments} */
     private String ${col.columnName};
      /**
       * 设置 ${col.columnName} ${col.comments}
       * @param ${col.comments}
       */
      public void set${col.columnName?cap_first}(String ${col.columnName}) {
       this.${col.columnName} = ${col.columnName};
      }

      /**
       * 获取  ${col.columnName} ${col.comments}
       * @return ${col.comments}
       */
      public String get${col.columnName?cap_first}() {
       return this.${col.columnName};
      }
    </#list>

    public ${domainName}() {
    }

    public ${domainName}(<#list pkCols as col>${col.columnName}<#sep>,</sep></#list>) {
    <#list pkCols as col>
     this.${col.columnName} = ${col.columnName};
    </#list>
    }

 public Key getPK() {
  Key key = new Key();
  <#list pkCols as col>
    if (this.get${col.columnName?cap_first}() == null) {
     throw new IllegalArgumentException("主键${col.columnName}不能为空。");
    }
    key.put("${col.columnName}", this.get${col.columnName?cap_first}());
  </#list>
  return key;
 }

 /**
  * 转换为map对象
  * @return Map
  */
 public java.util.Map toMap() {
  java.util.Map map = new java.util.HashMap();
    <#list columns as col>
      map.put("${col.columnName}", get${col.columnName?cap_first}()); //${col.comments}
    </#list>
  return map;
 }
}

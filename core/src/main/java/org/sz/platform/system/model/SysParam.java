 package org.sz.platform.system.model;
 
 import org.sz.platform.system.model.SysParam;

 import java.text.SimpleDateFormat;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysParam extends BaseModel
 {
   public static final int CONDITION_OR = 1;
   public static final int CONDITION_AND = 2;
   public static final int CONDITION_EXP = 3;
   public static final Map<String, String> DATA_COLUMN_MAP = new HashMap();
   public static SimpleDateFormat PARAM_DATE_FORMAT;
   public static final Map<String, String> DATA_TYPE_MAP;
   public static final Map<String, String> CONDITION_US;
   public static final short EFFECT_USER = 1;
   public static final short EFFECT_ORG = 2;
   protected Long paramId;
   protected String paramKey;
   protected String paramName;
   protected String dataType;
   protected Short effect;
 
   public Short getEffect()
   {
     return this.effect;
   }
   public void setEffect(Short effect) {
     this.effect = effect;
   }
 
   public void setParamId(Long paramId) {
     this.paramId = paramId;
   }
 
   public Long getParamId()
   {
     return this.paramId;
   }
 
   public void setParamKey(String paramKey)
   {
     this.paramKey = paramKey;
   }
 
   public String getParamKey()
   {
     return this.paramKey;
   }
 
   public void setParamName(String paramName)
   {
     this.paramName = paramName;
   }
 
   public String getParamName()
   {
     return this.paramName;
   }
 
   public void setDataType(String dataType)
   {
     this.dataType = dataType;
   }
 
   public String getDataType()
   {
     return this.dataType;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysParam))
     {
       return false;
     }
     SysParam rhs = (SysParam)object;
     return new EqualsBuilder().append(this.paramId, rhs.paramId).append(this.paramKey, rhs.paramKey).append(this.paramName, rhs.paramName).append(this.dataType, rhs.dataType).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.paramId).append(this.paramKey).append(this.paramName).append(this.dataType).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("paramId", this.paramId).append("paramKey", this.paramKey).append("paramName", this.paramName).append("dataType", this.dataType).toString();
   }
 
   static
   {
     DATA_COLUMN_MAP.put("String", "paramValue");
     DATA_COLUMN_MAP.put("Date", "paramDateValue");
     DATA_COLUMN_MAP.put("Integer", "paramIntValue");
 
     PARAM_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-DD");
 
     DATA_TYPE_MAP = new HashMap();
 
     DATA_TYPE_MAP.put("String", "字符");
     DATA_TYPE_MAP.put("Date", "日期");
     DATA_TYPE_MAP.put("Integer", "数字");
 
     CONDITION_US = new HashMap();
 
     CONDITION_US.put("=", "=");
     CONDITION_US.put("&lt;", "<");
     CONDITION_US.put("&gt;", ">");
     CONDITION_US.put("!=", "!=");
     CONDITION_US.put("&gt;=", ">=");
     CONDITION_US.put("&lt;=", "<=");
     CONDITION_US.put("LIKE", "like");
   }
 }


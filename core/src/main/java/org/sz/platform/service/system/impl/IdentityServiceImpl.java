 package org.sz.platform.service.system.impl;
 
  import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.platform.dao.system.IdentityDao;
import org.sz.platform.model.system.Identity;
import org.sz.platform.service.system.IdentityService;
 
 @Service("identityService")
 public class IdentityServiceImpl extends BaseServiceImpl<Identity> implements IdentityService
 {
 
   @Resource
   private IdentityDao dao;
 
   protected IEntityDao<Identity, Long> getEntityDao()
   {
     return this.dao;
   }
 
   public boolean isAliasExisted(String alias)
   {
     return this.dao.isAliasExisted(alias);
   }
 
   public boolean isAliasExistedByUpdate(Identity identity)
   {
     return this.dao.isAliasExistedByUpdate(identity);
   }
 
   public String nextId(String alias)
   {
     Identity identity = this.dao.getByAlias(alias);
     String rule = identity.getRule();
     int step = identity.getStep().shortValue();
     int genEveryDay = identity.getGenEveryDay().shortValue();
     Integer curValue = identity.getCurValue();
     if (curValue == null) curValue = identity.getInitValue();
 
     if (genEveryDay == 1) {
       String curDate = getCurDate();
       String oldDate = identity.getCurDate();
       if (!curDate.equals(oldDate)) {
         identity.setCurDate(curDate);
         curValue = identity.getInitValue();
       }
       else {
         curValue = Integer.valueOf(curValue.intValue() + step);
       }
     }
     else {
       curValue = Integer.valueOf(curValue.intValue() + step);
     }
     identity.setCurValue(curValue);
     this.dao.update(identity);
 
     String rtn = getByRule(rule, identity.getNoLength().intValue(), curValue.intValue());
 
     return rtn;
   }
 
   public  String getCurDate()
   {
     Date date = new Date();
     return date.getYear() + 1900 + "" + (date.getMonth() + 1) + "" + date.getDate();
   }
 
   private String getByRule(String rule, int length, int curValue)
   {
     Date date = new Date();
 
     String year = date.getYear() + 1900 + "";
     int month = date.getMonth() + 1;
     int day = date.getDate();
     String shortMonth = "" + month;
     String longMonth = "" + month;
     if(month<10){
    	 longMonth="0"+month;
     }
     String seqNo = getSeqNo(curValue, length);
 
     String shortDay = "" + day;
     String longDay = "" + day;
 
     String rtn = rule.replace("{yyyy}", year).replace("{MM}", longMonth).replace("{mm}", shortMonth).replace("{DD}", longDay).replace("{dd}", shortDay).replace("{NO}", seqNo);
 
     return rtn;
   }
 
   private static String getSeqNo(int curValue, int length)
   {
     String tmp = curValue + "";
     int len = length - tmp.length();
     String rtn = "";
     switch (len) {
     case 1:
       rtn = "0";
       break;
     case 2:
       rtn = "00";
       break;
     case 3:
       rtn = "000";
       break;
     case 4:
       rtn = "0000";
       break;
     case 5:
       rtn = "00000";
       break;
     case 6:
       rtn = "000000";
       break;
     case 7:
       rtn = "0000000";
       break;
     case 8:
       rtn = "00000000";
       break;
     case 9:
       rtn = "000000000";
       break;
     case 10:
       rtn = "0000000000";
       break;
     case 11:
       rtn = "00000000000";
       break;
     case 12:
       rtn = "000000000000";
     }
 
     return rtn + tmp;
   }
 
   public List<Identity> getList()
   {
     return this.dao.getList();
   }
 }


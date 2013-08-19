 package org.sz.core.sms;
 
 import org.sz.core.sms.IShortMessage;

 import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;
import org.sz.core.sms.impl.ModemMessage;
import org.sz.core.sms.soap.ShortMessageImpl;
 
 public class SmsFactoryBean
   implements FactoryBean<IShortMessage>
 {
   private Properties configproperties;
   private String type;
 
   public void setType(String type)
   {
     this.type = type;
   }
 
   public IShortMessage getObject() throws Exception
   {
     IShortMessage sms = null;
     if ("modem".equals(this.type))
       sms = ModemMessage.getInstance();
     else if ("soap".equals(this.type))
       sms = ShortMessageImpl.getInstance();
     else {
       throw new Exception("No support short message type: " + this.type);
     }
     return sms;
   }
 
   public Class<?> getObjectType()
   {
     return IShortMessage.class;
   }
 
   public boolean isSingleton()
   {
     return true;
   }
 
   public Properties getConfigproperties() {
     return this.configproperties;
   }
 
   public void setConfigproperties(Properties configproperties) {
     this.configproperties = configproperties;
   }
 }


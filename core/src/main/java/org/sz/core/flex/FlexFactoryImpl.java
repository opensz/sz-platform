 package org.sz.core.flex;
 
 import flex.messaging.FactoryInstance;
 import flex.messaging.FlexFactory;
 import flex.messaging.config.ConfigMap;
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sz.core.flex.SpringFactoryInstance;
 
 public class FlexFactoryImpl
   implements FlexFactory
 {
   private Log log = LogFactory.getLog(getClass());
 
   public FactoryInstance createFactoryInstance(String id, ConfigMap properties) {
     this.log.info("Create FactoryInstance.");
     SpringFactoryInstance instance = new SpringFactoryInstance(this, id, properties);
     instance.setSource(properties.getPropertyAsString("source", instance.getId()));
     return instance;
   }
 
   public Object lookup(FactoryInstance instanceInfo) {
     this.log.info("Lookup service object.");
     return instanceInfo.lookup();
   }
 
   public void initialize(String id, ConfigMap configMap)
   {
   }
 }


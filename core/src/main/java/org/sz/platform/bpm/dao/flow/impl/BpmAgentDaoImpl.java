 package org.sz.platform.bpm.dao.flow.impl;
 
  import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.core.dao.impl.BaseDaoImpl;
import org.sz.platform.bpm.dao.flow.BpmAgentDao;
import org.sz.platform.bpm.model.flow.BpmAgent;
 
@Repository("bpmAgentDao")
 public class BpmAgentDaoImpl extends BaseDaoImpl<BpmAgent> implements BpmAgentDao
 {
   public Class getEntityClass()
   {
     return BpmAgent.class;
   }
 
   public List<BpmAgent> getByAgentId(Long agentid) {
     Map params = new HashMap();
     params.put("agentid", agentid);
     List list = getSqlSessionTemplate().selectList(getIbatisMapperNamespace() + ".getAll", params);
     return list;
   }
 
   public void delByAgentId(Long agentid) {
     Map params = new HashMap();
     params.put("agentid", agentid);
     getBySqlKey("delByAgentId", params);
   }
 
   public List<BpmAgent> getByDefId(Long defId) {
     List list = getSqlSessionTemplate().selectList(getIbatisMapperNamespace() + ".getByDefId", defId);
     return list;
   }
 
   public List<String> getNotInByAgentId(Long agentid) {
     List list = getBySqlKey("getNotInByAgentId", agentid);
     return list;
   }
 }


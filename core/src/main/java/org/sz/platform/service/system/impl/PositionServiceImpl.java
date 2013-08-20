 package org.sz.platform.service.system.impl;
 
  import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.dao.system.PositionDao;
import org.sz.platform.dao.system.UserPositionDao;
import org.sz.platform.model.system.Position;
import org.sz.platform.model.system.UserPosition;
import org.sz.platform.service.system.PositionService;
 
 @Service("positionService")
 public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService
 {
 
   @Resource
   private PositionDao positionDao;
 
   @Resource
   private UserPositionDao userPositionDao;
 
   protected IEntityDao<Position, Long> getEntityDao()
   {
     return this.positionDao;
   }
 
   public Position getParentPositionByParentId(long parentId)
   {
     Position parent = (Position)this.positionDao.getById(Long.valueOf(parentId));
     if ((parentId == 0L) || (parent == null)) {
       parent = new Position();
 
       parent.setPosId(Long.valueOf(0L));
       parent.setDepth(Integer.valueOf(0));
       parent.setNodePath("0.");
       parent.setParentId(Long.valueOf(-1L));
       parent.setSn((short)0);
 
       parent.setPosName("岗位");
       parent.setPosDesc("岗位");
 
       return parent;
     }
     return parent;
   }
 
   public List<Position> getByNodePath(String nodePath)
   {
     return this.positionDao.getByNodePath(nodePath);
   }
 
   public void updateChildrenNodePath(Position father, List<Position> childrenList)
   {
     Map mapData = coverMapData(father.getPosId(), childrenList);
 
     Set set = mapData.entrySet();
     Iterator it = set.iterator();
     Position parent;
     while (it.hasNext()) {
       Map.Entry ent = (Map.Entry)it.next();
       Long parentId = (Long)ent.getKey();
       List<Position> list = (List)ent.getValue();
 
       parent = getParentPositionByParentId(parentId.longValue());
       if ((parent != null) && (list != null) && (list.size() > 0))
       {
         for (Position gt : list)
           if (gt.getPosId().longValue() != father.getPosId().longValue()) {
             gt.setParentId(parent.getPosId());
             gt.setNodePath(parent.getNodePath() + gt.getPosId() + ".");
             gt.setDepth(Integer.valueOf(parent.getDepth().intValue() + 1));
             update(gt);
           }
       }
     }
   }
 
   public Map<Long, List<Position>> coverMapData(Long rootId, List<Position> instList)
   {
     Map dataMap = new HashMap();
     dataMap.put(Long.valueOf(rootId.longValue()), new ArrayList());
     if ((instList != null) && (instList.size() > 0)) {
       for (Position gt : instList) {
         long parentId = gt.getParentId().longValue();
         if (dataMap.get(Long.valueOf(parentId)) == null) {
           dataMap.put(Long.valueOf(parentId), new ArrayList());
         }
         ((List)dataMap.get(Long.valueOf(parentId))).add(gt);
       }
     }
     return dataMap;
   }
 
   public List<Position> coverTreeList(Long rootId, List<Position> instList)
   {
     Map dataMap = coverMapData(rootId, instList);
     return getChildListByDicId(rootId, dataMap);
   }
 
   private List<Position> getChildListByDicId(Long parentId, Map<Long, List<Position>> dataMap)
   {
     List list = new ArrayList();
 
     List<Position> dicList = (List)dataMap.get(Long.valueOf(parentId.longValue()));
     if ((dicList != null) && (dicList.size() > 0)) {
       for (Position dic : dicList) {
         list.add(dic);
         List childList = getChildListByDicId(dic.getPosId(), dataMap);
         list.addAll(childList);
       }
     }
     return list;
   }
 
   public void delByIds(Long[] ids)
   {
     if ((ids == null) || (ids.length == 0)) return;
 
     Long[] arr$ = ids; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { long posId = arr$[i$].longValue();
       Position gt = (Position)this.positionDao.getById(Long.valueOf(posId));
 
       String oldNodePath = gt.getNodePath();
 
       List childrenList = this.positionDao.getByNodePath(oldNodePath);
 
       delById(posId);
 
       updateChildrenNodePath(getParentPositionByParentId(0L), childrenList);
     }
   }
 
   public void add(Position position, List<UserPosition> upList)
     throws Exception
   {
     add(position);
     if (BeanUtils.isEmpty(upList)) return;
     for (UserPosition up : upList) {
       Long posId = position.getPosId();
       Long userId = position.getPosId();
 
       boolean isPrimary = up.getIsPrimary() == UserPosition.PRIMARY_YES;
       if (isPrimary) {
         this.userPositionDao.updNotPrimaryByUser(userId);
       }
       up.setPosId(posId);
       up.setUserPosId(Long.valueOf(UniqueIdUtil.genId()));
       this.userPositionDao.add(up);
     }
   }
 
   public List<Position> getChildByParentId(long parentId)
   {
     return this.positionDao.getChildByParentId(parentId);
   }
 
   public List<Position> getAllChildByParentId(long parentId)
   {
     List ChildList = this.positionDao.getChildByParentId(parentId);
     int childSize = ChildList.size();
     for (int i = 0; i < childSize; i++)
       if (((Position)ChildList.get(i)).getIsLeaf().intValue() != 1) {
         List MoreList = getAllChildByParentId(((Position)ChildList.get(i)).getPosId().longValue());
         ChildList.addAll(MoreList);
       }
     return ChildList;
   }
 
   public void add(Position position)
   {
     this.positionDao.add(position);
     updateIsParent(position);
   }
 
   public void update(Position position)
   {
     this.positionDao.update(position);
     updateIsParent(position);
   }
 
   public void delById(long posId)
   {
     Position position = (Position)this.positionDao.getById(Long.valueOf(posId));
     this.positionDao.delById(Long.valueOf(posId));
     updateIsParent(position);
   }
 
   public void updSn(Long[] aryPosId)
   {
     for (int i = 0; i < aryPosId.length; i++) {
       short sn = (short)(i + 1);
       this.positionDao.updSn(aryPosId[i], Short.valueOf(sn));
     }
   }
 
   public void updateIsParent(Position position)
   {
     if (position == null) return;
     long typeId = position.getPosId().longValue();
     long parentId = position.getParentId().longValue();
     position = (Position)this.positionDao.getById(Long.valueOf(typeId));
     Position parent = (Position)this.positionDao.getById(Long.valueOf(parentId));
 
     if (position != null) {
       int childCount = this.positionDao.getChildCountByParentId(position.getPosId().longValue()).intValue();
       if (childCount > 0)
         position.setIsParent("true");
       else {
         position.setIsParent("false");
       }
       this.positionDao.update(position);
     }
 
     if (parent != null) {
       int childCount = this.positionDao.getChildCountByParentId(parent.getPosId().longValue()).intValue();
       if (childCount > 0)
         parent.setIsParent("true");
       else {
         parent.setIsParent("false");
       }
       this.positionDao.update(parent);
     }
   }
 
   public List<Position> getByUserId(Long userId)
   {
     return this.positionDao.getByUserId(userId);
   }
 }


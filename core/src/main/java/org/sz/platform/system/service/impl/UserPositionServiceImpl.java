 package org.sz.platform.system.service.impl;
 
  import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sz.core.dao.IEntityDao;
import org.sz.core.service.impl.BaseServiceImpl;
import org.sz.core.util.BeanUtils;
import org.sz.core.util.StringUtil;
import org.sz.core.util.UniqueIdUtil;
import org.sz.platform.system.dao.UserPositionDao;
import org.sz.platform.system.model.UserPosition;
import org.sz.platform.system.service.UserPositionService;
 
 @Service("userPositionService")
 public class UserPositionServiceImpl extends BaseServiceImpl<UserPosition> implements UserPositionService
 {
 
   @Resource
   private UserPositionDao userPositionDao;
 
   protected IEntityDao<UserPosition, Long> getEntityDao()
   {
     return this.userPositionDao;
   }
 
   public void add(Long posId, Long[] userIds)
     throws Exception
   {
     if ((posId == null) || (posId.longValue() == 0L) || (userIds == null) || (userIds.length == 0)) return;
 
     Long[] arr$ = userIds; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { long userId = arr$[i$].longValue();
       UserPosition userPosition = this.userPositionDao.getUserPosModel(Long.valueOf(userId), posId);
       if (userPosition == null) {
         long userPosId = UniqueIdUtil.genId();
         UserPosition up = new UserPosition();
         up.setUserPosId(Long.valueOf(userPosId));
         up.setIsPrimary(UserPosition.PRIMARY_YES);
         this.userPositionDao.updNotPrimaryByUser(Long.valueOf(userId));
         up.setPosId(posId);
         up.setUserId(Long.valueOf(userId));
         this.userPositionDao.add(up);
       }
     }
   }
 
   public void setIsPrimary(Long userPosId)
   {
     UserPosition userPosition = (UserPosition)this.userPositionDao.getById(userPosId);
     if (userPosition.getIsPrimary() == UserPosition.PRIMARY_NO) {
       userPosition.setIsPrimary(UserPosition.PRIMARY_YES);
       this.userPositionDao.updNotPrimaryByUser(userPosition.getUserId());
     }
     else {
       userPosition.setIsPrimary(UserPosition.PRIMARY_NO);
     }
     this.userPositionDao.update(userPosition);
   }
 
   public List<UserPosition> getByPosId(Long posId)
     throws Exception
   {
     return this.userPositionDao.getByPosId(posId);
   }
 
   public void delByPosId(Long posId) {
     this.userPositionDao.delByPosId(posId);
   }
 
   public UserPosition getUserPosModel(Long userId, Long posId)
   {
     return this.userPositionDao.getUserPosModel(userId, posId);
   }
 
   public void delUserPosByIds(String[] lAryId, Long userId)
   {
     if (BeanUtils.isEmpty(lAryId)) return;
     for (String posId : lAryId)
       if (!StringUtil.isEmpty(posId))
         this.userPositionDao.delUserPosByIds(userId, Long.valueOf(Long.parseLong(posId)));
   }
 
   public void saveUserPos(Long userId, Long[] posIds, Long posIdPrimary)
     throws Exception
   {
     this.userPositionDao.delByUserId(userId);
     if (BeanUtils.isEmpty(posIds)) return;
     for (Long posId : posIds) {
       UserPosition userPosition = new UserPosition();
       userPosition.setUserPosId(Long.valueOf(UniqueIdUtil.genId()));
       userPosition.setPosId(posId);
       userPosition.setUserId(userId);
       if ((posIdPrimary != null) && (posId.equals(posIdPrimary))) {
         userPosition.setIsPrimary(UserPosition.PRIMARY_YES);
       }
       this.userPositionDao.add(userPosition);
     }
   }
 
   public List<Long> getUserIdsByPosId(Long posId)
   {
     return this.userPositionDao.getUserIdsByPosId(posId);
   }
 }


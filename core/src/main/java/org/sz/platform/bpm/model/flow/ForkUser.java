 package org.sz.platform.bpm.model.flow;
 
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 
 public class ForkUser
 {
   public static final String USER_TYPE_USER = "user";
   public static final String USER_TYPE_ORG = "org";
   public static final String USER_TYPE_ROLE = "role";
   public static final String USER_TYPE_POS = "pos";
   private String forkUserType;
   protected Set<String> forkUserIds = new HashSet();
 
   public ForkUser()
   {
   }
 
   public ForkUser(Set<String> forkUserIds, String forkUserType) {
     this.forkUserIds = forkUserIds;
     this.forkUserType = forkUserType;
   }
 
   public ForkUser(List<String> forkUserIds, String forkUserType) {
     this.forkUserIds.addAll(forkUserIds);
     this.forkUserType = forkUserType;
   }
 
   public ForkUser(String forkUserIds, String forkUserType) {
     String[] uIds = forkUserIds.split("[,]");
     if (uIds != null) {
       for (int i = 0; i < uIds.length; i++) {
         this.forkUserIds.add(uIds[i]);
       }
     }
     this.forkUserType = forkUserType;
   }
 
   public String getForkUserType()
   {
     return this.forkUserType;
   }
 
   public void setForkUserType(String forkUserType) {
     this.forkUserType = forkUserType;
   }
 
   public Set<String> getForkUserIds() {
     return this.forkUserIds;
   }
 
   public List<String> getForkUserIdsAsList() {
     List userIds = new ArrayList();
     userIds.addAll(this.forkUserIds);
     return userIds;
   }
 
   public void setForkUserIds(Set<String> forkUserIds) {
     this.forkUserIds = forkUserIds;
   }
 }


 package org.sz.platform.bpm.service.flow.thread;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.activiti.engine.impl.persistence.entity.TaskEntity;
 
 public class TaskThreadService
 {
   private static ThreadLocal<String> forkTaskTokenLocal = new ThreadLocal();
 
   private static ThreadLocal<List<TaskEntity>> newTasksLocal = new ThreadLocal();
 
   private static ThreadLocal<String> preUserLocal = new ThreadLocal();
 
   public static void addTask(TaskEntity taskEntity) {
     List taskList = (List)newTasksLocal.get();
     if (taskList == null) {
       taskList = new ArrayList();
       newTasksLocal.set(taskList);
     }
     taskList.add(taskEntity);
   }
 
   public static List<TaskEntity> getNewTasks() {
     return (List)newTasksLocal.get();
   }
 
   public static String getToken() {
     return (String)forkTaskTokenLocal.get();
   }
 
   public static void setToken(String token) {
     forkTaskTokenLocal.set(token);
   }
 
   public static void clearToken() {
     forkTaskTokenLocal.remove();
   }
 
   public static void clearNewTasks() {
     newTasksLocal.remove();
   }
 
   public static void setPreTaskUser(String user) {
     preUserLocal.set(user);
   }
 
   public static void cleanTaskUser() {
     preUserLocal.remove();
   }
 
   public static String getPreTaskUser() {
     if (preUserLocal.get() == null) {
       return "";
     }
     return (String)preUserLocal.get();
   }
 }


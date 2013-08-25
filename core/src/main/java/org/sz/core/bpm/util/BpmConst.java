package org.sz.core.bpm.util;

public class BpmConst
{
  public static final String START_USER_ID = "startUserId";
  public static final String StartUser = "startUser";
  public static final String PrevUser = "prevUser";
  
  
  public static final String StartEvent = "start";
  public static final String EndEvent = "end";
  public static final String CreateEvent = "create";
  public static final String CompleteEvent = "complete";
  public static final String AssignmentEvent = "assignment";
  
  public static final Integer StartScript = 1;
  public static final Integer EndScript = 2;
  public static final Integer ScriptNodeScript = 3;
  public static final Integer AssignScript = 4;
  
  
  public static final String NODE_APPROVAL_STATUS = "approvalStatus";
  public static final String NODE_APPROVAL_CONTENT = "approvalContent";
  
  public static final Short OnLineForm = 0;
  public static final Short UrlForm = 1;
  
  public static final String FormPkRegEx = "\\{pk\\}";
}
package org.sz.core.sms;

import java.util.List;

public abstract interface IShortMessage
{
  public abstract boolean sendSms(List<String> paramList, String paramString);
}


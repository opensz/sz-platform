package org.sz.core.keygenerator.impl;

import org.sz.core.keygenerator.IKeyGenerator;
import org.sz.core.util.UniqueIdUtil;

public class TimeGenerator
  implements IKeyGenerator
{
  public Object nextId()
    throws Exception
  {
    return Long.valueOf(UniqueIdUtil.genId());
  }

  public void setAlias(String alias)
  {
  }
}
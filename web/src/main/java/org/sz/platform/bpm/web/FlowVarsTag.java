package org.sz.platform.bpm.web;

import org.sz.core.util.StringUtil;
import org.sz.core.web.util.AppUtil;
import org.sz.platform.bpm.dao.form.BpmFormFieldDao;
import org.sz.platform.bpm.model.form.BpmFormField;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FlowVarsTag extends BodyTagSupport
{
  private static final long serialVersionUID = 1L;
  private Long defId = Long.valueOf(0L);

  private String controlName = "flowVars";
  private String change = "";

  public void setChange(String change)
  {
    this.change = change;
  }

  public void setDefId(Long defId)
  {
    this.defId = defId;
  }

  public void setControlName(String controlName)
  {
    this.controlName = controlName;
  }

  public int doStartTag() throws JspTagException {
    return 2;
  }

  private String getFlowVars()
  {
    if (this.defId.longValue() == 0L) {
      return "未定义";
    }
    BpmFormFieldDao bpmFormFieldDao = (BpmFormFieldDao)AppUtil.getBean(BpmFormFieldDao.class);
    List<BpmFormField> fieldList = bpmFormFieldDao.getFlowVarByFlowDefId(this.defId);
    StringBuffer sb = new StringBuffer();
    sb.append("<select name='" + this.controlName + "'");
    if (StringUtil.isNotEmpty(this.change)) {
      sb.append(" onchange=\"" + this.change + "\"");
    }
    sb.append(">");
    sb.append("<option value=''>请选择...</option>");
    sb.append("<option value='startUser'>发起人(长整型)</option>");
    for (BpmFormField field : fieldList) {
      sb.append("<option value='" + field.getFieldName() + "'>" + field.getFieldDesc() + "</option>");
    }
    sb.append("</select>");
    return sb.toString();
  }

  public int doEndTag()
    throws JspTagException
  {
    try
    {
      String str = getFlowVars();
      this.pageContext.getOut().print(str);
    } catch (Exception e) {
      throw new JspTagException(e.getMessage());
    }
    return 0;
  }
}


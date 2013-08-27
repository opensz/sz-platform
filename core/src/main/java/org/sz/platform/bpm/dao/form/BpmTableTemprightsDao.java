package org.sz.platform.bpm.dao.form;

import java.util.List;

import org.sz.core.dao.BaseDao;
import org.sz.platform.bpm.model.form.BpmTableTemprights;

public interface BpmTableTemprightsDao extends BaseDao<BpmTableTemprights>{

	void delByTemplateId(Long templateId);

	void delByCategoryId(Long categoryId);

	List<BpmTableTemprights> getByTemplateId(Long templateId);

	List<BpmTableTemprights> getByCategoryId(Long categoryId);

}
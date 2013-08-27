package org.sz.platform.bpm.service.form;

import java.io.IOException;

import org.sz.core.service.BaseService;
import org.sz.platform.bpm.model.form.BpmFormRule;

public interface BpmFormRuleService extends BaseService<BpmFormRule>{

	void generateJS() throws IOException;

}
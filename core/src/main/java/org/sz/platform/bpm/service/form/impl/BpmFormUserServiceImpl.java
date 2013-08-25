package org.sz.platform.bpm.service.form.impl;

import org.springframework.stereotype.Service;
import org.sz.core.bpm.model.ProcessCmd;
import org.sz.platform.bpm.service.form.BpmFormUserService;


@Service("bpmFormUserService")
public class BpmFormUserServiceImpl implements BpmFormUserService {
	public void addUser(ProcessCmd processCmd){
		System.out.println("测试执行");
	}
}

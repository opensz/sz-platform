package org.sz.platform.bpm.service.flow;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public interface JumpRule {

	public static final String RULE_INVALID = "_RULE_INVALID";

	String evaluate(ExecutionEntity execution, Short isJumpForDef);

}
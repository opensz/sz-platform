package org.xx.reg;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;

public class RegCheckInterceptor extends SimpleTraceInterceptor {
	
	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
		String invocationDescription = getInvocationDescription(invocation);
		logger.trace("Entering " + invocationDescription);
		try {
			Object rval = invocation.proceed();
			logger.trace("Exiting " + invocationDescription);
			return rval;
		}
		catch (Throwable ex) {
			logger.trace("Exception thrown in " + invocationDescription, ex);
			throw ex;
		}
	}
}

package org.sz.core.util;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;

public class UniqueIdUtil {
	private static long uid = 0L;

	private static Lock lock = new ReentrantLock();

//	private static StrongUuidGenerator strongUidGen = new StrongUuidGenerator();

	public static long genId() throws Exception {
		lock.lock();
		try {
			long id = 0L;
			do {
				id = System.currentTimeMillis();
			} while (id == uid);
			uid = id;
			return id;
		} finally {
			lock.unlock();
		}
	}

	public static final String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static void main(String[] args) throws InterruptedException {
	}

	public static String getNextId() {
		ProcessEngineConfigurationImpl config = (ProcessEngineConfigurationImpl) ContextUtil
				.getBean("processEngineConfiguration");
		return config.getIdGenerator().getNextId();
	}

//	public static String genStrongUID() {
//		return strongUidGen.getNextId();
//	}
}

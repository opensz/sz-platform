package org.sz.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.core.dao.LookupDao;
import org.sz.core.service.LookupManager;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

}

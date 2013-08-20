package org.sz.core.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sz.core.dao.GenericDao;
import org.sz.core.dao.jpa.GenericDaoJpa;
import org.sz.core.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

public class GenericDaoTest extends BaseDaoTestCase {
    Log log = LogFactory.getLog(GenericDaoTest.class);
    GenericDao<User, Long> genericDao;
    @PersistenceContext(unitName=GenericDaoJpa.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Before
    public void setUp() {
        genericDao = new GenericDaoJpa<User, Long>(User.class, entityManager);
    }

    @Test
    public void getUser() {
        User user = genericDao.get(-1L);
        assertNotNull(user);
        assertEquals("user", user.getUsername());
    }
}

package org.sz.core.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.sz.core.dao.LookupDao;
import org.sz.core.model.Role;

/**
 * JPA implementation of LookupDao.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 */
@Repository
public class LookupDaoJpa implements LookupDao {
    private Log log = LogFactory.getLog(LookupDaoJpa.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return entityManager.createQuery(
                "select r from Role r order by name").getResultList();
    }
}

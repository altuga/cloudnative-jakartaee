package com.kodcu.cloudnative.connections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by altuga
 */
public class ConnectionRepository  {

    @PersistenceContext
    EntityManager em;


    public Iterable<Connection> findByFollower(long parseLong) {

        Query query = em.createQuery("select conn from Connection conn where conn.follower = :follower");
        query.setParameter("follower" , parseLong);
        return query.getResultList();
    }

    public Iterable<Connection> findAll() {
        Query query = em.createQuery("select conn from Connection conn ");
        return query.getResultList();

    }

    public void save(Connection newConnection) {
        em.merge(newConnection);
    }

    public Connection findById(Long connectionId) {


        return em.find(Connection.class, connectionId);
        /*Query query = em.createQuery("select conn from Connection conn where conn.id = :connectionId");
        query.setParameter("connectionId" , connectionId);
        return query.getResultList();
        */

    }

    public void delete(Connection connection) {
       em.remove(connection);
    }
}

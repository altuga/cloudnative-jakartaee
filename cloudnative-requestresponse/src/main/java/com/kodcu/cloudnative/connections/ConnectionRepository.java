package com.kodcu.cloudnative.connections;

import com.corneliadavis.cloudnative.connections.Connection;
import com.kodcu.cloudnative.posts.Post;
import org.springframework.data.repository.CrudRepository;

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

}

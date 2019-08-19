package com.kodcu.cloudnative.connections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by altuga
 */
public class UserRepository  {

    @PersistenceContext
    EntityManager em;


    public User findById(long parseLong) {

        Query query = em.createQuery("select user from User user where user.id = :id");
        query.setParameter("id" , parseLong);
        return (User) query.getSingleResult();
    }



    public User findByUsername(String username) {

        Query query = em.createQuery("select user from User user where user.username = :username");
        query.setParameter("username" , username);
        return (User) query.getSingleResult();
    }


    public List<User> findAll() {
        Query query = em.createQuery("select user from User user" );
        return query.getResultList();
    }


    public void save(User newUser) {
        em.merge(newUser);
    }
}

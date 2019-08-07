package com.kodcu.cloudnative.connections;

import com.corneliadavis.cloudnative.connections.User;
import com.kodcu.cloudnative.posts.Post;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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


    public Iterable<User> findAll() {
        Query query = em.createQuery("select user from User user" );
        return query.getResultList();
    }



}

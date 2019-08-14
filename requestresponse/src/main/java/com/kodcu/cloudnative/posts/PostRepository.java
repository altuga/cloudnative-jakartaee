package com.kodcu.cloudnative.posts;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class PostRepository  {


    @PersistenceContext
    EntityManager em;


    public Iterable<Post> findByUserId(long parseLong) {

        Query query = em.createQuery("select post from Post post where post.userId = :userid");
        query.setParameter("userid" , parseLong);
        return query.getResultList();
    }

    public void save(Post newPost) {
        em.merge(newPost);
    }

    public Iterable<Post> findAll() {
        Query query = em.createQuery("select post from Post post" );
        return query.getResultList();
    }
}

package com.kodcu.cloudnative.posts;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by corneliadavis on 9/4/17.
 */
public class PostRepository  {


    @PersistenceContext
    EntityManager em;


    public List<Post> findByUserId(long parseLong) {

        Query query = em.createQuery("select post from Post post where post.userId = :userid");
        query.setParameter("userid" , parseLong);
        return query.getResultList();
    }

    public void save(Post newPost) {
        em.merge(newPost);
    }

    public List<Post> findAll() {
        Query query = em.createQuery("select post from Post post" );
        return query.getResultList();
    }
}

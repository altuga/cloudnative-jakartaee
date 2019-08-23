package com.kodcu.cloudnative.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostsController {

    private static final Logger logger =
            LoggerFactory.getLogger(PostsController.class);


    @Inject
    PostRepository postRepository;

    @GET
    public List<Post> getPostsByUserId(
            @QueryParam("userIds") String userIds) {

        List<Post> posts;

        if (userIds == null) {
            logger.info("getting all posts");
            posts = postRepository.findAll();
            return posts;
        } else {
            ArrayList<Post> postsForUsers = new ArrayList<Post>();
            String userId[] = userIds.split(",");
            for (int i = 0; i < userId.length; i++) {
                logger.info("getting posts for userId " + userId[i]);
                posts = postRepository.findByUserId(Long.parseLong(userId[i]));
                posts.forEach(post -> postsForUsers.add(post));
            }
            return postsForUsers;

        }

    }

    @POST
    public void newPost(
            Post newPost) {

        logger.info("Have a new post with title " + newPost.getTitle());
        postRepository.save(newPost);

    }


}

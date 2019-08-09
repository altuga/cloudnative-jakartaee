package com.kodcu.cloudnative.config;


import com.kodcu.cloudnative.connections.Connection;
import com.kodcu.cloudnative.connections.ConnectionsController;
import com.kodcu.cloudnative.connections.User;
import com.kodcu.cloudnative.posts.Post;
import com.kodcu.cloudnative.posts.PostsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * Created by corneliadavis on 9/4/17.
 */



public class RepositoriesPopulator  {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesPopulator.class);


    @Inject
    ConnectionsController connectionsWriteController;

    @Inject
    PostsController postsWriteController;


    @PostConstruct
    private void populate() {
        logger.info("Loading sample data");
        User user1, user2, user3;
        Post post1, post2, post3, post4;
        Connection connection1, connection2, connection3;
        //ConnectionsController connectionsWriteController = applicationContext.getBean(ConnectionsController.class);
        // PostsController postsWriteController = applicationContext.getBean(PostsController.class);

        user1 = new User("Cornelia", "cdavisafc");
        connectionsWriteController.newUser(user1,null);
        user2 = new User("Max", "madmax");
        connectionsWriteController.newUser(user2,null);
        user3 = new User( "Glen", "gmaxdavis");
        connectionsWriteController.newUser(user3,null);

        post1 = new Post(2L, "Chicken Pho", "This is my attempt to recreate what I ate in Vietnam...");
        postsWriteController.newPost(post1, null);
        post2 = new Post(1L, "Whole Orange Cake", "That's right, you blend up whole oranges, rind and all...");
        postsWriteController.newPost(post2, null);
        post3 = new Post(1L, "German Dumplings (Kloesse)", "Russet potatoes, flour (gluten free!) and more...");
        postsWriteController.newPost(post3, null);
        post4 = new Post(3L, "French Press Lattes", "We've figured out how to make these dairy free, but just as good!...");
        postsWriteController.newPost(post4, null);

        connection1 = new Connection(2L, 1L);
        connectionsWriteController.newConnection(connection1, null);
        connection2 = new Connection(1L, 2L);
        connectionsWriteController.newConnection(connection2, null);
        connection3 = new Connection(1L, 3L);
        connectionsWriteController.newConnection(connection3, null);

    }

}



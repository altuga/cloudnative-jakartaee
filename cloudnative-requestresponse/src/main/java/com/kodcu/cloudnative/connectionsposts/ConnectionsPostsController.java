package com.kodcu.cloudnative.connectionsposts;


import com.kodcu.cloudnative.connections.Connection;
import com.kodcu.cloudnative.connections.User;
import com.kodcu.cloudnative.posts.Post;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;



public class ConnectionsPostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPostsController.class);

    //@Value("${newfromconnectionscontroller.connectionsUrl}")
    @ConfigProperty(name = "newfromconnectionscontroller.connectionsUrl")
    private String connectionsUrl;

    //@Value("${newfromconnectionscontroller.postsUrl}")
    @ConfigProperty(name = "newfromconnectionscontroller.postsUrl")
    private String postsUrl;


    //@Value("${newfromconnectionscontroller.usersUrl}")
    @ConfigProperty(name = "newfromconnectionscontroller.usersUrl")
    private String usersUrl;


    private Client client;
    private WebTarget tut;




    //@RequestMapping(method = RequestMethod.GET, value="/connectionsposts/{username}")
    @GET
    @Path("/connectionsposts/{username}")
    public Iterable<PostSummary> getByUsername(@PathParam("username") String username, HttpServletResponse response) {


        ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();
        logger.info("getting posts for user network " + username);

        String ids = "";
        //RestTemplate restTemplate = new RestTemplate();
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target(connectionsUrl+username);

        Response responseForConnection = this.tut.request().get();
        Connection[] connections = (Connection[])responseForConnection.getEntity();
        // get connections
       // ResponseEntity<Connection[]> respConns = restTemplate.getForEntity(connectionsUrl+username, Connection[].class);
        //Connection[] connections = respConns.getBody();
        for (int i=0; i<connections.length; i++) {
            if (i > 0) ids += ",";
            ids += connections[i].getFollowed().toString();
        }
        logger.info("connections = " + ids);

        // get posts for those connections
        //ResponseEntity<Post[]> respPosts = restTemplate.getForEntity(postsUrl+ids, Post[].class);
        //Post[] posts = respPosts.getBody();

        this.tut = this.client.target(postsUrl+ids);
        Response responseForPosts  =  this.tut.request().get();
        Post[] posts = (Post[])responseForPosts.getEntity();

        for (int i=0; i<posts.length; i++)
            postSummaries.add(new PostSummary(getUsersname(posts[i].getUserId()), posts[i].getTitle(), posts[i].getDate()));

        return postSummaries;
    }

    private String getUsersname(Long id) {

        this.client = ClientBuilder.newClient();
        this.tut = this.client.target(usersUrl+id);
        Response responseForUsers  =  this.tut.request().get();
        User user = (User)responseForUsers.getEntity();

        return user.getName();

        //RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<User> resp = restTemplate.getForEntity(usersUrl+id, User.class);
        //return resp.getBody().getName();
    }



}

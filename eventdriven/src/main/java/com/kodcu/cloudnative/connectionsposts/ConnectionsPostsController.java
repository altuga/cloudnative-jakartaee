package com.kodcu.cloudnative.connectionsposts;


import com.kodcu.cloudnative.connections.Connection;
import com.kodcu.cloudnative.connections.User;
import com.kodcu.cloudnative.posts.Post;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Stateless
@Path("connectionsposts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConnectionsPostsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPostsController.class);

    //@Value("${newfromconnectionscontroller.connectionsUrl}")
    @Inject
    @ConfigProperty(name = "newfromconnectionscontroller.connectionsUrl")
    private String connectionsUrl;

    //@Value("${newfromconnectionscontroller.postsUrl}")
    @Inject
    @ConfigProperty(name = "newfromconnectionscontroller.postsUrl")
    private String postsUrl;


    //@Value("${newfromconnectionscontroller.usersUrl}")
    @Inject
    @ConfigProperty(name = "newfromconnectionscontroller.usersUrl")
    private String usersUrl;


    private Client client;
    private WebTarget target;


    //@RequestMapping(method = RequestMethod.GET, value="/connectionsposts/{username}")
    @GET
    @Path("/{username}")
    public List<PostSummary> getByUsername(@PathParam("username") String username, HttpServletResponse response) {


        ArrayList<PostSummary> postSummaries = new ArrayList<PostSummary>();
        logger.info("getting posts for user network " + username);

        String ids = "";
        //RestTemplate restTemplate = new RestTemplate();
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(connectionsUrl + username);

        //List<Connection> connections = this.target.request(MediaType.APPLICATION_JSON).get(Response.class).readEntity(new GenericType<List<Connection>>() {
        //});

        //Response response1 = this.target.request().get();
        List<Connection> connections = this.target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Connection>>() {});

        System.out.println(" --> " + connections);

        for (int i = 0; i < connections.size(); i++) {
            System.out.println(connections.get(i).getFollowed());
            if (i > 0) ids += ",";
            ids += connections.get(i).getFollowed().toString();
        }

        /*

        for (Connection connection : connections) {
            System.out.println(connection.getFollowed());

            ids += connection.getFollowed().toString();
        }*/



        ;
        //List<Collection> connections = (Connection[])responseForConnection.getEntity();
        // get connections
        // ResponseEntity<Connection[]> respConns = restTemplate.getForEntity(connectionsUrl+username, Connection[].class);
        //Connection[] connections = respConns.getBody();

        /*
        for (Connection connection : connections) {
            System.out.println(connection.getFollowed());
            ids += connection.getFollowed().toString();
        }
        */
        /*for (int i=0; i<connections.; i++) {
            if (i > 0) ids += ",";
            ids += connections[i].getFollowed().toString();
        }*/
        logger.info("connections = " + ids);

        // get posts for those connections
        //ResponseEntity<Post[]> respPosts = restTemplate.getForEntity(postsUrl+ids, Post[].class);
        //Post[] posts = respPosts.getBody();

        this.target = this.client.target(postsUrl + ids);

        List<Post> posts = this.target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Post>>() {});

        //Response responseForPosts = this.target.request().get();
        //Post[] posts = (Post[]) responseForPosts.getEntity();

        for (Post post : posts) {
            postSummaries.add(new PostSummary(getUsersname(post.getUserId()), post.getTitle(), post.getDate()));
        }

        //for (int i = 0; i < posts.length; i++)
        //    postSummaries.add(new PostSummary(getUsersname(posts[i].getUserId()), posts[i].getTitle(), posts[i].getDate()));

        return postSummaries;
    }

    private String getUsersname(Long id) {

        this.client = ClientBuilder.newClient();
        this.target = this.client.target(usersUrl + id);
        User user = this.target.request(MediaType.APPLICATION_JSON).get(new GenericType<User>() {});

        return user.getName();

        //RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<User> resp = restTemplate.getForEntity(usersUrl+id, User.class);
        //return resp.getBody().getName();
    }


}

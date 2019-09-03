package com.kodcu.cloudnative.connections.write;



import com.kodcu.cloudnative.connections.ConnectionRepository;
import com.kodcu.cloudnative.connections.User;
import com.kodcu.cloudnative.connections.UserRepository;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConnectionsWriteController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsWriteController.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private ConnectionRepository connectionRepository;

    //@RequestMapping(method = RequestMethod.POST, value="/users")
    @POST
    @Path("/users")
    public void newUser(User newUser) {

        logger.info("Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);

        //event


        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080/eventdriven/resources/connections/");

        target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(newUser,MediaType.APPLICATION_JSON),
                        String.class);



    }

    /*
    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@PathVariable("id") Long userId, @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user with id " + userId);
        User user = userRepository.findById(userId).get();
        newUser.setId(userId);
        userRepository.save(newUser);

        //event
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8080/connectionsposts/users/"+newUser.getId(), newUser);

    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody Connection newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() +
                    " is following " + newConnection.getFollowed());
        connectionRepository.save(newConnection);

        //event
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.postForEntity(
                "http://localhost:8080/connectionsposts/connections", newConnection, String.class);
        logger.info("resp " + resp.getStatusCode());
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{id}")
    public void deleteConnection(@PathVariable("id") Long connectionId, HttpServletResponse response) {

        Connection connection = connectionRepository.findById(connectionId).get();

        logger.info("deleting connection: " + connection.getFollower() + " is no longer following " + connection.getFollowed());
        connectionRepository.delete(connection);

        //event
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/connectionsposts/connections/"+connectionId);

    }

    */

}

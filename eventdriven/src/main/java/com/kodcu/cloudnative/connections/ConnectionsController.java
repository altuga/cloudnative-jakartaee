package com.kodcu.cloudnative.connections;

import org.slf4j.LoggerFactory;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import java.util.List;


@Stateless
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private ConnectionRepository connectionRepository;


    @GET
    @Path("/users")
	public List<User> getUsers(HttpServletResponse response) {

        logger.info("getting users...");
        List<User> users;
        users = userRepository.findAll();

		return users;
	}

	@GET
    @Path("/users/{user}")
	public User getByUsername(@PathParam("user") String user) {
        logger.info("getting user " + user);
        try {
            Long id = Long.parseLong(user);
            return userRepository.findById(id);
        } catch(NumberFormatException e) {
            return userRepository.findByUsername(user);
        }
    }


    //@RequestMapping(method = RequestMethod.POST, value="/users")
    @POST
    @Path("/addusers/")
    public User newUser(User newUser) {
        logger.info("Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);
        return newUser;

    }

    // @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    @PUT
    @Path("/users/{id}")
    public void updateUser(@PathParam("id") Long userId, User newUser) {

        logger.info("Updating user with id " + userId);
        User user = userRepository.findById(userId);
        newUser.setId(userId);
        userRepository.save(newUser);

    }

    //@RequestMapping(method = RequestMethod.GET, value="/connections")
    @GET
    @Path("/connections")
    public GenericEntity<List<Connection>> getConnections() {

        logger.info("getting connections");
        List<Connection> connections;
        connections = connectionRepository.findAll();

        return new GenericEntity<List<Connection>>(connections) {};

    }

    //@RequestMapping(method = RequestMethod.GET, value="/connections/{username}")
    @GET
    @Path("/connections/{username}")
    @Consumes ( { MediaType.APPLICATION_JSON })
    @Produces ( { MediaType.APPLICATION_JSON })
    public List<Connection> getConnections(@PathParam("username") String username) {
        logger.info("getting connections for username " + username);
        Long userId = getByUsername(username).getId();
        List<Connection> connections;
        connections = connectionRepository.findByFollower(userId);

        return connections;
    }

    //@RequestMapping(method = RequestMethod.POST, value="/connections")
    @POST
    @Path("/connections")
    public void newConnection(Connection newConnection) {

        logger.info("Have a new connection: " + newConnection.getFollower() + " is following " + newConnection.getFollowed());
        connectionRepository.save(newConnection);

    }

    //@RequestMapping(method = RequestMethod.DELETE, value="/connections/{id}")
    @DELETE
    @Path("/connections/{id}")
    public void deleteConnection(@PathParam("id") Long connectionId) {

        Connection connection = connectionRepository.findById(connectionId);

        logger.info("deleting connection: " + connection.getFollower() + " is no longer following " + connection.getFollowed());
        connectionRepository.delete(connection);

    }




}

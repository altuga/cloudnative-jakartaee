package com.kodcu.cloudnative.connections;

import org.slf4j.LoggerFactory;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;
@Stateless
@Path("/users")
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private ConnectionRepository connectionRepository;


    @GET
	public Iterable<User> getUsers(HttpServletResponse response) {

        logger.info("getting users");
        Iterable<User> users;
        users = userRepository.findAll();

		return users;
	}

	@GET
    @Path("{user}")
	public User getByUsername( String user, HttpServletResponse response) {
        logger.info("getting user " + user);
        try {
            Long id = Long.parseLong(user);
            return userRepository.findById(id);
        } catch(NumberFormatException e) {
            return userRepository.findByUsername(user);
        }
    }

    /*
    @RequestMapping(method = RequestMethod.POST, value="/users")
    public void newUser(@RequestBody User newUser, HttpServletResponse response) {

        logger.info("Have a new user with username " + newUser.getUsername());
        userRepository.save(newUser);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@PathVariable("id") Long userId, @RequestBody User newUser, HttpServletResponse response) {

        logger.info("Updating user with id " + userId);
        User user = userRepository.findById(userId).get();
        newUser.setId(userId);
        userRepository.save(newUser);

    }

    @RequestMapping(method = RequestMethod.GET, value="/connections")
    public Iterable<Connection> getConnections(HttpServletResponse response) {

        logger.info("getting connections");
        Iterable<Connection> connections;
        connections = connectionRepository.findAll();

        return connections;
    }

    @RequestMapping(method = RequestMethod.GET, value="/connections/{username}")
    public Iterable<Connection> getConnections(@PathVariable("username") String username, HttpServletResponse response) {
        logger.info("getting connections for username " + username);
        Long userId = getByUsername(username, null).getId();
        Iterable<Connection> connections;
        connections = connectionRepository.findByFollower(userId);

        return connections;
    }

    @RequestMapping(method = RequestMethod.POST, value="/connections")
    public void newConnection(@RequestBody Connection newConnection, HttpServletResponse response) {

        logger.info("Have a new connection: " + newConnection.getFollower() + " is following " + newConnection.getFollowed());
        connectionRepository.save(newConnection);

    }

    @RequestMapping(method = RequestMethod.DELETE, value="/connections/{id}")
    public void deleteConnection(@PathVariable("id") Long connectionId, HttpServletResponse response) {

        Connection connection = connectionRepository.findById(connectionId).get();

        logger.info("deleting connection: " + connection.getFollower() + " is no longer following " + connection.getFollowed());
        connectionRepository.delete(connection);

    }

    */


}

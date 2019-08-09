package com.kodcu.cloudnative;

import com.kodcu.cloudnative.config.RepositoriesPopulator;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author Kodcu.com
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {

   //@Inject
    //RepositoriesPopulator repositoriesPopulator;

}

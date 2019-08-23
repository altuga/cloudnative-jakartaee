package com.kodcu.cloudnative.ping.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("ping")
public class PingResource {

    static int inc = 0 ;

    @GET
    public String ping() {
        return "Enjoy Java EE 8!!!!!***** " + inc++;
    }

}

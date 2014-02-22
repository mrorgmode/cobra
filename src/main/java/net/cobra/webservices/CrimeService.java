package net.cobra.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 

@Path("/crimes")
public class CrimeService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String location) {
 
		String output = "Location: " + location;
 
		return Response.status(200).entity(output).build();
 
	}
 
	
}

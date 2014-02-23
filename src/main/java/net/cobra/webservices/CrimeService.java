package net.cobra.webservices;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import net.cobra.CrimeProvider;
 

@Path("/crimes")
public class CrimeService {

	@Inject
	private CrimeProvider provider;
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String location) {
 
		String output = "Location: " + location + " nrCrimes: " + provider.getCrimes().size();
 
		return Response.status(200).entity(output).build();
 
	}
 
	
}

package net.cobra.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import net.cobra.CobraProvider;
import net.cobra.valueobjects.Crime;


@Path("/update")
public class TriggerFetch {
	@GET
	//@Path("/{param}")
	//public Response fetch(@PathParam("param") String location) {
	public Response fetch() {
		List<Crime> crimes = CobraProvider.getCrimes();
		return Response.status(200).entity("number of crimes:"+crimes.size()).build(); 
	}

}

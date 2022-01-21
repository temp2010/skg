package co.skg.test.controller;

import co.skg.test.model.User;
import co.skg.test.service.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

	@POST
	public boolean createUser(User user) {

		return UserService.createUser(user);
	}

	@GET
	@Path("{document}")
	public User getUser(@PathParam("document") Integer document) {

		return UserService.searchUser(document);
	}

}
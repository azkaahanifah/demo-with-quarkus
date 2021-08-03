package com.demo.test.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.demo.test.model.PostsDTO;

@Path(DemoResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DemoResource {
	
	public static final String RESOURCE_PATH = "/demo";
	
	@POST
	@Transactional
	public Response create(PostsDTO posts) {
		PostsDTO.persist(posts);
		if (posts.isPersistent()) {
			return Response.status(Response.Status.CREATED).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@GET
	public Response getAll() {
		List<PostsDTO> posts = PostsDTO.listAll();
		System.out.println(posts);
		if (!posts.isEmpty()) {
			return Response.ok(posts).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("{id}")
	public Response getById(@PathParam("id") Long id) {
		return PostsDTO.findByIdOptional(id)
			.map(posts -> Response.ok(posts).build())
			.orElse(Response.status(Response.Status.NOT_FOUND).build());
	}
	
	@DELETE
	@Path("id")
	@Transactional
	public Response deleteById(@PathParam("id") Long id) {
		boolean deleteById = PostsDTO.deleteById(id);
		if (deleteById) {
			return Response.noContent().build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

}

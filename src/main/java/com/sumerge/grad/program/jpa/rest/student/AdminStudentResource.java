package com.sumerge.grad.program.jpa.rest.student;

import com.sumerge.grad.program.jpa.entity.Student;
import com.sumerge.grad.program.jpa.rest.RestServices;

import javax.faces.bean.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/admin/students")
public class AdminStudentResource extends RestServices {


    @Context
    private SecurityContext securityContext;
//    @GET
//    public Response getAllStudents() {
//
//            return super.getAllStudents();
//    }

    @Transactional
    @PUT
    public Response addStudent(Student student)
    {
        if(securityContext.isUserInRole("admin"))
            return super.addStudent(student);
        else
            return Response.serverError().build();
    }

    @Transactional
    @POST
    public Response editStudent(Student student)
    {
        if (securityContext.isUserInRole("admin"))
            return super.editStudent(student);
        else
            return Response.serverError().build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") Long id)
    {
        if (securityContext.isUserInRole("admin"))
            return super.deleteStudent(id);
        else
            return Response.serverError().build();
    }



}

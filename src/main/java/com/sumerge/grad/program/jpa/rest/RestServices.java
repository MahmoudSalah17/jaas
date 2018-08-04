package com.sumerge.grad.program.jpa.rest;

import com.sumerge.grad.program.jpa.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.sumerge.grad.program.jpa.constants.Constants.PERSISTENT_UNIT;

public class RestServices {

    @PersistenceContext(unitName = PERSISTENT_UNIT)
    private EntityManager em;


    public Response addStudent(Student student)
    {
        try{
            if(student.getId() != null)
                throw new IllegalArgumentException("Can't create student since it exists in the database");
            em.merge(student);
            return Response.ok().entity(student).build();
        }
        catch (Exception e)
        {
            return Response.serverError().
                    entity(e).
                    build();
        }
    }


    public Response editStudent(Student student)
    {
        try {
            if(student.getId() == null)
                throw new IllegalArgumentException("Can't edit student since it doesn't exist in the database");
            Student old = em.find(Student.class, student.getId());
            old.setName(student.getName());
            old.setGender(student.getGender());
            old.setRollNumber(student.getRollNumber());
            em.merge(old);
            return Response.ok().entity(old).build();
        }
        catch (Exception e)
        {
            return Response.serverError().
                    entity(e).
                    build();
        }
    }


    public Response deleteStudent(Long id)
    {
        try {
            Student toBeDeleted = em.find(Student.class, id);
            if(toBeDeleted != null)
                em.remove(toBeDeleted);
            return Response.ok().entity(toBeDeleted).build();
        }
        catch (Exception e)
        {
            return Response.serverError().
                    entity(e).
                    build();
        }
    }

    public Response getAllStudents() {
        try {
            return Response.ok().
                    entity(em.createQuery("SELECT s FROM Student s", Student.class).getResultList()).
                    build();
        } catch (Exception e) {
            return Response.serverError().
                    entity(e).
                    build();
        }
    }
}

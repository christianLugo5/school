package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CareerController;
import com.school.portal.controller.StudentController;
import com.school.portal.model.Student;

@Component
public class StudentAssembler implements RepresentationModelAssembler<Student, EntityModel<Student>> {

	@Override
	public EntityModel<Student> toModel(Student student) {
		return EntityModel.of(student, 
				WebMvcLinkBuilder.linkTo(StudentController.class).slash(student.getId()).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CareerController.class)
						.allCareersByStudent(student.getId())).withRel("careers"));
	}

}

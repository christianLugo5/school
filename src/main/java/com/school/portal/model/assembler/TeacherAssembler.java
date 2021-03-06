package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.TeacherController;
import com.school.portal.model.Teacher;

@Component
public class TeacherAssembler implements RepresentationModelAssembler<Teacher, EntityModel<Teacher>> {

	@Override
	public EntityModel<Teacher> toModel(Teacher teacher) {
		return EntityModel.of(teacher,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TeacherController.class).one(teacher.getId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TeacherController.class).all())
						.withRel("Teachers"));
	}

}

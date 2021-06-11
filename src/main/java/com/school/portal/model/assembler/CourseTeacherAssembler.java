package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CourseTeacherController;
import com.school.portal.controller.TeacherController;
import com.school.portal.model.CourseTeacher;

@Component
public class CourseTeacherAssembler implements RepresentationModelAssembler<CourseTeacher, EntityModel<CourseTeacher>> {

	@Override
	public EntityModel<CourseTeacher> toModel(CourseTeacher courseTeacher) {
		return EntityModel.of(courseTeacher, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseTeacherController.class).one(courseTeacher.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TeacherController.class)
						.one(courseTeacher.getTeacher().getId())).withRel("teacher"));
	}

}

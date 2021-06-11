package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CourseController;
import com.school.portal.controller.SubjectController;
import com.school.portal.model.Course;

@Component
public class CourseAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {

	@Override
	public EntityModel<Course> toModel(Course course) {
		return EntityModel.of(course,
				WebMvcLinkBuilder.linkTo(CourseController.class).slash(course.getId()).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).all()).withRel("subjects"));
	}

}

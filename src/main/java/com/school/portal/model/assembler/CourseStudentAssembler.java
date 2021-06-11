package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CourseStudentController;
import com.school.portal.model.CourseStudent;

@Component
public class CourseStudentAssembler implements RepresentationModelAssembler<CourseStudent, EntityModel<CourseStudent>> {

	@Override
	public EntityModel<CourseStudent> toModel(CourseStudent courseStudent) {
		return EntityModel.of(courseStudent,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseStudentController.class).one(
						courseStudent.getCourseTeacher().getCourse().getId(), courseStudent.getStudent().getId(),
						courseStudent.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseStudentController.class)
						.all(courseStudent.getCourseTeacher().getCourse().getId(), courseStudent.getStudent().getId()))
						.withRel("studentsClasses"));
	}

}

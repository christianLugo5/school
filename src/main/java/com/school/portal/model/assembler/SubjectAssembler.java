package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.SubjectController;
import com.school.portal.model.Subject;

@Component
public class SubjectAssembler implements RepresentationModelAssembler<Subject, EntityModel<Subject>> {

	@Override
	public EntityModel<Subject> toModel(Subject subject) {
		return EntityModel.of(subject,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).one(subject.getId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).all())
						.withRel("Subjects"));
	}

}

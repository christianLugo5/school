package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.SchoolController;
import com.school.portal.model.School;

@Component
public class SchoolAssembler implements RepresentationModelAssembler<School, EntityModel<School>> {

	@Override
	public EntityModel<School> toModel(School school) {
		return EntityModel.of(school,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SchoolController.class).one(school.getId()))
						.withSelfRel());
	}

}

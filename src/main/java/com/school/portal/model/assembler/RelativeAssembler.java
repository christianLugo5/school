package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.RelativeController;
import com.school.portal.model.Relative;

@Component
public class RelativeAssembler implements RepresentationModelAssembler<Relative, EntityModel<Relative>>{

	@Override
	public EntityModel<Relative> toModel(Relative relative) {
		return EntityModel.of(relative, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelativeController.class).one(relative.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelativeController.class).allStudents(relative.getId())).withRel("students"));
	}

}

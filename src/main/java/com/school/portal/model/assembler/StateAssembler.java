package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.StateController;
import com.school.portal.model.State;

@Component
public class StateAssembler implements RepresentationModelAssembler<State, EntityModel<State>> {

	@Override
	public EntityModel<State> toModel(State state) {
		return EntityModel.of(state, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StateController.class).one(state.getCountry().getId(), state.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StateController.class).all(state.getCountry().getId())).withRel("states"));
	}

}

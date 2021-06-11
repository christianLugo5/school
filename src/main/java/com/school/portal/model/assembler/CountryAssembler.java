package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CountryController;
import com.school.portal.controller.StateController;
import com.school.portal.model.Country;

@Component
public class CountryAssembler implements RepresentationModelAssembler<Country, EntityModel<Country>> {

	@Override
	public EntityModel<Country> toModel(Country country) {
		return EntityModel.of(country,
				WebMvcLinkBuilder.linkTo(CountryController.class).slash(country.getId()).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StateController.class).all(country.getId()))
				.withRel("states"));
	}

}

package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CityController;
import com.school.portal.model.City;

@Component
public class CityAssembler implements RepresentationModelAssembler<City, EntityModel<City>> {

	@Override
	public EntityModel<City> toModel(City city) {
		return EntityModel.of(city, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).one(city.getState().getCountry().getId(), city.getState().getId(), city.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).all(city.getState().getCountry().getId(), city.getState().getId())).withRel("cities"));
	}

}

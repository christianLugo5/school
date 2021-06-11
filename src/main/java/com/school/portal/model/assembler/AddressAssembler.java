package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.AddressController;
import com.school.portal.model.Address;

@Component
public class AddressAssembler implements RepresentationModelAssembler<Address, EntityModel<Address>> {

	@Override
	public EntityModel<Address> toModel(Address address) {
		return EntityModel.of(address, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
						.one(address.getCity().getState().getCountry().getId(), address.getCity().getState().getId(), 
								address.getCity().getId(), address.getId())).withSelfRel());
	}

}

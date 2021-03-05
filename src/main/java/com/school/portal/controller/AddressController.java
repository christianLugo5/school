package com.school.portal.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Address;
import com.school.portal.model.assembler.AddressAssembler;
import com.school.portal.repository.AddressRepository;

@RestController
@Validated
public class AddressController {

	@Autowired
	private AddressRepository repository;
	@Autowired
	private AddressAssembler assembler;
		
	@GetMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}/addresses")
	@Validated
	public ResponseEntity<CollectionModel<EntityModel<Address>>> all(@Positive @PathVariable Integer countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId) {				
		List<EntityModel<Address>> addresses = repository.findAllByCityId(cityId).stream().map(assembler::toModel)
				.collect(Collectors.toList());		
		return ResponseEntity.ok(CollectionModel.of(addresses, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
						.all(countryId, stateId, cityId)).withSelfRel()));
	};

	@GetMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}/addresses/{id}")
	public ResponseEntity<EntityModel<Address>> one(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId, @Positive @PathVariable int id) {				
		Address address = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found " + id));
		if(countryId != address.getCity().getState().getCountry().getId() || stateId != address.getCity().getState().getId()
				|| cityId != address.getCity().getId())
			return ResponseEntity.badRequest().build();
				
		return ResponseEntity.ok(assembler.toModel(address));
	}

	@PostMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}/addresses")
	public ResponseEntity<?> newAddress(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId, @Positive @PathVariable int cityId,
			@Valid @RequestBody Address newAddress) {
		if (newAddress.getCity().getId() != cityId || newAddress.getCity().getState().getId() != stateId 
				|| newAddress.getCity().getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
		EntityModel<Address> address = assembler.toModel(repository.save(newAddress));		
		return ResponseEntity.created(address.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(address);
	}

	@PutMapping("/cities/{countryId}/states/{stateId}/cities/{cityId}/addresses/{id}")
	public ResponseEntity<?> replaceAddress(@RequestBody Address newAddress, @Positive @PathVariable int countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId, @Positive @PathVariable int id) {
		if (newAddress.getId() != id || newAddress.getCity().getId() != cityId || newAddress.getCity().getState().getId() != stateId 
				|| newAddress.getCity().getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
		
		Address updatedAddress = repository.findById(id)
				.map(address -> {
					address = newAddress;
					return repository.save(address);
				}).orElseThrow(() -> new RuntimeException("Not found " + id));
		EntityModel<Address> entityModel = assembler.toModel(updatedAddress);		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}/addresses/{id}")
	public ResponseEntity<?> deleteAddress(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId, @Positive @PathVariable int cityId,
			@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ExceptionHandler
	public String constraintViolationHandler(ConstraintViolationException ex){
		return ex.getConstraintViolations().iterator().next()
                .getMessage();
	}

}

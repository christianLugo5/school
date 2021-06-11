package com.school.portal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Address;
import com.school.portal.model.assembler.AddressAssembler;
import com.school.portal.repository.AddressRepository;

@RestController
@Validated
@RequestMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}/addresses")
public class AddressController {

	private final AddressRepository repository;
	private final AddressAssembler assembler;
	
	public AddressController(AddressRepository repository, AddressAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;		
	}
		
	@GetMapping
	@Validated
	public ResponseEntity<CollectionModel<EntityModel<Address>>> all(@Positive @PathVariable Integer countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId) {				
		List<EntityModel<Address>> addresses = repository.findAllByCityId(cityId).stream().map(assembler::toModel)
				.collect(Collectors.toList());		
		return ResponseEntity.ok(CollectionModel.of(addresses, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class)
						.all(countryId, stateId, cityId)).withSelfRel()));
	};

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Address>> one(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId, @Positive @PathVariable int id) {				
		Address address = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Found " + id));
		if(countryId != address.getCity().getState().getCountry().getId() || stateId != address.getCity().getState().getId()
				|| cityId != address.getCity().getId())
			return ResponseEntity.badRequest().build();
				
		return ResponseEntity.ok(assembler.toModel(address));
	}

	@PostMapping
	public ResponseEntity<?> newAddress(@Valid @RequestBody Address newAddress, @Positive @PathVariable int countryId, 
			@Positive @PathVariable int stateId, @Positive @PathVariable int cityId) {
		if (newAddress.getCity().getId() != cityId || newAddress.getCity().getState().getId() != stateId 
				|| newAddress.getCity().getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
		EntityModel<Address> address = assembler.toModel(repository.save(newAddress));		
		return ResponseEntity.created(address.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(address);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> replaceAddress(@Valid @RequestBody Address newAddress, @Positive @PathVariable int countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId, @Positive @PathVariable int id) {
		if (newAddress.getId() != id || newAddress.getCity().getId() != cityId || newAddress.getCity().getState().getId() != stateId 
				|| newAddress.getCity().getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
		
		Address updatedAddress = repository.findById(id)
				.map(address -> {
					address = newAddress;
					return repository.save(address);
				}).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		EntityModel<Address> entityModel = assembler.toModel(updatedAddress);		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/{id}")
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

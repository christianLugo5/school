package com.school.portal.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
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
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.City;
import com.school.portal.model.assembler.CityAssembler;
import com.school.portal.repository.CityRepository;

@RestController
@Validated
public class CityController {
	
	private final CityRepository repository;
	private final CityAssembler assembler; 
	
	public CityController(CityRepository repository, CityAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/countries/{countryId}/states/{stateId}/cities")
	public ResponseEntity<CollectionModel<EntityModel<City>>> all(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId) {		
		List<EntityModel<City>> cities = repository.findAllByStateId(stateId).stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(cities, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class)
						.all(countryId, stateId)).withSelfRel()));
	}

	@GetMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}")
	public EntityModel<City> one(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId) {
		City city = repository.findById(cityId).orElseThrow(() -> new RuntimeException("Not Found " + cityId));
		if(city.getState().getId() != stateId || city.getState().getCountry().getId() != countryId)
			return null; 
		return assembler.toModel(city);
	}

	@PostMapping("/countries/{countryId}/states/{stateId}/cities")
	public ResponseEntity<?> newCity(@RequestBody City newCity, @Positive @PathVariable int countryId, @Positive @PathVariable int stateId) {
		if (newCity.getState().getId() != stateId || newCity.getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
				
		EntityModel<City> city = assembler.toModel(repository.save(newCity));
		return ResponseEntity.created(city.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(city);
	}

	@PutMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}")
	public ResponseEntity<?> replaceCity(@RequestBody City newCity, @Positive @PathVariable int countryId, @Positive @PathVariable int stateId,
			@Positive @PathVariable int cityId) {
		if (newCity.getId() != cityId || newCity.getState().getId() != stateId || newCity.getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
		
		City updatedCity = repository.findById(cityId)
				.map(city -> {
					city = newCity;
					return repository.save(city);
				})
				.orElseThrow(() -> new RuntimeException("Not Found " + cityId));
		EntityModel<City> entityModel = assembler.toModel(updatedCity);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}")
	public ResponseEntity<?> deleteCity(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId, @Positive @PathVariable int cityId) {
		try {
			repository.deleteById(cityId);
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

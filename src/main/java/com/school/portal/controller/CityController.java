package com.school.portal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class CityController {
	
	private final CityRepository repository;
	private final CityAssembler assembler; 
	
	public CityController(CityRepository repository, CityAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/countries/{countryId}/states/{stateId}/cities")
	public ResponseEntity<CollectionModel<EntityModel<City>>> all(@PathVariable int countryId, @PathVariable int stateId) {
		if(countryId < 0 || stateId < 0)
			return ResponseEntity.badRequest().build();
		
		List<EntityModel<City>> cities = repository.findAllByStateId(stateId).stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(cities, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class)
						.all(countryId, stateId)).withSelfRel()));
	}

	@GetMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}")
	public EntityModel<City> one(@PathVariable int countryId, @PathVariable int stateId,
			@PathVariable int cityId) {
		City city = repository.findById(cityId).orElseThrow(() -> new RuntimeException("Not Found " + cityId));
		if(city.getState().getId() != stateId || city.getState().getCountry().getId() != countryId)
			return null; 
		return assembler.toModel(city);
	}

	@PostMapping("/countries/{countryId}/states/{stateId}/cities")
	public ResponseEntity<?> newCity(@RequestBody City newCity, @PathVariable int countryId, @PathVariable int stateId) {
		if (countryId < 1 || stateId < 1 || newCity.getState().getId() != stateId 
				|| newCity.getState().getCountry().getId() != countryId)
			return ResponseEntity.badRequest().build();
				
		EntityModel<City> city = assembler.toModel(repository.save(newCity));
		return ResponseEntity.created(city.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(city);
	}

	@PutMapping("/countries/{countryId}/states/{stateId}/cities/{cityId}")
	public ResponseEntity<?> replaceCity(@RequestBody City newCity, @PathVariable int countryId, @PathVariable int stateId,
			@PathVariable int cityId) {
		if (countryId < 1 || stateId < 1 || cityId < 1 || newCity.getId() != cityId || newCity.getState().getId() != stateId 
				|| newCity.getState().getCountry().getId() != countryId)
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
	public ResponseEntity<?> deleteCity(@PathVariable int countryId, @PathVariable int stateId, @PathVariable int cityId) {
		if (countryId < 1 || stateId < 1 || cityId < 1) 
			return ResponseEntity.notFound().build();
		try {
			repository.deleteById(cityId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}

}

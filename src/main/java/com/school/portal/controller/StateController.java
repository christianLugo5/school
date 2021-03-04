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

import com.school.portal.model.State;
import com.school.portal.model.assembler.StateAssembler;
import com.school.portal.repository.StateRepository;

@RestController
public class StateController {

	private final StateRepository repository;
	private final StateAssembler assembler;

	public StateController(StateRepository repository, StateAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/countries/{id}/states")
	public ResponseEntity<CollectionModel<EntityModel<State>>> all(@PathVariable int id) {
		if (id < 1)
			return ResponseEntity.notFound().build();
			List<EntityModel<State>> states = repository.findAllByCountryId(id).stream().map(assembler::toModel)
					.collect(Collectors.toList());
			/*
			 * return Optional.ofNullable(repository.findAllByCountryId(id)) .map(state ->
			 * ResponseEntity.ok().body(state)).orElseGet(() ->
			 * ResponseEntity.notFound().build());
			 */
			return ResponseEntity.ok(CollectionModel.of(states,
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StateController.class).all(id)).withSelfRel()));

	}

	@GetMapping("/countries/{countryId}/states/{stateId}")
	public EntityModel<State> one(@PathVariable int countryId, @PathVariable int stateId) {
		/*
		 * if (stateId > 0) return
		 * Optional.ofNullable(repository.findById(stateId)).map(state ->
		 * ResponseEntity.ok().body(state)) .orElseGet(() ->
		 * ResponseEntity.notFound().build()); else return
		 * ResponseEntity.notFound().build();
		 */
		State state = repository.findById(stateId).orElseThrow(() -> new RuntimeException("Not found " + stateId));
		if (state.getCountry().getId() != countryId)
			return null;// this should be response not found
		return assembler.toModel(state);
	}

	@PostMapping("/countries/{id}/states")
	public ResponseEntity<?> newState(@RequestBody State state, @PathVariable int id) {
		if (id > 0 && state.getCountry().getId() == id) {
			EntityModel<State> entityModel = assembler.toModel(repository.save(state));
			return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/countries/{countryId}/states/{stateId}")
	public ResponseEntity<?> replaceState(@RequestBody State newState, @PathVariable int countryId,
			@PathVariable int stateId) {
		if (countryId > 0 && stateId > 0 && newState.getId() == stateId && newState.getCountry().getId() == countryId) {
			State updatedState = repository.findById(stateId).map(state -> {
				state = newState;
				return repository.save(state);
			}).orElseThrow(() -> new RuntimeException("Not found " + stateId));

			EntityModel<State> entityModel = assembler.toModel(updatedState);
			return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/countries/{countryId}/states/{stateId}")
	public ResponseEntity<?> deleteState(@PathVariable int countryId, @PathVariable int stateId) {
		if (countryId > 0 && stateId > 0) {
			try {
				repository.deleteById(stateId);
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}

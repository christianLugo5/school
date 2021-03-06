package com.school.portal.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.State;
import com.school.portal.model.assembler.StateAssembler;
import com.school.portal.repository.StateRepository;

@RestController
@Validated
public class StateController {

	private final StateRepository repository;
	private final StateAssembler assembler;

	public StateController(StateRepository repository, StateAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/countries/{id}/states")
	public ResponseEntity<CollectionModel<EntityModel<State>>> all(@Positive @PathVariable int id) {
		/*
		 * return Optional.ofNullable(repository.findAllByCountryId(id)) .map(state ->
		 * ResponseEntity.ok().body(state)).orElseGet(() ->
		 * ResponseEntity.notFound().build());
		 */
		List<EntityModel<State>> states = repository.findAllByCountryId(id).stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(states,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StateController.class).all(id)).withSelfRel()));

	}

	@GetMapping("/countries/{countryId}/states/{stateId}")
	public EntityModel<State> one(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId) {
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
	public ResponseEntity<?> newState(@Valid @RequestBody State state, @Positive @PathVariable int id) {
		if (state.getCountry().getId() != id)
			return ResponseEntity.badRequest().build();

		EntityModel<State> entityModel = assembler.toModel(repository.save(state));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/countries/{countryId}/states/{stateId}")
	public ResponseEntity<?> replaceState(@Valid @RequestBody State newState, @Positive @PathVariable int countryId,
			@Positive @PathVariable int stateId) {
		if (newState.getId() != stateId || newState.getCountry().getId() != countryId)
			return ResponseEntity.notFound().build();

		State updatedState = repository.findById(stateId).map(state -> {
			state = newState;
			return repository.save(state);
		}).orElseThrow(() -> new RuntimeException("Not found " + stateId));

		EntityModel<State> entityModel = assembler.toModel(updatedState);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/countries/{countryId}/states/{stateId}")
	public ResponseEntity<?> deleteState(@Positive @PathVariable int countryId, @Positive @PathVariable int stateId) {
			try {
				repository.deleteById(stateId);
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}			
	}

	@ExceptionHandler
	public String constraintViolationHandler(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}

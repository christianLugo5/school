package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.CareerController;
import com.school.portal.controller.CourseController;
import com.school.portal.model.Career;

@Component
public class CareerAssembler implements RepresentationModelAssembler<Career, EntityModel<Career>> {

	@Override
	public EntityModel<Career> toModel(Career career) {
		return EntityModel.of(career, 
				WebMvcLinkBuilder.linkTo(CareerController.class).slash(career.getId()).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseController.class)
						.allByCareer(career.getId())).withRel("courses"));
	}

}

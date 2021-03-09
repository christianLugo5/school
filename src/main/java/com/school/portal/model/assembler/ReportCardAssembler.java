package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.ReportCardController;
import com.school.portal.model.ReportCard;

@Component
public class ReportCardAssembler implements RepresentationModelAssembler<ReportCard, EntityModel<ReportCard>> {

	@Override
	public EntityModel<ReportCard> toModel(ReportCard reportCard) {
		return EntityModel.of(reportCard,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportCardController.class).one(reportCard.getId()))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportCardController.class).all())
						.withRel("report cards"));
	}

}

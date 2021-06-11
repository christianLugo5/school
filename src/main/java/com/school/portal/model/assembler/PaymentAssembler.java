package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.PaymentController;
import com.school.portal.model.Payment;

@Component
public class PaymentAssembler implements RepresentationModelAssembler<Payment, EntityModel<Payment>> {

	@Override
	public EntityModel<Payment> toModel(Payment payment) {
		return EntityModel.of(payment, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class).one(payment.getId())).withSelfRel());
	}

}

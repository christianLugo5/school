package com.school.portal.model.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.school.portal.controller.EmployeeController;
import com.school.portal.model.Employee;

@Component
public class EmployeeAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

	@Override
	public EntityModel<Employee> toModel(Employee employee) {
		return EntityModel.of(employee, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withRel("Employees"));
	}

}

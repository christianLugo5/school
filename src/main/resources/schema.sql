CREATE DATABASE `tec` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `person` (
  `person_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `curp` varchar(18) NOT NULL,
  `rfc` varchar(13) DEFAULT NULL,
  `phone_number` varchar(15) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `allergy` varchar(100) DEFAULT NULL,
  `blood_type` enum('A_POSITIVE','A_NEGATIVE','B_POSITIVE','B_NEGATIVE','AB_POSITIVE','AB_NEGATIVE','O_POSITIVE','O_NEGATIVE','NO_ANSWER') NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `id_person_UNIQUE` (`person_id`),
  UNIQUE KEY `curp_UNIQUE` (`curp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `country` (
  `country_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `code` varchar(2) NOT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `id_country_UNIQUE` (`country_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `state_country` (
  `state_id` int unsigned NOT NULL AUTO_INCREMENT,
  `state` varchar(45) NOT NULL,
  `abbreviation` varchar(5) NOT NULL,
  `country_id` int unsigned NOT NULL,
  PRIMARY KEY (`state_id`),
  UNIQUE KEY `id_state_UNIQUE` (`state_id`),
  KEY `id_country__country_idx` (`country_id`),
  CONSTRAINT `c_state.country_id__country.country_id` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `city_state` (
  `city_id` int unsigned NOT NULL AUTO_INCREMENT,
  `city` varchar(62) NOT NULL,
  `abbreviation` varchar(25) NOT NULL,
  `state_id` int unsigned NOT NULL,
  PRIMARY KEY (`city_id`),
  UNIQUE KEY `id_city_UNIQUE` (`city_id`),
  KEY `id_state__state_idx` (`state_id`),
  CONSTRAINT `c_state.state_id__state.state_id` FOREIGN KEY (`state_id`) REFERENCES `state_country` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `address` (
  `address_id` int unsigned NOT NULL AUTO_INCREMENT,
  `city_id` int unsigned NOT NULL,
  `street` varchar(45) NOT NULL,
  `neighborhood` varchar(45) NOT NULL,
  `zip_code` int unsigned NOT NULL,
  `reference` varchar(120) NOT NULL,
  `outdoor_number` int NOT NULL,
  `interior_number` int DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  UNIQUE KEY `id_address_UNIQUE` (`address_id`),
  KEY `id_city__city_idx` (`city_id`),
  CONSTRAINT `address.city_id__city.city_id` FOREIGN KEY (`city_id`) REFERENCES `city_state` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `school` (
  `school_id` int unsigned NOT NULL AUTO_INCREMENT,
  `identifier` varchar(15) NOT NULL,
  `name` varchar(45) NOT NULL,
  `is_headquarters` bit(1) NOT NULL,
  `address_id` int unsigned NOT NULL,
  `rfc` varchar(13) NOT NULL,
  PRIMARY KEY (`school_id`),
  UNIQUE KEY `id_school_UNIQUE` (`school_id`),
  KEY `id_address_idx` (`address_id`),
  CONSTRAINT `school.address_id__address.address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `subject` (
  `subject_id` int unsigned NOT NULL AUTO_INCREMENT,
  `subject` varchar(120) NOT NULL,
  PRIMARY KEY (`subject_id`),
  UNIQUE KEY `id_subject_UNIQUE` (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
CREATE TABLE `access` (
  `access_id` int unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(128) NOT NULL,
  `person_id` int unsigned NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `created` datetime NOT NULL,
  `wrong_login_attempt` int unsigned NOT NULL,
  PRIMARY KEY (`access_id`),
  UNIQUE KEY `id_access_UNIQUE` (`access_id`),
  KEY `id_person__person_idx` (`person_id`),
  CONSTRAINT `access.person_id__person.person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `access_password` (
  `password_id` int unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(128) NOT NULL,
  `access_id` int unsigned NOT NULL,
  PRIMARY KEY (`password_id`),
  UNIQUE KEY `id_password_UNIQUE` (`password_id`),
  KEY `id_access__access_idx` (`access_id`),
  CONSTRAINT `a_password.access_id__access.access_id` FOREIGN KEY (`access_id`) REFERENCES `access` (`access_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `access_recover` (
  `recover_id` int unsigned NOT NULL AUTO_INCREMENT,
  `password_id` int unsigned NOT NULL,
  `token` varchar(128) NOT NULL,
  `creation` date NOT NULL,
  `expiration` date NOT NULL,
  PRIMARY KEY (`recover_id`),
  UNIQUE KEY `id_recover_UNIQUE` (`recover_id`),
  KEY `id_password__password_idx` (`password_id`),
  CONSTRAINT `a_recover.password_id__a_password.password_id` FOREIGN KEY (`password_id`) REFERENCES `access_password` (`password_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `rol` (
  `rol_id` int unsigned NOT NULL AUTO_INCREMENT,
  `rol` varchar(45) NOT NULL,
  `abbreviation` varchar(8) NOT NULL,
  PRIMARY KEY (`rol_id`),
  UNIQUE KEY `id_rol_UNIQUE` (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
CREATE TABLE `permission` (
  `permission_id` int unsigned NOT NULL AUTO_INCREMENT,
  `permission` varchar(45) NOT NULL,
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `id_permission_UNIQUE` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `rol_permission` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `rol_id` int unsigned NOT NULL,
  `permission_id` int unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  UNIQUE KEY `id_rol_fk_permission__UNIQUE` (`rol_id`,`permission_id`),
  KEY `id_rol__rol_idx` (`rol_id`),
  KEY `id_permission__permission_idx` (`permission_id`),
  CONSTRAINT `rol_permission.permission_id__permission.permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`),
  CONSTRAINT `rol_permission.rol_id__rol.rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `employee` (
  `person_id` int unsigned NOT NULL,
  `salary` int unsigned NOT NULL,
  `educational_attainment` enum('DOCTORAL','PROFESSIONAL','MASTER','BACHELOR','HIGHSCHOOL') NOT NULL,
  `identifier` varchar(25) NOT NULL,
  `address_id` int unsigned DEFAULT NULL,
  `person_type` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `identifier_UNIQUE` (`identifier`),
  UNIQUE KEY `address_id_UNIQUE` (`address_id`),
  KEY `employee.address_id__address.address_id_idx` (`address_id`),
  CONSTRAINT `employee.address_id__address.address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `employee.person_id__person.person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `career` (
  `career_id` int unsigned NOT NULL AUTO_INCREMENT,
  `career` varchar(45) NOT NULL,
  `identifier` varchar(25) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime DEFAULT NULL,
  `available` tinyint(1) NOT NULL,
  `cycle_type` enum('SEMESTER','QUARTER') NOT NULL,
  `max_duration` tinyint(1) NOT NULL,
  PRIMARY KEY (`career_id`),
  UNIQUE KEY `id_career_UNIQUE` (`career_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
CREATE TABLE `course` (
  `course_id` int unsigned NOT NULL AUTO_INCREMENT,
  `course` varchar(120) NOT NULL,
  `identifier` varchar(25) NOT NULL,
  `available` tinyint unsigned NOT NULL,
  `course_type` enum('SEMESTER','QUARTER') NOT NULL,
  `capacity` smallint unsigned NOT NULL,
  `price` decimal(13,4) unsigned NOT NULL,
  `previous_course_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `id_class_UNIQUE` (`course_id`),
  KEY `course.course_fk__course.course_id_idx` (`previous_course_id`),
  CONSTRAINT `course.previous_course_id__course.course_id` FOREIGN KEY (`previous_course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
CREATE TABLE `career_course` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `career_id` int unsigned NOT NULL,
  `course_id` int unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  KEY `link.id_career__career_id_idx` (`career_id`),
  KEY `link.id_course__course.course_id_idx` (`course_id`),
  CONSTRAINT `link.career_id__career.career_id` FOREIGN KEY (`career_id`) REFERENCES `career` (`career_id`),
  CONSTRAINT `link.course_id__course.course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_subject` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `course_id` int unsigned NOT NULL,
  `subject_id` int unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  KEY `coursesubject.id_course__course.id_course_idx` (`course_id`),
  KEY `coursesubject.id_subject__subject.id_subject_idx` (`subject_id`),
  CONSTRAINT `coursesubject.course_id__course.course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `coursesubject.subject_id__subject.subject_id` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `teacher` (
  `person_id` int unsigned NOT NULL,
  `experience_year` tinyint unsigned NOT NULL,
  `previous_job` varchar(90) NOT NULL,
  `active` tinyint unsigned NOT NULL,
  PRIMARY KEY (`person_id`),
  KEY `teacher.fk_employee__employee.fk_person_idx` (`person_id`),
  CONSTRAINT `teacher.person_id__employee.person_id` FOREIGN KEY (`person_id`) REFERENCES `employee` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_teacher` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `course_id` int unsigned NOT NULL,
  `teacher_id` int unsigned NOT NULL,
  `from` date NOT NULL,
  `to` date NOT NULL,
  `capacity` smallint unsigned NOT NULL,
  `available` tinyint unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  KEY `ctlink.id_course__course.id_course_idx` (`course_id`),
  KEY `ctlink.id_teacher__teacher.id_employee_idx` (`teacher_id`),
  CONSTRAINT `ctlink.course_id__course.course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `ctlink.teacher_id__teacher.employee_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	
CREATE TABLE `course_teacher_student` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `course_teacher_id` int unsigned NOT NULL,
  `student_id` int unsigned NOT NULL,
  `status` enum('NORMAL','DROPPED') NOT NULL DEFAULT 'NORMAL',
  `period` tinyint unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  KEY `cts.id_student__student.id_person_idx` (`student_id`),
  KEY `cts.course_teacher_id__ct.link_id_idx` (`course_teacher_id`),
  CONSTRAINT `cts.course_teacher_id__ct.link_id` FOREIGN KEY (`course_teacher_id`) REFERENCES `course_teacher` (`link_id`),
  CONSTRAINT `cts.student_id__student.person_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
CREATE TABLE `report_card` (
  `report_card_id` int unsigned NOT NULL AUTO_INCREMENT,
  `course_teacher_id` int unsigned NOT NULL,
  `grade_average` tinyint unsigned NOT NULL,
  `evaluation_type` enum('ORDINARY','COMPLEMENTARY') NOT NULL,
  PRIMARY KEY (`report_card_id`),
  UNIQUE KEY `id_report_card_UNIQUE` (`report_card_id`),
  KEY `report_card.id_course_student__course_student.id_link_idx` (`course_teacher_id`),
  CONSTRAINT `report_card.course_student_id__course_student.link_id` FOREIGN KEY (`course_teacher_id`) REFERENCES `course_teacher_student` (`link_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `report_card_detail` (
  `detail_id` int unsigned NOT NULL AUTO_INCREMENT,
  `report_card_id` int unsigned NOT NULL,
  `unit` tinyint unsigned NOT NULL,
  `grade` tinyint unsigned NOT NULL,
  `is_complementary` tinyint unsigned NOT NULL,
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `id_detail_UNIQUE` (`detail_id`),
  KEY `report_card_detail.id_report_card__report_card.id_card_idx` (`report_card_id`),
  CONSTRAINT `report_card_detail.report_card_id__report_card.report_card_id` FOREIGN KEY (`report_card_id`) REFERENCES `report_card` (`report_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student` (
  `person_id` int unsigned NOT NULL,
  `identifier` varchar(25) NOT NULL,
  `registration` date NOT NULL,
  `address_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `address_id_UNIQUE` (`address_id`),
  KEY `student.id_person__person.id_person_idx` (`person_id`),
  CONSTRAINT `student.address_id__address.address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `student.person_id__person.person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student_career` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int unsigned NOT NULL,
  `career_id` int unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  KEY `student_course.id_course__career.id_career_idx` (`career_id`),
  KEY `student_career.id_student__student.id_student_idx` (`student_id`),
  CONSTRAINT `student_career.id_career__career.id_career` FOREIGN KEY (`career_id`) REFERENCES `career` (`career_id`),
  CONSTRAINT `student_career.id_student__student.id_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `access_rol` (
  `link_id` int unsigned NOT NULL AUTO_INCREMENT,
  `access_id` int unsigned NOT NULL,
  `rol_id` int unsigned NOT NULL,
  PRIMARY KEY (`link_id`),
  UNIQUE KEY `id_link_UNIQUE` (`link_id`),
  KEY `apr.id_access__access.id_access_idx` (`access_id`),
  KEY `apr.id_rol__rol.id_rol_idx` (`rol_id`),
  CONSTRAINT `a_rol.access_id__access.access_id` FOREIGN KEY (`access_id`) REFERENCES `access` (`access_id`),
  CONSTRAINT `a_rol.rol_id__rol.rol_id` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student_course_payment` (
  `payment_id` int unsigned NOT NULL AUTO_INCREMENT,
  `total_courses` tinyint unsigned NOT NULL,
  `creation` date NOT NULL,
  `expiration` date NOT NULL,
  `status` set('CREATED','CHECKED','PAID','CANCELLED') NOT NULL,
  `payment` datetime DEFAULT NULL,
  `reference` varchar(90) DEFAULT NULL,
  `subtotal` decimal(13,4) unsigned NOT NULL,
  `dicount` decimal(13,4) unsigned NOT NULL,
  `total` decimal(13,4) unsigned NOT NULL,
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `id_payment_UNIQUE` (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student_course_payment_detail` (
  `detail_id` int unsigned NOT NULL AUTO_INCREMENT,
  `course_teacher_student_id` int unsigned NOT NULL,
  `payment_id` int unsigned NOT NULL,
  `price` decimal(13,4) unsigned NOT NULL,
  `discount` decimal(13,4) unsigned NOT NULL,
  `total` decimal(13,4) unsigned NOT NULL,
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `id_detail_UNIQUE` (`detail_id`),
  KEY `s_c_payment.id_course_teacher_student__c_t_student.id_link_idx` (`course_teacher_student_id`),
  KEY `s_c_p_detail.id_payment__s_c_payment_id_payment_idx` (`payment_id`),
  CONSTRAINT `s_c_p_detail.payment_id__s_c_payment.payment_id` FOREIGN KEY (`payment_id`) REFERENCES `student_course_payment` (`payment_id`),
  CONSTRAINT `s_c_payment.course_id_teacher_student__c_t_student.link_id` FOREIGN KEY (`course_teacher_student_id`) REFERENCES `course_teacher_student` (`link_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `relative` (
  `person_id` int unsigned NOT NULL,
  `student_id` int unsigned NOT NULL,
  `relationship` enum('FATHER','MOTHER','AUNT','UNCLE','GRANDMOTHER','GRANDFATHER','TUTOR','OTHER') NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `relative.person_id__relative.student_id_UNIQUE` (`person_id`,`student_id`),
  KEY `relative.student_id__student.person_fk_idx` (`student_id`),
  CONSTRAINT `relative.person_id__person.person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `relative.student_id__student.person_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

package com.enigmacamp.enigshop.specification;

import com.enigmacamp.enigshop.entity.Department;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;



public class DepartmentSpecification {


    public static Specification<Department> filterBy(String name, String code) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if (code != null && !code.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("code"), code));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}


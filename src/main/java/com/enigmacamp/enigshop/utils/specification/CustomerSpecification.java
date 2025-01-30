package com.enigmacamp.enigshop.utils.specification;

import com.enigmacamp.enigshop.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {

    public static Specification<Customer> getSpecification(String search){
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + search.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + search.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("phone")), "%" + search.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("address")), "%" + search.toLowerCase() + "%"));

            return cb.or(predicates.toArray(new Predicate[0]));
        });
    }

}

package com.enigmacamp.enigshop.utils.specification;

import com.enigmacamp.enigshop.entity.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> filterByCriteria(String query, String code) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (code != null && !code.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("code"), code));
            }

            if (query != null && !query.isEmpty()) {
                Predicate customerNamePredicate = criteriaBuilder.like(root.get("customer").get("name"), "%" + query + "%");
                Predicate transactionIdPredicate = criteriaBuilder.like(root.get("id"), "%" + query + "%");

                predicates.add(criteriaBuilder.or(customerNamePredicate, transactionIdPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


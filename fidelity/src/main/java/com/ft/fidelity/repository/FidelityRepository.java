package com.ft.fidelity.repository;

import com.ft.fidelity.domain.UserBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FidelityRepository extends JpaRepository<UserBonus, Integer> {
}

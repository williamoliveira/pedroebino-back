package com.pin2.pedrobino.domain.administrator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorsRepository extends JpaRepository<Administrator, Long> {}

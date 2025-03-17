package com.zgzg.company.domain.Persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

	Optional<Company> findById(UUID id);

	 List<Company> findAll();



}

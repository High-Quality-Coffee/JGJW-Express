package com.zgzg.company.domain.Persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}

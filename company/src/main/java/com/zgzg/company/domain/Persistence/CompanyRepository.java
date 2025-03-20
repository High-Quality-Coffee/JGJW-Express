package com.zgzg.company.domain.Persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zgzg.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

	Optional<Company> findById(UUID id);
	Optional<Company> findByIdAndDeletedAtIsNull(UUID id);

	List<Company> findAll();
	Page<Company> findAllByDeletedAtIsNull(Pageable pageable);

	@Query("SELECT c FROM Company c " +
		"WHERE (:keyword IS NULL OR :keyword = '' OR " +
		"      cast(c.id as string) LIKE concat('%', :keyword, '%') OR " +
		"      cast(c.hub_id as string) LIKE concat('%', :keyword, '%') OR " +
		"      lower(c.name) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      lower(c.type) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      lower(c.address) LIKE lower(concat('%', :keyword, '%')))")
	Page<Company> searchCompanies(@Param("keyword") String keyword, Pageable pageable);

}

package com.zgzg.ai.domain.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zgzg.ai.domain.Message;

public interface MessageReposiroty extends JpaRepository<Message, UUID> {

	Optional<Message> findById(UUID id);

	Optional<Message> findByIdAndDeletedAtIsNull(UUID id);

	@Query("SELECT m FROM Message m " +
		"WHERE (:keyword IS NULL OR :keyword = '' OR " +
		"      cast(m.id as string) LIKE concat('%', :keyword, '%') OR " +
		"      cast(m.receiverId as string) LIKE concat('%', :keyword, '%') OR " +
		"      lower(m.messageTitle) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      lower(m.orderNumber) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      lower(m.originHub) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      lower(m.finalDestination) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      cast(m.senderId as string) LIKE lower(concat('%', :keyword, '%')) OR " +
		"      (:keyword = 'true' AND m.isDelay = true) OR " +
		"      (:keyword = 'false' AND m.isDelay = false))")
	Page<Message> searchMessages(@Param("keyword") String keyword, Pageable pageable);
}

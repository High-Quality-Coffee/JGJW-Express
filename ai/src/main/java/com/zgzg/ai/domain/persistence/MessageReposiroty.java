package com.zgzg.ai.domain.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zgzg.ai.domain.Message;

public interface MessageReposiroty extends JpaRepository<Message, UUID> {

	Optional<Message> findById(UUID id);

}

package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface JpaUserRepository extends JpaRepository<User,Long> {

}

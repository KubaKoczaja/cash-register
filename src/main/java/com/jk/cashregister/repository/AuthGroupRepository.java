package com.jk.cashregister.repository;

import com.jk.cashregister.domain.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Long> {
		List<AuthGroup> findByUsername(String username);
}

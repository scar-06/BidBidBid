package org.codesofscar.bidbidbid.repository;

import org.codesofscar.bidbidbid.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}

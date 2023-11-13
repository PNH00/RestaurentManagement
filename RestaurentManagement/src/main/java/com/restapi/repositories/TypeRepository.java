package com.restapi.repositories;

import com.restapi.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface TypeRepository extends JpaRepository<Type,UUID> {
    Type findByTypeEquals(String keyword);
}

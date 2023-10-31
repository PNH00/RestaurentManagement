package com.restapi.repositories;

import com.restapi.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu,UUID>{
    List<Menu> findByNameEquals(String name);
    List<Menu> findByDescriptionEquals(String description);
    List<Menu> findByTypeTypeEquals(String type);
}

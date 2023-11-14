package com.restapi.repositories;

import com.restapi.models.Menu;
import com.restapi.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu,UUID>{
    Menu findByNameEquals(String name);
    List<Menu> findByNameContaining(String name);
    List<Menu> findByDescriptionContaining(String description);

    List<Menu> findMenusByTypesContaining(Type types);
}

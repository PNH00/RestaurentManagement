package com.restapi.repositories;

import com.restapi.models.Bill;
import com.restapi.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

    Bill findBillByMenusEquals(Menu menu);
}

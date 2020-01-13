package com.fabg21.mysport.financial.repository;
import com.fabg21.mysport.financial.domain.Spending;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Spending entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {

}

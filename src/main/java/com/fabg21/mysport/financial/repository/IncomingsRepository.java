package com.fabg21.mysport.financial.repository;
import com.fabg21.mysport.financial.domain.Incomings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Incomings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomingsRepository extends JpaRepository<Incomings, Long> {

}

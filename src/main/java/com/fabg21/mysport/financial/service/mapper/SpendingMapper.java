package com.fabg21.mysport.financial.service.mapper;

import com.fabg21.mysport.financial.domain.*;
import com.fabg21.mysport.financial.service.dto.SpendingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Spending} and its DTO {@link SpendingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpendingMapper extends EntityMapper<SpendingDTO, Spending> {



    default Spending fromId(Long id) {
        if (id == null) {
            return null;
        }
        Spending spending = new Spending();
        spending.setId(id);
        return spending;
    }
}

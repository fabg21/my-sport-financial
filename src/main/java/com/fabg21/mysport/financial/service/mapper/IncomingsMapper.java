package com.fabg21.mysport.financial.service.mapper;

import com.fabg21.mysport.financial.domain.*;
import com.fabg21.mysport.financial.service.dto.IncomingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incomings} and its DTO {@link IncomingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncomingsMapper extends EntityMapper<IncomingsDTO, Incomings> {



    default Incomings fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incomings incomings = new Incomings();
        incomings.setId(id);
        return incomings;
    }
}

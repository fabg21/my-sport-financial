package com.fabg21.mysport.financial.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class IncomingsMapperTest {

    private IncomingsMapper incomingsMapper;

    @BeforeEach
    public void setUp() {
        incomingsMapper = new IncomingsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(incomingsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(incomingsMapper.fromId(null)).isNull();
    }
}

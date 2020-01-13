package com.fabg21.mysport.financial.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SpendingMapperTest {

    private SpendingMapper spendingMapper;

    @BeforeEach
    public void setUp() {
        spendingMapper = new SpendingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(spendingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(spendingMapper.fromId(null)).isNull();
    }
}

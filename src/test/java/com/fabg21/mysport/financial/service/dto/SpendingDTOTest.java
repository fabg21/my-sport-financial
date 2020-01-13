package com.fabg21.mysport.financial.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fabg21.mysport.financial.web.rest.TestUtil;

public class SpendingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpendingDTO.class);
        SpendingDTO spendingDTO1 = new SpendingDTO();
        spendingDTO1.setId(1L);
        SpendingDTO spendingDTO2 = new SpendingDTO();
        assertThat(spendingDTO1).isNotEqualTo(spendingDTO2);
        spendingDTO2.setId(spendingDTO1.getId());
        assertThat(spendingDTO1).isEqualTo(spendingDTO2);
        spendingDTO2.setId(2L);
        assertThat(spendingDTO1).isNotEqualTo(spendingDTO2);
        spendingDTO1.setId(null);
        assertThat(spendingDTO1).isNotEqualTo(spendingDTO2);
    }
}

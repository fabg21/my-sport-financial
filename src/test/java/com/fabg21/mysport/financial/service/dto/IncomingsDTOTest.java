package com.fabg21.mysport.financial.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fabg21.mysport.financial.web.rest.TestUtil;

public class IncomingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomingsDTO.class);
        IncomingsDTO incomingsDTO1 = new IncomingsDTO();
        incomingsDTO1.setId(1L);
        IncomingsDTO incomingsDTO2 = new IncomingsDTO();
        assertThat(incomingsDTO1).isNotEqualTo(incomingsDTO2);
        incomingsDTO2.setId(incomingsDTO1.getId());
        assertThat(incomingsDTO1).isEqualTo(incomingsDTO2);
        incomingsDTO2.setId(2L);
        assertThat(incomingsDTO1).isNotEqualTo(incomingsDTO2);
        incomingsDTO1.setId(null);
        assertThat(incomingsDTO1).isNotEqualTo(incomingsDTO2);
    }
}

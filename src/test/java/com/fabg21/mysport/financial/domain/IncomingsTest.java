package com.fabg21.mysport.financial.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fabg21.mysport.financial.web.rest.TestUtil;

public class IncomingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incomings.class);
        Incomings incomings1 = new Incomings();
        incomings1.setId(1L);
        Incomings incomings2 = new Incomings();
        incomings2.setId(incomings1.getId());
        assertThat(incomings1).isEqualTo(incomings2);
        incomings2.setId(2L);
        assertThat(incomings1).isNotEqualTo(incomings2);
        incomings1.setId(null);
        assertThat(incomings1).isNotEqualTo(incomings2);
    }
}

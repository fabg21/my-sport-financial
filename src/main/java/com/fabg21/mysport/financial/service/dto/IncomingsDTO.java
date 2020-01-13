package com.fabg21.mysport.financial.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fabg21.mysport.financial.domain.Incomings} entity.
 */
public class IncomingsDTO implements Serializable {

    private Long id;

    private String label;

    private Float amount;

    private String status;

    private String category;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncomingsDTO incomingsDTO = (IncomingsDTO) o;
        if (incomingsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incomingsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncomingsDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}

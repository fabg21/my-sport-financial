package com.fabg21.mysport.financial.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Incomings.
 */
@Entity
@Table(name = "incomings")
public class Incomings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Incomings label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getAmount() {
        return amount;
    }

    public Incomings amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public Incomings status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public Incomings category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incomings)) {
            return false;
        }
        return id != null && id.equals(((Incomings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Incomings{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}

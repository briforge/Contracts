package com.presto.contracts.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {

    public enum Status {
        APPROVED, DENIED;
    }

    public enum ContractType {
        SALES, EXPRESS;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String businessNumber;

    private LocalDate activationDate;

    private double amountRequested;

    private Status status;

    private ContractType contractType;

    public long getId() {
        return id;
    }

    public Contract setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Contract setName(String name) {
        this.name = name;
        return this;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public Contract setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
        return this;
    }

    @JsonProperty("activationDate")
    public LocalDate getActivationDate() {
        return activationDate;
    }

    @JsonIgnore
    public Contract setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public double getAmountRequested() {
        return amountRequested;
    }

    public Contract setAmountRequested(double amountRequested) {
        this.amountRequested = amountRequested;
        return this;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonIgnore
    public Contract setStatus(Status status) {
        this.status = status;
        return this;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public Contract setContractType(ContractType contractType) {
        this.contractType = contractType;
        return this;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", businessNumber='" + businessNumber + '\'' +
                ", activationDate=" + activationDate +
                ", amountRequested=" + amountRequested +
                ", status=" + status +
                '}';
    }
}

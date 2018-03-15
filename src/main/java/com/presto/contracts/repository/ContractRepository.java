package com.presto.contracts.repository;

import com.presto.contracts.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByStatus(Contract.Status status);

}

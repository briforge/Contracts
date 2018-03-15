package com.presto.contracts.service;

import com.presto.contracts.exceptions.RequestAmountExceeded;
import com.presto.contracts.model.Contract;
import com.presto.contracts.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContractsService {

    @Autowired
    ContractRepository contractRepository;

    public Contract createContract(Contract contract) throws Exception {
        if(Contract.ContractType.EXPRESS.equals(contract.getContractType())) {
            if(contract.getAmountRequested() < 50000) {
                approveContract(contract);
            } else {
                throw new RequestAmountExceeded();
            }
        } else if(Contract.ContractType.SALES.equals(contract.getContractType())) {
            approveContract(contract);
        }
        return contractRepository.save(contract);
    }

    private void approveContract(Contract contract) {
        contract.setStatus(Contract.Status.APPROVED);
        contract.setActivationDate(LocalDate.now());
    }
}

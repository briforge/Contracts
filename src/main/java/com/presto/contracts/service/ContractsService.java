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

    public Contract createContract(Contract contract) throws RequestAmountExceeded {
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

    public Contract updateContract(Contract updatingContract, Contract existingContract) {
        // TODO prevent changes to read-only attributes: status and activationDate
        if(updatingContract.getActivationDate() != null &&
                !updatingContract.getActivationDate().equals(existingContract.getActivationDate())) {
            updatingContract.setActivationDate(existingContract.getActivationDate());
        }
        if(updatingContract.getStatus() != null &&
                !updatingContract.getStatus().equals(existingContract.getStatus())) {
            updatingContract.setStatus(existingContract.getStatus());
        }
        contractRepository.save(updatingContract);
        return updatingContract;
    }

    private void approveContract(Contract contract) {
        contract.setStatus(Contract.Status.APPROVED);
        contract.setActivationDate(LocalDate.now());
    }
}

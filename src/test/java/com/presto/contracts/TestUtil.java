package com.presto.contracts;

import com.presto.contracts.model.Contract;

public class TestUtil {

    public static Contract createContract(String name, String businessNumber, Contract.ContractType type, double amountRequested) {
        Contract contract = new Contract()
                .setName(name)
                .setBusinessNumber(businessNumber)
                .setContractType(type)
                .setAmountRequested(amountRequested);

        return contract;
    }
}

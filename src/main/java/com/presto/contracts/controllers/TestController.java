package com.presto.contracts.controllers;

import com.presto.contracts.model.Contract;
import com.presto.contracts.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    ContractRepository contractRepository;

    @GetMapping("/createTestData")
    public void createTestData() {
        try {
            Contract contract = new Contract()
                    .setName("test1")
                    .setBusinessNumber("12345")
                    .setAmountRequested(25000);

            contractRepository.save(contract);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

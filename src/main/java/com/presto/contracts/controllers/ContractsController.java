package com.presto.contracts.controllers;

import com.presto.contracts.model.Contract;
import com.presto.contracts.repository.ContractRepository;
import com.presto.contracts.service.ContractsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contracts")
public class ContractsController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ContractsService contractsService;

    @GetMapping
    public List<Contract> getAll(@RequestParam(required = false) String approved) {
        if(StringUtils.hasText(approved) && Boolean.valueOf(approved)) {
            return contractRepository.findByStatus(Contract.Status.APPROVED);
        } else {
            return contractRepository.findAll();
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Contract> getContract(@PathVariable("id") String contractId) {
        Contract contract = contractRepository.findById(Long.valueOf(contractId)).get();
        if(contract == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(contract, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Contract> deleteContract(@PathVariable("id") String contractId) {
        Contract contract = contractRepository.findById(Long.valueOf(contractId)).get();
        if(contract == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            contractRepository.delete(contract);
            return new ResponseEntity<>(contract, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Contract> updateContract(@PathVariable("id") String contractId,
                                                   @RequestBody Contract contract) {
        Contract existingContract = contractRepository.findById(Long.valueOf(contractId)).get();
        if(existingContract == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Contract updatedContract = contractsService.updateContract(contract);
            return new ResponseEntity<>(updatedContract, HttpStatus.OK);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity createContact(@RequestBody Contract contract) {
        try {
            Contract createdContract = contractsService.createContract(contract);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdContract);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}

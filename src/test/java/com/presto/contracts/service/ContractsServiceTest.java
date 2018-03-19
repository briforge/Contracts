package com.presto.contracts.service;

import com.presto.contracts.TestUtil;
import com.presto.contracts.exceptions.RequestAmountExceeded;
import com.presto.contracts.model.Contract;
import com.presto.contracts.repository.ContractRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractsServiceTest {

    @Mock
    ContractRepository contractRepository;

    @Autowired
    @InjectMocks
    ContractsService contractsService;

    @Test
    public void testApprovalSetsActivationDateAndStatus() {
        Contract contract = TestUtil.createContract("TestContract", "12345", Contract.ContractType.SALES, 20000);
        try {
            contractsService.createContract(contract);
            assertEquals("Contract status not approved", Contract.Status.APPROVED, contract.getStatus());
            assertEquals("approval date not today", LocalDate.now(), contract.getActivationDate());
        } catch (Exception e) {
            fail("failed with exception : " + e);
        }
    }

    @Test
    public void testExpressApprovalLimit() {
        Contract contract = TestUtil.createContract("TestExpressContract", "23456", Contract.ContractType.EXPRESS, 20000);
        try {
            contractsService.createContract(contract);
            assertEquals("Contract status not approved", Contract.Status.APPROVED, contract.getStatus());
            assertEquals("approval date not today", LocalDate.now(), contract.getActivationDate());
        } catch (Exception e) {
            fail("failed with exception : " + e);
        }
    }

    @Test(expected = RequestAmountExceeded.class)
    public void testExpressApprovalOverLimit() throws Exception {
        Contract contract = TestUtil.createContract("TestExpressContractTooMuch", "23456", Contract.ContractType.EXPRESS, 60000);
        contractsService.createContract(contract);
    }

    @Test
    public void testCannotUpdateActivationDateOrStatus() {
        Contract existingContract = TestUtil.createContract("testUpdate", "ddddd", Contract.ContractType.SALES, 40000);
        existingContract.setId(5);
        existingContract.setActivationDate(LocalDate.now().minusDays(7));
        existingContract.setStatus(Contract.Status.APPROVED);

        // test updating amount
        Contract attemptedUpdatedContract = TestUtil.createContract("testUpdate", "ddddd", Contract.ContractType.SALES, 45000);
        attemptedUpdatedContract.setId(5);
        attemptedUpdatedContract.setActivationDate(LocalDate.now());
        attemptedUpdatedContract.setStatus(Contract.Status.DENIED);

        when(contractRepository.getOne(new Long(5))).thenReturn(existingContract);

        Contract updatedContract = contractsService.updateContract(attemptedUpdatedContract, existingContract);

        // amount should be updated but not activation date
        assertEquals(45000, updatedContract.getAmountRequested(), 0.1);
        assertEquals(LocalDate.now().minusDays(7), updatedContract.getActivationDate());
        assertEquals(Contract.Status.APPROVED, updatedContract.getStatus());
    }
}

package org.talan.account.manager.services.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.talan.account.manager.Exceptions.OperationException;
import org.talan.account.manager.controllers.AccountController;
import org.talan.account.manager.dto.OperationDto;
import org.talan.account.manager.dto.TransactionDto;
import org.talan.account.manager.dto.TransactionFilterDto;
import org.talan.account.manager.models.Account;
import org.talan.account.manager.models.Transaction;
import org.talan.account.manager.service.AccountService;
import org.talan.account.manager.utils.ResponseStat;

//TODO To be completed
//@RunWith(SpringRunner.class)
//@WebMvcTest(AccountController.class)
public class AccountControllerTest {

//	@Autowired
//	MockMvc mockMvc;
//
//	@MockBean
//	private AccountService accountService;
//
//	@Test
//	public void TestgetAccount() throws Exception {
//		given(accountService.findAccount("acc1")).willReturn(new Account("acc1", 100F));
//		mockMvc.perform(MockMvcRequestBuilders.get("/account/acc1"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("accountNumber").value("123"));
//	}

}

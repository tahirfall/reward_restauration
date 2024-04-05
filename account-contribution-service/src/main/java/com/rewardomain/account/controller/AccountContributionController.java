
package com.rewardomain.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.account.bean.Account;
import com.rewardomain.account.bean.AccountContributionRequest;
import com.rewardomain.account.bean.AccountContributionResponse;
import com.rewardomain.account.bean.AccountError;
import com.rewardomain.account.bean.Beneficiary;
import com.rewardomain.account.bean.BeneficiaryError;
import com.rewardomain.account.bean.CreditCard;
import com.rewardomain.account.bean.Distribution;
import com.rewardomain.account.repository.AccountRepository;
import com.rewardomain.account.repository.BeneficiaryRepository;
import com.rewardomain.account.repository.CreditCardRepository;

import io.github.resilience4j.retry.annotation.Retry;
@RestController
public class AccountContributionController {
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private Distribution distribution;
	
	private Logger logger = LoggerFactory.getLogger(AccountContributionController.class);
	
	public ResponseEntity<Account> accountDefaultResponse(RuntimeException e) {
    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
    	return new ResponseEntity<>(new AccountError(404L, e.getMessage()), httpStatusCode);
    }
	
	public ResponseEntity<Beneficiary> beneficiaryDefaultResponse(RuntimeException e) {
    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(404);
    	return new ResponseEntity<>(new BeneficiaryError(404L, e.getMessage()), httpStatusCode);
    }
	
	
    @PostMapping("/account-contribution/accounts")
    public ResponseEntity<AccountContributionResponse> createAccount(@RequestBody AccountContributionRequest request) {

      CreditCard creditCard = new CreditCard(request.getCcnumber());
      Account account = new Account(request.getName(), request.getAnumber());

      account.setCreditCard(creditCard);

      creditCardRepository.save(creditCard);
      accountRepository.save(account);

      return new ResponseEntity<>
    		  (new AccountContributionResponse(201, "Account created."), HttpStatusCode.valueOf(201));
    }

    @Retry(name= "account-contribution", fallbackMethod ="accountDefaultResponse")
    @GetMapping("/account-contribution/accounts/{account_number}")
    public ResponseEntity<Account> getAccount(@PathVariable("account_number") String number) {

    	logger.info("Account Contribution Service call service");
    	
	  Account account = accountRepository.findByNumber(number);
	  
	  if (account == null)
	 	 throw new RuntimeException("Account not found for number account: " + number + ".");
	  else
     	 return new ResponseEntity<Account>(account, HttpStatusCode.valueOf(200));

    }
    
    @Retry(name= "account-contribution", fallbackMethod ="accountDefaultResponse")
    @GetMapping("/account-contribution/accounts/creditCard/{credit_card_number}")
    public ResponseEntity<Optional<Account>> getAccountByCreditCardNumber(@PathVariable("credit_card_number") String number) {

    	logger.info("Account Contribution Service call service");
    	
    	Optional<Account> account = accountRepository.findByCreditCard_Number(number);
	  
	  if (account == null)
	 	 throw new RuntimeException("Account not found for credit card number : " + number + ".");
	  else
     	 return new ResponseEntity<Optional<Account>>(account, HttpStatusCode.valueOf(200));

    }

    @Retry(name= "account-contribution", fallbackMethod ="accountDefaultResponse")
    @GetMapping("/account-contribution/accounts")
    public ResponseEntity<List<Account>> getAccounts() {

    	logger.info("Account Contribution Service call service");
    	
    	List<Account> accounts = accountRepository.findAll();
    	
    	if(accounts.isEmpty())
    		throw new RuntimeException("List of account is empty!");
    	else
    		return new ResponseEntity<List<Account>>(accounts, HttpStatusCode.valueOf(200));
    }
    
    @Retry(name= "account-contribution", fallbackMethod ="accountDefaultResponse")
    @PostMapping("/account-contribution/accounts/{account_number}/beneficiaries")
    public ResponseEntity<AccountContributionResponse> createBeneficiary(
             @RequestBody AccountContributionRequest request,
             @PathVariable("account_number") String anumber) {
    	
    	logger.info("Account Contribution Service call service");

         Beneficiary beneficiary = new Beneficiary(request.getPercentage(), request.getName());
         Account account = accountRepository.findByNumber(anumber);

         if (account == null) {
    	 	 throw new RuntimeException("Account not found for number account: " + anumber + ".");
              
         }
         else {
        	 beneficiary.setAccount(account);
             beneficiaryRepository.save(beneficiary);
             return new ResponseEntity<AccountContributionResponse>
           		  (new AccountContributionResponse(201, "Beneficiary created and added."), HttpStatusCode.valueOf(201));

         }
        	 

    }                              

    
    @Retry(name= "account-contribution", fallbackMethod ="beneficiaryDefaultResponse")
    @PutMapping("/account-contribution/beneficiaries/{id}")
    public ResponseEntity<AccountContributionResponse> updateBeneficiary(@RequestBody AccountContributionRequest request, @PathVariable long id) {
    	logger.info("Account Contribution Service call service"); 
    	
    	Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(id);

        if (beneficiary.isPresent()) {
            beneficiary.get().setPercentage(request.getPercentage());
            beneficiaryRepository.save(beneficiary.get());

        return new ResponseEntity<AccountContributionResponse>
        	(new AccountContributionResponse(200, "Beneficiary updated."), HttpStatusCode.valueOf(200));
        } 
        else throw new RuntimeException("Beneficiary not found for beneficiary id: " + id + ".");
    }
    
    @Retry(name= "account-contribution", fallbackMethod ="accountDefaultResponse")
    @GetMapping("/account-contribution/accounts/{account_number}/beneficiaries")
    public ResponseEntity<List<Beneficiary>> getAccounts(@PathVariable("account_number") String number) {

    	logger.info("Account Contribution Service call service");
    	
        Account account = accountRepository.findByNumber(number);

        if (account != null) {
            return new ResponseEntity<List<Beneficiary>>
            	(account.getBeneficiaries(), HttpStatusCode.valueOf(200));
        } else {
	       	 throw new RuntimeException("Account number: " + number + " is null!");
        }
    }

    
    @Retry(name = "account-contribution", fallbackMethod = "accountDefaultResponse")
    @GetMapping("/account-contribution/accounts/{account_number}/beneficiaries/{id}")
    public ResponseEntity<Beneficiary> getBeneficiary(
            @PathVariable("account_number") String accountNumber,
            @PathVariable("id") Long id) {

        logger.info("Account Contribution Service call service for beneficiary");

        Account account = accountRepository.findByNumber(accountNumber);

        if (account != null) {
            List<Beneficiary> beneficiaries = account.getBeneficiaries();

            for (Beneficiary beneficiary : beneficiaries) {
                if (beneficiary.getId() == id) {
                    return new ResponseEntity<>(beneficiary, HttpStatusCode.valueOf(200));
                }
            }

            throw new RuntimeException("Beneficiary with id " + id + " not found in account " + accountNumber);
        } else {
            throw new RuntimeException("Account number: " + accountNumber + " is null!");
        }
    }

    
    
    @Retry(name= "account-contribution", fallbackMethod ="accountDefaultResponse")
    @PutMapping("/account-contribution/accounts/{account_number}/reward/{reward}")
    public ResponseEntity<AccountContributionResponse> distributeReward(
            @PathVariable("account_number") String number,
            @PathVariable double reward) {
    	
    	logger.info("Account Contribution Service call service"); 
    	
    	
		Optional<Account> optional = accountRepository.findByCreditCard_Number(number);
		if (optional.isEmpty()) {
		 	 throw new RuntimeException("Account not found for number account: " + number + ".");
    	} else {
    		Account account = optional.get();
    		if(account.isValid()) {
        	    account.setBenefits(account.getBenefits() + reward);
        	    List<Beneficiary> beneficiaries = account.getBeneficiaries();
        	    distribution.distribute(beneficiaries, reward);

        	    accountRepository.save(account);
        	    return new ResponseEntity<AccountContributionResponse>
        	    	(new AccountContributionResponse(200, "Reward distributed."), HttpStatusCode.valueOf(200));
    		}

    	    else {
   	       	 throw new RuntimeException("Account number: " + number + " invalid!");
        	}
    	}
    }

}

/*
package com.rewardomain.account.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.account.bean.AccountContributionRequest;
import com.rewardomain.account.bean.AccountContributionResponse;
import com.rewardomain.account.bean.Distribution;
import com.rewardomain.account.bean.Account;
import com.rewardomain.account.bean.Beneficiary;
import com.rewardomain.account.bean.CreditCard;
import com.rewardomain.account.repository.AccountRepository;
import com.rewardomain.account.repository.BeneficiaryRepository;
import com.rewardomain.account.repository.CreditCardRepository;


@RestController
public class AccountContributionController2 {
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	@Autowired
	private Distribution distribution;
	
	@Autowired
	private Environment environment;
	
	
	
	//testHEllo
	@GetMapping("/account-contribution/hello")
	public String hello() {
		return "Hello World";
	}
	
	// Créer un compte
	@PostMapping("/account-contribution/accounts")
	public ResponseEntity<AccountContributionResponse>
	createAccount (
			@RequestBody AccountContributionRequest request
			) {
		
		CreditCard creditCard = new CreditCard(request.getCcnumber());
		
		Account account = new Account (request.getName(), request.getAnumber()); 
		
		account.setCreditCard(creditCard);
		
		
		
		creditCardRepository.save(creditCard);
		accountRepository.save(account);
		
		String port = environment.getProperty("local.server.port");
		
		AccountContributionResponse accounContributionResponse = new AccountContributionResponse (201, "Account created.");
	accounContributionResponse.setExecutionChain(
                "Account-contribution instance - " + port);
		
		
		
		return new ResponseEntity<>
		(accounContributionResponse, HttpStatusCode.valueOf(201));
	}

	
	// Rechercher un compte à partir du numéro du compte
	@GetMapping("/account-contribution/accounts/{account_number}")
	public ResponseEntity<Account> getAccount (@PathVariable("account_number") String number) {
		Account account = accountRepository.findByNumber(number);
		
		return new ResponseEntity<Account>(account, HttpStatusCode.valueOf(200));
	}

	
	// Lister tous les comptes
	@GetMapping("/account-contribution/accounts")
	public ResponseEntity<List<Account>> getAccounts() {
		return new ResponseEntity<List<Account>>
		(accountRepository.findAll(), HttpStatusCode.valueOf(200));
	}
	
	// Création d'un bénéficiaire pour un compte spécifique 
	@PostMapping("/account-contribution/accounts/{account_number}/beneficiaries")
	public ResponseEntity<AccountContributionResponse>
	createBeneficiary (
		@RequestBody AccountContributionRequest request,
		@PathVariable("account_number") String anumber
		) {
		Beneficiary beneficiary = new Beneficiary(request.getPercentage(), request.getName());
		Account account = accountRepository.findByNumber(anumber);
		if ( account != null) {
			
			beneficiary.setAccount(account);
			account.addBeneficiary(beneficiary);
			beneficiaryRepository.save(beneficiary);
			return new ResponseEntity<AccountContributionResponse>
			(new AccountContributionResponse(201, "Beneficiary created and added."), HttpStatusCode.valueOf(404));
		}
		//return new ResponseEntity<>
		return new ResponseEntity<AccountContributionResponse>
		(new AccountContributionResponse(404, "Account not found."), HttpStatusCode.valueOf(404));
	}
	
	// Obtenir les informations d'un bénéficiaire par ID
	@GetMapping("/account-contribution/beneficiaries/{id}")
	public ResponseEntity<Beneficiary> getBeneficiaryByID(@PathVariable long id) {
		 Optional<Beneficiary> beneficiaryOptional = beneficiaryRepository.findById(id);
		    
		    if (beneficiaryOptional.isPresent()) {
		        Beneficiary beneficiary = beneficiaryOptional.get();
		        return new ResponseEntity<>(beneficiary, HttpStatusCode.valueOf(200));
		    } else {
		        return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
		    }
	}

	
	// Modification de taux d'allocation d'un bénéficiaire
	@PutMapping("/account-contribution/beneficiaries/{id}")
	public ResponseEntity<AccountContributionResponse> updateBeneficiary
	(
			@RequestBody AccountContributionRequest request,
			@PathVariable long id
	){
		Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(id);
		if (beneficiary.isPresent()) {
			beneficiary.get().setPercentage(request.getPercentage());
			beneficiaryRepository.save(beneficiary.get());
		return new ResponseEntity<AccountContributionResponse>
			(new AccountContributionResponse(200, "Beneficiary updated."), HttpStatusCode.valueOf(200));
	} else return new ResponseEntity<AccountContributionResponse>
			(new AccountContributionResponse(404, "Beneficiary not found."), HttpStatusCode.valueOf(404));
	}
	
	
	//
	// Liste des bénéficiaires d'un compte spécifique
	@GetMapping("/account-contribution/accounts/{account_number}/beneficiaries")
		public ResponseEntity<List<Beneficiary>>
		getAccounts (@PathVariable("account_number") String number) {
		System.out.println("getAccounts called with " + number);
		Account account = accountRepository.findByNumber(number);
		if (account != null) {
			return new ResponseEntity<List<Beneficiary>> (account.getBeneficiaries(), HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<List<Beneficiary>> (new ArrayList<>(), HttpStatusCode.valueOf(404));
	}
	
	// Distribution de la récompense à tous les bénéficiaires d'un compte spécifique
	@PutMapping("/account-contribution/{credit_card_number}/reward/{reward}")
	public ResponseEntity<AccountContributionResponse>
	distributeReward ( 
			@PathVariable("credit_card_number") String number,
			@PathVariable("reward") double reward
			) {
		Optional<Account> optional = accountRepository.findByCreditCard_Number(number);
		if (optional.isEmpty())
			return new ResponseEntity<AccountContributionResponse>
			(new AccountContributionResponse(404, "Account not found."), HttpStatusCode.valueOf(404));
		else {
			Account account = optional.get();
			if (account.isValid()) {
			account.setBenefits(account.getBenefits() + reward);
			List<Beneficiary> beneficiaries = account.getBeneficiaries();
			distribution.distribute(beneficiaries, reward);
			accountRepository.save(account);
			return new ResponseEntity<AccountContributionResponse>(
			new AccountContributionResponse(200, "Reward distributed."), HttpStatusCode.valueOf(200));
			}
			else 
				return 
						new ResponseEntity<AccountContributionResponse>
					(new AccountContributionResponse(403, "Account is invalid"), HttpStatusCode.valueOf(403));
		}
		}
}
*/
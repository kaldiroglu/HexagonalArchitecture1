package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web;

import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.request.CreateAccountRequest;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.request.MoneyOperationRequest;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.request.TransferRequest;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.response.AccountResponse;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.response.BalanceResponse;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.response.TransactionResponse;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.AccountId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.CustomerId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.Money;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final CreateAccountUseCase createAccount;
    private final DepositMoneyUseCase depositMoney;
    private final WithdrawMoneyUseCase withdrawMoney;
    private final GetBalanceUseCase getBalance;
    private final GetTransactionsUseCase getTransactions;
    private final TransferMoneyUseCase transferMoney;
    private final ListAccountsUseCase listAccounts;

    public AccountController(CreateAccountUseCase createAccount,
                             DepositMoneyUseCase depositMoney,
                             WithdrawMoneyUseCase withdrawMoney,
                             GetBalanceUseCase getBalance,
                             GetTransactionsUseCase getTransactions,
                             TransferMoneyUseCase transferMoney,
                             ListAccountsUseCase listAccounts) {
        this.createAccount = createAccount;
        this.depositMoney = depositMoney;
        this.withdrawMoney = withdrawMoney;
        this.getBalance = getBalance;
        this.getTransactions = getTransactions;
        this.transferMoney = transferMoney;
        this.listAccounts = listAccounts;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestParam String ownerId,
                                                          @Valid @RequestBody CreateAccountRequest request) {
        var account = createAccount.createAccount(
                new CreateAccountUseCase.Command(CustomerId.of(ownerId), request.currency()));
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(account));
    }

    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<List<AccountResponse>> listAccounts(@PathVariable String customerId) {
        var accounts = listAccounts.listAccounts(CustomerId.of(customerId)).stream()
                .map(AccountResponse::from).toList();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String accountId) {
        Money balance = getBalance.getBalance(AccountId.of(accountId));
        return ResponseEntity.ok(BalanceResponse.from(balance));
    }

    @PostMapping("/accounts/{accountId}/deposit")
    public ResponseEntity<TransactionResponse> deposit(@PathVariable String accountId,
                                                        @Valid @RequestBody MoneyOperationRequest request) {
        var tx = depositMoney.deposit(new DepositMoneyUseCase.Command(
                AccountId.of(accountId), Money.of(request.amount(), request.currency())));
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponse.from(tx));
    }

    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable String accountId,
                                                         @Valid @RequestBody MoneyOperationRequest request) {
        var tx = withdrawMoney.withdraw(new WithdrawMoneyUseCase.Command(
                AccountId.of(accountId), Money.of(request.amount(), request.currency())));
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponse.from(tx));
    }

    @PostMapping("/accounts/{accountId}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable String accountId,
                                          @Valid @RequestBody TransferRequest request) {
        transferMoney.transfer(new TransferMoneyUseCase.Command(
                AccountId.of(accountId),
                AccountId.of(request.targetAccountId()),
                Money.of(request.amount(), request.currency())));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable String accountId) {
        var txs = getTransactions.getTransactions(AccountId.of(accountId)).stream()
                .map(TransactionResponse::from).toList();
        return ResponseEntity.ok(txs);
    }
}

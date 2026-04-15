# Ayvalık Bank CC-1 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a hexagonal-architecture Java banking app with customer/account management, transfers, and role-based access.

**Architecture:** Domain layer is pure Java (no Spring/JPA annotations). Application services orchestrate domain + infrastructure. Inbound adapters are REST controllers; outbound adapters are JPA persistence and BCrypt password hashing.

**Tech Stack:** Java 25, Spring Boot 3.4, Spring Security (Basic Auth), Spring Data JPA, PostgreSQL, JUnit 5, Mockito, Maven

---

### Package root: `dev.kaldiroglu.hexagonal.ayvalikbank`

### File Map

| File | Responsibility |
|------|---------------|
| `pom.xml` | Maven build, deps |
| `AyvalikBankApplication.java` | Spring Boot entry |
| `application.properties` | DB + security config |
| `docker-compose.yml` | Local PostgreSQL |
| `data.sql` | Admin seed user |
| **Domain — model** | |
| `domain/model/Currency.java` | Enum: USD, EUR, TL |
| `domain/model/TransactionType.java` | Enum: DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT |
| `domain/model/CustomerId.java` | Record wrapping UUID |
| `domain/model/AccountId.java` | Record wrapping UUID |
| `domain/model/TransactionId.java` | Record wrapping UUID |
| `domain/model/Money.java` | Record: amount + Currency, arithmetic |
| `domain/model/Password.java` | Record: hashed value + static format validator |
| `domain/model/Customer.java` | Entity: name, email, role, password + history |
| `domain/model/Account.java` | Entity: owner, currency, balance, rich operations |
| `domain/model/Transaction.java` | Entity: immutable record of one operation |
| **Domain — services** | |
| `domain/service/PasswordValidationService.java` | Format rules (8-16 chars, upper/lower/digit/special) |
| `domain/service/TransferDomainService.java` | Cross-customer fee calculation |
| **Domain — ports in** | |
| `domain/port/in/CreateCustomerUseCase.java` | Interface + Command record |
| `domain/port/in/DeleteCustomerUseCase.java` | Interface |
| `domain/port/in/ListCustomersUseCase.java` | Interface |
| `domain/port/in/ChangePasswordUseCase.java` | Interface + Command record |
| `domain/port/in/CreateAccountUseCase.java` | Interface + Command record |
| `domain/port/in/DepositMoneyUseCase.java` | Interface + Command record |
| `domain/port/in/WithdrawMoneyUseCase.java` | Interface + Command record |
| `domain/port/in/GetBalanceUseCase.java` | Interface |
| `domain/port/in/GetTransactionsUseCase.java` | Interface |
| `domain/port/in/TransferMoneyUseCase.java` | Interface + Command record |
| `domain/port/in/ListAccountsUseCase.java` | Interface |
| `domain/port/in/SetTransferFeeUseCase.java` | Interface + Command record |
| **Domain — ports out** | |
| `domain/port/out/CustomerRepositoryPort.java` | CRUD for Customer |
| `domain/port/out/AccountRepositoryPort.java` | CRUD + find-by-owner for Account |
| `domain/port/out/TransactionRepositoryPort.java` | Save + find-by-account |
| `domain/port/out/PasswordHasherPort.java` | hash(raw), matches(raw, hash) |
| `domain/port/out/SettingsRepositoryPort.java` | getTransferFeePercent, setTransferFeePercent |
| **Application exceptions** | |
| `application/exception/CustomerNotFoundException.java` | |
| `application/exception/AccountNotFoundException.java` | |
| `application/exception/InsufficientFundsException.java` | |
| `application/exception/PasswordReusedException.java` | |
| `application/exception/InvalidPasswordException.java` | |
| `application/exception/CurrencyMismatchException.java` | |
| `application/exception/UnauthorizedAccessException.java` | |
| **Application services** | |
| `application/service/CustomerApplicationService.java` | Implements customer use cases |
| `application/service/AccountApplicationService.java` | Implements account use cases |
| **Persistence — JPA entities (DTOs)** | |
| `adapter/out/persistence/entity/CustomerJpaEntity.java` | @Entity Customer table |
| `adapter/out/persistence/entity/AccountJpaEntity.java` | @Entity Account table |
| `adapter/out/persistence/entity/TransactionJpaEntity.java` | @Entity Transaction table |
| `adapter/out/persistence/entity/SettingsJpaEntity.java` | @Entity Settings table (key-value) |
| `adapter/out/persistence/entity/PasswordHistoryJpaEntity.java` | @Entity password history |
| **Persistence — Spring Data repos** | |
| `adapter/out/persistence/repository/CustomerJpaRepository.java` | JpaRepository |
| `adapter/out/persistence/repository/AccountJpaRepository.java` | JpaRepository |
| `adapter/out/persistence/repository/TransactionJpaRepository.java` | JpaRepository |
| `adapter/out/persistence/repository/SettingsJpaRepository.java` | JpaRepository |
| **Persistence — mappers** | |
| `adapter/out/persistence/mapper/CustomerPersistenceMapper.java` | JPA entity ↔ domain |
| `adapter/out/persistence/mapper/AccountPersistenceMapper.java` | JPA entity ↔ domain |
| `adapter/out/persistence/mapper/TransactionPersistenceMapper.java` | JPA entity ↔ domain |
| **Persistence — adapters** | |
| `adapter/out/persistence/CustomerPersistenceAdapter.java` | Implements CustomerRepositoryPort |
| `adapter/out/persistence/AccountPersistenceAdapter.java` | Implements AccountRepositoryPort |
| `adapter/out/persistence/TransactionPersistenceAdapter.java` | Implements TransactionRepositoryPort |
| `adapter/out/persistence/SettingsPersistenceAdapter.java` | Implements SettingsRepositoryPort |
| `adapter/out/security/BCryptPasswordHasherAdapter.java` | Implements PasswordHasherPort |
| **Web — DTOs** | |
| `adapter/in/web/dto/request/CreateCustomerRequest.java` | |
| `adapter/in/web/dto/request/ChangePasswordRequest.java` | |
| `adapter/in/web/dto/request/CreateAccountRequest.java` | |
| `adapter/in/web/dto/request/MoneyOperationRequest.java` | deposit/withdraw |
| `adapter/in/web/dto/request/TransferRequest.java` | |
| `adapter/in/web/dto/request/SetTransferFeeRequest.java` | |
| `adapter/in/web/dto/response/CustomerResponse.java` | |
| `adapter/in/web/dto/response/AccountResponse.java` | |
| `adapter/in/web/dto/response/TransactionResponse.java` | |
| `adapter/in/web/dto/response/BalanceResponse.java` | |
| **Web — controllers** | |
| `adapter/in/web/AdminController.java` | /api/admin/** (ROLE_ADMIN) |
| `adapter/in/web/CustomerController.java` | /api/customers/** (ROLE_CUSTOMER) |
| `adapter/in/web/AccountController.java` | /api/accounts/** (ROLE_CUSTOMER) |
| `adapter/in/web/GlobalExceptionHandler.java` | @RestControllerAdvice |
| **Security config** | |
| `config/SecurityConfig.java` | Basic Auth, role-based rules |
| `config/BankUserDetailsService.java` | Loads user from Customer table |
| **Tests** | |
| `domain/model/CustomerTest.java` | Password change, history |
| `domain/model/AccountTest.java` | Deposit, withdraw, balance |
| `domain/model/MoneyTest.java` | Arithmetic, negative guard |
| `domain/service/PasswordValidationServiceTest.java` | All format rules |
| `application/service/CustomerApplicationServiceTest.java` | Mockito-based |
| `application/service/AccountApplicationServiceTest.java` | Mockito-based |

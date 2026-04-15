package dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web;

import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.request.CreateCustomerRequest;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.request.SetTransferFeeRequest;
import dev.kaldiroglu.hexagonal.ayvalikbank.adapter.in.web.dto.response.CustomerResponse;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.model.CustomerId;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in.CreateCustomerUseCase;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in.DeleteCustomerUseCase;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in.ListCustomersUseCase;
import dev.kaldiroglu.hexagonal.ayvalikbank.domain.port.in.SetTransferFeeUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CreateCustomerUseCase createCustomer;
    private final DeleteCustomerUseCase deleteCustomer;
    private final ListCustomersUseCase listCustomers;
    private final SetTransferFeeUseCase setTransferFee;

    public AdminController(CreateCustomerUseCase createCustomer,
                           DeleteCustomerUseCase deleteCustomer,
                           ListCustomersUseCase listCustomers,
                           SetTransferFeeUseCase setTransferFee) {
        this.createCustomer = createCustomer;
        this.deleteCustomer = deleteCustomer;
        this.listCustomers = listCustomers;
        this.setTransferFee = setTransferFee;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        var customer = createCustomer.createCustomer(
                new CreateCustomerUseCase.Command(request.name(), request.email(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerResponse.from(customer));
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        deleteCustomer.deleteCustomer(CustomerId.of(customerId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> listCustomers() {
        var customers = listCustomers.listCustomers().stream()
                .map(CustomerResponse::from)
                .toList();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/settings/transfer-fee")
    public ResponseEntity<Void> setTransferFee(@Valid @RequestBody SetTransferFeeRequest request) {
        setTransferFee.setTransferFee(new SetTransferFeeUseCase.Command(request.feePercent()));
        return ResponseEntity.ok().build();
    }
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BorrowRequestDTO;
import com.example.demo.dto.BorrowingTransactionDTO;
import com.example.demo.model.BorrowingTransaction;
import com.example.demo.service.BorrowingTransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrows")
@Tag(name = "Borrowing Transactions", description = "Endpoints for borrowing and returning books")
@SecurityRequirement(name = "bearerAuth")
public class BorrowingTransactionController {

    @Autowired
    private BorrowingTransactionService borrowService;

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Returns all borrow/return transactions")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public List<BorrowingTransactionDTO> getAllTransactions() {
        return borrowService.getAllTransactionDTOs();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Returns a specific transaction by ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public Optional<BorrowingTransaction> getTransactionById(@PathVariable Long id) {
        return borrowService.getTransactionById(id);
    }

    @PostMapping
    @Operation(summary = "Borrow a book", description = "Creates a new borrowing transaction for a specific member and book")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public BorrowingTransaction borrowBook(@RequestBody BorrowRequestDTO request) {
        return borrowService.borrowBook(request.getMemberId(), request.getBookId());
    }

    @PutMapping("/return/{id}")
    @Operation(summary = "Return a book", description = "Marks a borrowed book as returned")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('STAFF')")
    public BorrowingTransaction returnBook(@PathVariable Long id) {
        return borrowService.returnBook(id);
    }
}

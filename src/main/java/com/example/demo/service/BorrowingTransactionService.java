package com.example.demo.service;

import com.example.demo.dto.BorrowRequestDTO;
import com.example.demo.dto.BorrowingTransactionDTO;
import com.example.demo.model.Book;
import com.example.demo.model.BorrowingTransaction;
import com.example.demo.model.Member;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingTransactionRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowingTransactionService {

    @Autowired
    private BorrowingTransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    private final UserActivityLogService activityLogService;

    public BorrowingTransactionService(UserActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    public List<BorrowingTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<BorrowingTransaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            return userDetails.getUser();
        }
        throw new RuntimeException("User is not authenticated");
    }

    public BorrowingTransaction borrowBook(Long memberId, Long bookId) {
        User currentUser = getCurrentUser();

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found"));

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setMember(member);
        transaction.setBook(book);
        transaction.setBorrowDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusWeeks(2));

        activityLogService.log(currentUser, "BORROW_BOOK", "Book ID: " + bookId + ", Member ID: " + memberId);

        return transactionRepository.save(transaction);
    }

    public List<BorrowingTransactionDTO> getAllTransactionDTOs() {
        List<BorrowingTransaction> transactions = transactionRepository.findAll();

        return transactions.stream().map(tx -> {
            BorrowingTransactionDTO dto = new BorrowingTransactionDTO();
            dto.setId(tx.getId());
            dto.setMemberId(tx.getMember().getId());
            dto.setMemberName(tx.getMember().getName());
            dto.setBook(tx.getBook());
            dto.setBorrowDate(tx.getBorrowDate());
            dto.setDueDate(tx.getDueDate());
            dto.setReturnDate(tx.getReturnDate());
            dto.setReturned(tx.isReturned());
            return dto;
        }).collect(Collectors.toList());
    }

    public BorrowingTransaction returnBook(Long transactionId) {
        Optional<BorrowingTransaction> optional = transactionRepository.findById(transactionId);
        if (optional.isPresent()) {
            BorrowingTransaction tx = optional.get();
            tx.setReturnDate(LocalDate.now());
            tx.setReturned(true); 

            User currentUser = getCurrentUser();
            activityLogService.log(currentUser, "RETURN_BOOK", "Book ID: " + tx.getBook().getId());

            return transactionRepository.save(tx); 
        }
        throw new RuntimeException("Transaction not found");
    }

}

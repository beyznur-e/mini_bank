package com.example.mini_bank.Controller;

import com.example.mini_bank.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Kullanıcının bakiyesini görüntüleme
    @GetMapping("/balance/{userId}")
    public ResponseEntity<Double> getBalance(@PathVariable Long userId) {
        Double balance = transactionService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    // Para yatırma işlemi
    @PostMapping("/yatir/{userId}")
    public ResponseEntity<String> yatir(@PathVariable Long userId, @RequestParam Double amount) {
        String response = transactionService.yatir(userId, amount);
        return ResponseEntity.ok(response);
    }

    // Para çekme işlemi
    @PostMapping("/cek/{userId}")
    public ResponseEntity<String> cek(@PathVariable Long userId, @RequestParam Double amount) {
        String response = transactionService.cek(userId, amount);
        return ResponseEntity.ok(response);
    }

    // Para transferi işlemi
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam Double amount) {
        String response = transactionService.transfer(senderId, receiverId, amount);
        return ResponseEntity.ok(response);
    }

    // Kullanıcı bakiyesi cache'ini temizleme
    @DeleteMapping("/clear-cache/{userId}")
    public ResponseEntity<String> clearCache(@PathVariable Long userId) {
        transactionService.clearCache(userId);
        return ResponseEntity.ok("Cache temizlendi.");
    }
}
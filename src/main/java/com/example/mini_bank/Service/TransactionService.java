package com.example.mini_bank.Service;

import com.example.mini_bank.Entity.User;
import com.example.mini_bank.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Autowired
    public TransactionService(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    // Kullanıcının bakiyesini cache’den getir (yoksa veritabanından alır ve cache’e ekler)
    @Cacheable(value = "userBalance", key = "#userId")
    public Double getBalance(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::getBalance).orElse(0.0);  // Null kontrolü ekledim
    }

    // Para yatırma işlemi -> Cache’i güncelle
    @Transactional
    @CachePut(value = "userBalance", key = "#userId")
    public String yatir(Long userId, Double amount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "Kullanıcı bulunamadı!";
        }
        User user = userOpt.get();
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return "Para yatırma işlemi başarılı! Yeni bakiye: " + user.getBalance();
    }

    // Para çekme işlemi -> Cache’i güncelle
    @Transactional
    @CachePut(value = "userBalance", key = "#userId")
    public String cek(Long userId, Double amount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "Kullanıcı bulunamadı!";
        }
        User user = userOpt.get();

        if (user.getBalance() < amount) {
            return "Yetersiz bakiye!";
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
        return "Para çekme işlemi başarılı! Yeni bakiye: " + user.getBalance();
    }

    @Transactional
    public String transfer(Long senderId, Long receiverId, Double amount) {
        if (senderId.equals(receiverId)) {
            return "Kendi hesabına para gönderemezsin!";
        }

        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            return "Gönderen veya alıcı kullanıcı bulunamadı!";
        }

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        double senderBalance = sender.getBalance() != null ? sender.getBalance() : 0.0;
        double receiverBalance = receiver.getBalance() != null ? receiver.getBalance() : 0.0;

        if (senderBalance < amount) {
            return "Yetersiz bakiye!";
        }

        sender.setBalance(senderBalance - amount);
        receiver.setBalance(receiverBalance + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        // Cache güncelleme işlemi
        cacheManager.getCache("userBalance").put(senderId, sender.getBalance());
        cacheManager.getCache("userBalance").put(receiverId, receiver.getBalance());

        return "Havale işlemi başarılı! Yeni bakiyeler -> Gönderen: " + sender.getBalance() + ", Alıcı: " + receiver.getBalance();
    }

    // Kullanıcının bakiyesini cache’den temizle (örneğin kullanıcı silindiğinde)
    @CacheEvict(value = "userBalance", key = "#userId")
    public void clearCache(Long userId) {
        System.out.println("Cache temizlendi: " + userId);
    }
}

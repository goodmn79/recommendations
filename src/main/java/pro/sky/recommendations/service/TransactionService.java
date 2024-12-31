package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.repository.TransactionRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public boolean isCompliance(String query, UUID userId) {
        return transactionRepository.isCompliance(query, userId);
    }
}

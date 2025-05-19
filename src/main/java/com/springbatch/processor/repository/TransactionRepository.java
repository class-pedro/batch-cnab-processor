package com.springbatch.processor.repository;

public class TransactionRepository {

    public String saveTransaction() {

        String query = """       
                    INSERT INTO transactions (
                        transactionType, transactionDate, amount, cpf, card, transactionHour, store_owner, store_name
                    ) VALUES (
                        :transactionType, :transactionDate, :amount, :cpf, :card, :transactionHour, :storeOwner, :storeName
                    )
                """;

        return query;
    }
}

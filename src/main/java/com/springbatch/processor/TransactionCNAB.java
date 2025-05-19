package com.springbatch.processor;

import java.math.BigDecimal;

public record TransactionCNAB(
        Integer type,
        String data,
        BigDecimal value,
        Long cpf,
        String card,
        String hour,
        String storeOwner,
        String storeName
) {
}

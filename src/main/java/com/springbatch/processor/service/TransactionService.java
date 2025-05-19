package com.springbatch.processor.service;

import com.springbatch.processor.Transaction;
import com.springbatch.processor.TransactionCNAB;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private static final String PATH_FILE = "file\\CNAB.txt";

    public FlatFileItemReader<TransactionCNAB> reader() {
        return new FlatFileItemReaderBuilder<TransactionCNAB>()
                .name("reader")
                .resource(new FileSystemResource(PATH_FILE))
                .fixedLength()
                .columns(
                        new Range(1, 1), new Range(2, 9),
                        new Range(10, 19), new Range(20, 30),
                        new Range(31, 42), new Range(43, 48),
                        new Range(49, 62), new Range(63, 80)
                )
                .names(
                        "type", "data", "value", "cpf",
                        "card", "hour", "storeOwner", "storeName"
                )
                .targetType(TransactionCNAB.class)
                .build();
    }

    public ItemProcessor<TransactionCNAB, Transaction> processor() {
        return item -> {
            Transaction transaction = new Transaction(
                    null, item.type(), null, null, item.cpf(),
                    item.card(), null, item.storeOwner().trim(),
                    item.storeName().trim())
                    .withValue(
                            item.value().divide(BigDecimal.valueOf(100)))
                    .withDate(item.data())
                    .withHour(item.hour());

            return transaction;
        };
    }


}
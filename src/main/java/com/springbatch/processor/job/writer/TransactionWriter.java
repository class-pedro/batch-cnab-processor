package com.springbatch.processor.job.writer;

import com.springbatch.processor.Transaction;
import com.springbatch.processor.repository.TransactionRepository;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Component
public class TransactionWriter {

    private TransactionRepository transactionRepository = new TransactionRepository();

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transaction>()
                .dataSource(dataSource)
                .sql(transactionRepository.saveTransaction())
                .beanMapped()
                .build();
    }

}

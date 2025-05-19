package com.springbatch.processor.job;

import com.springbatch.processor.Transaction;
import com.springbatch.processor.TransactionCNAB;
import com.springbatch.processor.job.writer.TransactionWriter;
import com.springbatch.processor.service.TransactionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Configuration
public class BatchConfig {

    public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository,
                       TransactionWriter transactionWriter, TransactionService transactionService) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.transactionWriter = transactionWriter;
        this.transactionService = transactionService;
    }

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private TransactionWriter transactionWriter;
    private TransactionService transactionService;

    @Bean
    Job job(Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    Step step(ItemReader<TransactionCNAB> reader,
              ItemProcessor<TransactionCNAB, Transaction> processor,
              ItemWriter<Transaction> writer) {
        return new StepBuilder("step", jobRepository)
                .<TransactionCNAB, Transaction>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    FlatFileItemReader<TransactionCNAB> reader() {
        return transactionService.reader();
    }

    @Bean
    ItemProcessor<TransactionCNAB, Transaction> processor() {
        return transactionService.processor();
    }

    @Bean
    JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
        return transactionWriter.writer(dataSource);
    }

}

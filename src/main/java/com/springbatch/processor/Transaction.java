package com.springbatch.processor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public record Transaction(
        Long id,
        Integer transactionType,
        Date transactionDate,
        BigDecimal amount,
        Long cpf,
        String card,
        Time transactionHour,
        String storeOwner,
        String storeName
) {
    public Transaction withValue(BigDecimal value) {
        return new Transaction(
                this.id(), this.transactionType(), this.transactionDate(), value,
                this.cpf(), card(), transactionHour(), storeOwner(),
                storeName());
    }

    public Transaction withDate(String date) throws ParseException {
        var simpleDate = new SimpleDateFormat("yyyyMMdd");
        var formattedDate = simpleDate.parse(date);

        return new Transaction(
                this.id(), this.transactionType(), new Date(formattedDate.getTime()),
                this.amount, this.cpf(), this.card(), this.transactionHour(),
                this.storeOwner(), this.storeName());
    }

    public Transaction withHour(String hour) throws ParseException {
        var simpleHour = new SimpleDateFormat("HHmmss");
        var formattedHour = simpleHour.parse(hour);

        return new Transaction(
                this.id(), this.transactionType(), this.transactionDate(),
                this.amount(), this.cpf(), this.card(),
                new Time(formattedHour.getTime()),
                this.storeOwner(), this.storeName());
    }


}

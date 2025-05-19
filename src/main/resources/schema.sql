CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transactionType INT,
    transactionDate DATE,
    amount DECIMAL,
    cpf BIGINT,
    card VARCHAR(255),
    transactionHour TIME,
    store_owner VARCHAR(255),
    store_name VARCHAR(255)
);

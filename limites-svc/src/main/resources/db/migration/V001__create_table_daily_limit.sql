CREATE TABLE daily_limit (
    id SERIAL NOT NULL,
    bank_branch INT NOT NULL,
    account INT NOT NULL,
    value DECIMAL(8,2),
    PRIMARY KEY (id)
);
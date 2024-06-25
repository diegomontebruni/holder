CREATE TABLE transaction (
    id UUID PRIMARY KEY,
    status VARCHAR NOT NULL,
    wallet_id UUID NOT NULL,
    ticker VARCHAR NOT NULL,
    quantity INT NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    operation VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    description VARCHAR,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_transaction_wallet_id on transaction (wallet_id);
create index idx_transaction_status on transaction (status);

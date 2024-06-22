CREATE TABLE asset (
    id UUID PRIMARY KEY,
    wallet_id UUID NOT NULL,
    ticker VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    average_price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE asset ADD CONSTRAINT unique_wallet_id_ticker UNIQUE (wallet_id, ticker);

CREATE TABLE stock (
    id UUID PRIMARY KEY,
    symbol VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_stock_symbol on stock (symbol);

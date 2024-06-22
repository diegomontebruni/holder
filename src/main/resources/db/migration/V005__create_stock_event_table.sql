CREATE TABLE stock_event (
    id UUID PRIMARY KEY,
    ticker VARCHAR NOT NULL,
    type VARCHAR(100) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    payment_date TIMESTAMP WITH TIME ZONE NOT NULL,
    approved_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_stock_event_ticker on stock_event (ticker);

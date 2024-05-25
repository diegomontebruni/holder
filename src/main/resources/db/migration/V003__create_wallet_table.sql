CREATE TABLE wallet (
    id UUID PRIMARY KEY,
    manager_id UUID NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_wallet_manager_id on wallet (manager_id);

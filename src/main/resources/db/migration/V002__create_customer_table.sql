CREATE TABLE customer (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    name VARCHAR,
    email VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_customer_user_id on customer (user_id);

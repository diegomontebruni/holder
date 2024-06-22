CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    password_recover_token VARCHAR,
    password_recover_token_expiration TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_users_username on users (username);
create index idx_users_status on users (status);
create index idx_users_password_recover_token on users (password_recover_token);

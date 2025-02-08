CREATE TABLE users (
    user_id UUID,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(5) NOT NULL,

    CONSTRAINT pk_user PRIMARY KEY(user_id),
    CONSTRAINT uk_user_email UNIQUE(email)
);
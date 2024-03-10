CREATE TABLE users (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_username ON users(username);

CREATE TABLE roles (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE user_role (
    id UUID DEFAULT RANDOM_UUID(),
    user_id UUID,
    role_id UUID UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT pk_user_role PRIMARY KEY (id, user_id, role_id)
);



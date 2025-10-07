-- Criação da tabela Perfil
CREATE TABLE profile (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criação da tabela Usuario
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE,
    phone VARCHAR(20),
    street VARCHAR(255),
    number INTEGER,
    neighborhood VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Tabela intermediária para a relação ManyToMany entre Usuario e Perfil
CREATE TABLE users_profile (
    user_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, profile_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (profile_id) REFERENCES profile(id)
);
CREATE TABLE IF NOT EXISTS preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    species VARCHAR(50),
    gender VARCHAR(20),
    age VARCHAR(20),
    size VARCHAR(20),
    active BOOLEAN,
    good_with_pets BOOLEAN,
    calm BOOLEAN,
    good_with_kids BOOLEAN,
    extrovert BOOLEAN,
    introvert BOOLEAN,
    max_distance INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

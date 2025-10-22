CREATE TABLE IF NOT EXISTS personality (
    id BIGSERIAL PRIMARY KEY,
    pet_id BIGINT NOT NULL UNIQUE,
    active BOOLEAN,
    good_with_pets BOOLEAN,
    calm BOOLEAN,
    good_with_kids BOOLEAN,
    extrovert BOOLEAN,
    introvert BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);

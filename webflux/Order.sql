CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        uid INTEGER NOT NULL REFERENCES my_users(id),

                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP
                            NOT NULL
);
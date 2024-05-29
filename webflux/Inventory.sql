CREATE TABLE Inventory (
                           id SERIAL PRIMARY KEY AUTO_INCREMENT,
                           pid INT NOT NULL,
                           quantity INT,
                           date TIMESTAMP
);

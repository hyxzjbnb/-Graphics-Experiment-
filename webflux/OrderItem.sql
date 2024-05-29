CREATE TABLE orderitem (
                           id SERIAL PRIMARY KEY AUTO_INCREMENT,
                           oid INT NOT NULL,
                           pid INT NOT NULL,
                           quantity INT
);
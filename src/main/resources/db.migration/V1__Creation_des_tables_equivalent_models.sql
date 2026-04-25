CREATE TABLE patients (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          nom VARCHAR(50) NOT NULL,
                          prenom VARCHAR(50) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          telephone VARCHAR(20) NOT NULL CHECK (telephone REGEXP '^\\+?[0-9]{8,15}$'),
                          date_naissance DATE NOT NULL
);
CREATE TABLE medecins(
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         nom VARCHAR(50) NOT NULL,
                         prenom VARCHAR(50) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         specialite VARCHAR(255) NOT NULL
);
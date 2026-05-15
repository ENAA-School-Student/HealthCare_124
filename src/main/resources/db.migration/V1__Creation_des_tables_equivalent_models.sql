CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('SECRETAIRE', 'ADMIN', 'MEDECIN', 'PATIENT'))
);

CREATE TABLE patients (
    id BIGINT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    date_naissance DATE NOT NULL,
    CONSTRAINT fk_patient_user FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE medecins (
    id BIGINT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    specialite VARCHAR(255) NOT NULL,
    CONSTRAINT fk_medecin_user FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE dossiers_medicaux (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_creation DATETIME NOT NULL,
    patient_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_dossier_patient FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE rendez_vous (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_rendez_vous DATETIME NOT NULL,
    statut VARCHAR(20) NOT NULL CHECK (statut IN ('PLANIFIE', 'ANNULE', 'TERMINE')),
    patient_id BIGINT,
    medecin_id BIGINT,
    CONSTRAINT fk_rendez_vous_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_rendez_vous_medecin FOREIGN KEY (medecin_id) REFERENCES medecins (id)
);

CREATE TABLE consultations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    diagnostic TEXT,
    observation TEXT,
    date_consultation DATETIME NOT NULL,
    dossier_id BIGINT,
    medecin_id BIGINT,
    CONSTRAINT fk_consultation_dossier FOREIGN KEY (dossier_id) REFERENCES dossiers_medicaux (id),
    CONSTRAINT fk_consultation_medecin FOREIGN KEY (medecin_id) REFERENCES medecins (id)
);

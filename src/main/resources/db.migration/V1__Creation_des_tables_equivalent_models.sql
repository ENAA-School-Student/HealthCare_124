CREATE TABLE patients
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom       VARCHAR(50)  NOT NULL,
    prenom    VARCHAR(50)  NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(20)  NOT NULL CHECK (telephone REGEXP '^\\+?[0-9]{8,15}$'
) ,
    date_naissance DATE NOT NULL
);
CREATE TABLE medecins
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom        VARCHAR(50)  NOT NULL,
    prenom     VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    specialite VARCHAR(255) NOT NULL
);
CREATE TABLE dossiers_medicaux
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    diagnostic   TEXT,
    observations TEXT,
    date_creation DATETIME   NOT NULL,
    patient_id   BIGINT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients (id)
);


CREATE TABLE rendez_vous
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_rendez_vous DATETIME         NOT NULL,
    statut         VARCHAR(255) NOT NULL CHECK ( statut IN ("PLANIFIE", "ANNULE", "TERMINE") ),
    patient_id     BIGINT,
    medecin_id     BIGINT,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (medecin_id) REFERENCES medecins (id)
);

CREATE TABLE consultations
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    diagnostic        TEXT,
    observation       TEXT,
    date_consultation DATETIME NOT NULL,
    dossier_id        BIGINT   NOT NULL,
    medecin_id        BIGINT   NOT NULL,
    FOREIGN KEY (dossier_id) REFERENCES dossiers_medicaux (id),
    FOREIGN KEY (medecin_id) REFERENCES medecins (id)
);
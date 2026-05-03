INSERT INTO patients (nom, prenom, email, telephone, date_naissance)
VALUES ('Benali', 'Amine', 'amine.benali@email.com', '+212611223344', '1990-05-15'),
       ('Mansouri', 'Sara', 'sara.m@email.com', '0655443322', '1985-11-20'),
       ('Idrissi', 'Youssef', 'y.idrissi@email.com', '+212788990011', '1998-02-10'),
       ('Alami', 'Nisrine', 'n.alami@email.com', '0522334455', '2002-07-30');


INSERT INTO medecins (nom, prenom, email, specialite)
VALUES ('Dr. Chami', 'Karim', 'k.chami@health.com', 'Cardiologie'),
       ('Dr. Tazi', 'Leila', 'l.tazi@health.com', 'Pédiatrie'),
       ('Dr. Haddad', 'Omar', 'o.haddad@health.com', 'Généraliste');


INSERT INTO dossiers_medicaux (diagnostic, observations, date_creation, patient_id)
VALUES ('Hypertension légère', 'Suivre le régime pauvre en sel', NOW(), 1),
       ('Asthme saisonnier', 'Utiliser inhalateur si besoin', NOW(), 2),
       ('Bilan annuel', 'Patient en excellente santé', NOW(), 3);

INSERT INTO rendez_vous (date_rendez_vous, statut, patient_id, medecin_id)
VALUES ('2026-05-20 09:00:00', 'PLANIFIE', 1, 1),
       ('2026-05-20 10:30:00', 'PLANIFIE', 2, 1),
       ('2026-05-21 14:00:00', 'TERMINE', 3, 2),
       ('2026-05-22 11:00:00', 'ANNULE', 4, 3),
       (NOW(), 'PLANIFIE', 1, 3);

INSERT INTO consultations (diagnostic, observation, date_consultation, dossier_id, medecin_id)
VALUES ('Grippe B', 'Repos strict 3 jours', '2026-04-15 10:00:00', 1, 3),
       ('Angine', 'Antibiotiques prescrits', '2026-04-16 11:00:00', 2, 2);
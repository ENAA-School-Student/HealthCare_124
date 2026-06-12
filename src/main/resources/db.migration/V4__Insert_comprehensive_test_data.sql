-- Nettoyage des tables pour éviter les doublons (ordre respectant les FK)
DELETE FROM consultations;
DELETE FROM rendez_vous;
DELETE FROM dossiers_medicaux;
DELETE FROM patients;
DELETE FROM medecins;
DELETE FROM admins;
DELETE FROM users;

-- Insertion de quelques utilisateurs de base
INSERT INTO users (id, user_name, email, password, role) VALUES
(1, 'admin', 'admin@healthcare.com', '$2a$10$8.UnVuG9HHgffUDAlk8q6uy5akLPNWuxmH8eHnS1OqS0T8mP697u2', 'ADMIN'), -- password: password
(2, 'drsmith', 'smith@healthcare.com', '$2a$10$8.UnVuG9HHgffUDAlk8q6uy5akLPNWuxmH8eHnS1OqS0T8mP697u2', 'MEDECIN'),
(3, 'drjones', 'jones@healthcare.com', '$2a$10$8.UnVuG9HHgffUDAlk8q6uy5akLPNWuxmH8eHnS1OqS0T8mP697u2', 'MEDECIN'),
(4, 'palami', 'alami@healthcare.com', '$2a$10$8.UnVuG9HHgffUDAlk8q6uy5akLPNWuxmH8eHnS1OqS0T8mP697u2', 'PATIENT'),
(5, 'pbenjelloun', 'benjelloun@healthcare.com', '$2a$10$8.UnVuG9HHgffUDAlk8q6uy5akLPNWuxmH8eHnS1OqS0T8mP697u2', 'PATIENT');

-- Insertion des détails pour l'Admin
INSERT INTO admins (id) VALUES (1);

-- Insertion des détails pour les Médecins
INSERT INTO medecins (id, nom, prenom, specialite) VALUES
(2, 'Smith', 'John', 'Cardiologie'),
(3, 'Jones', 'Emma', 'Dermatologie');

-- Insertion des détails pour les Patients
INSERT INTO patients (id, nom, prenom, telephone, date_naissance) VALUES
(4, 'Alami', 'Ahmed', '+212612345678', '1985-05-15'),
(5, 'Benjelloun', 'Sara', '+212687654321', '1992-11-20');

-- Insertion des Dossiers Médicaux
INSERT INTO dossiers_medicaux (id, date_creation, patient_id) VALUES
(1, '2026-01-10 10:00:00', 4),
(2, '2026-02-15 09:30:00', 5);

-- Insertion des Rendez-vous
INSERT INTO rendez_vous (id, date_rendez_vous, statut, patient_id, medecin_id) VALUES
(1, '2026-06-15 10:00:00', 'PLANIFIE', 4, 2),
(2, '2026-06-16 14:30:00', 'PLANIFIE', 5, 3),
(3, '2026-05-20 09:00:00', 'TERMINE', 4, 2);

-- Insertion des Consultations
INSERT INTO consultations (id, diagnostic, observation, date_consultation, dossier_id, medecin_id) VALUES
(1, 'Hypertension légère', 'À surveiller, régime pauvre en sel préconisé.', '2026-05-20 09:30:00', 1, 2),
(2, 'Acné juvénile', 'Traitement topique prescrit.', '2026-02-15 10:00:00', 2, 3),
(3, 'Tachycardie sinusale', 'ECG normal, stress probable.', '2026-05-25 11:00:00', 1, 2);

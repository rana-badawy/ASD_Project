INSERT INTO `addresses` (city, state, street, zip_code) VALUES
('Chicago', 'IL', '123 Main St', '60601'),
('New York', 'NY', '456 Broadway', '10001'),
('Los Angeles', 'CA', '789 Sunset Blvd', '90001'),
('Miami', 'FL', '321 Ocean Dr', '33139'),
('Seattle', 'WA', '654 Pine St', '98101'),
('Austin', 'TX', '987 Barton Springs', '78704');


INSERT INTO `patients` (dob, email, first_name, last_name, patient_no, phone, address_id) VALUES
('1990-05-15', 'alice@example.com', 'Alice', 'Smith', 'P001', '3125551234', 1),
('1985-11-23', 'bob@example.com', 'Bob', 'Jones', 'P002', '6465555678', 2),
('2000-03-10', 'carol@example.com', 'Carol', 'Taylor', 'P003', '2135559876', 3);


INSERT INTO `surgeries` (name, phone, surgery_no, address_id) VALUES
('Downtown Dental', '3055550001', 'S001', 4),
('Uptown Smiles', '2065550002', 'S002', 5),
('Capitol Dental', '5125550003', 'S003', 6);


INSERT INTO `dentists` (dentist_no, email, first_name, last_name, phone, specialization) VALUES
('D001', 'dr.john@example.com', 'John', 'Doe', '3125550001', 'Orthodontics'),
('D002', 'dr.emily@example.com', 'Emily', 'Clark', '6465550002', 'Pediatrics'),
('D003', 'dr.ryan@example.com', 'Ryan', 'Lee', '2135550003', 'Oral Surgery');


INSERT INTO appointments (appointment_date, appointment_time, dentist_id, patient_id, surgery_id) VALUES
('2025-05-01', '09:00:00', 1, 1, 1),
('2025-05-01', '10:00:00', 2, 2, 2),
('2025-05-01', '11:00:00', 3, 3, 3),
('2025-05-02', '09:30:00', 1, 2, 1),
('2025-05-02', '10:30:00', 2, 3, 2),
('2025-05-02', '11:30:00', 3, 1, 3),
('2025-05-03', '08:00:00', 1, 3, 1),
('2025-05-03', '08:30:00', 2, 1, 2),
('2025-05-03', '09:00:00', 3, 2, 3),
('2025-05-03', '09:30:00', 1, 1, 1);

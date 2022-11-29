 USE db_springboot;

INSERT INTO `users` (username, password, enabled) VALUES ('admin','$2a$10$j7mycLFGZ3WCrKOqpPvRGOTG8aHRo4VqN9Pi3okaYCDryFWtSyS/C',1);
INSERT INTO `users` (username, password, enabled) VALUES ('armando','$2a$10$9bndJPYUEjAWPaxX6sZYSO.QKQdT3aK9llxOzu/03Nzp12jVO5UiO',1);

INSERT INTO autorithies (user_id, authority) VALUES (1,'ROLE_ADMIN');
INSERT INTO `autorithies` (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO `autorithies` (user_id, authority) VALUES (2,'ROLE_USER');
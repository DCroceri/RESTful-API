INSERT INTO db_awsomerecipes.role (name) VALUES ('admin');
INSERT INTO db_awsomerecipes.role (name) VALUES ('user');
INSERT INTO db_awsomerecipes.role (name) VALUES ('chef');

INSERT INTO db_awsomerecipes.user (role_id, username, password, name, email, enabled)
	VALUES (1, 'admin', '$2a$10$HoZvRjv.sD3wc2fd/6Ws6.b4DH94mgtnxDbQP7hX0idjjJK63L7si', 'Administrator', 'admin@awsomerecipes.net', true);
INSERT INTO db_awsomerecipes.user (role_id, username, password, name, email, enabled)
	VALUES (3, 'chef1', '$2a$10$HoZvRjv.sD3wc2fd/6Ws6.b4DH94mgtnxDbQP7hX0idjjJK63L7si', 'Chef 1', 'chef1@awsomerecipes.net', true);
INSERT INTO db_awsomerecipes.user (role_id, username, password, name, email, enabled)
	VALUES (2, 'user1', '$2a$10$HoZvRjv.sD3wc2fd/6Ws6.b4DH94mgtnxDbQP7hX0idjjJK63L7si', 'User 1', 'user1@awsomerecipes.net', true);


-- password = "password123" (BCrypt hash)
INSERT INTO users (username, password, role)
VALUES ('testuser', '$2a$12$8Cq1nwzvHJD181tCudJT9OPAV/GIuI2ZyDHLyZ5qzfqXUt0UsMLsm', 'USER');

-- password = "admin123" (BCrypt hash)
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$12$zCqaWAVFc7hrWh3.4v7j7eHRoATEGQFsVaxsFqHuJDGn9.WwwHOCS', 'ADMIN');

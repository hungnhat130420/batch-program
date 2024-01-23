CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);

INSERT INTO students (id, name, age) VALUES (1, 'John Doe', 20);
INSERT INTO students (id, name, age) VALUES (2, 'Jane Doe', 22);
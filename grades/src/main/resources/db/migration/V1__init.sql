CREATE TABLE IF NOT EXISTS grade (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(100) NOT NULL,
    grade DOUBLE PRECISION NOT NULL,
    student_id INT
);
CREATE DATABASE Group5;
USE Group5;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role ENUM('cashier', 'admin', 'manager') NOT NULL
);

CREATE TABLE movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    poster_path VARCHAR(255),
    summary_path VARCHAR(255),
    duration INT DEFAULT 120
);

CREATE TABLE movie_genres (
    movie_id INT,
    genre VARCHAR(50),
    PRIMARY KEY (movie_id, genre),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

CREATE TABLE halls (
    hall_id INT PRIMARY KEY AUTO_INCREMENT,
    name ENUM('HALL_A', 'HALL_B') NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE sessions (
    session_id INT PRIMARY KEY AUTO_INCREMENT,
    start_time TIME NOT NULL
);

CREATE TABLE schedule (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT,
    hall_id INT,
    session_id INT,
    schedule_date DATE NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (hall_id) REFERENCES halls(hall_id),
    FOREIGN KEY (session_id) REFERENCES sessions(session_id),
    UNIQUE (hall_id, session_id, schedule_date)
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type ENUM('beverage', 'biscuit', 'toy') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    image_path VARCHAR(255)
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE
);

CREATE TABLE sales (
    sale_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    cashier_id INT,
    sale_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    tax_amount DECIMAL(10,2) NOT NULL,
    invoice_path VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (cashier_id) REFERENCES users(user_id)
);

CREATE TABLE tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    sale_id INT,
    schedule_id INT,
    seat_number VARCHAR(10) NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    discount_applied DECIMAL(5,2) DEFAULT 0.00,
    is_cancelled BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sale_id) REFERENCES sales(sale_id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id)
);

CREATE TABLE sale_items (
    sale_id INT,
    product_id INT,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(sale_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    PRIMARY KEY (sale_id, product_id)
);

CREATE TABLE config ( 
    config_key VARCHAR(50) PRIMARY KEY,
    config_value VARCHAR(255) NOT NULL
);

-- Insert initial data
INSERT INTO users (username, password, first_name, last_name, role) VALUES
('cashier1', 'cashier1', 'Cashier', 'One', 'cashier'),
('admin1', 'admin1', 'Admin', 'One', 'admin'),
('manager1', 'manager1', 'Manager', 'One', 'manager');

INSERT INTO halls (name, capacity) VALUES
('HALL_A', 16),
('HALL_B', 48);

INSERT INTO sessions (start_time) VALUES
('10:00:00'),
('12:00:00'),
('14:00:00'),
('16:00:00'),
('18:00:00'),
('20:00:00');

INSERT INTO config (config_key, config_value) VALUES
('ticket_base_price', '100.00'),
('age_discount_rate', '50.00'),
('ticket_tax_rate', '20.00'),
('product_tax_rate', '10.00');

INSERT INTO movies (title, poster_path, summary_path, duration) VALUES
('The Matrix', '/posters/matrix.jpg', '/summaries/matrix.txt', 120),
('Inception', '/posters/inception.jpg', '/summaries/inception.txt', 120),
('Interstellar', '/posters/interstellar.jpg', '/summaries/interstellar.txt', 120),
('The Dark Knight', '/posters/dark_knight.jpg', '/summaries/dark_knight.txt', 120),
('Pulp Fiction', '/posters/pulp_fiction.jpg', '/summaries/pulp_fiction.txt', 120);

INSERT INTO movie_genres (movie_id, genre) VALUES
-- The Matrix (Sci-Fi, Action, Cyberpunk, Philosophy)
(1, 'Sci-Fi'),
(1, 'Action'),
(1, 'Cyberpunk'),
(1, 'Philosophy'),

-- Inception (Sci-Fi, Action, Thriller, Mystery, Heist)
(2, 'Sci-Fi'),
(2, 'Action'),
(2, 'Thriller'),
(2, 'Mystery'),
(2, 'Heist'),

-- Interstellar (Sci-Fi, Drama, Adventure, Space, Mystery)
(3, 'Sci-Fi'),
(3, 'Drama'),
(3, 'Adventure'),
(3, 'Space'),
(3, 'Mystery'),

-- The Dark Knight (Action, Drama, Crime, Thriller, Superhero)
(4, 'Action'),
(4, 'Drama'),
(4, 'Crime'),
(4, 'Thriller'),
(4, 'Superhero'),

-- Pulp Fiction (Crime, Drama, Dark Comedy, Thriller, Neo-noir)
(5, 'Crime'),
(5, 'Drama'),
(5, 'Dark Comedy'),
(5, 'Thriller'),
(5, 'Neo-noir');

INSERT INTO products (name, type, price, stock_quantity, image_path) VALUES
('Cola', 'beverage', 15.00, 100, '/products/cola.jpg'),
('Water', 'beverage', 5.00, 200, '/products/water.jpg'),
('Popcorn', 'biscuit', 25.00, 150, '/products/popcorn.jpg'),
('Chocolate Bar', 'biscuit', 12.00, 100, '/products/chocolate.jpg'),
('Movie Character Plush', 'toy', 50.00, 30, '/products/plush.jpg'),
('Action Figure', 'toy', 75.00, 20, '/products/action_figure.jpg');

-- Generate January 2025 Schedule
INSERT INTO schedule (movie_id, hall_id, session_id, schedule_date)
SELECT 
    ((seq.seq + h.hall_id + s.session_id) % 5) + 1 as movie_id,
    h.hall_id,
    s.session_id,
    DATE_ADD('2025-01-01', INTERVAL seq.seq DAY) as schedule_date
FROM 
    halls h
    CROSS JOIN sessions s
    CROSS JOIN (
        SELECT a.N + b.N * 10 as seq
        FROM (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 
              UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a
        CROSS JOIN (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3) b
        WHERE a.N + b.N * 10 < 31
    ) seq
WHERE 
    NOT EXISTS (
        SELECT 1 FROM schedule s2 
        WHERE s2.hall_id = h.hall_id 
        AND s2.session_id = s.session_id 
        AND s2.schedule_date = DATE_ADD('2025-01-01', INTERVAL seq.seq DAY)
    )
ORDER BY seq.seq, h.hall_id, s.session_id;

-- Create views
CREATE VIEW available_seats AS
SELECT
    s.schedule_id,
    h.capacity - COUNT(t.ticket_id) as seats_available
FROM schedule s
JOIN halls h ON s.hall_id = h.hall_id
LEFT JOIN tickets t ON s.schedule_id = t.schedule_id AND t.is_cancelled = FALSE
GROUP BY s.schedule_id;

CREATE VIEW movie_schedule_view AS
SELECT 
    m.title,
    h.name as hall_name,
    s.start_time,
    sch.schedule_date,
    COALESCE(av.seats_available, h.capacity) as available_seats
FROM 
    schedule sch
    JOIN movies m ON sch.movie_id = m.movie_id
    JOIN halls h ON sch.hall_id = h.hall_id
    JOIN sessions s ON sch.session_id = s.session_id
    LEFT JOIN available_seats av ON sch.schedule_id = av.schedule_id
WHERE 
    sch.schedule_date >= CURRENT_DATE();

CREATE VIEW sales_report AS
SELECT 
    DATE(s.sale_date) as sale_date,
    COUNT(DISTINCT s.sale_id) as total_sales,
    SUM(s.total_amount) as total_revenue,
    SUM(s.tax_amount) as total_tax,
    COUNT(t.ticket_id) as tickets_sold,
    SUM(si.quantity) as products_sold
FROM 
    sales s
    LEFT JOIN tickets t ON s.sale_id = t.sale_id AND t.is_cancelled = FALSE
    LEFT JOIN sale_items si ON s.sale_id = si.sale_id
GROUP BY 
    DATE(s.sale_date);

-- Create indexes
CREATE INDEX idx_schedule_date ON schedule(schedule_date);
CREATE INDEX idx_movie_title ON movies(title);
CREATE INDEX idx_sales_date ON sales(sale_date);
CREATE INDEX idx_tickets_schedule ON tickets(schedule_id);

-- Create triggers
DELIMITER //

CREATE TRIGGER after_sale_items_insert
AFTER INSERT ON sale_items
FOR EACH ROW
BEGIN
    UPDATE products 
    SET stock_quantity = stock_quantity - NEW.quantity 
    WHERE product_id = NEW.product_id;
END//

CREATE TRIGGER after_ticket_cancel
AFTER UPDATE ON tickets
FOR EACH ROW
BEGIN
    IF NEW.is_cancelled = TRUE AND OLD.is_cancelled = FALSE THEN
        UPDATE available_seats 
        SET seats_available = seats_available + 1
        WHERE schedule_id = NEW.schedule_id;
    END IF;
END//

DELIMITER ;

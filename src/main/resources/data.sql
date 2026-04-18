INSERT INTO customer (id, name, customer_type, credit_score) VALUES (1, 'Juan Pérez', 'VIP', 850);
INSERT INTO customer (id, name, customer_type, credit_score) VALUES (2, 'María García', 'REGULAR', 720);
INSERT INTO customer (id, name, customer_type, credit_score) VALUES (3, 'Carlos López', 'NEW', 680);
INSERT INTO customer (id, name, customer_type, credit_score) VALUES (4, 'Ana Martínez', 'VIP', 800);

INSERT INTO account (id, account_number, customer_id, account_status, balance, debt_ratio) VALUES (100, 'ACC-001', 1, 'ACTIVE', 50000.00, 0.2);
INSERT INTO account (id, account_number, customer_id, account_status, balance, debt_ratio) VALUES (101, 'ACC-002', 2, 'ACTIVE', 15000.00, 0.3);
INSERT INTO account (id, account_number, customer_id, account_status, balance, debt_ratio) VALUES (102, 'ACC-003', 3, 'ACTIVE', 5000.00, 0.35);
INSERT INTO account (id, account_number, customer_id, account_status, balance, debt_ratio) VALUES (103, 'ACC-004', 4, 'ACTIVE', 75000.00, 0.15);

-- Insertar cuentas
INSERT INTO account (client_id, type, "number", balance, state)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'SAVINGS', '000123456', 1000.00, TRUE),
    ('550e8400-e29b-41d4-a716-446655440001', 'CHECKING', '000654321', 500.00, TRUE);

-- Insertar movimientos
INSERT INTO movement (id, date, amount, movement, balance, type, "number")
VALUES
    ('550e8400-e29b-41d4-a716-446655440004', '2025-03-11', 200.00, 'DEPOSIT', 1200.00, 'SAVINGS', '000123456'),
    ('550e8400-e29b-41d4-a716-446655440005', '2025-03-12', -100.00, 'WITHDRAWAL', 400.00, 'CHECKING', '000654321');
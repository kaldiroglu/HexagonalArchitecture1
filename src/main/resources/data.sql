-- Seed admin user (password: Admin@123!)
INSERT INTO customers (id, name, email, role, current_password)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'System Admin',
    'admin@ayvalikbank.dev',
    'ADMIN',
    '$2a$12$wPTHvMFcQn4CjDNlSq/Tk.MNVdXeFh/XGbGwz3P8gY9kQr6Ov5wQy'
) ON CONFLICT (id) DO NOTHING;

-- Default transfer fee: 1%
INSERT INTO settings (key, value)
VALUES ('TRANSFER_FEE_PERCENT', '1.0')
ON CONFLICT (key) DO NOTHING;

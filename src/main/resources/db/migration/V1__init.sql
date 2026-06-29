-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);

-- =========================
-- ACCOUNTS
-- =========================
CREATE TABLE accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name TEXT,
    type TEXT,
    institution TEXT,
    current_balance NUMERIC(12,2) DEFAULT 0,
    currency TEXT DEFAULT 'USD'
);

CREATE INDEX idx_accounts_user_id ON accounts(user_id);

-- =========================
-- CATEGORIES
-- =========================
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    parent_id UUID REFERENCES categories(id) ON DELETE SET NULL
);

CREATE INDEX idx_categories_user_id ON categories(user_id);
CREATE INDEX idx_categories_parent_id ON categories(parent_id);

-- =========================
-- TRANSACTIONS
-- =========================
CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    amount NUMERIC(12,2) NOT NULL,
    description TEXT,
    merchant TEXT,
    category_id UUID REFERENCES categories(id),
    transaction_date TIMESTAMP NOT NULL,
    posted_at TIMESTAMP DEFAULT now(),
    source TEXT,
    external_id TEXT
);

CREATE INDEX idx_transactions_account_id ON transactions(account_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_category_id ON transactions(category_id);

-- Prevent duplicate bank imports (very important for Plaid-like data)
CREATE UNIQUE INDEX idx_transactions_account_external
ON transactions(account_id, external_id);

-- =========================
-- BUDGETS
-- =========================
CREATE TABLE budgets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id UUID REFERENCES categories(id),
    monthly_limit NUMERIC(12,2) NOT NULL,
    month DATE NOT NULL
);

CREATE INDEX idx_budgets_user_id ON budgets(user_id);
CREATE INDEX idx_budgets_category_id ON budgets(category_id);
CREATE INDEX idx_budgets_month ON budgets(month);

-- =========================
-- TRANSACTION RULES
-- =========================
CREATE TABLE transaction_rules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    keyword TEXT,
    merchant TEXT,
    category_id UUID REFERENCES categories(id)
);

CREATE INDEX idx_transaction_rules_user_id ON transaction_rules(user_id);
CREATE INDEX idx_transaction_rules_category_id ON transaction_rules(category_id);

-- =========================
-- ACCOUNT SNAPSHOTS
-- =========================
CREATE TABLE account_snapshots (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    balance NUMERIC(12,2) NOT NULL
);

CREATE INDEX idx_account_snapshots_account_id ON account_snapshots(account_id);
CREATE INDEX idx_account_snapshots_date ON account_snapshots(date);

-- Optional: prevent duplicate daily snapshots per account
CREATE UNIQUE INDEX idx_account_snapshots_unique_day
ON account_snapshots(account_id, date);
-- =====================================================================
-- data-postgres.sql — initial seed data for the Future API (PostgreSQL)
-- =====================================================================
-- Seeds a SYSTEM-scope "System Super Admin" role carrying every permission
-- available in the SYSTEM scope, plus an admin user assigned to that role.
--
-- Notes
--   * Passwords are stored in plain text: the app currently uses
--     NoOpPasswordEncoder (see WebSecurityConfig#passwordEncoder).
--   * PostgreSQL has a native uuid type, so ids are written as dashed
--     string literals (auto-cast by the driver). No UNHEX needed.
--   * Upserts use ON CONFLICT (id) DO UPDATE; EXCLUDED is the row that
--     would have been inserted.
--   * Inserts are idempotent; the permission set is re-seeded each run.
--   * The role and user use fixed UUIDs so foreign keys resolve deterministically.
--
-- Loaded when spring.sql.init.platform=postgres, plus (in every profile):
--     spring.sql.init.mode=always
--     spring.jpa.defer-datasource-initialization=true
-- =====================================================================

-- Fixed identifiers (native uuid literals):
--   00000000-0000-0000-0000-000000000001 -> System Super Admin role
--   00000000-0000-0000-0000-000000000002 -> admin user

-- ---------------------------------------------------------------------
-- 1) System Super Admin role (scope = SYSTEM, not tied to a merchant)
-- ---------------------------------------------------------------------
INSERT INTO user_role (id, created_at, updated_at, name, scope, merchant_id)
VALUES ('00000000-0000-0000-0000-000000000001', LOCALTIMESTAMP, LOCALTIMESTAMP, 'System Super Admin', 'SYSTEM', NULL)
ON CONFLICT (id) DO UPDATE SET
    name        = EXCLUDED.name,
    scope       = EXCLUDED.scope,
    merchant_id = EXCLUDED.merchant_id,
    updated_at  = LOCALTIMESTAMP;

-- ---------------------------------------------------------------------
-- 2) Every permission available in the SYSTEM scope.
--    Excludes CREATE_PRODUCT and UPDATE_PRODUCT, which are MERCHANT-only
--    (see UserPermission enum). Re-seeded: delete then insert so any
--    permission removed from this list is also dropped from the role.
-- ---------------------------------------------------------------------
DELETE FROM user_role_permission WHERE role_id = '00000000-0000-0000-0000-000000000001';

INSERT INTO user_role_permission (role_id, permission) VALUES
    ('00000000-0000-0000-0000-000000000001', 'CREATE_USER'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_USER'),
    ('00000000-0000-0000-0000-000000000001', 'UPDATE_USER'),
    ('00000000-0000-0000-0000-000000000001', 'DELETE_USER'),
    ('00000000-0000-0000-0000-000000000001', 'CREATE_USER_ROLE'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_USER_ROLE'),
    ('00000000-0000-0000-0000-000000000001', 'UPDATE_USER_ROLE'),
    ('00000000-0000-0000-0000-000000000001', 'DELETE_USER_ROLE'),
    ('00000000-0000-0000-0000-000000000001', 'CREATE_MERCHANT'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_MERCHANT'),
    ('00000000-0000-0000-0000-000000000001', 'UPDATE_MERCHANT'),
    ('00000000-0000-0000-0000-000000000001', 'DELETE_MERCHANT'),
    ('00000000-0000-0000-0000-000000000001', 'CREATE_STORE'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_STORE'),
    ('00000000-0000-0000-0000-000000000001', 'UPDATE_STORE'),
    ('00000000-0000-0000-0000-000000000001', 'DELETE_STORE'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_STORE_FEATURE'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_PRODUCT'),
    ('00000000-0000-0000-0000-000000000001', 'DELETE_PRODUCT'),
    ('00000000-0000-0000-0000-000000000001', 'CREATE_PRODUCT_CATEGORY'),
    ('00000000-0000-0000-0000-000000000001', 'VIEW_PRODUCT_CATEGORY'),
    ('00000000-0000-0000-0000-000000000001', 'UPDATE_PRODUCT_CATEGORY'),
    ('00000000-0000-0000-0000-000000000001', 'DELETE_PRODUCT_CATEGORY'),
    ('00000000-0000-0000-0000-000000000001', 'CREATE_PRODUCT_FILTER');

-- ---------------------------------------------------------------------
-- 3) Admin user assigned to the System Super Admin role
-- ---------------------------------------------------------------------
INSERT INTO "user" (id, created_at, updated_at, email, username, password, first_name, last_name, role_id, enabled, merchant_id)
VALUES (
    '00000000-0000-0000-0000-000000000002',
    LOCALTIMESTAMP,
    LOCALTIMESTAMP,
    'albin.berisha@future.com',
    'albin.berisha',
    '123123',
    'Albin',
    'Berisha',
    '00000000-0000-0000-0000-000000000001',
    TRUE,
    NULL
)
ON CONFLICT (id) DO UPDATE SET
    email       = EXCLUDED.email,
    username    = EXCLUDED.username,
    password    = EXCLUDED.password,
    first_name  = EXCLUDED.first_name,
    last_name   = EXCLUDED.last_name,
    role_id     = EXCLUDED.role_id,
    enabled     = EXCLUDED.enabled,
    merchant_id = EXCLUDED.merchant_id,
    updated_at  = LOCALTIMESTAMP;

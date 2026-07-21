-- =====================================================================
-- data-mysql.sql — initial seed data for the Future API (MySQL)
-- =====================================================================
-- Seeds a SYSTEM-scope "System Super Admin" role carrying every permission
-- available in the SYSTEM scope, plus an admin user assigned to that role.
--
-- Notes
--   * Passwords are stored in plain text: the app currently uses
--     NoOpPasswordEncoder (see WebSecurityConfig#passwordEncoder).
--   * Primary keys are UUIDs. Hibernate 6+ stores UUIDs as BINARY(16) on
--     MySQL by default, so every id is written via UNHEX(REPLACE('-','')).
--   * Inserts are idempotent: re-running updates existing rows rather than
--     failing, and the permission set is cleared and re-seeded each run.
--   * The role and user use fixed UUIDs so foreign keys resolve deterministically.
--
-- Loaded when spring.sql.init.platform=mysql, plus (in every profile):
--     spring.sql.init.mode=always
--     spring.jpa.defer-datasource-initialization=true
-- =====================================================================

-- Fixed identifiers. UNHEX turns the dashed UUID into the BINARY(16) value
-- Hibernate reads and writes.
SET @role_system_super_admin = UNHEX(REPLACE('00000000-0000-0000-0000-000000000001', '-', ''));
SET @user_albin              = UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', ''));

-- ---------------------------------------------------------------------
-- 1) System Super Admin role (scope = SYSTEM, not tied to a merchant)
-- ---------------------------------------------------------------------
INSERT INTO user_role (id, created_at, updated_at, name, scope, merchant_id)
VALUES (@role_system_super_admin, NOW(6), NOW(6), 'System Super Admin', 'SYSTEM', NULL) AS incoming
ON DUPLICATE KEY UPDATE
    name        = incoming.name,
    scope       = incoming.scope,
    merchant_id = incoming.merchant_id,
    updated_at  = NOW(6);

-- ---------------------------------------------------------------------
-- 2) Every permission available in the SYSTEM scope.
--    Excludes CREATE_PRODUCT and UPDATE_PRODUCT, which are MERCHANT-only
--    (see UserPermission enum). Re-seeded: delete then insert so any
--    permission removed from this list is also dropped from the role.
-- ---------------------------------------------------------------------
DELETE FROM user_role_permission WHERE role_id = @role_system_super_admin;

INSERT INTO user_role_permission (role_id, permission) VALUES
    (@role_system_super_admin, 'CREATE_USER'),
    (@role_system_super_admin, 'VIEW_USER'),
    (@role_system_super_admin, 'UPDATE_USER'),
    (@role_system_super_admin, 'DELETE_USER'),
    (@role_system_super_admin, 'CREATE_USER_ROLE'),
    (@role_system_super_admin, 'VIEW_USER_ROLE'),
    (@role_system_super_admin, 'UPDATE_USER_ROLE'),
    (@role_system_super_admin, 'DELETE_USER_ROLE'),
    (@role_system_super_admin, 'CREATE_MERCHANT'),
    (@role_system_super_admin, 'VIEW_MERCHANT'),
    (@role_system_super_admin, 'UPDATE_MERCHANT'),
    (@role_system_super_admin, 'DELETE_MERCHANT'),
    (@role_system_super_admin, 'CREATE_STORE'),
    (@role_system_super_admin, 'VIEW_STORE'),
    (@role_system_super_admin, 'UPDATE_STORE'),
    (@role_system_super_admin, 'DELETE_STORE'),
    (@role_system_super_admin, 'VIEW_STORE_FEATURE'),
    (@role_system_super_admin, 'VIEW_PRODUCT'),
    (@role_system_super_admin, 'DELETE_PRODUCT'),
    (@role_system_super_admin, 'CREATE_PRODUCT_CATEGORY'),
    (@role_system_super_admin, 'VIEW_PRODUCT_CATEGORY'),
    (@role_system_super_admin, 'UPDATE_PRODUCT_CATEGORY'),
    (@role_system_super_admin, 'DELETE_PRODUCT_CATEGORY'),
    (@role_system_super_admin, 'CREATE_PRODUCT_FILTER');

-- ---------------------------------------------------------------------
-- 3) Admin user assigned to the System Super Admin role
-- ---------------------------------------------------------------------
INSERT INTO `user` (id, created_at, updated_at, email, username, password, first_name, last_name, role_id, enabled, merchant_id)
VALUES (
    @user_albin,
    NOW(6),
    NOW(6),
    'albin.berisha@future.com',
    'albin.berisha',
    '123123',
    'Albin',
    'Berisha',
    @role_system_super_admin,
    TRUE,
    NULL
) AS incoming
ON DUPLICATE KEY UPDATE
    email       = incoming.email,
    username    = incoming.username,
    password    = incoming.password,
    first_name  = incoming.first_name,
    last_name   = incoming.last_name,
    role_id     = incoming.role_id,
    enabled     = incoming.enabled,
    merchant_id = incoming.merchant_id,
    updated_at  = NOW(6);

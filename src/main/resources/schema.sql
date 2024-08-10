CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- DDL
CREATE TABLE IF NOT EXISTS notification
(
    notification_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    type            VARCHAR   NOT NULL,
    content         VARCHAR   NOT NULL,
    note            TEXT      NOT NULL,
    receiver_id     uuid      NOT NULL,
    notified_date   TIMESTAMP NOT NULL,
    checked         BOOLEAN   NOT NULL
);

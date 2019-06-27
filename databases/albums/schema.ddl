CREATE DATABASE albums;

CREATE TABLE album
(
  id     BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  artist VARCHAR(255),
  rating INT(11)                NOT NULL,
  title  VARCHAR(255),
  year   INT(11)                NOT NULL
);

CREATE TABLE album_scheduler_task (
  started_at TIMESTAMP NULL DEFAULT NULL
);

INSERT INTO album_scheduler_task (started_at) VALUES (NULL);
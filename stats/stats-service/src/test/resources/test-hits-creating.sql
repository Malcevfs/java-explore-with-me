-- insert into APPS(APP_NAME)
-- values ('ewm-main-service');

insert into hits (app, uri, ip, timestamp)
values ('ewm-main-service', '/events/1', '192.168.0.0', '2023-02-18 12:00:00'),
       ('ewm-main-service', '/events/1', '192.168.0.1', '2023-01-18 12:00:00'),
       ('ewm-main-service', '/events/2', '192.168.0.2', '2023-02-10 12:00:00');
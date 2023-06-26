CREATE SEQUENCE tenant_seq as bigint;

create table tenants
(
    id                                     bigint                   default nextval('tenant_seq'::regclass) not null
        primary key,
    name                          varchar(128)
);


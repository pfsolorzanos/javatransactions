DROP TABLE IF EXISTS client;

CREATE TABLE IF NOT EXISTS public.client
(
    id uuid NOT NULL,
    address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    age integer NOT NULL,
    gender character varying(255) COLLATE pg_catalog."default" NOT NULL,
    identification_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    state boolean NOT NULL,
    CONSTRAINT client_pkey PRIMARY KEY (id)
);
DROP TABLE IF EXISTS movement;
DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS public.account
(
    "number" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    balance numeric(38,2) NOT NULL,
    client_id uuid NOT NULL,
    state boolean NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY ("number", type),
    CONSTRAINT account_type_check CHECK (type::text = ANY (ARRAY['SAVINGS'::character varying, 'CHECKING'::character varying]::text[]))
);
CREATE TABLE IF NOT EXISTS public.movement
(
    id uuid NOT NULL,
    amount numeric(38,2) NOT NULL,
    balance numeric(38,2) NOT NULL,
    date date NOT NULL,
    movement character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "number" character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT movement_pkey PRIMARY KEY (id),
    CONSTRAINT fk53viukxy651la9pdysx7dfh1j FOREIGN KEY ("number", type)
        REFERENCES public.account ("number", type) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

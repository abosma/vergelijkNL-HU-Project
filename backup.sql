--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.3

-- Started on 2017-06-12 10:30:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 185 (class 1259 OID 16470)
-- Name: gebruiker; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE gebruiker (
    username character varying(20) NOT NULL,
    password character varying(20),
    role character varying(20),
    gebruikerid integer NOT NULL
);


--
-- TOC entry 186 (class 1259 OID 16483)
-- Name: Gebruiker_GebruikerID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Gebruiker_GebruikerID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 186
-- Name: Gebruiker_GebruikerID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Gebruiker_GebruikerID_seq" OWNED BY gebruiker.gebruikerid;


--
-- TOC entry 191 (class 1259 OID 16612)
-- Name: favorietenlijst; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE favorietenlijst (
    gebruikerid integer NOT NULL,
    productid integer NOT NULL
);


--
-- TOC entry 189 (class 1259 OID 16608)
-- Name: favorietenlijst_gebruikerid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE favorietenlijst_gebruikerid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 189
-- Name: favorietenlijst_gebruikerid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE favorietenlijst_gebruikerid_seq OWNED BY favorietenlijst.gebruikerid;


--
-- TOC entry 190 (class 1259 OID 16610)
-- Name: favorietenlijst_productid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE favorietenlijst_productid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 190
-- Name: favorietenlijst_productid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE favorietenlijst_productid_seq OWNED BY favorietenlijst.productid;


--
-- TOC entry 188 (class 1259 OID 16602)
-- Name: product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE product (
    productid integer NOT NULL,
    naam character varying(150) NOT NULL,
    prijs numeric(6,2) NOT NULL,
    link character varying(200) NOT NULL,
    imagelink character varying(200)
);


--
-- TOC entry 187 (class 1259 OID 16600)
-- Name: product_productid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE product_productid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 187
-- Name: product_productid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE product_productid_seq OWNED BY product.productid;


--
-- TOC entry 2024 (class 2604 OID 16615)
-- Name: favorietenlijst gebruikerid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY favorietenlijst ALTER COLUMN gebruikerid SET DEFAULT nextval('favorietenlijst_gebruikerid_seq'::regclass);


--
-- TOC entry 2025 (class 2604 OID 16616)
-- Name: favorietenlijst productid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY favorietenlijst ALTER COLUMN productid SET DEFAULT nextval('favorietenlijst_productid_seq'::regclass);


--
-- TOC entry 2022 (class 2604 OID 16485)
-- Name: gebruiker gebruikerid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY gebruiker ALTER COLUMN gebruikerid SET DEFAULT nextval('"Gebruiker_GebruikerID_seq"'::regclass);


--
-- TOC entry 2023 (class 2604 OID 16605)
-- Name: product productid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY product ALTER COLUMN productid SET DEFAULT nextval('product_productid_seq'::regclass);


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 186
-- Name: Gebruiker_GebruikerID_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"Gebruiker_GebruikerID_seq"', 5, true);


--
-- TOC entry 2163 (class 0 OID 16612)
-- Dependencies: 191
-- Data for Name: favorietenlijst; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO favorietenlijst VALUES (4, 91);
INSERT INTO favorietenlijst VALUES (5, 91);
INSERT INTO favorietenlijst VALUES (4, 26);


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 189
-- Name: favorietenlijst_gebruikerid_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('favorietenlijst_gebruikerid_seq', 1, false);


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 190
-- Name: favorietenlijst_productid_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('favorietenlijst_productid_seq', 1, false);


--
-- TOC entry 2157 (class 0 OID 16470)
-- Dependencies: 185
-- Data for Name: gebruiker; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO gebruiker VALUES ('Bob', 'Welkom123', 'User', 4);
INSERT INTO gebruiker VALUES ('Tom', 'Welkom123', 'User', 5);


--
-- TOC entry 2160 (class 0 OID 16602)
-- Dependencies: 188
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product VALUES (73, 'Ninzer®', 25.99, '//www.bol.com/nl/b/algemeen/ninzer/14523264/index.html', 'https://s.s-bol.com/imgbase0/imagebase3/regular/FC/3/7/7/7/9200000061547773.jpg');
INSERT INTO product VALUES (74, 'Marantz CD-5005 CD Speler', 229.00, 'https://www.wehkamp.nl/Zoeken/ArtikelDetail.aspx?ArtikelNummer=812610&Ntt=CD&PI=0&PrI=0&Nrpp=96&Blocks=0', '//assets.wehkamp.com/i/wehkamp/812610_pb_01/marantz-cd-5005-cd-speler-zilver-4951035053560.jpg?$pop210x210$');
INSERT INTO product VALUES (77, 'Capcom', 25.99, '//www.bol.com/nl/b/algemeen/capcom/2300935/index.html', 'https://s.s-bol.com/imgbase0/imagebase3/regular/FC/2/8/7/7/9200000045947782.jpg');
INSERT INTO product VALUES (78, 'Street fighter V (PC)', 42.99, 'https://www.wehkamp.nl/Zoeken/ArtikelDetail.aspx?ArtikelNummer=640738&Ntt=Street%20Fighter%20V&PI=0&PrI=0&Nrpp=96&Blocks=0', '//assets.wehkamp.com/i/wehkamp/640738_pb_01/street-fighter-v-pc-5055060972496.jpg?$pop210x210$');
INSERT INTO product VALUES (26, 'Tekken 7 - PS4', 59.99, 'https://www.bol.com/nl/p/tekken-7-ps4/9200000038448511/', 'https://s.s-bol.com/imgbase0/imagebase3/regular/FC/1/1/5/8/9200000038448511.jpg');
INSERT INTO product VALUES (27, 'Tekken 7 (PS4)', 59.99, 'https://www.wehkamp.nl/Zoeken/ArtikelDetail.aspx?ArtikelNummer=863332&Ntt=Tekken%207&PI=0&PrI=0&Nrpp=96&Blocks=0', '//assets.wehkamp.com/i/wehkamp/863332_pb_01/tekken-7-ps4-3391891990899.jpg?$pop210x210$');
INSERT INTO product VALUES (89, 'Street Fighter V - PS4', 25.99, 'https://www.bol.com/nl/p/street-fighter-v-ps4/9200000045947782/', 'https://s.s-bol.com/imgbase0/imagebase3/regular/FC/2/8/7/7/9200000045947782.jpg');
INSERT INTO product VALUES (91, 'Street Fighter V - Windows', 16.99, 'https://www.bol.com/nl/p/street-fighter-v-windows/9200000050376346/', 'https://s.s-bol.com/imgbase0/imagebase3/regular/FC/6/4/3/6/9200000050376346.jpg');


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 187
-- Name: product_productid_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('product_productid_seq', 112, true);


--
-- TOC entry 2035 (class 2606 OID 16618)
-- Name: favorietenlijst favorietenlijst_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY favorietenlijst
    ADD CONSTRAINT favorietenlijst_pkey PRIMARY KEY (gebruikerid, productid);


--
-- TOC entry 2027 (class 2606 OID 16554)
-- Name: gebruiker gebruiker_gebruikerid_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gebruiker
    ADD CONSTRAINT gebruiker_gebruikerid_key UNIQUE (gebruikerid);


--
-- TOC entry 2031 (class 2606 OID 16632)
-- Name: product product_naam_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_naam_key UNIQUE (naam);


--
-- TOC entry 2033 (class 2606 OID 16607)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (productid);


--
-- TOC entry 2037 (class 2606 OID 16637)
-- Name: favorietenlijst uq_favorietenlijst; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY favorietenlijst
    ADD CONSTRAINT uq_favorietenlijst UNIQUE (gebruikerid, productid);


--
-- TOC entry 2029 (class 2606 OID 16474)
-- Name: gebruiker useraccount_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gebruiker
    ADD CONSTRAINT useraccount_pkey PRIMARY KEY (username);


--
-- TOC entry 2038 (class 2606 OID 16619)
-- Name: favorietenlijst favorietenlijst_gebruikerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY favorietenlijst
    ADD CONSTRAINT favorietenlijst_gebruikerid_fkey FOREIGN KEY (gebruikerid) REFERENCES gebruiker(gebruikerid);


--
-- TOC entry 2039 (class 2606 OID 16624)
-- Name: favorietenlijst favorietenlijst_productid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY favorietenlijst
    ADD CONSTRAINT favorietenlijst_productid_fkey FOREIGN KEY (productid) REFERENCES product(productid);


-- Completed on 2017-06-12 10:30:16

--
-- PostgreSQL database dump complete
--


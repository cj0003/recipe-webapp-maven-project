--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-04-12 19:17:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16415)
-- Name: allergen; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema.allergen (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 16414)
-- Name: allergen_id_seq; Type: SEQUENCE; Schema: recipe_platform_schema; Owner: -
--

CREATE SEQUENCE recipe_platform_schema.allergen_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4840 (class 0 OID 0)
-- Dependencies: 216
-- Name: allergen_id_seq; Type: SEQUENCE OWNED BY; Schema: recipe_platform_schema; Owner: -
--

ALTER SEQUENCE recipe_platform_schema.allergen_id_seq OWNED BY recipe_platform_schema.allergen.id;


--
-- TOC entry 219 (class 1259 OID 16422)
-- Name: category; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema.category (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


--
-- TOC entry 218 (class 1259 OID 16421)
-- Name: category_id_seq; Type: SEQUENCE; Schema: recipe_platform_schema; Owner: -
--

CREATE SEQUENCE recipe_platform_schema.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4841 (class 0 OID 0)
-- Dependencies: 218
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: recipe_platform_schema; Owner: -
--

ALTER SEQUENCE recipe_platform_schema.category_id_seq OWNED BY recipe_platform_schema.category.id;


--
-- TOC entry 225 (class 1259 OID 16462)
-- Name: collection; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema.collection (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    "desc" character varying(500),
    creator integer NOT NULL
);


--
-- TOC entry 224 (class 1259 OID 16461)
-- Name: collection_id_seq; Type: SEQUENCE; Schema: recipe_platform_schema; Owner: -
--

CREATE SEQUENCE recipe_platform_schema.collection_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4842 (class 0 OID 0)
-- Dependencies: 224
-- Name: collection_id_seq; Type: SEQUENCE OWNED BY; Schema: recipe_platform_schema; Owner: -
--

ALTER SEQUENCE recipe_platform_schema.collection_id_seq OWNED BY recipe_platform_schema.collection.id;


--
-- TOC entry 226 (class 1259 OID 16475)
-- Name: like; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema."like" (
    "userID" integer NOT NULL,
    "recipeID" integer NOT NULL
);


--
-- TOC entry 228 (class 1259 OID 16505)
-- Name: r_has_c; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema.r_has_c (
    "recipeID" integer NOT NULL,
    "categoryID" integer NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16435)
-- Name: recipe; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema.recipe (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    "desc" character varying,
    ingredients character varying NOT NULL,
    instructions character varying NOT NULL,
    prep_time smallint NOT NULL,
    author integer NOT NULL,
    likes_num integer DEFAULT 0 NOT NULL,
    upload_date time without time zone,
    allergy_trigger boolean DEFAULT false NOT NULL,
    image bytea,
    image_type text
);


--
-- TOC entry 220 (class 1259 OID 16434)
-- Name: recipe_id_seq; Type: SEQUENCE; Schema: recipe_platform_schema; Owner: -
--

CREATE SEQUENCE recipe_platform_schema.recipe_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4843 (class 0 OID 0)
-- Dependencies: 220
-- Name: recipe_id_seq; Type: SEQUENCE OWNED BY; Schema: recipe_platform_schema; Owner: -
--

ALTER SEQUENCE recipe_platform_schema.recipe_id_seq OWNED BY recipe_platform_schema.recipe.id;


--
-- TOC entry 227 (class 1259 OID 16490)
-- Name: use; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema.use (
    "recipeID" integer NOT NULL,
    "allergenID" integer NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 16446)
-- Name: user; Type: TABLE; Schema: recipe_platform_schema; Owner: -
--

CREATE TABLE recipe_platform_schema."user" (
    id integer NOT NULL,
    name character varying(255),
    surname character varying(255),
    email character varying(255) NOT NULL,
    password text NOT NULL,
    username character varying(255) NOT NULL,
    bio character varying(400),
    reg_date time without time zone,
    image bytea,
    image_type text
);


--
-- TOC entry 222 (class 1259 OID 16445)
-- Name: user_id_seq; Type: SEQUENCE; Schema: recipe_platform_schema; Owner: -
--

CREATE SEQUENCE recipe_platform_schema.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4844 (class 0 OID 0)
-- Dependencies: 222
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: recipe_platform_schema; Owner: -
--

ALTER SEQUENCE recipe_platform_schema.user_id_seq OWNED BY recipe_platform_schema."user".id;


--
-- TOC entry 4659 (class 2604 OID 16418)
-- Name: allergen id; Type: DEFAULT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.allergen ALTER COLUMN id SET DEFAULT nextval('recipe_platform_schema.allergen_id_seq'::regclass);


--
-- TOC entry 4660 (class 2604 OID 16425)
-- Name: category id; Type: DEFAULT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.category ALTER COLUMN id SET DEFAULT nextval('recipe_platform_schema.category_id_seq'::regclass);


--
-- TOC entry 4665 (class 2604 OID 16465)
-- Name: collection id; Type: DEFAULT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.collection ALTER COLUMN id SET DEFAULT nextval('recipe_platform_schema.collection_id_seq'::regclass);


--
-- TOC entry 4661 (class 2604 OID 16438)
-- Name: recipe id; Type: DEFAULT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.recipe ALTER COLUMN id SET DEFAULT nextval('recipe_platform_schema.recipe_id_seq'::regclass);


--
-- TOC entry 4664 (class 2604 OID 16449)
-- Name: user id; Type: DEFAULT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema."user" ALTER COLUMN id SET DEFAULT nextval('recipe_platform_schema.user_id_seq'::regclass);


--
-- TOC entry 4667 (class 2606 OID 16420)
-- Name: allergen allergen_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.allergen
    ADD CONSTRAINT allergen_pkey PRIMARY KEY (id);


--
-- TOC entry 4669 (class 2606 OID 16427)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 4677 (class 2606 OID 16469)
-- Name: collection collection_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.collection
    ADD CONSTRAINT collection_pkey PRIMARY KEY (id);


--
-- TOC entry 4679 (class 2606 OID 16479)
-- Name: like like_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema."like"
    ADD CONSTRAINT like_pkey PRIMARY KEY ("userID", "recipeID");


--
-- TOC entry 4683 (class 2606 OID 16509)
-- Name: r_has_c r_has_c_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.r_has_c
    ADD CONSTRAINT r_has_c_pkey PRIMARY KEY ("recipeID", "categoryID");


--
-- TOC entry 4671 (class 2606 OID 16444)
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- TOC entry 4681 (class 2606 OID 16494)
-- Name: use use_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.use
    ADD CONSTRAINT use_pkey PRIMARY KEY ("recipeID", "allergenID");


--
-- TOC entry 4673 (class 2606 OID 16453)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 4675 (class 2606 OID 16460)
-- Name: user user_username_key; Type: CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema."user"
    ADD CONSTRAINT user_username_key UNIQUE (username);


--
-- TOC entry 4685 (class 2606 OID 16470)
-- Name: collection collection_creator_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.collection
    ADD CONSTRAINT collection_creator_fkey FOREIGN KEY (creator) REFERENCES recipe_platform_schema."user"(id);


--
-- TOC entry 4686 (class 2606 OID 16485)
-- Name: like like_recipeID_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema."like"
    ADD CONSTRAINT "like_recipeID_fkey" FOREIGN KEY ("recipeID") REFERENCES recipe_platform_schema.recipe(id) NOT VALID;


--
-- TOC entry 4687 (class 2606 OID 16480)
-- Name: like like_userID_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema."like"
    ADD CONSTRAINT "like_userID_fkey" FOREIGN KEY ("userID") REFERENCES recipe_platform_schema."user"(id);


--
-- TOC entry 4690 (class 2606 OID 16515)
-- Name: r_has_c r_has_c_categoryID_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.r_has_c
    ADD CONSTRAINT "r_has_c_categoryID_fkey" FOREIGN KEY ("categoryID") REFERENCES recipe_platform_schema.category(id);


--
-- TOC entry 4691 (class 2606 OID 16510)
-- Name: r_has_c r_has_c_recipeID_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.r_has_c
    ADD CONSTRAINT "r_has_c_recipeID_fkey" FOREIGN KEY ("recipeID") REFERENCES recipe_platform_schema.recipe(id);


--
-- TOC entry 4684 (class 2606 OID 16454)
-- Name: recipe recipe_author_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.recipe
    ADD CONSTRAINT recipe_author_fkey FOREIGN KEY (author) REFERENCES recipe_platform_schema."user"(id) NOT VALID;


--
-- TOC entry 4688 (class 2606 OID 16500)
-- Name: use use_allergenID_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.use
    ADD CONSTRAINT "use_allergenID_fkey" FOREIGN KEY ("allergenID") REFERENCES recipe_platform_schema.allergen(id);


--
-- TOC entry 4689 (class 2606 OID 16495)
-- Name: use use_recipeID_fkey; Type: FK CONSTRAINT; Schema: recipe_platform_schema; Owner: -
--

ALTER TABLE ONLY recipe_platform_schema.use
    ADD CONSTRAINT "use_recipeID_fkey" FOREIGN KEY ("recipeID") REFERENCES recipe_platform_schema.recipe(id);


-- Completed on 2024-04-12 19:17:35

--
-- PostgreSQL database dump complete
--


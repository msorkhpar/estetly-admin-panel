--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1 (Debian 16.1-1.pgdg120+1)

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
-- Name: body_area; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.body_area (
    id bigint NOT NULL,
    code character varying(255),
    display_name character varying(255) NOT NULL,
    parent_id bigint
);


ALTER TABLE public.body_area OWNER TO "Estetly";

--
-- Name: body_area_concern_association; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.body_area_concern_association (
    id bigint NOT NULL,
    body_area_id bigint NOT NULL,
    concern_id bigint NOT NULL
);


ALTER TABLE public.body_area_concern_association OWNER TO "Estetly";

--
-- Name: category; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    model character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO "Estetly";

--
-- Name: concern; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.concern (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    gender character varying(255) NOT NULL,
    other_names character varying(255),
    description text,
    picture oid,
    picture_content_type character varying(255),
    category_id bigint
);


ALTER TABLE public.concern OWNER TO "Estetly";

--
-- Name: concern_procedure_association; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.concern_procedure_association (
    id bigint NOT NULL,
    procedure_id bigint NOT NULL,
    concern_id bigint NOT NULL
);


ALTER TABLE public.concern_procedure_association OWNER TO "Estetly";

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO "Estetly";

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO "Estetly";

--
-- Name: doctor; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.doctor (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.doctor OWNER TO "Estetly";

--
-- Name: doctor_procedure_association; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.doctor_procedure_association (
    id bigint NOT NULL,
    picture oid,
    picture_content_type character varying(255),
    description text,
    cost real,
    procedure_id bigint NOT NULL,
    doctor_id bigint NOT NULL
);


ALTER TABLE public.doctor_procedure_association OWNER TO "Estetly";

--
-- Name: jhi_authority; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.jhi_authority (
    name character varying(50) NOT NULL
);


ALTER TABLE public.jhi_authority OWNER TO "Estetly";

--
-- Name: jhi_user; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.jhi_user (
    id bigint NOT NULL,
    login character varying(50) NOT NULL,
    password_hash character varying(60) NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    email character varying(191),
    image_url character varying(256),
    activated boolean NOT NULL,
    lang_key character varying(10),
    activation_key character varying(20),
    reset_key character varying(20),
    created_by character varying(50) NOT NULL,
    created_date timestamp without time zone,
    reset_date timestamp without time zone,
    last_modified_by character varying(50),
    last_modified_date timestamp without time zone
);


ALTER TABLE public.jhi_user OWNER TO "Estetly";

--
-- Name: jhi_user_authority; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.jhi_user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);


ALTER TABLE public.jhi_user_authority OWNER TO "Estetly";

--
-- Name: procedure; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.procedure (
    id bigint NOT NULL,
    title character varying(254) NOT NULL,
    description text,
    picture oid,
    picture_content_type character varying(255),
    inventiveness integer,
    average_cost double precision
);


ALTER TABLE public.procedure OWNER TO "Estetly";

--
-- Name: review; Type: TABLE; Schema: public; Owner: Estetly
--

CREATE TABLE public.review (
    id bigint NOT NULL,
    title character varying(254) NOT NULL,
    description text,
    rate integer,
    "timestamp" timestamp without time zone NOT NULL
);


ALTER TABLE public.review OWNER TO "Estetly";

--
-- Name: sequence_generator; Type: SEQUENCE; Schema: public; Owner: Estetly
--

CREATE SEQUENCE public.sequence_generator
    START WITH 1050
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sequence_generator OWNER TO "Estetly";

--
-- Data for Name: body_area; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.body_area (id, code, display_name, parent_id) FROM stdin;
1100	Scalp
1150	NaN	1100
1700	Face
1750	Forehead	1700
1950	Temples	1700
2100	Ears	1700
2250	Eyebrows	1700
2350	Eyelids	1700
2450	Eyes	1700
2550	Eyelashes	1700
2650	Nose	1700
2800	Nostrils	1700
2900	Cheeks	1700
3100	Mouth	1700
3200	Lips	1700
3300	Jaw	1700
3500	Neck
3800	Shoulders
4100	Chest
4400	Abdomen
4700	Lower abdomen
5000	Intimate area
5200	Armpits
5400	Upper arms
5700	Elbows
5950	Forearms
6250	Hands back
6450	Hands palm
6650	Thighs
6950	Knees
7200	Legs
7500	Ankles
7750	Feet
8000	Back
8300	Lower Back
8600	Buttocks
8950	BEFORE CLICKING ON AREAS
25650	NaN
25900	Gums
26350	Teeth
\.


--
-- Data for Name: body_area_concern_association; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.body_area_concern_association (id, body_area_id, concern_id) FROM stdin;
1250	1150	1200
1350	1150	1300
1450	1150	1400
1550	1150	1500
1650	1150	1600
1800	1750	1200
1850	1750	1300
1900	1750	1600
2000	1950	1200
2050	1950	1600
2150	2100	1200
2200	2100	1600
2300	2250	1200
2400	2350	1200
2500	2450	1200
2600	2550	1200
2700	2650	1200
2750	2650	1600
2850	2800	1200
2950	2900	1200
3000	2900	1300
3050	2900	1600
3150	3100	1200
3250	3200	1200
3350	3300	1200
3400	3300	1300
3450	3300	1600
3550	1150	1200
3600	1150	1300
3650	1150	1400
3700	1150	1500
3750	1150	1600
3850	1150	1200
3900	1150	1300
3950	1150	1400
4000	1150	1500
4050	1150	1600
4150	1150	1200
4200	1150	1300
4250	1150	1400
4300	1150	1500
4350	1150	1600
4450	1150	1200
4500	1150	1300
4550	1150	1400
4600	1150	1500
4650	1150	1600
4750	1150	1200
4800	1150	1300
4850	1150	1400
4900	1150	1500
4950	1150	1600
5050	1150	1200
5100	1150	1300
5150	1150	1600
5250	1150	1200
5300	1150	1300
5350	1150	1600
5450	1150	1200
5500	1150	1300
5550	1150	1400
5600	1150	1500
5650	1150	1600
5750	1150	1200
5800	1150	1300
5850	1150	1400
5900	1150	1600
6000	1150	1200
6050	1150	1300
6100	1150	1400
6150	1150	1500
6200	1150	1600
6300	1150	1200
6350	1150	1300
6400	1150	1600
6500	1150	1200
6550	1150	1300
6600	1150	1600
6700	1150	1200
6750	1150	1300
6800	1150	1400
6850	1150	1500
6900	1150	1600
7000	1150	1200
7050	1150	1300
7100	1150	1400
7150	1150	1600
7250	1150	1200
7300	1150	1300
7350	1150	1400
7400	1150	1500
7450	1150	1600
7550	1150	1200
7600	1150	1300
7650	1150	1400
7700	1150	1600
7800	1150	1200
7850	1150	1300
7900	1150	1400
7950	1150	1600
8050	1150	1200
8100	1150	1300
8150	1150	1400
8200	1150	1500
8250	1150	1600
8350	1150	1200
8400	1150	1300
8450	1150	1400
8500	1150	1500
8550	1150	1600
8650	1150	1200
8700	1150	1300
8750	1150	1400
8800	1150	1500
8850	1150	1600
9050	1150	9000
9150	1150	9100
9250	1150	9200
9350	1150	9300
9400	1750	9100
9450	1750	9200
9500	1950	9100
9550	1950	9200
9600	2100	9100
9700	2100	9650
9750	2250	9100
9800	2250	9200
9850	2350	9100
9950	2350	9900
10050	2450	10000
10100	2550	10000
10150	2650	9100
10250	2650	10200
10300	2800	10000
10350	2800	10000
10400	2900	9100
10450	2900	9200
10500	3100	10000
10600	3200	10550
10700	3200	10650
10750	3300	9100
10800	3300	9200
10850	1150	9100
10950	1150	10900
11050	1150	11000
11100	1150	9100
11150	1150	10900
11200	1150	9100
11300	1150	11250
11350	1150	10900
11400	1150	11000
11450	1150	9200
11500	1150	9100
11550	1150	11000
11600	1150	10900
11650	1150	9100
11700	1150	10650
11750	1150	10900
11800	1150	11000
11850	1150	9100
11950	1150	11900
12050	1150	12000
12100	1150	10650
12200	1150	12150
12300	1150	12250
12350	1150	10900
12400	1150	9100
12450	1150	10900
12500	1150	12000
12550	1150	11000
12600	1150	10650
12650	1150	9100
12700	1150	10900
12750	1150	9100
12800	1150	10900
12850	1150	9100
12900	1150	10900
12950	1150	9100
13050	1150	13000
13100	1150	9100
13200	1150	13150
13250	1150	10900
13300	1150	9100
13350	1150	11000
13400	1150	10900
13450	1150	9100
13500	1150	10900
13550	1150	9100
13600	1150	10900
13650	1150	9100
13700	1150	10900
13750	1150	9100
13800	1150	10900
13900	1150	13850
13950	1150	9100
14000	1150	10900
14100	1150	14050
14150	1150	11000
14200	1150	9100
14250	1150	11000
14300	1150	10900
14350	1150	9100
14400	1150	11000
14450	1150	10900
14500	1150	10650
14650	1150	14600
14750	1150	14700
14850	1150	14800
14950	1150	14900
15050	1150	15000
15150	1150	15100
15250	1150	15200
15350	1150	15300
15450	1150	15400
15550	1150	15500
15650	1150	15600
15750	1150	15700
15850	1750	15800
15900	1750	14900
15950	1950	14900
16050	1950	16000
16100	1950	15800
16150	1950	15000
16200	1950	15600
16250	1950	15100
16300	1950	15200
16350	1950	15300
16400	1950	15400
16450	1950	15500
16500	2100	15800
16600	2250	16550
16650	2250	15600
16700	2250	15400
16750	2250	14900
16800	2250	15300
16850	2250	15500
16950	2350	16900
17000	2350	15400
17050	2450	10000
17100	2550	16550
17150	2550	15600
17200	2550	15300
17250	2550	15400
17300	2550	15500
17350	2650	15800
17400	2800	16000
17500	2800	17450
17550	2800	15800
17600	2900	15800
17650	2900	17450
17700	2900	14900
17750	3100	16000
17800	3100	15800
17850	3100	14900
17900	3100	15300
17950	3100	17450
18050	3100	18000
18100	3100	15400
18150	3100	15500
18200	3100	15600
18250	3200	10000
18350	3300	18300
18400	3300	15600
18450	3300	15200
18500	3300	15300
18550	3300	15400
18600	3300	14900
18650	3300	16000
18700	3300	15800
18750	3300	15500
18800	3300	17450
18850	3300	18000
18900	1150	15800
18950	1150	14900
19000	1150	17450
19050	1150	18000
19100	1150	15800
19150	1150	16000
19200	1150	15800
19250	1150	15300
19300	1150	15600
19350	1150	15400
19400	1150	15500
19450	1150	17450
19500	1150	18000
19550	1150	16000
19600	1150	15600
19650	1150	15800
19700	1150	15300
19750	1150	15400
19800	1150	17450
19850	1150	18000
19900	1150	15500
19950	1150	16000
20000	1150	15800
20050	1150	15600
20100	1150	15300
20150	1150	15400
20200	1150	17450
20250	1150	18000
20300	1150	15500
20350	1150	16000
20400	1150	14900
20450	1150	15800
20500	1150	15600
20550	1150	15400
20600	1150	15300
20650	1150	18000
20700	1150	17450
20750	1150	15500
20800	1150	16000
20850	1150	15800
20900	1150	15300
20950	1150	15600
21000	1150	15400
21050	1150	15500
21100	1150	18000
21150	1150	17450
21200	1150	16000
21250	1150	15800
21300	1150	18000
21350	1150	17450
21400	1150	15600
21450	1150	15400
21500	1150	15300
21550	1150	15500
21600	1150	16000
21650	1150	15600
21700	1150	18000
21750	1150	15300
21800	1150	15400
21850	1150	15500
21900	1150	15800
21950	1150	16000
22000	1150	15400
22050	1150	15800
22100	1150	15600
22150	1150	18000
22200	1150	15300
22250	1150	15500
22300	1150	16000
22350	1150	15500
22400	1150	15600
22450	1150	15300
22500	1150	15400
22550	1150	15800
22600	1150	15800
22650	1150	15800
22700	1150	16000
22750	1150	15600
22800	1150	15400
22850	1150	15300
22900	1150	15500
22950	1150	17450
23000	1150	18000
23050	1150	15800
23100	1150	16000
23150	1150	15600
23200	1150	18000
23250	1150	17450
23300	1150	15400
23350	1150	15300
23400	1150	15500
23450	1150	15800
23500	1150	16000
23550	1150	15400
23600	1150	15600
23650	1150	15300
23700	1150	15500
23750	1150	18000
23800	1150	16000
23850	1150	15800
23900	1150	15600
23950	1150	15400
24000	1150	15300
24050	1150	15500
24100	1150	16000
24150	1150	15400
24200	1150	15300
24250	1150	15500
24300	1150	15600
24350	1150	15800
24400	1150	16000
24450	1150	15800
24500	1150	15400
24550	1150	15600
24600	1150	17450
24650	1150	15300
24700	1150	15500
24750	1150	18000
24800	1150	16000
24850	1150	15800
24900	1150	15600
24950	1150	15300
25000	1150	15500
25050	1150	15400
25100	1150	17450
25150	1150	18000
25200	1150	16000
25250	1150	15800
25300	1150	15400
25350	1150	15600
25400	1150	15300
25450	1150	15500
25500	1150	17450
25550	1150	18000
25750	1150	25700
25800	1150	25700
26000	1150	25950
26100	1150	26050
26200	1150	26150
26300	1150	26250
26450	1150	26400
26550	1150	26500
26650	1150	26600
26750	1150	26700
26850	1150	26800
26950	1150	26900
27050	1150	27000
27150	1150	27100
27250	1150	27200
27350	1150	27300
27450	1150	27400
27550	1150	27500
27650	1150	27600
27750	1150	27700
27850	1150	27800
27950	1150	27900
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.category (id, name, model) FROM stdin;
1050	Tattoo removal	MODEL_2
8900	Odor / Sweating	MODEL_2
14550	Hair	MODEL_2
25600	NaN	NaN
25850	Dental	MODEL_4
\.


--
-- Data for Name: concern; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.concern (id, title, gender, other_names, description, picture, picture_content_type, category_id) FROM stdin;
1200	Small: Under two fingers	ALL	1050
1300	Medium: Under one hand	ALL	1050
1400	Large: Under two hands	ALL	1050
1500	Extra Large: Under three hands	ALL	1050
1600	Full Area	ALL	1050
9000	Odor - Trimethylaminuria (TMAU)	ALL	8900
9100	Sweat - Hyperhidrosis	ALL	8900
9200	Odor - Seborrheic Dermatitis	ALL	8900
9300	Odor - Folliculitis	ALL	8900
9650	Odor - Otitis Externa (Swimmer's Ear)	ALL	8900
9900	Odor - Blepharitis	ALL	8900
10000	NOT CLICKABLE	ALL	8900
10200	Odor - Rhinitis	ALL	8900
10550	Odor - Dental & Oral Condition	ALL	8900
10650	Odor - Candidiasis	ALL	8900
10900	Odor - Bromhidrosis	ALL	8900
11000	Odor - Intertrigo	ALL	8900
11250	Odor - Tinea Versicolor	ALL	8900
11900	Odor - Bacterial Vaginosis	ALL	8900
12000	Odor - Trichomoniasis	ALL	8900
12150	Odor - Urinary Incontinence	ALL	8900
12250	Odor - Hemorrhoids	ALL	8900
13000	Odor - Xerosis (Dry Skin)	ALL	8900
13150	Odor - Hand Eczema	ALL	8900
13850	Odor - Athlete's Foot (Tinea Pedis)	ALL	8900
14050	Odor - Acne	ALL	8900
14600	Polycystic Ovary Syndrome (PCOS) - Women	ALL	14550
14700	Loss/Thinning - Alopecia Universalis	ALL	14550
14800	Loss/Thinning - Alopetia Totalis	ALL	14550
14900	Excess/Contouring	ALL	14550
15000	Loss/Thinning - Androgenetic Alopecia	ALL	14550
15100	Loss/Baldness - Androgenetic Alopecia	ALL	14550
15200	Loss - Traction Alopecia	ALL	14550
15300	Loss/Thinning - Alopecia Areata	ALL	14550
15400	Loss - Telogen Effluvium	ALL	14550
15500	Loss - Cicatricial Alopecia	ALL	14550
15600	Loss - Trichotillomania	ALL	14550
15700	Other - Dandruff	ALL	14550
15800	Excess or Hypertrichosis	ALL	14550
16000	Excess - Hirsutism (Women)	ALL	14550
16550	Loss - Madarosis	ALL	14550
16900	Excess - Distichiasis	ALL	14550
17450	Other - Ingrown Hair (Pseudofolliculitis)	ALL	14550
18000	Other - Folliculitis	ALL	14550
18300	Loss - Patchy/Uneven Beard	ALL	14550
25700	NaN	ALL	25600
25950	Excessive Gum Tissue (Gummy Smile)	ALL	25850
26050	Gum Recession	ALL	25850
26150	Gummy Smile	ALL	25850
26250	Uneven Gum Line	ALL	25850
26400	Stained Teeth	ALL	25850
26500	Deep Stained Teeth	ALL	25850
26600	Yellow Teeth	ALL	25850
26700	Internal Tooth Discoloration	ALL	25850
26800	Tartar Buildup	ALL	25850
26900	Chipped/Cracked Teeth	ALL	25850
27000	Enamel Erosion	ALL	25850
27100	Tooth Decay	ALL	25850
27200	Major Misalignment	ALL	25850
27300	Minor Misalignment	ALL	25850
27400	Gaps in Teeth	ALL	25850
27500	Missing Teeth	ALL	25850
27600	Short Teeth	ALL	25850
27700	Uneven Teeth	ALL	25850
27800	Worn-down Teeth	ALL	25850
27900	Tooth Shape Concerns	ALL	25850
\.


--
-- Data for Name: concern_procedure_association; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.concern_procedure_association (id, procedure_id, concern_id) FROM stdin;
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
00000000000000	jhipster	config/liquibase/changelog/00000000000000_initial_schema.xml	2024-02-14 04:50:01.604616	1	EXECUTED	9:b6b4a3e0d2a6d7f1e5139675af65d7b0	createSequence sequenceName=sequence_generator		4.24.0	7904201513
00000000000001	jhipster	config/liquibase/changelog/00000000000000_initial_schema.xml	2024-02-14 04:50:01.71811	2	EXECUTED	9:1fe8b4e13f20fc44432c21045f92d736	createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...		4.24.0	7904201513
20240214094822-1	jhipster	config/liquibase/changelog/20240214094822_added_entity_Category.xml	2024-02-14 04:50:01.728514	3	EXECUTED	9:6d975e9f56e946f13267eeff419684dd	createTable tableName=category		4.24.0	7904201513
20240214094823-1	jhipster	config/liquibase/changelog/20240214094823_added_entity_BodyArea.xml	2024-02-14 04:50:01.736916	4	EXECUTED	9:838417bd692149def1ae5ca04908fd57	createTable tableName=body_area		4.24.0	7904201513
20240214094824-1	jhipster	config/liquibase/changelog/20240214094824_added_entity_Doctor.xml	2024-02-14 04:50:01.743391	5	EXECUTED	9:726877360fcbf350e1b5754ff0e67454	createTable tableName=doctor		4.24.0	7904201513
20240214094825-1	jhipster	config/liquibase/changelog/20240214094825_added_entity_Review.xml	2024-02-14 04:50:01.752749	6	EXECUTED	9:1e7d8d5ab14b93130911f0476f99a64c	createTable tableName=review; dropDefaultValue columnName=timestamp, tableName=review		4.24.0	7904201513
20240214094826-1	jhipster	config/liquibase/changelog/20240214094826_added_entity_Procedure.xml	2024-02-14 04:50:01.761944	7	EXECUTED	9:895e6c6decd9c3321c7ad33f849b4c2c	createTable tableName=procedure		4.24.0	7904201513
20240214094827-1	jhipster	config/liquibase/changelog/20240214094827_added_entity_Concern.xml	2024-02-14 04:50:01.770217	8	EXECUTED	9:5724da981ea998a70f268cfb665c462b	createTable tableName=concern		4.24.0	7904201513
20240214094828-1	jhipster	config/liquibase/changelog/20240214094828_added_entity_BodyAreaConcernAssociation.xml	2024-02-14 04:50:01.777292	9	EXECUTED	9:468a42bc0b052abaf1fa36ff41f093a7	createTable tableName=body_area_concern_association		4.24.0	7904201513
20240214094829-1	jhipster	config/liquibase/changelog/20240214094829_added_entity_ConcernProcedureAssociation.xml	2024-02-14 04:50:01.784122	10	EXECUTED	9:dc1d0e8a6988faa993498645f223d0ba	createTable tableName=concern_procedure_association		4.24.0	7904201513
20240214094830-1	jhipster	config/liquibase/changelog/20240214094830_added_entity_DoctorProcedureAssociation.xml	2024-02-14 04:50:01.796163	11	EXECUTED	9:d253c27c6bcf8d485b80bbea454a27a1	createTable tableName=doctor_procedure_association		4.24.0	7904201513
20240214094823-2	jhipster	config/liquibase/changelog/20240214094823_added_entity_constraints_BodyArea.xml	2024-02-14 04:50:01.803125	12	EXECUTED	9:20caaef7ca517f6598b01b7b4015b8f2	addForeignKeyConstraint baseTableName=body_area, constraintName=fk_body_area__parent_id, referencedTableName=body_area		4.24.0	7904201513
20240214094827-2	jhipster	config/liquibase/changelog/20240214094827_added_entity_constraints_Concern.xml	2024-02-14 04:50:01.809719	13	EXECUTED	9:6ae64e1f016aec88394ac07da6eab086	addForeignKeyConstraint baseTableName=concern, constraintName=fk_concern__category_id, referencedTableName=category		4.24.0	7904201513
20240214094828-2	jhipster	config/liquibase/changelog/20240214094828_added_entity_constraints_BodyAreaConcernAssociation.xml	2024-02-14 04:50:01.819842	14	EXECUTED	9:ff2c4805c8aa8956f436c5e0ced480ae	addForeignKeyConstraint baseTableName=body_area_concern_association, constraintName=fk_body_area_concern_association__body_area_id, referencedTableName=body_area; addForeignKeyConstraint baseTableName=body_area_concern_association, constraintName=...		4.24.0	7904201513
20240214094829-2	jhipster	config/liquibase/changelog/20240214094829_added_entity_constraints_ConcernProcedureAssociation.xml	2024-02-14 04:50:01.829702	15	EXECUTED	9:4bccee655a5b6956b4a2a22c7889a3dc	addForeignKeyConstraint baseTableName=concern_procedure_association, constraintName=fk_concern_procedure_association__procedure_id, referencedTableName=procedure; addForeignKeyConstraint baseTableName=concern_procedure_association, constraintName=...		4.24.0	7904201513
20240214094830-2	jhipster	config/liquibase/changelog/20240214094830_added_entity_constraints_DoctorProcedureAssociation.xml	2024-02-14 04:50:01.840313	16	EXECUTED	9:ff937852482a9dacc7cbcb31d4a447f2	addForeignKeyConstraint baseTableName=doctor_procedure_association, constraintName=fk_doctor_procedure_association__procedure_id, referencedTableName=procedure; addForeignKeyConstraint baseTableName=doctor_procedure_association, constraintName=fk_...		4.24.0	7904201513
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f
\.


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.doctor (id, name) FROM stdin;
\.


--
-- Data for Name: doctor_procedure_association; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.doctor_procedure_association (id, picture, picture_content_type, description, cost, procedure_id, doctor_id) FROM stdin;
\.


--
-- Data for Name: jhi_authority; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.jhi_authority (name) FROM stdin;
ROLE_ADMIN
ROLE_USER
\.


--
-- Data for Name: jhi_user; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) FROM stdin;
1	admin	$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC	Administrator	Administrator	admin@localhost		t	en	system	system
2	user	$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K	User	User	user@localhost		t	en	system	system
\.


--
-- Data for Name: jhi_user_authority; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.jhi_user_authority (user_id, authority_name) FROM stdin;
1	ROLE_ADMIN
1	ROLE_USER
2	ROLE_USER
\.


--
-- Data for Name: procedure; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.procedure (id, title, description, picture, picture_content_type, inventiveness, average_cost) FROM stdin;
\.


--
-- Data for Name: review; Type: TABLE DATA; Schema: public; Owner: Estetly
--

COPY public.review (id, title, description, rate, "timestamp") FROM stdin;
\.


--
-- Name: sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: Estetly
--

SELECT pg_catalog.setval('public.sequence_generator', 27950, true);


--
-- Name: body_area_concern_association body_area_concern_association_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.body_area_concern_association
    ADD CONSTRAINT body_area_concern_association_pkey PRIMARY KEY (id);


--
-- Name: body_area body_area_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.body_area
    ADD CONSTRAINT body_area_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: concern concern_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.concern
    ADD CONSTRAINT concern_pkey PRIMARY KEY (id);


--
-- Name: concern_procedure_association concern_procedure_association_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.concern_procedure_association
    ADD CONSTRAINT concern_procedure_association_pkey PRIMARY KEY (id);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- Name: doctor_procedure_association doctor_procedure_association_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.doctor_procedure_association
    ADD CONSTRAINT doctor_procedure_association_pkey PRIMARY KEY (id);


--
-- Name: jhi_authority jhi_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_authority
    ADD CONSTRAINT jhi_authority_pkey PRIMARY KEY (name);


--
-- Name: jhi_user_authority jhi_user_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);


--
-- Name: jhi_user jhi_user_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT jhi_user_pkey PRIMARY KEY (id);


--
-- Name: procedure procedure_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.procedure
    ADD CONSTRAINT procedure_pkey PRIMARY KEY (id);


--
-- Name: review review_pkey; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_pkey PRIMARY KEY (id);


--
-- Name: jhi_user ux_user_email; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_email UNIQUE (email);


--
-- Name: jhi_user ux_user_login; Type: CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_login UNIQUE (login);


--
-- Name: jhi_user_authority fk_authority_name; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES public.jhi_authority(name);


--
-- Name: body_area fk_body_area__parent_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.body_area
    ADD CONSTRAINT fk_body_area__parent_id FOREIGN KEY (parent_id) REFERENCES public.body_area(id);


--
-- Name: body_area_concern_association fk_body_area_concern_association__body_area_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.body_area_concern_association
    ADD CONSTRAINT fk_body_area_concern_association__body_area_id FOREIGN KEY (body_area_id) REFERENCES public.body_area(id);


--
-- Name: body_area_concern_association fk_body_area_concern_association__concern_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.body_area_concern_association
    ADD CONSTRAINT fk_body_area_concern_association__concern_id FOREIGN KEY (concern_id) REFERENCES public.concern(id);


--
-- Name: concern fk_concern__category_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.concern
    ADD CONSTRAINT fk_concern__category_id FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: concern_procedure_association fk_concern_procedure_association__concern_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.concern_procedure_association
    ADD CONSTRAINT fk_concern_procedure_association__concern_id FOREIGN KEY (concern_id) REFERENCES public.concern(id);


--
-- Name: concern_procedure_association fk_concern_procedure_association__procedure_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.concern_procedure_association
    ADD CONSTRAINT fk_concern_procedure_association__procedure_id FOREIGN KEY (procedure_id) REFERENCES public.procedure(id);


--
-- Name: doctor_procedure_association fk_doctor_procedure_association__doctor_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.doctor_procedure_association
    ADD CONSTRAINT fk_doctor_procedure_association__doctor_id FOREIGN KEY (doctor_id) REFERENCES public.doctor(id);


--
-- Name: doctor_procedure_association fk_doctor_procedure_association__procedure_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.doctor_procedure_association
    ADD CONSTRAINT fk_doctor_procedure_association__procedure_id FOREIGN KEY (procedure_id) REFERENCES public.procedure(id);


--
-- Name: jhi_user_authority fk_user_id; Type: FK CONSTRAINT; Schema: public; Owner: Estetly
--

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);


--
-- PostgreSQL database dump complete
--


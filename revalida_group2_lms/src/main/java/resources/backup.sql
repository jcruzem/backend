--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 12.4

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

--
-- Name: lms_ss; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA lms_ss;


ALTER SCHEMA lms_ss OWNER TO postgres;

--
-- Name: account_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.account_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.account_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.account (
    account_id numeric(6,0) DEFAULT nextval('lms_ss.account_seq'::regclass) NOT NULL,
    role character varying(20) NOT NULL,
    first_name character varying(100) NOT NULL,
    middle_name character varying(100),
    last_name character varying(100) NOT NULL,
    gender character varying(1) NOT NULL,
    birthdate date NOT NULL,
    active boolean DEFAULT true,
    username character varying(20) NOT NULL,
    password character varying(100) NOT NULL,
    child_id numeric(6,0),
    degree_id numeric(6,0)
);


ALTER TABLE lms_ss.account OWNER TO postgres;

--
-- Name: attendance_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.attendance_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.attendance_seq OWNER TO postgres;

--
-- Name: attendance; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.attendance (
    attendance_id numeric(4,0) DEFAULT nextval('lms_ss.attendance_seq'::regclass) NOT NULL,
    attendance_date date NOT NULL,
    status character varying(10),
    lecture_id numeric(6,0) NOT NULL,
    account_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.attendance OWNER TO postgres;

--
-- Name: course_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.course_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.course_seq OWNER TO postgres;

--
-- Name: course; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.course (
    course_id numeric(6,0) DEFAULT nextval('lms_ss.course_seq'::regclass) NOT NULL,
    course_code character varying(6) NOT NULL,
    course_name character varying(100) NOT NULL,
    units numeric(2,1) NOT NULL,
    degree_id numeric(6,0) NOT NULL,
    timeslot_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.course OWNER TO postgres;

--
-- Name: courses_assigned_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.courses_assigned_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.courses_assigned_seq OWNER TO postgres;

--
-- Name: courses_assigned; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.courses_assigned (
    courses_assigned_id numeric(6,0) DEFAULT nextval('lms_ss.courses_assigned_seq'::regclass) NOT NULL,
    status character varying(10) NOT NULL,
    course_id numeric(6,0) NOT NULL,
    account_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.courses_assigned OWNER TO postgres;

--
-- Name: degree_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.degree_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.degree_seq OWNER TO postgres;

--
-- Name: degree; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.degree (
    degree_id numeric(6,0) DEFAULT nextval('lms_ss.degree_seq'::regclass) NOT NULL,
    degree_code character varying(6) NOT NULL,
    degree_name character varying(100) NOT NULL,
    units_required numeric(4,1) NOT NULL
);


ALTER TABLE lms_ss.degree OWNER TO postgres;

--
-- Name: grade_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.grade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.grade_seq OWNER TO postgres;

--
-- Name: grade; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.grade (
    grade_id numeric(6,0) DEFAULT nextval('lms_ss.grade_seq'::regclass) NOT NULL,
    grade_value numeric(3,2) NOT NULL,
    datemodified date NOT NULL,
    status character varying(15) NOT NULL,
    remarks character varying(50),
    lecture_id numeric(6,0) NOT NULL,
    account_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.grade OWNER TO postgres;

--
-- Name: lecture_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.lecture_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.lecture_seq OWNER TO postgres;

--
-- Name: lecture; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.lecture (
    lecture_id numeric(6,0) DEFAULT nextval('lms_ss.lecture_seq'::regclass) NOT NULL,
    schedule character varying(50),
    section character varying(50),
    course_id numeric(6,0) NOT NULL,
    account_id numeric(6,0) NOT NULL,
    semester_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.lecture OWNER TO postgres;

--
-- Name: prerequisite_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.prerequisite_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.prerequisite_seq OWNER TO postgres;

--
-- Name: prerequisite; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.prerequisite (
    prerequisite_id numeric(6,0) DEFAULT nextval('lms_ss.prerequisite_seq'::regclass) NOT NULL,
    course_id numeric(6,0) NOT NULL,
    prerequisite_course_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.prerequisite OWNER TO postgres;

--
-- Name: semester_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.semester_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.semester_seq OWNER TO postgres;

--
-- Name: semester; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.semester (
    semester_id numeric(6,0) DEFAULT nextval('lms_ss.semester_seq'::regclass) NOT NULL,
    semester_code character varying(8) NOT NULL,
    start_date date NOT NULL,
    is_current boolean NOT NULL
);


ALTER TABLE lms_ss.semester OWNER TO postgres;

--
-- Name: student_load_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.student_load_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.student_load_seq OWNER TO postgres;

--
-- Name: student_load; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.student_load (
    sload_id numeric(6,0) DEFAULT nextval('lms_ss.student_load_seq'::regclass) NOT NULL,
    account_id numeric(4,0) NOT NULL,
    lecture_id numeric(6,0) NOT NULL
);


ALTER TABLE lms_ss.student_load OWNER TO postgres;

--
-- Name: subject_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.subject_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.subject_seq OWNER TO postgres;

--
-- Name: timeslot_seq; Type: SEQUENCE; Schema: lms_ss; Owner: postgres
--

CREATE SEQUENCE lms_ss.timeslot_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lms_ss.timeslot_seq OWNER TO postgres;

--
-- Name: timeslot; Type: TABLE; Schema: lms_ss; Owner: postgres
--

CREATE TABLE lms_ss.timeslot (
    timeslot_id numeric(6,0) DEFAULT nextval('lms_ss.timeslot_seq'::regclass) NOT NULL,
    timeslot_code character varying(6) NOT NULL,
    year_no character varying(1) NOT NULL,
    sem_no character varying(1) NOT NULL
);


ALTER TABLE lms_ss.timeslot OWNER TO postgres;

--
-- Data for Name: account; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.account (account_id, role, first_name, middle_name, last_name, gender, birthdate, active, username, password, child_id, degree_id) FROM stdin;
3	admin	Jazel	\N	Cruzem	f	2000-01-01	t	jcruzem77	$2a$10$mvapPVMpsBip/Pw7XZTKTeeJ07rVsuT07D0yfKdNExrp9I.S.4Oue	\N	\N
4	admin	Prince	\N	Monisit	m	2000-01-01	t	pmonisit77	$2a$10$QCMDbcDMDGO0BM2CD6t06uDG7tuXbI6m1R2r/khuN8acfSBXuetEG	\N	\N
8	professor	Sherwin	\N	Tragura	m	2000-01-01	t	stragura77	$2a$10$MU7LBkuP/7m2m/1.bZ/Vfe3QL23YFEtOesQmMIIqdjYvG121v8072	\N	\N
5	student	Johanna	\N	Doros	f	2000-01-01	t	jdoros77	$2a$10$D/qd9zwnjto0bYaVIhkdneB6JTu/VuhG/KGtc4lL09uLLwb5wUHYa	\N	1
1	admin	Matt	Bueno	Mitra	m	2000-01-01	t	mmitra77	$2a$10$TcxcTKV0sLFsWbWvtAcbKedFFKlDpafJ1FGcUr.GTXq6HxHh.qMmu	\N	\N
2	admin	EJ	\N	Roxas	m	2000-01-01	t	ejroxas77	$2a$10$c1.s.Az.usH4OcwAGpay2.BIPxtLdMmtU2BSBMtGDemXEiRQHOtna	\N	\N
9	parent	Audrey	\N	Hepburn	f	2000-01-01	t	ahepburn77	$2a$10$CGGDCMSk3imQLyNaNohSEuhd0E7RJ6IrE1ixMQi1JNriZRLYbaBTe	\N	\N
\.


--
-- Data for Name: attendance; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.attendance (attendance_id, attendance_date, status, lecture_id, account_id) FROM stdin;
3	2022-08-09	PRESENT	1	5
\.


--
-- Data for Name: course; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.course (course_id, course_code, course_name, units, degree_id, timeslot_id) FROM stdin;
2	ITENG1	English Fundamentals (For IT)	2.0	1	2
3	ENG101	English Fundamentals 2 (For IT)	2.0	1	2
\.


--
-- Data for Name: courses_assigned; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.courses_assigned (courses_assigned_id, status, course_id, account_id) FROM stdin;
1	ONGOING	2	5
\.


--
-- Data for Name: degree; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.degree (degree_id, degree_code, degree_name, units_required) FROM stdin;
2	IT-GD	Information Technology (Game Development)	210.0
1	IT	Information Technology	210.0
\.


--
-- Data for Name: grade; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.grade (grade_id, grade_value, datemodified, status, remarks, lecture_id, account_id) FROM stdin;
4	3.00	2022-10-03	PASSED	\N	1	5
\.


--
-- Data for Name: lecture; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.lecture (lecture_id, schedule, section, course_id, account_id, semester_id) FROM stdin;
1	T TH 7:00AM - 11:00AM	A	2	8	1
\.


--
-- Data for Name: prerequisite; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.prerequisite (prerequisite_id, course_id, prerequisite_course_id) FROM stdin;
2	3	2
\.


--
-- Data for Name: semester; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.semester (semester_id, semester_code, start_date, is_current) FROM stdin;
1	2022-1	2022-08-08	f
2	2022-2	2022-11-02	t
\.


--
-- Data for Name: student_load; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.student_load (sload_id, account_id, lecture_id) FROM stdin;
1	5	1
\.


--
-- Data for Name: timeslot; Type: TABLE DATA; Schema: lms_ss; Owner: postgres
--

COPY lms_ss.timeslot (timeslot_id, timeslot_code, year_no, sem_no) FROM stdin;
2	Y1Sem1	1	1
4	Y1Sem2	1	2
5	Y1Sem3	1	3
\.


--
-- Name: account_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.account_seq', 9, true);


--
-- Name: attendance_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.attendance_seq', 3, true);


--
-- Name: course_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.course_seq', 3, true);


--
-- Name: courses_assigned_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.courses_assigned_seq', 2, true);


--
-- Name: degree_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.degree_seq', 2, true);


--
-- Name: grade_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.grade_seq', 4, true);


--
-- Name: lecture_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.lecture_seq', 1, true);


--
-- Name: prerequisite_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.prerequisite_seq', 2, true);


--
-- Name: semester_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.semester_seq', 2, true);


--
-- Name: student_load_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.student_load_seq', 1, true);


--
-- Name: subject_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.subject_seq', 1, false);


--
-- Name: timeslot_seq; Type: SEQUENCE SET; Schema: lms_ss; Owner: postgres
--

SELECT pg_catalog.setval('lms_ss.timeslot_seq', 5, true);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (account_id);


--
-- Name: account account_username_key; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.account
    ADD CONSTRAINT account_username_key UNIQUE (username);


--
-- Name: attendance attendance_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.attendance
    ADD CONSTRAINT attendance_pkey PRIMARY KEY (attendance_id);


--
-- Name: course course_course_code_key; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.course
    ADD CONSTRAINT course_course_code_key UNIQUE (course_code);


--
-- Name: course course_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (course_id);


--
-- Name: courses_assigned courses_assigned_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.courses_assigned
    ADD CONSTRAINT courses_assigned_pkey PRIMARY KEY (courses_assigned_id);


--
-- Name: degree degree_degree_code_key; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.degree
    ADD CONSTRAINT degree_degree_code_key UNIQUE (degree_code);


--
-- Name: degree degree_degree_name_key; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.degree
    ADD CONSTRAINT degree_degree_name_key UNIQUE (degree_name);


--
-- Name: degree degree_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.degree
    ADD CONSTRAINT degree_pkey PRIMARY KEY (degree_id);


--
-- Name: grade grade_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.grade
    ADD CONSTRAINT grade_pkey PRIMARY KEY (grade_id);


--
-- Name: lecture lecture_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.lecture
    ADD CONSTRAINT lecture_pkey PRIMARY KEY (lecture_id);


--
-- Name: prerequisite prerequisite_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.prerequisite
    ADD CONSTRAINT prerequisite_pkey PRIMARY KEY (prerequisite_id);


--
-- Name: semester semester_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.semester
    ADD CONSTRAINT semester_pkey PRIMARY KEY (semester_id);


--
-- Name: semester semester_semester_code_key; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.semester
    ADD CONSTRAINT semester_semester_code_key UNIQUE (semester_code);


--
-- Name: student_load student_load_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.student_load
    ADD CONSTRAINT student_load_pkey PRIMARY KEY (sload_id);


--
-- Name: timeslot timeslot_pkey; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.timeslot
    ADD CONSTRAINT timeslot_pkey PRIMARY KEY (timeslot_id);


--
-- Name: timeslot timeslot_timeslot_code_key; Type: CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.timeslot
    ADD CONSTRAINT timeslot_timeslot_code_key UNIQUE (timeslot_code);


--
-- Name: account account_child_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.account
    ADD CONSTRAINT account_child_id_fkey FOREIGN KEY (child_id) REFERENCES lms_ss.account(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: account account_degree_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.account
    ADD CONSTRAINT account_degree_id_fkey FOREIGN KEY (degree_id) REFERENCES lms_ss.degree(degree_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: attendance attendance_account_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.attendance
    ADD CONSTRAINT attendance_account_id_fkey FOREIGN KEY (account_id) REFERENCES lms_ss.account(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: attendance attendance_lecture_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.attendance
    ADD CONSTRAINT attendance_lecture_id_fkey FOREIGN KEY (lecture_id) REFERENCES lms_ss.lecture(lecture_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: course course_degree_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.course
    ADD CONSTRAINT course_degree_id_fkey FOREIGN KEY (degree_id) REFERENCES lms_ss.degree(degree_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: course course_timeslot_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.course
    ADD CONSTRAINT course_timeslot_id_fkey FOREIGN KEY (timeslot_id) REFERENCES lms_ss.timeslot(timeslot_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: courses_assigned courses_assigned_account_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.courses_assigned
    ADD CONSTRAINT courses_assigned_account_id_fkey FOREIGN KEY (account_id) REFERENCES lms_ss.account(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: courses_assigned courses_assigned_course_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.courses_assigned
    ADD CONSTRAINT courses_assigned_course_id_fkey FOREIGN KEY (course_id) REFERENCES lms_ss.course(course_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: grade grade_account_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.grade
    ADD CONSTRAINT grade_account_id_fkey FOREIGN KEY (account_id) REFERENCES lms_ss.account(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: grade grade_lecture_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.grade
    ADD CONSTRAINT grade_lecture_id_fkey FOREIGN KEY (lecture_id) REFERENCES lms_ss.lecture(lecture_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: lecture lecture_account_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.lecture
    ADD CONSTRAINT lecture_account_id_fkey FOREIGN KEY (account_id) REFERENCES lms_ss.account(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: lecture lecture_course_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.lecture
    ADD CONSTRAINT lecture_course_id_fkey FOREIGN KEY (course_id) REFERENCES lms_ss.course(course_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: lecture lecture_semester_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.lecture
    ADD CONSTRAINT lecture_semester_id_fkey FOREIGN KEY (semester_id) REFERENCES lms_ss.semester(semester_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: prerequisite prerequisite_course_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.prerequisite
    ADD CONSTRAINT prerequisite_course_id_fkey FOREIGN KEY (course_id) REFERENCES lms_ss.course(course_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: prerequisite prerequisite_prerequisite_course_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.prerequisite
    ADD CONSTRAINT prerequisite_prerequisite_course_id_fkey FOREIGN KEY (prerequisite_course_id) REFERENCES lms_ss.course(course_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: student_load student_load_account_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.student_load
    ADD CONSTRAINT student_load_account_id_fkey FOREIGN KEY (account_id) REFERENCES lms_ss.account(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: student_load student_load_lecture_id_fkey; Type: FK CONSTRAINT; Schema: lms_ss; Owner: postgres
--

ALTER TABLE ONLY lms_ss.student_load
    ADD CONSTRAINT student_load_lecture_id_fkey FOREIGN KEY (lecture_id) REFERENCES lms_ss.lecture(lecture_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--


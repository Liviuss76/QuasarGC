--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.1
-- Started on 2014-10-09 15:20:37 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE quasargc;
--
-- TOC entry 2321 (class 1262 OID 16386)
-- Name: quasargc; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE quasargc WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


ALTER DATABASE quasargc OWNER TO postgres;

\connect quasargc

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner:
--

CREATE SCHEMA public;


--
-- TOC entry 2322 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 182 (class 3079 OID 12018)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2324 (class 0 OID 0)
-- Dependencies: 182
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 183 (class 3079 OID 16454)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2325 (class 0 OID 0)
-- Dependencies: 183
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 176 (class 1259 OID 32888)
-- Name: achievement; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE achievement (
    achievementid bigint NOT NULL,
    achievementcode character varying NOT NULL,
    achievementname character varying NOT NULL,
    achievementdesc character varying NOT NULL,
    achievementimage character varying,
    achievementunlockpoints double precision NOT NULL,
    achievementgivepoints bigint NOT NULL,
    achievementhidden boolean NOT NULL,
    gameid uuid NOT NULL,
    achievementrepeatable boolean NOT NULL,
    achievementcreationdate timestamp without time zone NOT NULL,
    achievementincrementpoints boolean DEFAULT false NOT NULL
);


ALTER TABLE public.achievement OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 32886)
-- Name: achievement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE achievement_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.achievement_id_seq OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 41119)
-- Name: blockedips; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE blockedips (
    id bigint NOT NULL,
    ip character varying
);


ALTER TABLE public.blockedips OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 17325)
-- Name: game; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE game (
    gameid uuid DEFAULT uuid_generate_v1() NOT NULL,
    gamename character varying(255) NOT NULL,
    gameimage character varying,
    gamecreationdate timestamp without time zone
);


ALTER TABLE public.game OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 41128)
-- Name: ips_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ips_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ips_id_seq OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 17330)
-- Name: leaderboard; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE leaderboard (
    leaderboardid uuid DEFAULT uuid_generate_v1() NOT NULL,
    leaderboardimage character varying,
    leaderboardmaxsubmitvalue integer NOT NULL,
    leaderboardminsubmitvalue integer NOT NULL,
    leaderboardname character varying(255) NOT NULL,
    gameid uuid,
    leaderboardscoreincrement boolean DEFAULT false NOT NULL,
    leaderboardcreationdate timestamp without time zone NOT NULL
);


ALTER TABLE public.leaderboard OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 17338)
-- Name: player; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE player (
    playerid uuid DEFAULT uuid_generate_v1() NOT NULL,
    datetimeofcreation timestamp without time zone DEFAULT now() NOT NULL,
    playerenabled boolean DEFAULT true NOT NULL,
    playerfirstname character varying(255),
    playerlastname character varying(255),
    playerpassword character varying(255) NOT NULL,
    playerusername character varying(255) NOT NULL,
    playerplatform character varying(255) NOT NULL,
    playerrole character varying(255) DEFAULT 'USER'::character varying NOT NULL,
    playerdisplayname character varying(255) NOT NULL,
    playeremail character varying,
    playerbirthdate timestamp without time zone,
    playersex character varying(255),
    playerpicture character varying,
    ip character varying,
    playerprofile character varying
);


ALTER TABLE public.player OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 32908)
-- Name: playerachievement; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE playerachievement (
    playerid uuid NOT NULL,
    achievementid bigint NOT NULL,
    unlockpoints double precision NOT NULL,
    unlockedcount integer
);


ALTER TABLE public.playerachievement OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 41135)
-- Name: playerlog; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE playerlog (
    logid bigint NOT NULL,
    playerid uuid NOT NULL,
    actiontype character varying(255) NOT NULL,
    action character varying NOT NULL,
    actiondate timestamp without time zone NOT NULL,
    ip character varying NOT NULL
);


ALTER TABLE public.playerlog OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 41154)
-- Name: playerlog_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE playerlog_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.playerlog_seq OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 17346)
-- Name: playerscore; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE playerscore (
    score integer,
    playerid uuid NOT NULL,
    leaderboardid uuid NOT NULL
);


ALTER TABLE public.playerscore OWNER TO postgres;


--
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 175
-- Name: achievement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('achievement_id_seq', 9, true);


--
-- TOC entry 2313 (class 0 OID 41119)
-- Dependencies: 178
-- Data for Name: blockedips; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2306 (class 0 OID 17325)
-- Dependencies: 170
-- Data for Name: game; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2327 (class 0 OID 0)
-- Dependencies: 179
-- Name: ips_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ips_id_seq', 1, true);


--
-- TOC entry 2307 (class 0 OID 17330)
-- Dependencies: 171
-- Data for Name: leaderboard; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2308 (class 0 OID 17338)
-- Dependencies: 172
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO player VALUES ('c8f51601-f2a9-4927-8dc0-e27b91564a4d', '2014-09-25 12:32:51.575', true, '', '', 'c6cfbd500c8d374f90a61138b2be2f19436ca886953de1592806cefe1051e55fde5af636c1eac8e9', 'admin', 'MAC', 'ROLE_ADMIN', 'Admin', '', '2014-09-25 03:00:00', '', NULL, '127.0.0.1');


--
-- TOC entry 2312 (class 0 OID 32908)
-- Dependencies: 177
-- Data for Name: playerachievement; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2315 (class 0 OID 41135)
-- Dependencies: 180
-- Data for Name: playerlog; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2328 (class 0 OID 0)
-- Dependencies: 181
-- Name: playerlog_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('playerlog_seq', 22, true);


--
-- TOC entry 2309 (class 0 OID 17346)
-- Dependencies: 173
-- Data for Name: playerscore; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2174 (class 2606 OID 32902)
-- Name: achievement_achievementcode_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY achievement
    ADD CONSTRAINT achievement_achievementcode_key UNIQUE (achievementcode);


--
-- TOC entry 2179 (class 2606 OID 32895)
-- Name: achievement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY achievement
    ADD CONSTRAINT achievement_pkey PRIMARY KEY (achievementid);


--
-- TOC entry 2184 (class 2606 OID 41126)
-- Name: blockedips_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY blockedips
    ADD CONSTRAINT blockedips_pkey PRIMARY KEY (id);


--
-- TOC entry 2152 (class 2606 OID 17329)
-- Name: game_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY game
    ADD CONSTRAINT game_pkey PRIMARY KEY (gameid);


--
-- TOC entry 2158 (class 2606 OID 17337)
-- Name: leaderboard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY leaderboard
    ADD CONSTRAINT leaderboard_pkey PRIMARY KEY (leaderboardid);


--
-- TOC entry 2160 (class 2606 OID 17345)
-- Name: player_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY player
    ADD CONSTRAINT player_pkey PRIMARY KEY (playerid);


--
-- TOC entry 2181 (class 2606 OID 32912)
-- Name: playerachievement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY playerachievement
    ADD CONSTRAINT playerachievement_pkey PRIMARY KEY (playerid, achievementid);


--
-- TOC entry 2189 (class 2606 OID 41142)
-- Name: playerlog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY playerlog
    ADD CONSTRAINT playerlog_pkey PRIMARY KEY (logid);


--
-- TOC entry 2169 (class 2606 OID 17350)
-- Name: playerscore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY playerscore
    ADD CONSTRAINT playerscore_pkey PRIMARY KEY (leaderboardid, playerid);


--
-- TOC entry 2154 (class 2606 OID 17352)
-- Name: uk_5s2cfxlan6j4ww1l6aq0em38q; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY game
    ADD CONSTRAINT uk_5s2cfxlan6j4ww1l6aq0em38q UNIQUE (gamename);


--
-- TOC entry 2167 (class 2606 OID 17354)
-- Name: uk_r1rodtev2moie5eeqrf2rbedm; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY player
    ADD CONSTRAINT uk_r1rodtev2moie5eeqrf2rbedm UNIQUE (playerusername);


--
-- TOC entry 2175 (class 1259 OID 32904)
-- Name: achievement_achievementcreationdate_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX achievement_achievementcreationdate_idx ON achievement USING btree (achievementcreationdate);


--
-- TOC entry 2176 (class 1259 OID 32903)
-- Name: achievement_achievementname_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX achievement_achievementname_idx ON achievement USING btree (achievementname);


--
-- TOC entry 2177 (class 1259 OID 41152)
-- Name: achievement_gameid_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX achievement_gameid_idx ON achievement USING btree (gameid);


--
-- TOC entry 2182 (class 1259 OID 41127)
-- Name: blockedips_ip_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX blockedips_ip_idx ON blockedips USING btree (ip);


--
-- TOC entry 2150 (class 1259 OID 32828)
-- Name: game_gamecreationdate_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX game_gamecreationdate_idx ON game USING btree (gamecreationdate);


--
-- TOC entry 2155 (class 1259 OID 41153)
-- Name: leaderboard_gameid_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX leaderboard_gameid_idx ON leaderboard USING btree (gameid);


--
-- TOC entry 2156 (class 1259 OID 32841)
-- Name: leaderboard_leaderboardcreationdate_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX leaderboard_leaderboardcreationdate_idx ON leaderboard USING btree (leaderboardcreationdate);


--
-- TOC entry 2161 (class 1259 OID 32885)
-- Name: player_playerrole_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX player_playerrole_idx ON player USING btree (playerrole);


--
-- TOC entry 2162 (class 1259 OID 24581)
-- Name: playerblockidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerblockidx ON player USING btree (playerenabled);


--
-- TOC entry 2163 (class 1259 OID 24580)
-- Name: playerdateidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerdateidx ON player USING btree (datetimeofcreation);


--
-- TOC entry 2185 (class 1259 OID 41150)
-- Name: playerlog_action_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerlog_action_idx ON playerlog USING btree (action);


--
-- TOC entry 2186 (class 1259 OID 41151)
-- Name: playerlog_actiondate_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerlog_actiondate_idx ON playerlog USING btree (actiondate);


--
-- TOC entry 2187 (class 1259 OID 41149)
-- Name: playerlog_actiontype_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerlog_actiontype_idx ON playerlog USING btree (actiontype);


--
-- TOC entry 2190 (class 1259 OID 41148)
-- Name: playerlog_playerid_idx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerlog_playerid_idx ON playerlog USING btree (playerid);


--
-- TOC entry 2164 (class 1259 OID 24583)
-- Name: playerpasswdidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerpasswdidx ON player USING btree (playerpassword);


--
-- TOC entry 2170 (class 1259 OID 24587)
-- Name: playerscoreidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerscoreidx ON playerscore USING btree (score);


--
-- TOC entry 2165 (class 1259 OID 24584)
-- Name: playerusernameidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX playerusernameidx ON player USING btree (playerusername);


--
-- TOC entry 2171 (class 1259 OID 24586)
-- Name: psplayerfkidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX psplayerfkidx ON playerscore USING btree (playerid);


--
-- TOC entry 2172 (class 1259 OID 24585)
-- Name: pspleaderbdfkidx; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX pspleaderbdfkidx ON playerscore USING btree (leaderboardid);


--
-- TOC entry 2194 (class 2606 OID 32896)
-- Name: achievement_gameid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY achievement
    ADD CONSTRAINT achievement_gameid_fkey FOREIGN KEY (gameid) REFERENCES game(gameid) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2191 (class 2606 OID 17355)
-- Name: fk_7m9lm1jhermnpnfslmspbo58u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY leaderboard
    ADD CONSTRAINT fk_7m9lm1jhermnpnfslmspbo58u FOREIGN KEY (gameid) REFERENCES game(gameid);


--
-- TOC entry 2195 (class 2606 OID 41156)
-- Name: playerachievement_achievementid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY playerachievement
    ADD CONSTRAINT playerachievement_achievementid_fkey FOREIGN KEY (achievementid) REFERENCES achievement(achievementid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2196 (class 2606 OID 41161)
-- Name: playerachievement_playerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY playerachievement
    ADD CONSTRAINT playerachievement_playerid_fkey FOREIGN KEY (playerid) REFERENCES player(playerid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2197 (class 2606 OID 41143)
-- Name: playerlog_playerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY playerlog
    ADD CONSTRAINT playerlog_playerid_fkey FOREIGN KEY (playerid) REFERENCES player(playerid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2193 (class 2606 OID 24631)
-- Name: playerscore_leaderboardid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY playerscore
    ADD CONSTRAINT playerscore_leaderboardid_fkey FOREIGN KEY (leaderboardid) REFERENCES leaderboard(leaderboardid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2192 (class 2606 OID 24626)
-- Name: playerscore_playerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY playerscore
    ADD CONSTRAINT playerscore_playerid_fkey FOREIGN KEY (playerid) REFERENCES player(playerid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2323 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner:
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-10-09 15:20:37 EEST

--
-- PostgreSQL database dump complete
--


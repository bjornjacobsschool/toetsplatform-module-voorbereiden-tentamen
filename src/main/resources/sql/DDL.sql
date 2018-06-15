/*CREATE TABLE IF NOT EXISTS versie
(
  id           INTEGER PRIMARY KEY AUTOINCREMENT,
  datum        INT(8),
  nummer       INT,
  omschrijving VARCHAR(255)
);*/

CREATE TABLE IF NOT EXISTS voorbereiden_tentamen
(
  id                       VARCHAR(100),
  naam                     VARCHAR(255),
  beschrijving             VARCHAR(1024),
  toegestaandeHulpmiddelen VARCHAR(1024),
  vak                      VARCHAR(255),
  versie_id                INT
  tijdsduur                INT,

  versie_datum        INT(8),
  versie_nummer       INT,
  versie_omschrijving VARCHAR(255),

  primary key (id, versie_nummer)
);

CREATE TABLE IF NOT EXISTS voorbereiden_klaargezetten_tentamen(
  tentamen_id VARCHAR (100),
  tentamen_versie INT,
  start_datum INT(8),
  eind_datum INT(8),
  sleutel VARCHAR (100),

  PRIMARY KEY (tentamen_id, tentamen_versie)
);

CREATE TABLE IF NOT EXISTS voorbereiden_vraag
(
  id                VARCHAR(100),
  naam              VARCHAR(255),
  vraag_type        VARCHAR(255),
  thema             VARCHAR(255),
  punten            INT,
  versie_id         INT,
  nakijkInstrucites VARCHAR(1024),
  vraag_data        VARCHAR(10000),
  nakijk_model      VARCHAR(10000),


  versie_datum        INT(8),
  versie_nummer       INT,
  versie_omschrijving VARCHAR(255),

  primary key (id, versie_nummer)
);

cREATE TABLE IF NOT EXISTS voorbereiden_vraag_van_tentamen
(
  vraag_id        VARCHAR (100),
  vraag_versie_nummer    INT,
  tentamen_id     VARCHAR (100),
  tentamen_versie_nummer INT,
  PRIMARY KEY (vraag_id, vraag_versie_nummer, tentamen_id, tentamen_versie_nummer),
  FOREIGN KEY (vraag_id, vraag_versie_nummer) REFERENCES vraag (id, versie_nummer),
  FOREIGN KEY (tentamen_id, tentamen_versie_nummer) REFERENCES tentamen (id, versie_nummer)
);


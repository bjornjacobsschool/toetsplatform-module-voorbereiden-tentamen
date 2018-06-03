CREATE TABLE IF NOT EXISTS versie
(
  id           INT,
  datum        VARCHAR(255),
  number       VARCHAR(255),
  omschrijving VARCHAR(255),

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tentamen
(
  id                       INT,
  naam                     VARCHAR(255),
  beschrijving             VARCHAR(1024),
  toegestaandeHulpmiddelen VARCHAR(1024),
  vak                      VARCHAR(255),
  versie_id                INT
  tijdsduur                INT,
  primary key (id, versie_id),
  FOREIGN KEY (versie_id) REFERENCES versie (id)
);

CREATE TABLE IF NOT EXISTS vraag
(
  id                INT,
  naam              VARCHAR(255),
  vraag_type        VARCHAR(255),
  thema             VARCHAR(255),
  punten            INT,
  versie_id         INT,
  nakijkInstrucites VARCHAR(1024),
  vraag_data        VARCHAR(10000),
  nakijk_model      VARCHAR(10000),
  primary key (id, versie_id),
  FOREIGN KEY (versie_id) REFERENCES versie (id)
);

cREATE TABLE IF NOT EXISTS vraag_van_tentamen
(
  vraag_id        INT,
  vraag_versie    INT,
  tentamen_id     INT,
  tentamen_versie INT,
  PRIMARY KEY (vraag_id, vraag_versie, tentamen_id, tentamen_versie),
  FOREIGN KEY (vraag_id, vraag_versie) REFERENCES vraag,
  FOREIGN KEY (tentamen_id, tentamen_versie) REFERENCES tentamen
);


DROP SCHEMA IF EXISTS tp_vol;
	
CREATE SCHEMA tp_vol COLLATE = utf8_bin;

USE tp_vol;

/*==============================================================*/
/* Table : ADDRESS                                               */
/*==============================================================*/
create table ADRESSE
(
   ID                   integer not null auto_increment,
   RUE	                varchar(255),
   COMPLEMENT           varchar(255),
   CODE_POSTAL          varchar(10),
   VILLE                varchar(100),
   PAYS                 varchar(100),
   primary key (ID)
);

create table AEROPORT
(
   CODE                 varchar(20) not null,
   NOM	                varchar(255),
   primary key (CODE)
);

create table VILLE
(
   ID                   integer not null auto_increment,
   NOM	                varchar(255),
   primary key (ID)
);

create table AEROPORT_VILLE
(
   AEROPORT_CODE        varchar(20) not null,
   VILLE_ID             integer not null,
   primary key (AEROPORT_CODE, VILLE_ID),
   foreign key (AEROPORT_CODE) references AEROPORT(CODE),
   foreign key (VILLE_ID) references VILLE(ID)
);

create table VOL
(
   ID                   integer not null auto_increment,
   DT_DEPART            datetime,
   DT_ARRIVEE           datetime,
   STATUT_VOL			varchar(20),
   DEPART_CODE			varchar(20),
   ARRIVEE_CODE		    varchar(20),
   NB_PLACE_DISPO       integer,
   primary key (ID),
   foreign key (DEPART_CODE) references AEROPORT(CODE),
   foreign key (ARRIVEE_CODE) references AEROPORT(CODE)
);

create table COMPAGNIE_AERIENNE
(
   CODE                 varchar(30) not null,
   NOM	                varchar(255),
   primary key (CODE)
);

create table COMPAGNIE_AERIENNE_VOL
(
   ID                   	integer not null auto_increment,
   NUMERO_VOL	        	varchar(20) not null,
   COMPAGNIE_AERIENNE_CODE  varchar(30) not null,
   VOL_ID		            integer not null,
   primary key (ID),
   foreign key (COMPAGNIE_AERIENNE_CODE) references COMPAGNIE_AERIENNE(CODE),
   foreign key (VOL_ID) references VOL(ID)
);

create table PASSAGER
(
   ID                   	integer not null auto_increment,
   NOM			        	varchar(100),
   PRENOM  		        	varchar(100),
   NUMERO_IDENTITE        	varchar(30),
   TYPE_IDENTITE        	varchar(20),
   primary key (ID)
);

create table CLIENT
(
   ID                   integer not null auto_increment,
   TYPE                 varchar(1) not null,
   NOM		            varchar(255),
   PRENOM               varchar(255),
   SIRET                varchar(30),
   NUMERO_TVA           varchar(30),
   STATUT_JURIDIQUE     varchar(10),
   ADRESSE_ID           integer,
   primary key (ID),
   foreign key (ADRESSE_ID) references ADRESSE(ID)
);

create table RESERVATION
(
   NUMERO                  	integer not null,
   DT_RESERVATION           date,
   STATUT_RESERVATION       varchar(20),
   PASSAGER_ID				integer,
   CLIENT_ID				integer,
   primary key (NUMERO),
   foreign key (PASSAGER_ID) references PASSAGER(ID),
   foreign key (CLIENT_ID) references CLIENT(ID)
);

create table BILLET
(
   ID                   integer not null auto_increment,
   NUMERO_PLACE         varchar(4),
   CLASSE	            varchar(5),
   PRIX		            float,
   ORDRE                integer
   RESERVATION_NUMERO   integer not null,
   VOL_ID           	integer not null,
   primary key (ID),
   foreign key (RESERVATION_NUMERO) references RESERVATION(NUMERO),
   foreign key (VOL_ID) references VOL(ID)
);


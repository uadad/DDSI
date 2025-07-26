-- Descomentar para Oracle
-- DROP TABLE SOCIO CASCADE CONSTRAINT;

-- MySQL/MariaDB no tiene implementada la opción "CASCADE CONSTRAINT"
-- en la sentencia "DROP TABLE". Para estos SGBD, antes de borrar tablas,
-- se deben eliminar todas restricciones de integridad referencial que existan
-- entre ellas

create table SOCIO (
    numeroSocio char(4),
    nombre varchar(300) not null,
    dni varchar(9) not null,
    fechaNacimiento varchar(10),
    telefono varchar(9),
    correo varchar(50),
    fechaEntrada varchar(10) not null,
    categoria char(1) not null,
    CONSTRAINT CP_Socio PRIMARY KEY (numeroSocio),
    CONSTRAINT dniSocioUnico UNIQUE (dni),
    CONSTRAINT R_Categoria check (categoria in ('A','B','C','D','E')));

-- Descomentar para Oracle
-- DROP TABLE MONITOR CASCADE CONSTRAINT;

create table MONITOR (
    codMonitor char(4),
    nombre varchar(300) not null,
    dni varchar(9) not null,
    telefono varchar(9),
    correo varchar(50),
    fechaEntrada varchar(10) not null,
    nick varchar(6),
    CONSTRAINT CP_Monitor PRIMARY KEY (codMonitor),
    CONSTRAINT dniMonitorUnico UNIQUE (dni));


-- Descomentar para Oracle
-- DROP TABLE ACTIVIDAD CASCADE CONSTRAINT;

create table ACTIVIDAD (
    idActividad char(4),
    nombre varchar(100) not null,
    descripcion varchar(500),
    precioBaseMes int not null,
    monitorResponsable char(4),
    CONSTRAINT CP_Actividad PRIMARY KEY (idActividad),
    CONSTRAINT nombreActividadUnico UNIQUE (nombre),
    CONSTRAINT CAj_ActividadMonitor FOREIGN KEY (monitorResponsable)
        REFERENCES MONITOR(codMonitor) ON DELETE SET NULL);

-- Descomentar para Oracle
-- DROP TABLE REALIZA CASCADE CONSTRAINT;

create table REALIZA (
    numeroSocio char(4),
    idActividad char(4),
    CONSTRAINT CP_Realiza PRIMARY KEY (numeroSocio, idActividad),
    CONSTRAINT CAj_RealizaSocio FOREIGN KEY (numeroSocio) REFERENCES SOCIO(numeroSocio) ON DELETE CASCADE,
    CONSTRAINT CAj_RealizaActividad FOREIGN KEY (idActividad) REFERENCES ACTIVIDAD(idActividad) ON DELETE CASCADE);

INSERT INTO SOCIO VALUES ('S001','Iria Mosquera Gil','54941721B','31/03/1977','656391774','iria_89@post.com','04/06/2016','A');
INSERT INTO SOCIO VALUES ('S002','Jonathan Saez Gracia','46288486C','16/11/1953','782479970','jonathan_03@yahoo.com','24/08/2015','A');
INSERT INTO SOCIO VALUES ('S003','María Fernanda Arce Peralta','65298503P','18/04/1994','783908961','mariafernanda_07@journalism.com','22/10/2015','B');
INSERT INTO SOCIO VALUES ('S004','Alexis Catalán Frías','56612261S','19/07/1996','603232342','alexis_29@writeme.com','27/02/2004','B');
INSERT INTO SOCIO VALUES ('S005','Adolfo Franco Galindo','92325966X','04/03/2002','707592289','adolfo_33@techie.com','17/04/2004','C');
INSERT INTO SOCIO VALUES ('S006','José María Garzón Miranda','78504430W','11/08/1976','617707844','josemaria_79@unforgettable.com','21/07/2017','C');
INSERT INTO SOCIO VALUES ('S007','Kevin Camacho Guzmán','00174037L','06/10/1945','700702096','kevin_88@unforgettable.com','01/04/2020','D');
INSERT INTO SOCIO VALUES ('S008','Rosa Álvarez Crespo','78159605Q','08/06/2001','689981039','rosa_13@aim.com','06/06/2020','D');
INSERT INTO SOCIO VALUES ('S009','Virginia de la Fuente Campos','92248499F','15/06/2003','600094259','virginia_89@scientist.com','11/03/2011','E');
INSERT INTO SOCIO VALUES ('S010','Juan Pedro Mesa Guzmán','68401554Z','07/06/1983','775855384','juanpedro_25@lycos.co.uk','21/10/2018','E');

INSERT INTO MONITOR VALUES ('M001','Samuel Sola Vidal','59354777B','663882935','samuel_53@lycos.es','20/08/2010','Robby');
INSERT INTO MONITOR VALUES ('M002','Oscar Caro Salcedo','65745956L','777150614','oscar_13@hotmail.co.uk','09/05/2001','Chato');
INSERT INTO MONITOR VALUES ('M003','Mercedes Varela Torres','78265588S','745506998','mercedes_30@libero.it','05/07/2003','Nelsa');
INSERT INTO MONITOR VALUES ('M004','Arnau Marrero Castellano','91706729W','746720525','arnau_39@teacher.com','20/11/2000','Manny');
INSERT INTO MONITOR VALUES ('M005','Jacobo Varela Sola','40079584Z','653233008','jacobo_79@lycos.es','04/07/2019','Waldo');
INSERT INTO MONITOR VALUES ('M006','Francisco Antonio Camacho Benito','70784291C','796449086','franciscoantonio_88@email.com','21/07/2016','Yanko');
INSERT INTO MONITOR VALUES ('M007','Florentina Cobos Collado','17415823Q','676252092','florentina_47@msn.com','18/03/2019','Fanny');
INSERT INTO MONITOR VALUES ('M008','Marco Antonio Cruz León','26130141W','668570734','marcoantonio_24@teacher.com','21/06/2000','Fito');
INSERT INTO MONITOR VALUES ('M009','Elisabet Solís Ortiz','30453132M','773861386','elisabet_47@caramail.com','26/02/2005','Ensy');
INSERT INTO MONITOR VALUES ('M010','Asunción Alarcón Bartolomé','76855698W','783386243','asuncion_57@unforgettable.com','05/07/2015','Sasha');

insert into ACTIVIDAD values ('AC01','Body Combat','Ejercicio cardiovascular con música para liberar tensiones',25,'M003');
insert into ACTIVIDAD values ('AC02','Body Pump','Tonificación muscular con barra',30,'M008');
insert into ACTIVIDAD values ('AC03','HIIT','Alta intensidad en intervalos',40,'M008');
insert into ACTIVIDAD values ('AC04','Kick Boxing','Mezcla de karate japonés y boxeo occidental',25,'M001');
insert into ACTIVIDAD values ('AC05','Boxeo','Deporte de contacto de lucha con guantes',25,'M004');
insert into ACTIVIDAD values ('AC06','Zumba','Baile con rutinas aeróbicas',20,'M010');
insert into ACTIVIDAD values ('AC07','Pilates','Para estirar, fortalecer y equilibrar el cuerpo',25,'M002');
insert into ACTIVIDAD values ('AC08','Yoga','Equilibrio del cuerpo y mente.',30,'M004');
insert into ACTIVIDAD values ('AC09','Tai Chí','Arte marcial suave',50,'M002');
insert into ACTIVIDAD values ('AC10','Spinning','Ejercicio aeróbico de gran intensidad',20,'M008');

insert into REALIZA values ('S002','AC01');
insert into REALIZA values ('S003','AC01');
insert into REALIZA values ('S004','AC01');
insert into REALIZA values ('S006','AC01');
insert into REALIZA values ('S007','AC01');
insert into REALIZA values ('S009','AC01');

insert into REALIZA values ('S001','AC02');
insert into REALIZA values ('S003','AC02');
insert into REALIZA values ('S005','AC02');
insert into REALIZA values ('S007','AC02');
insert into REALIZA values ('S009','AC02');

insert into REALIZA values ('S002','AC03');
insert into REALIZA values ('S006','AC03');

insert into REALIZA values ('S010','AC04');
insert into REALIZA values ('S009','AC04');
insert into REALIZA values ('S008','AC04');
insert into REALIZA values ('S007','AC04');

insert into REALIZA values ('S010','AC05');
insert into REALIZA values ('S008','AC05');
insert into REALIZA values ('S006','AC05');
insert into REALIZA values ('S004','AC05');
insert into REALIZA values ('S002','AC05');

insert into REALIZA values ('S001','AC06');
insert into REALIZA values ('S002','AC06');
insert into REALIZA values ('S005','AC06');
insert into REALIZA values ('S007','AC06');
insert into REALIZA values ('S008','AC06');

insert into REALIZA values ('S008','AC07');
insert into REALIZA values ('S007','AC07');
insert into REALIZA values ('S002','AC07');
insert into REALIZA values ('S003','AC07');
insert into REALIZA values ('S004','AC07');

insert into REALIZA values ('S001','AC08');
insert into REALIZA values ('S003','AC08');
insert into REALIZA values ('S005','AC08');

insert into REALIZA values ('S010','AC09');
insert into REALIZA values ('S007','AC09');
insert into REALIZA values ('S002','AC09');
insert into REALIZA values ('S003','AC09');

insert into REALIZA values ('S002','AC10');
insert into REALIZA values ('S003','AC10');
insert into REALIZA values ('S005','AC10');
insert into REALIZA values ('S006','AC10');
insert into REALIZA values ('S008','AC10');
insert into REALIZA values ('S010','AC10');


CREATE DATABASE IF NOT EXISTS listadetareas;

CREATE TABLE IF NOT EXISTS usuario (
   id bigint NOT NULL AUTO_INCREMENT,
   email varchar(255) unique DEFAULT NULL,
   nombre varchar(255) DEFAULT NULL,
   password varchar(255) DEFAULT NULL,
   rol varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
 );
 
CREATE TABLE IF NOT EXISTS tarea (
   id bigint NOT NULL AUTO_INCREMENT,
   cancelada bit(1) NOT NULL,
   completada bit(1) NOT NULL,
   descripcion varchar(255) DEFAULT NULL,
   fecha_cancelacion date DEFAULT NULL,
   fecha_creacion date DEFAULT NULL,
   fecha_finalizacion date DEFAULT NULL,
   fecha_objetivo date DEFAULT NULL,
   nombre varchar(255) DEFAULT NULL,
   id_usuario bigint DEFAULT NULL,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (id_usuario) REFERENCES usuario (id)
 );
 
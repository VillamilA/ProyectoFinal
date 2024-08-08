create database Futbolreserva;
use Futbolreserva;

create table USUARIO(
cedula int primary key,
usuario varchar(50),
contrasena varchar(50)
);

select * from USUARIO;
INSERT INTO `futbolreserva`.`USUARIO` (`cedula`, `usuario`, `contrasena`) VALUES ('1728021468', 'angel', '123');
create table reserva (
    id int auto_increment primary key,
    cedula varchar(10) not null,
    fecha date not null,
    hora time not null,
    cancha varchar(50) not null
);
SELECT * from reserva;

create table ADMIN(
id int primary key ,
usuario varchar(50) not null,
contrasena varchar(50) not null
);
INSERT INTO `futbolreserva`.`admin` (`id`, `usuario`, `contrasena`) VALUES ('1306639517', 'paula', 'pau123');
select * from admin;

CREATE TABLE canchas (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(100)
);

INSERT INTO canchas (id, nombre, ubicacion) VALUES 
(1,'F1', 'Norte'), (2,'F2', 'Sur'), (3,'F3', 'Este'), (4,'F4', 'Oeste')

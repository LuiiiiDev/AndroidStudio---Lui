drop table Usuario cascade constraints;
drop table Ticket cascade constraints;

create table Usuario(
    uuidUsuario varchar2(50) primary key,
    usuario varchar2(70),
    contrasena varchar2(8)
);

create table Ticket(
    uuidTicket varchar2(50) primary key,
    titulo varchar2(100),
    descripcion varchar2(250),
    autor varchar2(30),
    email varchar2(75),
    estado varchar2(80)
);


select * from Usuario; 

select * from Ticket;

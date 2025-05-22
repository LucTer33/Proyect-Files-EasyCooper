DROP DATABASE IF EXISTS DB_EasyCooper;
CREATE SCHEMA DB_EasyCooper;
USE DDBB_EasyCooper;
/*Inicio Region UserEC*/
DROP TABLE IF EXISTS UserEC;
CREATE TABLE UserEC(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name_user VARCHAR(60),
    email VARCHAR(60),
    dni VARCHAR(20),
    password_user VARCHAR(60),
    type_user VARCHAR(60)    
);

INSERT INTO UserEC VALUES(NULL,"Juan","juanlopez@gmail.com","13652582H","juanlo","Client");
INSERT INTO UserEC VALUES(NULL,"Robert","robertogomez@gmail.com","32652582W","robertgo","Client");
INSERT INTO UserEC VALUES(NULL,"Ruben","rubensoliz@gmail.com","13652662A","rubenso","Worker");
INSERT INTO UserEC VALUES(NULL,"Santiago","santiagoVelez@gmail.com","11222582T","santiagove","Worker");


/*Fin Region UserEC*/

/*Inicio Region Reservations*/
DROP TABLE IF EXISTS Reservations;
CREATE TABLE Reservations(
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_user INT ,
    id_vehicle INT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_reservation DATE, 
    initHour TIME,
    finalHour TIME, 
    state VARCHAR(20) NOT NULL CHECK (state IN('EN_PROCESO','CANCELED','COMPLETED')),   
    FOREIGN KEY (id_user) REFERENCES UserEC(id),
    FOREIGN KEY	(id_vehicle) REFERENCES Vehicle(id) 
);

INSERT INTO Reservations (id_user,id_vehicle,date_reservation,initHour,finalHour,state) VALUES(1,1,'2025-04-05','10:00','14:00','EN_PROCESO');
INSERT INTO Reservations (id_user,id_vehicle,date_reservation,initHour,finalHour,state) VALUES(2,1,'2025-04-05','14:00','16:00','CANCELED');
INSERT INTO Reservations (id_user,id_vehicle,date_reservation,initHour,finalHour,state) VALUES(4,1,'2025-04-06','10:00','14:00','EN_PROCESO');
INSERT INTO Reservations (id_user,id_vehicle,date_reservation,initHour,finalHour,state) VALUES(3,2,'2025-04-07','9:00','14:00','EN_PROCESO');
INSERT INTO Reservations (id_user,id_vehicle,date_reservation,initHour,finalHour,state) VALUES(3,4,'2025-04-08','8:00','13:00','EN_PROCESO');
INSERT INTO Reservations (id_user,id_vehicle,date_reservation,initHour,finalHour,state) VALUES(3,3,'2025-04-08','10:00','14:00','COMPLETED');


/*Fin Region Reservations*/
DROP TABLE IF EXISTS Vehicle;
CREATE TABLE Vehicle(
	id INT AUTO_INCREMENT PRIMARY KEY,    
    type_vehicle VARCHAR(60),
    model VARCHAR(60),
    details VARCHAR(60),
    max_speed VARCHAR(20)
);

INSERT INTO Vehicle VALUES(NULL,"E-Bike","VanMoof S5","Estilo urbano con diseño minimalista","25 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Bike","Specialized Turbo Levo","Bicicleta híbrida de alto rendimiento.","45 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Bike","Trek Powerfly","Bicicleta eléctrica de montaña","63 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Bike","Rad Power Bikes RadCity","Para uso diario en la ciudad","50 km/h");

INSERT INTO Vehicle VALUES(NULL,"E-Scooters","Xiaomi Mi Electric Scooter 4 Pro","Ligero y con buena autonomía","25 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Scooters","Segway Ninebot MAX G2","Más potencia y suspensión mejorada","30 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Scooters","NIU KQi3 Pro","Scooter eléctrico de alto rendimiento","32 km/h");

INSERT INTO Vehicle VALUES(NULL,"E-Bike Plegable","Brompton Electric","Ligera y con diseño plegable icónico","25 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Bike Plegable","Fiido D3 Pro","Compacta para cualquier entorno","25 km/h");
INSERT INTO Vehicle VALUES(NULL,"E-Bike Plegable","Tern Vektron S10"," Más robusta y con gran autonomía","32 km/h");


CREATE TABLE poligonos (
 id bigint(20) NOT NULL AUTO_INCREMENT,
 coordenadax decimal(10) NOT NULL,
 coordenaday decimal(10) NOT NULL,
 nome_poligono varchar(250) NOT NULL,
 ordem_do_ponto int NOT NULL,
PRIMARY KEY (id)
);
CREATE TABLE detalhamento_poligonos (
 id bigint(20) NOT NULL AUTO_INCREMENT,
 nome_poligono varchar(250) NOT NULL,
 numero_lados int NOT NULL,
 perimetro DOUBLE NOT NULL,
 area DOUBLE NOT NULL,
 numero_diagonais int NOT NULL,
 soma_angulos_internos DOUBLE NOT NULL,
 nome_do_arquivo varchar(250) NOT NULL,
PRIMARY KEY (id)
);
CREATE TABLE `rates` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `fromCurrency` char(3) DEFAULT NULL,
                         `date` varchar(10) DEFAULT NULL,
                         `rate` decimal(25,6) DEFAULT NULL,
                         `toCurrency` char(3) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;


-- reto_db.`user` definition

CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(100) DEFAULT NULL,
                        `pwd` varchar(100) DEFAULT NULL,
                        `numdoc` int(11) DEFAULT NULL,
                        `typedoc` char(2) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;



INSERT INTO reto_db.rates (id,fromCurrency,`date`,rate,toCurrency) VALUES
(10,'USD',CURRENT_DATE,3.748825,'PEN'),
(2,'USD',CURRENT_DATE,0.914837,'EUR'),
(3,'PEN',CURRENT_DATE,0.266799,'USD'),
(4,'PEN',CURRENT_DATE,0.244012,'EUR'),
(5,'EUR',CURRENT_DATE,4.098170,'PEN'),
(6,'EUR',CURRENT_DATE,1.093430,'USD'),
(7,'PEN',CURRENT_DATE + interval 1 day,0.266799,'USD'),
(8,'PEN',CURRENT_DATE + interval 1 day,0.244012,'EUR'),
(9,'USD',CURRENT_DATE + interval 1 day,114.819000,'JPY');


INSERT INTO reto_db.`user` (id,username,pwd,numdoc,typedoc) VALUES
(1,'egonzalesd','1234567',40000001,'01');
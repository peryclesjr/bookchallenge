INSERT INTO spring_db.room_type (id,name,occupants) VALUES
	 (1,'Junior',1),
	 (2,'Senior',3),
	 (3,'Executive',2),
	 (4,'Family',5),
	 (5,'Presidential',8);
INSERT INTO spring_db.room_type_bed (room_type_id,bed_type,quantity) VALUES
	 (1,'SINGLE',1),
	 (2,'QUEEN',1),
	 (2,'SINGLE',1),
	 (3,'KING',1),
	 (4,'QUEEN',1),
	 (4,'TWIN',1),
	 (4,'SINGLE',1),
	 (5,'KING',1),
	 (5,'QUEEN',1),
	 (5,'TWIN',2);

INSERT INTO spring_db.room (room_type_id,room_view,status) VALUES
	 (1,'Sea View',0),
	 (1,'Sea View',0),
	 (1,'Sea View',0),
	 (1,'Sea View',0),
	 (1,'Pool View',0),
	 (1,'Pool View',0),
	 (1,'Pool View',0),
	 (1,'Pool View',0),
	 (2,'Sea View',0),
	 (2,'Sea View',0);
INSERT INTO spring_db.room (room_type_id,room_view,status) VALUES
	 (2,'Sea View',0),
	 (2,'Sea View',1),
	 (2,'Pool View',0),
	 (3,'Sea View',0),
	 (3,'Sea View',0),
	 (3,'Sea View',0),
	 (4,'Pool View',0),
	 (4,'Pool View',0),
	 (4,'Pool View',0),
	 (5,'Sea View',0);
INSERT INTO spring_db.room (room_type_id,room_view,status) VALUES
	 (5,'Sea View',0);

INSERT INTO spring_db.reservation (status,guest_name,guest_email,guest_phone,created_at,updated_at) VALUES
	 (1,'Perycles Jr','perycles@gmail.com','5561981554958','2022-07-08 04:06:55.780012','2022-07-09 06:06:55.690907'),
	 (1,'Karinne Muniz','karinne.muniz@gmail.com','5561981184822','2022-07-10 07:54:39.891364','2021-07-12 10:30:55.160897');
INSERT INTO spring_db.reservation_room (room_id,reservation_id,stay_from,stay_to,status,created_at,updated_at) VALUES
	 (12,2,'2022-07-08 00:00:00','2022-07-09 23:59:59',1,'2022-06-29 09:34:32.677377','2022-06-29 09:34:32.956012');
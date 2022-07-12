
-- spring_db.reservation definition

CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` int DEFAULT NULL,
  `guest_name` text NOT NULL,
  `guest_email` text,
  `guest_phone` text,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- spring_db.room_type definition

CREATE TABLE IF NOT EXISTS `room_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `occupants` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- spring_db.room_type_bed definition

CREATE TABLE IF NOT EXISTS `room_type_bed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_type_id` int DEFAULT NULL,
  `bed_type` text NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- spring_db.room definition

CREATE TABLE IF NOT EXISTS `room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_type_id` int DEFAULT NULL,
  `room_view` text NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- spring_db.reservation_room definition

CREATE TABLE IF NOT EXISTS `reservation_room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int DEFAULT NULL,
  `reservation_id` int DEFAULT NULL,
  `stay_from` timestamp NULL DEFAULT NULL,
  `stay_to` timestamp NULL DEFAULT NULL,
  `status` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

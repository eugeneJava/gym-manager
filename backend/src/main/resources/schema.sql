/*-- DROP TABLE IF EXISTS Client;
CREATE TABLE IF NOT EXISTS Client (
    id VARCHAR(36) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    `name` VARCHAR(1024) NOT NULL,
    secondName VARCHAR(100) NULL,
    PRIMARY KEY(id)
);

-- CREATE UNIQUE INDEX phone_UX ON Client(phone);
-- CREATE UNIQUE INDEX name_UX ON Client(name);


-- DROP TABLE IF EXISTS Name;
CREATE TABLE IF NOT EXISTS Name (
   `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`name`)
);

DROP TABLE IF EXISTS ProductItem;
DROP TABLE IF EXISTS Product;

CREATE TABLE IF NOT EXISTS Product (
    id VARCHAR(36) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    faIcon VARCHAR(100) NOT NULL,
    orderIndex INT NULL,
    PRIMARY KEY(id)
);
CREATE UNIQUE INDEX product_name_UX ON Product(`name`);
INSERT INTO Product(id, `name`, `description`, price, active, faIcon, orderIndex) VALUES (UUID(), 'RACKET', 'Ракетка аренда', 10, true, 'table-tennis', 0);
INSERT INTO Product(id, `name`, `description`, price, active, faIcon, orderIndex) VALUES (UUID(), 'BALL', 'Мячик аренда', 5, true, 'bowling-ball', 1);
INSERT INTO Product(id, `name`, `description`, price, active, faIcon, orderIndex) VALUES (UUID(), 'WATER', 'Вода', 10, true, 'wine-bottle',2);
INSERT INTO Product(id, `name`, `description`, price, active, faIcon, orderIndex) VALUES (UUID(), 'TEA', 'Чай', 10, true, 'coffee',3);
INSERT INTO Product(id, `name`, `description`, price, active, faIcon, orderIndex) VALUES (UUID(), 'COFFEE', 'Кофе', 15, true, 'mug-hot', 4);

CREATE TABLE IF NOT EXISTS ProductItem (
    id VARCHAR(36) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    tableNumber INT NULL,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    product_id VARCHAR(36) NOT NULL,
    updateDate DATETIME NOT NULL,
    createDate DATETIME NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);

DROP TABLE IF EXISTS ProductDeleteLog;
CREATE TABLE IF NOT EXISTS ProductDeleteLog (
    id VARCHAR(36) NOT NULL,
    `productName` VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    tableNumber INT NULL,
     paid BOOLEAN NOT NULL DEFAULT FALSE,
    createDate DATETIME NOT NULL,
    PRIMARY KEY(id)
);*/
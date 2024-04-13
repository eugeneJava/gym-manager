Given an sql statements. Generate jpa entites from the table sql statements
inherit all entities from a java class
@MappedSuperclass
public class Identifiable {
   @Id
   private String id = UUID.randomUUID().toString();

public String getId() {
      return this.id;
}
}

also make trades_parcel.deliveryType, trades_product_sale_group.type as enums




CREATE TABLE IF NOT EXISTS trades_product
(
    id       VARCHAR(36)  NOT NULL,
    code     VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    comments VARCHAR(255) NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX code_UX ON trades_product (code);

CREATE TABLE IF NOT EXISTS trades_parcel
(
    id                VARCHAR(36)    NOT NULL,
    weight            DECIMAL(10, 2) NOT NULL,
    deliveryPrice     DECIMAL(10, 2) NOT NULL,
    startedDeliveryAt DATETIME       NOT NULL,
    deliveredAt       DATETIME       NULL,
    deliveryType      VARCHAR(255)   NOT NULL,
    comments          VARCHAR(255)   NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trades_parcel_group
(
    id        VARCHAR(36)    NOT NULL,
    parcel_id VARCHAR(36)     NULL,
    weight    DECIMAL(10, 2)  NULL,
    trackId   VARCHAR(255)    NULL,
    name      VARCHAR(255)   NOT NULL,
    comments  VARCHAR(255)   NOT NULL,
    FOREIGN KEY (parcel_id) REFERENCES trades_parcel (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trades_product_buy
(
    id VARCHAR(36) NOT NULL,
    parcelGroup_id VARCHAR(36) NOT NULL,
    totalBuyPriceInYuan  DECIMAL(10, 2) NOT NULL,
    totalBuyPriceInUah   DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (parcelGroup_id) REFERENCES trades_parcel_group (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trades_product_unit
(
    id VARCHAR(36) NOT NULL,
    productBuy_id VARCHAR(36) not NULL,
    productSale_id VARCHAR(36) NULL,
    product_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (productBuy_id) REFERENCES trades_product_buy (id),
    FOREIGN KEY (productSale_id) REFERENCES trades_product_sale (id),
    FOREIGN KEY (product_id) REFERENCES trades_product (id)
);

CREATE TABLE IF NOT EXISTS trades_product_sale_group
(
    id VARCHAR(36) NOT NULL,
    type    varchar(255) not null,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trades_product_sale
(
    id VARCHAR(36) NOT NULL,
    sellPrice    DECIMAL(10, 2) NOT NULL,
    productUnit_id VARCHAR(36) NOT NULL,
    productSaleGroup_id VARCHAR(36) NULL,
    soldAt       DATETIME       not null,
    PRIMARY KEY (id),
    FOREIGN KEY (productUnit_id) REFERENCES trades_product_unit (id),
    FOREIGN KEY (productSaleGroup_id) REFERENCES trades_product_sale_group (id)
);
CREATE UNIQUE INDEX code_UX ON trades_product_sale (productUnit_id);

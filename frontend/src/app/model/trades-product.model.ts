export class TradesProductDto {

    id: string;
    name: string;
    comments: string
}

export class TradesProductBuyDto {
  id: string;
  totalBuyPriceInYuan: number;
  totalBuyPriceInUah: number;
  parcelGroup: TradesParcelGroupDto;
  product: TradesProductDto;
  amount: number;
  unitPrice: number;
  weight: number;
  trackId: string;
  parcelId: string;
  name: string;
  comments: string;
  purchaseDate: string;
}

export class TradesParcelGroupDto {
  id: string;
  weight: number;
  trackId: string;
  name: string;
  comments: string;
  productName: string;
  productAmount: number;
  parcelId: string;
  allProductsSameWeight: boolean;
  purchaseDate: string;
  totalBuyPriceInYuan: number;
  totalBuyPriceInUah: number;
  productBuys: TradesProductBuyDto[];
}

export class ProductBuyStatisticsDto {
    product: TradesProductDto;
    productsBuyInParcel: ProductBuyInParcelDto[];
}

export class ProductBuyInParcelDto {
    buyPrice: number;
    parcelFormedDate: Date;
    deliveryType: string;
}

export class TradesProductUnitDto {
  product: TradesProductDto;
  id: string;
}

export class ProductsAvailableForSaleDto {
  product: TradesProductDto;
  amountToSell: number;
}

export class TradesParcelDto {
  id?: string;
  weight: number;
  deliveryPrice: number;
  startedDeliveryAt: number;
  deliveredAt?: number;
  deliveryType: string;
  comments?: string;
  name: string;
  parcelGroups: TradesParcelGroupDto[];
}

export class TradesProductSaleDto {
  id: string;
  sellPrice: number; // Assuming BigDecimal maps to number in TypeScript
  product: TradesProductDto; // Use appropriate type or DTO
  productSaleGroup: TradesParcelGroupDto; // Use appropriate type or DTO
  soldAt: number;
  amountToSale: number
  soldAmount: number;
}


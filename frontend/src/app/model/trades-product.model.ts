export class TradesProductDto {
    id: string;
    name: string;
    comments: string;
    recommendedPrice: number;
    category: TradesProductCategoryDto;
}

export class TradesProductBuyDto {
  id: string;
  totalBuyPriceInYuan: number;
  totalBuyPriceInUah: number;
  parcelGroupId: string;
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
    deliveryPrice: number;
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
  totalBuyPriceOfAllGroups: number;
}

export class TradesProductSaleDto {
  id: string;
  sellPrice: number; // Assuming BigDecimal maps to number in TypeScript
  product: TradesProductDto; // Use appropriate type or DTO
  productSaleGroup: TradesParcelGroupDto; // Use appropriate type or DTO
  soldAt: number;
  amountToSale: number;
  soldAmount: number;
  comments?: string;
  parcelGroupId: string;
}

export class TradesProductSaleTotalStatisticsDto {
  totalProfit: number;
  totalSold: number;
  productStat: ProductSaleStatDto[];
  from: string;
  to: string;
}

export class TradesProductSaleStatisticsDto {
  id: string;
  sellPrice: number; // Assuming BigDecimal maps to number in TypeScript
  buyPrice: number; // Assuming BigDecimal maps to number in TypeScript
  profitPerUnit: number; // Assuming BigDecimal maps to number in TypeScript
  totalSaleProfit: number; // Assuming BigDecimal maps to number in TypeScript
  product: TradesProductDto; // Use appropriate type or DTO
  productSaleGroup: TradesParcelGroupDto; // Use appropriate type or DTO
  soldAt: number;
  amountToSale: number;
  soldAmount: number;
  comments?: string;
  parcelGroupId: string;
}

export class ProductSaleStatDto {
  productName: string;
  soldAmount: number;
  totalProfit: number;
  avgProfitPerUnit: number;
  saleStatistics: TradesProductSaleStatisticsDto[];
}


export class TradesProductSaleGroupDto {
  id: string;
  type: SaleGroupType; // This is assuming that 'RACKET' is the only value in the SaleGroupType enum in your Java code
  productSales: TradesProductSaleDto[] = [];
}

export enum SaleGroupType {
  RACKET = 'RACKET'
}

export enum TradesProductCategory {
    BLADE = 'BLADE',
    RUBBER = 'RUBBER',
    BALL = 'BALL',
    CASE = 'CASE',
    OTHER = 'OTHER'
}

export class TradesProductCategoryDto {
    id: TradesProductCategory;
    name: string;
}

export class ProductTradeStatisticsDto {
  productName: string;
  totalBought: number;
  totalSold: number;
  totalProfit: number;
  history: ProductTradeHistoryItemDto[];
}

export enum TradeDirection {
  BUY = 'BUY',
  SELL = 'SELL'
}

export class ProductTradeHistoryItemDto {
  productName: string;
  direction: TradeDirection;
  date: string;
  amount: number;
  price: number;
}


import {IconProp} from "@fortawesome/fontawesome-svg-core";

export class ProductItem {
  id: string;
  price: number;
  tableNumber: number;
  paid: boolean;
  createDate: string;
  product: Product;
}

export class Product {
  id: string;
  name: string;
  description: string;
  price: number;
  active: boolean;
  faIcon: IconProp;
}

export class Client {
  phone: string;
  name: string;
  secondName: string;
}

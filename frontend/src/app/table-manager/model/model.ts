export class Client {
  phone: string;
  name: string;
  nick: string;
}

export interface Time {
  hours: number;
  minutes: number;
  seconds: number;
  description?: string;
}

export interface AddedTime {
  duration: Time;
  paidAmount: boolean;
  table: Table;
}

export interface ClientSession {
  elapsedTime: Time;
  startTime: string;
  endTime: string;
  duration: Time;
  status: Status;
  paid: boolean;
  remainedTime: Time;
  spentMoney: number;
  totalMoney: number;
  paidMoney: number;
  needToPay: number;
  clients: Client[];
  tableSessions: TableSession[];
}

export interface TableSession {
  id: string
  elapsedTime: Time;
  startDate: string;
  endDate: string;
  closeDate: string;
  duration: Time;
  status: Status;
  paid: boolean;
  tableNumber: number;
  paidAmount: number;
  totalPay: number;
  needToPay: number;
  rate: number;
  clients: Client[];
  clientSessionId: string;
}

export enum Status {
  RUNNING = 'RUNNING',
  PAUSED = 'PAUSED',
  FINISHED = 'FINISHED'
}

export interface Table {
  number: number;
  rate: number;
}

export interface Rate {
  rate: number;
}

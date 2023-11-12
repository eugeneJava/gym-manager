export class Client {
  phone: string;
  name: string;
  nick: string;
}

export interface Time {
  hour: number;
  minute: number;
  second: number;
  description?: string;
}

export interface AddedTime {
  duration: Time;
  paid: boolean;
  table: Table;
}

export interface ClientSession {
  elapsedTime: Time;
  startTime: Date;
  endTime: Date;
  duration: Time;
  status: Status;
  paid: boolean;
  remainedTime: Time;
  spentMoney: number;
  totalMoney: number;
  paidMoney: number;
  needToPay: number;
  clients: Client[];
  playSessions: PlaySession[];
}

export interface PlaySession {
  elapsedTime: Time;
  startTime: Date;
  endTime: Date;
  duration: Time;
  status: Status;
  paid: boolean;
  table: Table;
  rate: number;
  clients: Client[];
  clientSession: ClientSession;
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

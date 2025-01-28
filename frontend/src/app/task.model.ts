import { User } from "./user.model";
export enum Status{
  FINISHED = 'FINISHED',
  IN_PROGRESS = 'IN_PROGRESS',
  PENDING = 'PENDING',
}
export enum Importance{
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM',
  LOW = 'LOW',
}

export interface Task{
  idTask:number,
  title:string,
  description:string,
  status:Status,
  importance:Importance,
  user:User;

}

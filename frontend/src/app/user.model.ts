import { Task } from "./task.model"

export enum Role{
  USER='USER',
}
export interface User{
  userId:number,
  firstName:string,
  lastName:string,
  email:string,
  password:string,
  enabled: boolean;
  accountLocked: boolean;
  role: Role;
  tasks?: Task[];

}

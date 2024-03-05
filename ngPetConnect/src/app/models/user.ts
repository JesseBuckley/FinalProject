export class User {
  id: number;
  username: string;
  password: string;
  // enabled: boolean;

constructor(
  id: number = 0,
  username: string = '',
  password: string = '',
  // enabled: boolean = false,
  ){
  this.id = id;
  this.username = username;
  this.password = password;
  // this.enabled = enabled;


}
}

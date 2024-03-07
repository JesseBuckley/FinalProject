import { User } from "./user";

export class DirectMessage {
  id: number;
  content: string;
  user: User; // Sender
  receivingUser: User; // Receiver

  constructor() {
    this.id = 0;
    this.content = '';
    this.user = new User();
    this.receivingUser = new User();
  }
}

import { Component, OnInit } from '@angular/core';
import { DirectMessage } from '../../models/directmessage';
import { User } from '../../models/user';
import { DirectMessageService } from '../../services/direct-message.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-direct-message',
  standalone: true,
  imports: [FormsModule, CommonModule],
  providers: [DatePipe],
  templateUrl: './direct-message.component.html',
  styleUrls: ['./direct-message.component.css'],
})
export class DirectMessageComponent implements OnInit {
  directMessages: DirectMessage[] = [];
  errorMessage: string | undefined;
  currentUser: User | undefined;
  newDirectMessage: any = {};
  directMessage: DirectMessage | any;
  recipientId: number | undefined;
  receivingUser: User | any;
  users: User[] | any;

  constructor(
    private dmService: DirectMessageService,
    private auth: AuthService,
    private date: DatePipe
  ) {}

  ngOnInit(): void {
    this.loadDirectMessages();
    this.loadUsers();
    this.getLoggedInUser();
  }

  loadDirectMessages(): void {
    this.dmService.findAll().subscribe({
      next: (directMessages) => {
        this.directMessages = directMessages;
        this.errorMessage = undefined;
      },
      error: (err) => {
        console.error('Error loading direct messages:', err);
        this.errorMessage = `Error loading direct messages: ${err}`;
      },
    });
  }
  loadUsers(): void {
    this.dmService.getAllUsers().subscribe(
      (users: User[]) => {
        this.users = users;
      },
      (err: any) => {
        console.error('Error loading users:', err);
        this.users = [];
      }
    );
  }


  createDirectMessage(): void {
    if (!this.recipientId) {
      console.error('Recipient not selected');
      this.errorMessage = 'Recipient not selected';
      return;
    }

    const recipient = this.users.find(
      (user: { id: number | undefined }) => user.id === this.recipientId
    );
    if (!recipient) {
      console.error('Recipient not found');
      this.errorMessage = 'Recipient not found';
      return;
    }

    const directMessage: DirectMessage = {
      content: this.newDirectMessage.content,
      user: this.currentUser!,
      receivingUser: recipient,
      createdAt: new Date().toISOString(),
      id: 0,
    };

    this.dmService.create(directMessage, this.recipientId).subscribe({
      next: (newMessage) => {
        this.directMessages.push(newMessage);
        this.newDirectMessage.content = ''; // Clear input
        this.errorMessage = undefined; // Clear error message
      },
      error: (err) => {
        console.error('Error creating direct message:', err);
        this.errorMessage = `Error creating direct message: ${err}`;
      },
    });
  }

  updateDirectMessage(dmId: number, updatedMessage: DirectMessage): void {
    this.dmService.update(dmId, updatedMessage).subscribe({
      next: (updatedMessage) => {
        const index = this.directMessages.findIndex((dm) => dm.id === dmId);
        if (index !== -1) {
          this.directMessages[index] = updatedMessage;
        }
      },
      error: (err) => {
        console.error('Error updating direct message:', err);
        this.errorMessage = `Error updating direct message: ${err}`;
      },
    });
  }

  deleteDirectMessage(dmId: number): void {
    this.dmService.deleteById(dmId).subscribe({
      next: () => {
        this.directMessages = this.directMessages.filter(
          (dm) => dm.id !== dmId
        );
      },
      error: (err) => {
        console.error('Error deleting direct message:', err);
        this.errorMessage = `Error deleting direct message: ${err}`;
      },
    });
  }

  getLoggedInUser(): void {
    this.auth.getLoggedInUser().subscribe({
      next: (user) => {
        this.currentUser = user;
        this.errorMessage = undefined;
      },
      error: (err) => {
        console.error('Error loading current user:', err);
        this.errorMessage = `Error loading current user: ${err}`;
      },
    });
  }
}

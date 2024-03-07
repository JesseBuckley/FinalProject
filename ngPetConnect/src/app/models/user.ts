import { DirectMessage } from "./directmessage";
import { Pet } from "./pet";
import { Post } from "./post";

export class User {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  enabled: boolean;
  role: string;
  profilePicture: string;
  backgroundPicture: string;
  biography: string;
  followers: User[];
  followedUsers: User[];
  posts: Post[];
  comments: Comment[];
  messagesSent: DirectMessage[];
  receivedMessages: DirectMessage[];
  pets: Pet[];

  constructor(
    id: number = 0,
    username: string = '',
    password: string = '',
    firstName: string = '',
    lastName: string = '',
    enabled: boolean = false,
    role: string = '',
    profilePicture: string = '',
    backgroundPicture: string = '',
    biography: string = '',
    followers: User[] = [],
    followedUsers: User[] = [],
    posts: Post[] = [],
    comments: Comment[] = [],
    messagesSent: DirectMessage[] = [],
    receivedMessages: DirectMessage[] = [],
    pets: Pet[] = []
  ) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.enabled = enabled;
    this.role = role;
    this.profilePicture = profilePicture;
    this.backgroundPicture = backgroundPicture;
    this.biography = biography;
    this.followers = followers;
    this.followedUsers = followedUsers;
    this.posts = posts;
    this.comments = comments;
    this.messagesSent = messagesSent;
    this.receivedMessages = receivedMessages;
    this.pets = pets;
  }
}

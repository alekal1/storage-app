export class PersonDto {
  username: string;
  password: string;
  representativeUsername?: string;
  profileType?: string;

  constructor(
    username: string,
    password: string,
    representativeUsername?: string,
    profileType?: string
  ) {
    this.username = username;
    this.password = password;
    this.representativeUsername = representativeUsername;
    this.profileType = profileType;
  }
}

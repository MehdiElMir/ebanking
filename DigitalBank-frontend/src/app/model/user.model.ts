export interface User {
  userId:   string;
  username: string;
  password: string;
  confirmPassword : string;
  email:    string;
  roles:    Role[];
}

export interface Role {
  role: string;
}

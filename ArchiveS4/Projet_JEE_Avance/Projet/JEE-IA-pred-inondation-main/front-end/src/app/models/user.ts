import { Role } from "./role";

export interface User {
    fullName ?: string;
    email ?: string;
    password ?: string;
    role ?: Role;
}



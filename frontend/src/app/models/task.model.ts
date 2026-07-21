export interface TaskCreateRequest {
  taskName: string;
  userId: number;
  statusTypeId: string;
  dueDate: string;
}

export interface TaskUpdateRequest {
  taskName: string;
  userId: number;
  statusTypeId: string;
  dueDate: string;
}


export interface StatusType {
  statusTypeId: string;
  statusName: string;
}

export interface User {
  userId: number;
  username: string;
  email: string;
  password: string;
  birthDate: string;
}

export interface Credentials {
  email: string;
  password: string;
}

export interface TaskResponse {
  taskId: number;
  taskName: string;
  userId: number;
  statusType: StatusType;
  dueDate: string;
  createdBy: string;
  creationDate: string;
  lastUpdateDate: string;
  lastUpdatedBy: string;
  createdByFullName: string;
}

export interface LoginResponse {
  token: string
}

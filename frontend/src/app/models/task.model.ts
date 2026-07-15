export interface TaskCreateRequest {
  taskName: string;
  userId: number;
  statusTypeId: string;
  dueDate: string;
  createdBy: string;
}

export interface TaskUpdateRequest {
  taskName: string;
  userId: number;
  statusTypeId: string;
  dueDate: string;
  lastUpdatedBy: string;
}


export interface StatusType {
  statusTypeId: string;
  statusName: string;
}

export interface User {
  userId: number;
  username: string;
  isInternal: boolean;
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

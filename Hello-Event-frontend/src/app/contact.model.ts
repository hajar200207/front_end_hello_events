export interface Contact {
  id?: number;
  name: string;
  email: string;
  subject: string;
  message: string;
  submissionTime?: Date;
  status?: ContactStatus;
}

export enum ContactStatus {
  NEW = 'NEW',
  IN_PROGRESS = 'IN_PROGRESS',
  PROCESSED = 'PROCESSED',
  RESOLVED = 'RESOLVED'
}

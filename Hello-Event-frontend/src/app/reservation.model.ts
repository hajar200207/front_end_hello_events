export interface Reservation {
  id: number;
  userEmail: string;
  eventId: number | null;
  numberOfTickets: number;
  lastUpdated: string;
}

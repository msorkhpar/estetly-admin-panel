import dayjs from 'dayjs';

export interface IReview {
  id?: number;
  title?: string;
  description?: string;
  rate?: number | null;
  timestamp?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IReview> = {};

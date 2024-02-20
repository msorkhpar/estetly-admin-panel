import dayjs from 'dayjs';
import { IProcedure } from 'app/shared/model/procedure.model';

export interface IReview {
  id?: number;
  title?: string;
  description?: string | null;
  rate?: number | null;
  timestamp?: dayjs.Dayjs;
  procedure?: IProcedure | null;
}

export const defaultValue: Readonly<IReview> = {};

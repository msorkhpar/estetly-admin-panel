import { IReview } from 'app/shared/model/review.model';

export interface IProcedure {
  id?: number;
  title?: string;
  titleFr?: string;
  description?: string | null;
  descriptionFr?: string | null;
  pictureContentType?: string | null;
  picture?: string | null;
  invasiveness?: number | null;
  averageCost?: number | null;
  reviews?: IReview[] | null;
}

export const defaultValue: Readonly<IProcedure> = {};

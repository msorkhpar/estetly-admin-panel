import { ICategory } from 'app/shared/model/category.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IConcern {
  id?: number;
  title?: string;
  gender?: keyof typeof Gender;
  otherNames?: string | null;
  description?: string;
  pictureContentType?: string | null;
  picture?: string | null;
  category?: ICategory | null;
}

export const defaultValue: Readonly<IConcern> = {};

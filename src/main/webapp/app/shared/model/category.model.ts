import { IConcern } from 'app/shared/model/concern.model';
import { Models } from 'app/shared/model/enumerations/models.model';

export interface ICategory {
  id?: number;
  name?: string;
  nameFr?: string;
  model?: keyof typeof Models;
  concerns?: IConcern[] | null;
}

export const defaultValue: Readonly<ICategory> = {};

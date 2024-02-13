import { IBodyArea } from 'app/shared/model/body-area.model';
import { IConcern } from 'app/shared/model/concern.model';

export interface IBodyAreaConcernAssociation {
  id?: number;
  bodyArea?: IBodyArea;
  concern?: IConcern;
}

export const defaultValue: Readonly<IBodyAreaConcernAssociation> = {};

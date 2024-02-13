import { IProcedure } from 'app/shared/model/procedure.model';
import { IConcern } from 'app/shared/model/concern.model';

export interface IConcernProcedureAssociation {
  id?: number;
  procedure?: IProcedure;
  concern?: IConcern;
}

export const defaultValue: Readonly<IConcernProcedureAssociation> = {};

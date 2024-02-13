import { IProcedure } from 'app/shared/model/procedure.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IDoctorProcedureAssociation {
  id?: number;
  pictureContentType?: string | null;
  picture?: string | null;
  description?: string;
  cost?: number | null;
  procedure?: IProcedure;
  doctor?: IDoctor;
}

export const defaultValue: Readonly<IDoctorProcedureAssociation> = {};

export interface IProcedure {
  id?: number;
  title?: string;
  description?: string;
  pictureContentType?: string | null;
  picture?: string | null;
  inventiveness?: number | null;
  averageCost?: number | null;
}

export const defaultValue: Readonly<IProcedure> = {};
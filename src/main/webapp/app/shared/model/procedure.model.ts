export interface IProcedure {
  id?: number;
  title?: string;
  description?: string | null;
  pictureContentType?: string | null;
  picture?: string | null;
  invasiveness?: number | null;
  averageCost?: number | null;
}

export const defaultValue: Readonly<IProcedure> = {};

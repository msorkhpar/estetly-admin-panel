export interface IBodyArea {
  id?: number;
  code?: string | null;
  displayName?: string;
  displayNameFr?: string;
  parent?: IBodyArea | null;
}

export const defaultValue: Readonly<IBodyArea> = {};

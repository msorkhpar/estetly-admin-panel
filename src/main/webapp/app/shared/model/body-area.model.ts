export interface IBodyArea {
  id?: number;
  code?: string | null;
  displayName?: string;
  parent?: IBodyArea | null;
}

export const defaultValue: Readonly<IBodyArea> = {};

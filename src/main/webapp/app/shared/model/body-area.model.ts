export interface IBodyArea {
  id?: number;
  code?: string;
  displayName?: string;
  parent?: IBodyArea | null;
}

export const defaultValue: Readonly<IBodyArea> = {};

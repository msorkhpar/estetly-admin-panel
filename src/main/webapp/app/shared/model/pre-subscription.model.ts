import dayjs from 'dayjs';
import { Subscriber } from 'app/shared/model/enumerations/subscriber.model';
import { EmailStatus } from 'app/shared/model/enumerations/email-status.model';
import { SubscriberStatus } from 'app/shared/model/enumerations/subscriber-status.model';

export interface IPreSubscription {
  id?: number;
  subscriberType?: keyof typeof Subscriber;
  fullname?: string;
  email?: string;
  phoneNumber?: string;
  timestamp?: dayjs.Dayjs;
  emailStatus?: keyof typeof EmailStatus;
  subscriberStatus?: keyof typeof SubscriberStatus;
}

export const defaultValue: Readonly<IPreSubscription> = {};

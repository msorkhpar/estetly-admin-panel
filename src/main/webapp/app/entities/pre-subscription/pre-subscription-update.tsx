import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPreSubscription } from 'app/shared/model/pre-subscription.model';
import { Subscriber } from 'app/shared/model/enumerations/subscriber.model';
import { EmailStatus } from 'app/shared/model/enumerations/email-status.model';
import { SubscriberStatus } from 'app/shared/model/enumerations/subscriber-status.model';
import { getEntity, updateEntity, createEntity, reset } from './pre-subscription.reducer';

export const PreSubscriptionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const preSubscriptionEntity = useAppSelector(state => state.preSubscription.entity);
  const loading = useAppSelector(state => state.preSubscription.loading);
  const updating = useAppSelector(state => state.preSubscription.updating);
  const updateSuccess = useAppSelector(state => state.preSubscription.updateSuccess);
  const subscriberValues = Object.keys(Subscriber);
  const emailStatusValues = Object.keys(EmailStatus);
  const subscriberStatusValues = Object.keys(SubscriberStatus);

  const handleClose = () => {
    navigate('/pre-subscription' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.timestamp = convertDateTimeToServer(values.timestamp);

    const entity = {
      ...preSubscriptionEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          timestamp: displayDefaultDateTime(),
        }
      : {
          subscriberType: 'PROFESSIONAL',
          emailStatus: 'SENDING',
          subscriberStatus: 'INITIATED',
          ...preSubscriptionEntity,
          timestamp: convertDateTimeFromServer(preSubscriptionEntity.timestamp),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.preSubscription.home.createOrEditLabel" data-cy="PreSubscriptionCreateUpdateHeading">
            <Translate contentKey="estetlyApp.preSubscription.home.createOrEditLabel">Create or edit a PreSubscription</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="pre-subscription-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('estetlyApp.preSubscription.subscriberType')}
                id="pre-subscription-subscriberType"
                name="subscriberType"
                data-cy="subscriberType"
                type="select"
              >
                {subscriberValues.map(subscriber => (
                  <option value={subscriber} key={subscriber}>
                    {translate('estetlyApp.Subscriber.' + subscriber)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('estetlyApp.preSubscription.fullname')}
                id="pre-subscription-fullname"
                name="fullname"
                data-cy="fullname"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.preSubscription.email')}
                id="pre-subscription-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.preSubscription.phoneNumber')}
                id="pre-subscription-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.preSubscription.timestamp')}
                id="pre-subscription-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.preSubscription.emailStatus')}
                id="pre-subscription-emailStatus"
                name="emailStatus"
                data-cy="emailStatus"
                type="select"
              >
                {emailStatusValues.map(emailStatus => (
                  <option value={emailStatus} key={emailStatus}>
                    {translate('estetlyApp.EmailStatus.' + emailStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('estetlyApp.preSubscription.subscriberStatus')}
                id="pre-subscription-subscriberStatus"
                name="subscriberStatus"
                data-cy="subscriberStatus"
                type="select"
              >
                {subscriberStatusValues.map(subscriberStatus => (
                  <option value={subscriberStatus} key={subscriberStatus}>
                    {translate('estetlyApp.SubscriberStatus.' + subscriberStatus)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pre-subscription" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PreSubscriptionUpdate;

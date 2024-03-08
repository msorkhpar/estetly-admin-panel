import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pre-subscription.reducer';

export const PreSubscriptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preSubscriptionEntity = useAppSelector(state => state.preSubscription.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preSubscriptionDetailsHeading">
          <Translate contentKey="estetlyApp.preSubscription.detail.title">PreSubscription</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.id}</dd>
          <dt>
            <span id="subscriberType">
              <Translate contentKey="estetlyApp.preSubscription.subscriberType">Subscriber Type</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.subscriberType}</dd>
          <dt>
            <span id="fullname">
              <Translate contentKey="estetlyApp.preSubscription.fullname">Fullname</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.fullname}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="estetlyApp.preSubscription.email">Email</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="estetlyApp.preSubscription.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.phoneNumber}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="estetlyApp.preSubscription.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {preSubscriptionEntity.timestamp ? (
              <TextFormat value={preSubscriptionEntity.timestamp} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="emailStatus">
              <Translate contentKey="estetlyApp.preSubscription.emailStatus">Email Status</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.emailStatus}</dd>
          <dt>
            <span id="subscriberStatus">
              <Translate contentKey="estetlyApp.preSubscription.subscriberStatus">Subscriber Status</Translate>
            </span>
          </dt>
          <dd>{preSubscriptionEntity.subscriberStatus}</dd>
        </dl>
        <Button tag={Link} to="/pre-subscription" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pre-subscription/${preSubscriptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreSubscriptionDetail;

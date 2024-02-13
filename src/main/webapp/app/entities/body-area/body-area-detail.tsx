import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './body-area.reducer';

export const BodyAreaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bodyAreaEntity = useAppSelector(state => state.bodyArea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bodyAreaDetailsHeading">
          <Translate contentKey="estetlyApp.bodyArea.detail.title">BodyArea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bodyAreaEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="estetlyApp.bodyArea.code">Code</Translate>
            </span>
          </dt>
          <dd>{bodyAreaEntity.code}</dd>
          <dt>
            <span id="displayName">
              <Translate contentKey="estetlyApp.bodyArea.displayName">Display Name</Translate>
            </span>
          </dt>
          <dd>{bodyAreaEntity.displayName}</dd>
        </dl>
        <Button tag={Link} to="/body-area" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/body-area/${bodyAreaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BodyAreaDetail;

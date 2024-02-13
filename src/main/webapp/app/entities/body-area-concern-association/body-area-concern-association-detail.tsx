import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './body-area-concern-association.reducer';

export const BodyAreaConcernAssociationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bodyAreaConcernAssociationEntity = useAppSelector(state => state.bodyAreaConcernAssociation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bodyAreaConcernAssociationDetailsHeading">
          <Translate contentKey="estetlyApp.bodyAreaConcernAssociation.detail.title">BodyAreaConcernAssociation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bodyAreaConcernAssociationEntity.id}</dd>
          <dt>
            <Translate contentKey="estetlyApp.bodyAreaConcernAssociation.bodyArea">Body Area</Translate>
          </dt>
          <dd>{bodyAreaConcernAssociationEntity.bodyArea ? bodyAreaConcernAssociationEntity.bodyArea.displayName : ''}</dd>
          <dt>
            <Translate contentKey="estetlyApp.bodyAreaConcernAssociation.concern">Concern</Translate>
          </dt>
          <dd>{bodyAreaConcernAssociationEntity.concern ? bodyAreaConcernAssociationEntity.concern.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/body-area-concern-association" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/body-area-concern-association/${bodyAreaConcernAssociationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BodyAreaConcernAssociationDetail;

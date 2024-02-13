import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './concern-procedure-association.reducer';

export const ConcernProcedureAssociationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const concernProcedureAssociationEntity = useAppSelector(state => state.concernProcedureAssociation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="concernProcedureAssociationDetailsHeading">
          <Translate contentKey="estetlyApp.concernProcedureAssociation.detail.title">ConcernProcedureAssociation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{concernProcedureAssociationEntity.id}</dd>
          <dt>
            <Translate contentKey="estetlyApp.concernProcedureAssociation.procedure">Procedure</Translate>
          </dt>
          <dd>{concernProcedureAssociationEntity.procedure ? concernProcedureAssociationEntity.procedure.title : ''}</dd>
          <dt>
            <Translate contentKey="estetlyApp.concernProcedureAssociation.concern">Concern</Translate>
          </dt>
          <dd>{concernProcedureAssociationEntity.concern ? concernProcedureAssociationEntity.concern.title : ''}</dd>
        </dl>
        <Button tag={Link} to="/concern-procedure-association" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/concern-procedure-association/${concernProcedureAssociationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConcernProcedureAssociationDetail;

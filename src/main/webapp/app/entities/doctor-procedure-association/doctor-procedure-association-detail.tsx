import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doctor-procedure-association.reducer';

export const DoctorProcedureAssociationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doctorProcedureAssociationEntity = useAppSelector(state => state.doctorProcedureAssociation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doctorProcedureAssociationDetailsHeading">
          <Translate contentKey="estetlyApp.doctorProcedureAssociation.detail.title">DoctorProcedureAssociation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{doctorProcedureAssociationEntity.id}</dd>
          <dt>
            <span id="picture">
              <Translate contentKey="estetlyApp.doctorProcedureAssociation.picture">Picture</Translate>
            </span>
          </dt>
          <dd>
            {doctorProcedureAssociationEntity.picture ? (
              <div>
                {doctorProcedureAssociationEntity.pictureContentType ? (
                  <a onClick={openFile(doctorProcedureAssociationEntity.pictureContentType, doctorProcedureAssociationEntity.picture)}>
                    <img
                      src={`data:${doctorProcedureAssociationEntity.pictureContentType};base64,${doctorProcedureAssociationEntity.picture}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {doctorProcedureAssociationEntity.pictureContentType}, {byteSize(doctorProcedureAssociationEntity.picture)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="estetlyApp.doctorProcedureAssociation.description">Description</Translate>
            </span>
          </dt>
          <dd>{doctorProcedureAssociationEntity.description}</dd>
          <dt>
            <span id="descriptionFr">
              <Translate contentKey="estetlyApp.doctorProcedureAssociation.descriptionFr">Description Fr</Translate>
            </span>
          </dt>
          <dd>{doctorProcedureAssociationEntity.descriptionFr}</dd>
          <dt>
            <span id="cost">
              <Translate contentKey="estetlyApp.doctorProcedureAssociation.cost">Cost</Translate>
            </span>
          </dt>
          <dd>{doctorProcedureAssociationEntity.cost}</dd>
          <dt>
            <Translate contentKey="estetlyApp.doctorProcedureAssociation.procedure">Procedure</Translate>
          </dt>
          <dd>{doctorProcedureAssociationEntity.procedure ? doctorProcedureAssociationEntity.procedure.title : ''}</dd>
          <dt>
            <Translate contentKey="estetlyApp.doctorProcedureAssociation.doctor">Doctor</Translate>
          </dt>
          <dd>{doctorProcedureAssociationEntity.doctor ? doctorProcedureAssociationEntity.doctor.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctor-procedure-association" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctor-procedure-association/${doctorProcedureAssociationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoctorProcedureAssociationDetail;

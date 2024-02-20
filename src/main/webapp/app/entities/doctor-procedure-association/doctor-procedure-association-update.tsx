import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProcedure } from 'app/shared/model/procedure.model';
import { getEntities as getProcedures } from 'app/entities/procedure/procedure.reducer';
import { IDoctor } from 'app/shared/model/doctor.model';
import { getEntities as getDoctors } from 'app/entities/doctor/doctor.reducer';
import { IDoctorProcedureAssociation } from 'app/shared/model/doctor-procedure-association.model';
import { getEntity, updateEntity, createEntity, reset } from './doctor-procedure-association.reducer';

export const DoctorProcedureAssociationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const procedures = useAppSelector(state => state.procedure.entities);
  const doctors = useAppSelector(state => state.doctor.entities);
  const doctorProcedureAssociationEntity = useAppSelector(state => state.doctorProcedureAssociation.entity);
  const loading = useAppSelector(state => state.doctorProcedureAssociation.loading);
  const updating = useAppSelector(state => state.doctorProcedureAssociation.updating);
  const updateSuccess = useAppSelector(state => state.doctorProcedureAssociation.updateSuccess);

  const handleClose = () => {
    navigate('/doctor-procedure-association' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProcedures({}));
    dispatch(getDoctors({}));
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
    if (values.cost !== undefined && typeof values.cost !== 'number') {
      values.cost = Number(values.cost);
    }

    const entity = {
      ...doctorProcedureAssociationEntity,
      ...values,
      procedure: procedures.find(it => it.id.toString() === values.procedure.toString()),
      doctor: doctors.find(it => it.id.toString() === values.doctor.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...doctorProcedureAssociationEntity,
          procedure: doctorProcedureAssociationEntity?.procedure?.id,
          doctor: doctorProcedureAssociationEntity?.doctor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.doctorProcedureAssociation.home.createOrEditLabel" data-cy="DoctorProcedureAssociationCreateUpdateHeading">
            <Translate contentKey="estetlyApp.doctorProcedureAssociation.home.createOrEditLabel">
              Create or edit a DoctorProcedureAssociation
            </Translate>
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
                  id="doctor-procedure-association-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedBlobField
                label={translate('estetlyApp.doctorProcedureAssociation.picture')}
                id="doctor-procedure-association-picture"
                name="picture"
                data-cy="picture"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('estetlyApp.doctorProcedureAssociation.description')}
                id="doctor-procedure-association-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('estetlyApp.doctorProcedureAssociation.descriptionFr')}
                id="doctor-procedure-association-descriptionFr"
                name="descriptionFr"
                data-cy="descriptionFr"
                type="textarea"
              />
              <ValidatedField
                label={translate('estetlyApp.doctorProcedureAssociation.cost')}
                id="doctor-procedure-association-cost"
                name="cost"
                data-cy="cost"
                type="text"
              />
              <ValidatedField
                id="doctor-procedure-association-procedure"
                name="procedure"
                data-cy="procedure"
                label={translate('estetlyApp.doctorProcedureAssociation.procedure')}
                type="select"
                required
              >
                <option value="" key="0" />
                {procedures
                  ? procedures.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="doctor-procedure-association-doctor"
                name="doctor"
                data-cy="doctor"
                label={translate('estetlyApp.doctorProcedureAssociation.doctor')}
                type="select"
                required
              >
                <option value="" key="0" />
                {doctors
                  ? doctors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/doctor-procedure-association"
                replace
                color="info"
              >
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

export default DoctorProcedureAssociationUpdate;

import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProcedure } from 'app/shared/model/procedure.model';
import { getEntities as getProcedures } from 'app/entities/procedure/procedure.reducer';
import { IConcern } from 'app/shared/model/concern.model';
import { getEntities as getConcerns } from 'app/entities/concern/concern.reducer';
import { IConcernProcedureAssociation } from 'app/shared/model/concern-procedure-association.model';
import { getEntity, updateEntity, createEntity, reset } from './concern-procedure-association.reducer';

export const ConcernProcedureAssociationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const procedures = useAppSelector(state => state.procedure.entities);
  const concerns = useAppSelector(state => state.concern.entities);
  const concernProcedureAssociationEntity = useAppSelector(state => state.concernProcedureAssociation.entity);
  const loading = useAppSelector(state => state.concernProcedureAssociation.loading);
  const updating = useAppSelector(state => state.concernProcedureAssociation.updating);
  const updateSuccess = useAppSelector(state => state.concernProcedureAssociation.updateSuccess);

  const handleClose = () => {
    navigate('/concern-procedure-association');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProcedures({}));
    dispatch(getConcerns({}));
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

    const entity = {
      ...concernProcedureAssociationEntity,
      ...values,
      procedure: procedures.find(it => it.id.toString() === values.procedure.toString()),
      concern: concerns.find(it => it.id.toString() === values.concern.toString()),
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
          ...concernProcedureAssociationEntity,
          procedure: concernProcedureAssociationEntity?.procedure?.id,
          concern: concernProcedureAssociationEntity?.concern?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.concernProcedureAssociation.home.createOrEditLabel" data-cy="ConcernProcedureAssociationCreateUpdateHeading">
            <Translate contentKey="estetlyApp.concernProcedureAssociation.home.createOrEditLabel">
              Create or edit a ConcernProcedureAssociation
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
                  id="concern-procedure-association-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="concern-procedure-association-procedure"
                name="procedure"
                data-cy="procedure"
                label={translate('estetlyApp.concernProcedureAssociation.procedure')}
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
                id="concern-procedure-association-concern"
                name="concern"
                data-cy="concern"
                label={translate('estetlyApp.concernProcedureAssociation.concern')}
                type="select"
                required
              >
                <option value="" key="0" />
                {concerns
                  ? concerns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
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
                to="/concern-procedure-association"
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

export default ConcernProcedureAssociationUpdate;

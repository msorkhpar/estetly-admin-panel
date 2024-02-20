import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProcedure } from 'app/shared/model/procedure.model';
import { getEntity, updateEntity, createEntity, reset } from './procedure.reducer';

export const ProcedureUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const procedureEntity = useAppSelector(state => state.procedure.entity);
  const loading = useAppSelector(state => state.procedure.loading);
  const updating = useAppSelector(state => state.procedure.updating);
  const updateSuccess = useAppSelector(state => state.procedure.updateSuccess);

  const handleClose = () => {
    navigate('/procedure' + location.search);
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
    if (values.invasiveness !== undefined && typeof values.invasiveness !== 'number') {
      values.invasiveness = Number(values.invasiveness);
    }
    if (values.averageCost !== undefined && typeof values.averageCost !== 'number') {
      values.averageCost = Number(values.averageCost);
    }

    const entity = {
      ...procedureEntity,
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
      ? {}
      : {
          ...procedureEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.procedure.home.createOrEditLabel" data-cy="ProcedureCreateUpdateHeading">
            <Translate contentKey="estetlyApp.procedure.home.createOrEditLabel">Create or edit a Procedure</Translate>
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
                  id="procedure-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('estetlyApp.procedure.title')}
                id="procedure-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.procedure.titleFr')}
                id="procedure-titleFr"
                name="titleFr"
                data-cy="titleFr"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.procedure.description')}
                id="procedure-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('estetlyApp.procedure.descriptionFr')}
                id="procedure-descriptionFr"
                name="descriptionFr"
                data-cy="descriptionFr"
                type="textarea"
              />
              <ValidatedBlobField
                label={translate('estetlyApp.procedure.picture')}
                id="procedure-picture"
                name="picture"
                data-cy="picture"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('estetlyApp.procedure.invasiveness')}
                id="procedure-invasiveness"
                name="invasiveness"
                data-cy="invasiveness"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  max: { value: 100, message: translate('entity.validation.max', { max: 100 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.procedure.averageCost')}
                id="procedure-averageCost"
                name="averageCost"
                data-cy="averageCost"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/procedure" replace color="info">
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

export default ProcedureUpdate;

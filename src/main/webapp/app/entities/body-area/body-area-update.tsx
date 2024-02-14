import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBodyAreas } from 'app/entities/body-area/body-area.reducer';
import { IBodyArea } from 'app/shared/model/body-area.model';
import { getEntity, updateEntity, createEntity, reset } from './body-area.reducer';

export const BodyAreaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bodyAreas = useAppSelector(state => state.bodyArea.entities);
  const bodyAreaEntity = useAppSelector(state => state.bodyArea.entity);
  const loading = useAppSelector(state => state.bodyArea.loading);
  const updating = useAppSelector(state => state.bodyArea.updating);
  const updateSuccess = useAppSelector(state => state.bodyArea.updateSuccess);

  const handleClose = () => {
    navigate('/body-area');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBodyAreas({}));
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
      ...bodyAreaEntity,
      ...values,
      parent: bodyAreas.find(it => it.id.toString() === values.parent.toString()),
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
          ...bodyAreaEntity,
          parent: bodyAreaEntity?.parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.bodyArea.home.createOrEditLabel" data-cy="BodyAreaCreateUpdateHeading">
            <Translate contentKey="estetlyApp.bodyArea.home.createOrEditLabel">Create or edit a BodyArea</Translate>
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
                  id="body-area-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('estetlyApp.bodyArea.code')} id="body-area-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label={translate('estetlyApp.bodyArea.displayName')}
                id="body-area-displayName"
                name="displayName"
                data-cy="displayName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="body-area-parent"
                name="parent"
                data-cy="parent"
                label={translate('estetlyApp.bodyArea.parent')}
                type="select"
              >
                <option value="" key="0" />
                {bodyAreas
                  ? bodyAreas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.displayName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/body-area" replace color="info">
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

export default BodyAreaUpdate;

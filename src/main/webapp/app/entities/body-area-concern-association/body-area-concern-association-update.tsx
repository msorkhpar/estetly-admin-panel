import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBodyArea } from 'app/shared/model/body-area.model';
import { getEntities as getBodyAreas } from 'app/entities/body-area/body-area.reducer';
import { IConcern } from 'app/shared/model/concern.model';
import { getEntities as getConcerns } from 'app/entities/concern/concern.reducer';
import { IBodyAreaConcernAssociation } from 'app/shared/model/body-area-concern-association.model';
import { getEntity, updateEntity, createEntity, reset } from './body-area-concern-association.reducer';

export const BodyAreaConcernAssociationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bodyAreas = useAppSelector(state => state.bodyArea.entities);
  const concerns = useAppSelector(state => state.concern.entities);
  const bodyAreaConcernAssociationEntity = useAppSelector(state => state.bodyAreaConcernAssociation.entity);
  const loading = useAppSelector(state => state.bodyAreaConcernAssociation.loading);
  const updating = useAppSelector(state => state.bodyAreaConcernAssociation.updating);
  const updateSuccess = useAppSelector(state => state.bodyAreaConcernAssociation.updateSuccess);

  const handleClose = () => {
    navigate('/body-area-concern-association' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBodyAreas({}));
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
      ...bodyAreaConcernAssociationEntity,
      ...values,
      bodyArea: bodyAreas.find(it => it.id.toString() === values.bodyArea.toString()),
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
          ...bodyAreaConcernAssociationEntity,
          bodyArea: bodyAreaConcernAssociationEntity?.bodyArea?.id,
          concern: bodyAreaConcernAssociationEntity?.concern?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.bodyAreaConcernAssociation.home.createOrEditLabel" data-cy="BodyAreaConcernAssociationCreateUpdateHeading">
            <Translate contentKey="estetlyApp.bodyAreaConcernAssociation.home.createOrEditLabel">
              Create or edit a BodyAreaConcernAssociation
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
                  id="body-area-concern-association-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="body-area-concern-association-bodyArea"
                name="bodyArea"
                data-cy="bodyArea"
                label={translate('estetlyApp.bodyAreaConcernAssociation.bodyArea')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="body-area-concern-association-concern"
                name="concern"
                data-cy="concern"
                label={translate('estetlyApp.bodyAreaConcernAssociation.concern')}
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
                to="/body-area-concern-association"
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

export default BodyAreaConcernAssociationUpdate;

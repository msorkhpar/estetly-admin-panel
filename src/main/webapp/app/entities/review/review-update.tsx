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
import { IReview } from 'app/shared/model/review.model';
import { getEntity, updateEntity, createEntity, reset } from './review.reducer';

export const ReviewUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const procedures = useAppSelector(state => state.procedure.entities);
  const reviewEntity = useAppSelector(state => state.review.entity);
  const loading = useAppSelector(state => state.review.loading);
  const updating = useAppSelector(state => state.review.updating);
  const updateSuccess = useAppSelector(state => state.review.updateSuccess);

  const handleClose = () => {
    navigate('/review' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProcedures({}));
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
    if (values.rate !== undefined && typeof values.rate !== 'number') {
      values.rate = Number(values.rate);
    }
    values.timestamp = convertDateTimeToServer(values.timestamp);

    const entity = {
      ...reviewEntity,
      ...values,
      procedure: procedures.find(it => it.id.toString() === values.procedure.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          timestamp: displayDefaultDateTime(),
        }
      : {
          ...reviewEntity,
          timestamp: convertDateTimeFromServer(reviewEntity.timestamp),
          procedure: reviewEntity?.procedure?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="estetlyApp.review.home.createOrEditLabel" data-cy="ReviewCreateUpdateHeading">
            <Translate contentKey="estetlyApp.review.home.createOrEditLabel">Create or edit a Review</Translate>
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
                  id="review-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('estetlyApp.review.title')}
                id="review-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 254, message: translate('entity.validation.maxlength', { max: 254 }) },
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.review.description')}
                id="review-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('estetlyApp.review.rate')}
                id="review-rate"
                name="rate"
                data-cy="rate"
                type="text"
                validate={{
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  max: { value: 5, message: translate('entity.validation.max', { max: 5 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('estetlyApp.review.timestamp')}
                id="review-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="review-procedure"
                name="procedure"
                data-cy="procedure"
                label={translate('estetlyApp.review.procedure')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/review" replace color="info">
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

export default ReviewUpdate;

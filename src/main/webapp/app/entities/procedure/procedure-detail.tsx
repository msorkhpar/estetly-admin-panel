import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './procedure.reducer';

export const ProcedureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const procedureEntity = useAppSelector(state => state.procedure.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="procedureDetailsHeading">
          <Translate contentKey="estetlyApp.procedure.detail.title">Procedure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{procedureEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="estetlyApp.procedure.title">Title</Translate>
            </span>
          </dt>
          <dd>{procedureEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="estetlyApp.procedure.description">Description</Translate>
            </span>
          </dt>
          <dd>{procedureEntity.description}</dd>
          <dt>
            <span id="picture">
              <Translate contentKey="estetlyApp.procedure.picture">Picture</Translate>
            </span>
          </dt>
          <dd>
            {procedureEntity.picture ? (
              <div>
                {procedureEntity.pictureContentType ? (
                  <a onClick={openFile(procedureEntity.pictureContentType, procedureEntity.picture)}>
                    <img
                      src={`data:${procedureEntity.pictureContentType};base64,${procedureEntity.picture}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {procedureEntity.pictureContentType}, {byteSize(procedureEntity.picture)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="invasiveness">
              <Translate contentKey="estetlyApp.procedure.invasiveness">Invasiveness</Translate>
            </span>
          </dt>
          <dd>{procedureEntity.invasiveness}</dd>
          <dt>
            <span id="averageCost">
              <Translate contentKey="estetlyApp.procedure.averageCost">Average Cost</Translate>
            </span>
          </dt>
          <dd>{procedureEntity.averageCost}</dd>
        </dl>
        <Button tag={Link} to="/procedure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/procedure/${procedureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProcedureDetail;

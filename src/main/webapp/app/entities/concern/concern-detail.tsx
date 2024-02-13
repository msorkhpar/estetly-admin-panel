import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './concern.reducer';

export const ConcernDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const concernEntity = useAppSelector(state => state.concern.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="concernDetailsHeading">
          <Translate contentKey="estetlyApp.concern.detail.title">Concern</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{concernEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="estetlyApp.concern.title">Title</Translate>
            </span>
          </dt>
          <dd>{concernEntity.title}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="estetlyApp.concern.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{concernEntity.gender}</dd>
          <dt>
            <span id="otherNames">
              <Translate contentKey="estetlyApp.concern.otherNames">Other Names</Translate>
            </span>
          </dt>
          <dd>{concernEntity.otherNames}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="estetlyApp.concern.description">Description</Translate>
            </span>
          </dt>
          <dd>{concernEntity.description}</dd>
          <dt>
            <span id="picture">
              <Translate contentKey="estetlyApp.concern.picture">Picture</Translate>
            </span>
          </dt>
          <dd>
            {concernEntity.picture ? (
              <div>
                {concernEntity.pictureContentType ? (
                  <a onClick={openFile(concernEntity.pictureContentType, concernEntity.picture)}>
                    <img src={`data:${concernEntity.pictureContentType};base64,${concernEntity.picture}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {concernEntity.pictureContentType}, {byteSize(concernEntity.picture)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="estetlyApp.concern.category">Category</Translate>
          </dt>
          <dd>{concernEntity.category ? concernEntity.category.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/concern" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/concern/${concernEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConcernDetail;

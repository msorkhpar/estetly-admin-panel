import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './concern.reducer';

export const Concern = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const concernList = useAppSelector(state => state.concern.entities);
  const loading = useAppSelector(state => state.concern.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="concern-heading" data-cy="ConcernHeading">
        <Translate contentKey="estetlyApp.concern.home.title">Concerns</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="estetlyApp.concern.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/concern/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="estetlyApp.concern.home.createLabel">Create new Concern</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {concernList && concernList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="estetlyApp.concern.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="estetlyApp.concern.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('titleFr')}>
                  <Translate contentKey="estetlyApp.concern.titleFr">Title Fr</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('titleFr')} />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="estetlyApp.concern.gender">Gender</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('gender')} />
                </th>
                <th className="hand" onClick={sort('otherNames')}>
                  <Translate contentKey="estetlyApp.concern.otherNames">Other Names</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('otherNames')} />
                </th>
                <th className="hand" onClick={sort('otherNamesFr')}>
                  <Translate contentKey="estetlyApp.concern.otherNamesFr">Other Names Fr</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('otherNamesFr')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="estetlyApp.concern.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('descriptionFr')}>
                  <Translate contentKey="estetlyApp.concern.descriptionFr">Description Fr</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('descriptionFr')} />
                </th>
                <th className="hand" onClick={sort('picture')}>
                  <Translate contentKey="estetlyApp.concern.picture">Picture</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('picture')} />
                </th>
                <th>
                  <Translate contentKey="estetlyApp.concern.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {concernList.map((concern, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/concern/${concern.id}`} color="link" size="sm">
                      {concern.id}
                    </Button>
                  </td>
                  <td>{concern.title}</td>
                  <td>{concern.titleFr}</td>
                  <td>
                    <Translate contentKey={`estetlyApp.Gender.${concern.gender}`} />
                  </td>
                  <td>{concern.otherNames}</td>
                  <td>{concern.otherNamesFr}</td>
                  <td>{concern.description}</td>
                  <td>{concern.descriptionFr}</td>
                  <td>
                    {concern.picture ? (
                      <div>
                        {concern.pictureContentType ? (
                          <a onClick={openFile(concern.pictureContentType, concern.picture)}>
                            <img src={`data:${concern.pictureContentType};base64,${concern.picture}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {concern.pictureContentType}, {byteSize(concern.picture)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{concern.category ? <Link to={`/category/${concern.category.id}`}>{concern.category.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/concern/${concern.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/concern/${concern.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/concern/${concern.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="estetlyApp.concern.home.notFound">No Concerns found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Concern;

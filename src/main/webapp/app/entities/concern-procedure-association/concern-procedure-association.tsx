import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './concern-procedure-association.reducer';

export const ConcernProcedureAssociation = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const concernProcedureAssociationList = useAppSelector(state => state.concernProcedureAssociation.entities);
  const loading = useAppSelector(state => state.concernProcedureAssociation.loading);

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
      <h2 id="concern-procedure-association-heading" data-cy="ConcernProcedureAssociationHeading">
        <Translate contentKey="estetlyApp.concernProcedureAssociation.home.title">Concern Procedure Associations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="estetlyApp.concernProcedureAssociation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/concern-procedure-association/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="estetlyApp.concernProcedureAssociation.home.createLabel">
              Create new Concern Procedure Association
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {concernProcedureAssociationList && concernProcedureAssociationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="estetlyApp.concernProcedureAssociation.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th>
                  <Translate contentKey="estetlyApp.concernProcedureAssociation.procedure">Procedure</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="estetlyApp.concernProcedureAssociation.concern">Concern</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {concernProcedureAssociationList.map((concernProcedureAssociation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/concern-procedure-association/${concernProcedureAssociation.id}`} color="link" size="sm">
                      {concernProcedureAssociation.id}
                    </Button>
                  </td>
                  <td>
                    {concernProcedureAssociation.procedure ? (
                      <Link to={`/procedure/${concernProcedureAssociation.procedure.id}`}>
                        {concernProcedureAssociation.procedure.title}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {concernProcedureAssociation.concern ? (
                      <Link to={`/concern/${concernProcedureAssociation.concern.id}`}>{concernProcedureAssociation.concern.title}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/concern-procedure-association/${concernProcedureAssociation.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/concern-procedure-association/${concernProcedureAssociation.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/concern-procedure-association/${concernProcedureAssociation.id}/delete`)}
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
              <Translate contentKey="estetlyApp.concernProcedureAssociation.home.notFound">
                No Concern Procedure Associations found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ConcernProcedureAssociation;

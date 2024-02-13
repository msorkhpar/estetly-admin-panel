import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './doctor-procedure-association.reducer';

export const DoctorProcedureAssociation = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const doctorProcedureAssociationList = useAppSelector(state => state.doctorProcedureAssociation.entities);
  const loading = useAppSelector(state => state.doctorProcedureAssociation.loading);

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
      <h2 id="doctor-procedure-association-heading" data-cy="DoctorProcedureAssociationHeading">
        <Translate contentKey="estetlyApp.doctorProcedureAssociation.home.title">Doctor Procedure Associations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="estetlyApp.doctorProcedureAssociation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/doctor-procedure-association/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="estetlyApp.doctorProcedureAssociation.home.createLabel">
              Create new Doctor Procedure Association
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {doctorProcedureAssociationList && doctorProcedureAssociationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="estetlyApp.doctorProcedureAssociation.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('picture')}>
                  <Translate contentKey="estetlyApp.doctorProcedureAssociation.picture">Picture</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('picture')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="estetlyApp.doctorProcedureAssociation.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('cost')}>
                  <Translate contentKey="estetlyApp.doctorProcedureAssociation.cost">Cost</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cost')} />
                </th>
                <th>
                  <Translate contentKey="estetlyApp.doctorProcedureAssociation.procedure">Procedure</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="estetlyApp.doctorProcedureAssociation.doctor">Doctor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {doctorProcedureAssociationList.map((doctorProcedureAssociation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/doctor-procedure-association/${doctorProcedureAssociation.id}`} color="link" size="sm">
                      {doctorProcedureAssociation.id}
                    </Button>
                  </td>
                  <td>
                    {doctorProcedureAssociation.picture ? (
                      <div>
                        {doctorProcedureAssociation.pictureContentType ? (
                          <a onClick={openFile(doctorProcedureAssociation.pictureContentType, doctorProcedureAssociation.picture)}>
                            <img
                              src={`data:${doctorProcedureAssociation.pictureContentType};base64,${doctorProcedureAssociation.picture}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {doctorProcedureAssociation.pictureContentType}, {byteSize(doctorProcedureAssociation.picture)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{doctorProcedureAssociation.description}</td>
                  <td>{doctorProcedureAssociation.cost}</td>
                  <td>
                    {doctorProcedureAssociation.procedure ? (
                      <Link to={`/procedure/${doctorProcedureAssociation.procedure.id}`}>{doctorProcedureAssociation.procedure.title}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {doctorProcedureAssociation.doctor ? (
                      <Link to={`/doctor/${doctorProcedureAssociation.doctor.id}`}>{doctorProcedureAssociation.doctor.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/doctor-procedure-association/${doctorProcedureAssociation.id}`}
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
                        to={`/doctor-procedure-association/${doctorProcedureAssociation.id}/edit`}
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
                        onClick={() => (window.location.href = `/doctor-procedure-association/${doctorProcedureAssociation.id}/delete`)}
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
              <Translate contentKey="estetlyApp.doctorProcedureAssociation.home.notFound">No Doctor Procedure Associations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DoctorProcedureAssociation;

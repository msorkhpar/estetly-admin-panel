import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './procedure.reducer';

export const Procedure = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const procedureList = useAppSelector(state => state.procedure.entities);
  const loading = useAppSelector(state => state.procedure.loading);
  const totalItems = useAppSelector(state => state.procedure.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="procedure-heading" data-cy="ProcedureHeading">
        <Translate contentKey="estetlyApp.procedure.home.title">Procedures</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="estetlyApp.procedure.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/procedure/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="estetlyApp.procedure.home.createLabel">Create new Procedure</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {procedureList && procedureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="estetlyApp.procedure.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="estetlyApp.procedure.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('titleFr')}>
                  <Translate contentKey="estetlyApp.procedure.titleFr">Title Fr</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('titleFr')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="estetlyApp.procedure.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('descriptionFr')}>
                  <Translate contentKey="estetlyApp.procedure.descriptionFr">Description Fr</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('descriptionFr')} />
                </th>
                <th className="hand" onClick={sort('picture')}>
                  <Translate contentKey="estetlyApp.procedure.picture">Picture</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('picture')} />
                </th>
                <th className="hand" onClick={sort('invasiveness')}>
                  <Translate contentKey="estetlyApp.procedure.invasiveness">Invasiveness</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('invasiveness')} />
                </th>
                <th className="hand" onClick={sort('averageCost')}>
                  <Translate contentKey="estetlyApp.procedure.averageCost">Average Cost</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('averageCost')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {procedureList.map((procedure, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/procedure/${procedure.id}`} color="link" size="sm">
                      {procedure.id}
                    </Button>
                  </td>
                  <td>{procedure.title}</td>
                  <td>{procedure.titleFr}</td>
                  <td>{procedure.description}</td>
                  <td>{procedure.descriptionFr}</td>
                  <td>
                    {procedure.picture ? (
                      <div>
                        {procedure.pictureContentType ? (
                          <a onClick={openFile(procedure.pictureContentType, procedure.picture)}>
                            <img src={`data:${procedure.pictureContentType};base64,${procedure.picture}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {procedure.pictureContentType}, {byteSize(procedure.picture)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{procedure.invasiveness}</td>
                  <td>{procedure.averageCost}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/procedure/${procedure.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/procedure/${procedure.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        onClick={() =>
                          (window.location.href = `/procedure/${procedure.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
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
              <Translate contentKey="estetlyApp.procedure.home.notFound">No Procedures found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={procedureList && procedureList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Procedure;

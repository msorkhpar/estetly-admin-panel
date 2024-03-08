import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './pre-subscription.reducer';

export const PreSubscription = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const preSubscriptionList = useAppSelector(state => state.preSubscription.entities);
  const loading = useAppSelector(state => state.preSubscription.loading);
  const totalItems = useAppSelector(state => state.preSubscription.totalItems);

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
      <h2 id="pre-subscription-heading" data-cy="PreSubscriptionHeading">
        <Translate contentKey="estetlyApp.preSubscription.home.title">Pre Subscriptions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="estetlyApp.preSubscription.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pre-subscription/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="estetlyApp.preSubscription.home.createLabel">Create new Pre Subscription</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {preSubscriptionList && preSubscriptionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="estetlyApp.preSubscription.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('subscriberType')}>
                  <Translate contentKey="estetlyApp.preSubscription.subscriberType">Subscriber Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('subscriberType')} />
                </th>
                <th className="hand" onClick={sort('fullname')}>
                  <Translate contentKey="estetlyApp.preSubscription.fullname">Fullname</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fullname')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="estetlyApp.preSubscription.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('phoneNumber')}>
                  <Translate contentKey="estetlyApp.preSubscription.phoneNumber">Phone Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phoneNumber')} />
                </th>
                <th className="hand" onClick={sort('timestamp')}>
                  <Translate contentKey="estetlyApp.preSubscription.timestamp">Timestamp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('timestamp')} />
                </th>
                <th className="hand" onClick={sort('emailStatus')}>
                  <Translate contentKey="estetlyApp.preSubscription.emailStatus">Email Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('emailStatus')} />
                </th>
                <th className="hand" onClick={sort('subscriberStatus')}>
                  <Translate contentKey="estetlyApp.preSubscription.subscriberStatus">Subscriber Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('subscriberStatus')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {preSubscriptionList.map((preSubscription, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pre-subscription/${preSubscription.id}`} color="link" size="sm">
                      {preSubscription.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`estetlyApp.Subscriber.${preSubscription.subscriberType}`} />
                  </td>
                  <td>{preSubscription.fullname}</td>
                  <td>{preSubscription.email}</td>
                  <td>{preSubscription.phoneNumber}</td>
                  <td>
                    {preSubscription.timestamp ? (
                      <TextFormat type="date" value={preSubscription.timestamp} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`estetlyApp.EmailStatus.${preSubscription.emailStatus}`} />
                  </td>
                  <td>
                    <Translate contentKey={`estetlyApp.SubscriberStatus.${preSubscription.subscriberStatus}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/pre-subscription/${preSubscription.id}`}
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
                        to={`/pre-subscription/${preSubscription.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/pre-subscription/${preSubscription.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="estetlyApp.preSubscription.home.notFound">No Pre Subscriptions found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={preSubscriptionList && preSubscriptionList.length > 0 ? '' : 'd-none'}>
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

export default PreSubscription;

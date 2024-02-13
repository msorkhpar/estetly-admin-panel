import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Concern from './concern';
import ConcernDetail from './concern-detail';
import ConcernUpdate from './concern-update';
import ConcernDeleteDialog from './concern-delete-dialog';

const ConcernRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Concern />} />
    <Route path="new" element={<ConcernUpdate />} />
    <Route path=":id">
      <Route index element={<ConcernDetail />} />
      <Route path="edit" element={<ConcernUpdate />} />
      <Route path="delete" element={<ConcernDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConcernRoutes;

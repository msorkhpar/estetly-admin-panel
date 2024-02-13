import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BodyArea from './body-area';
import BodyAreaDetail from './body-area-detail';
import BodyAreaUpdate from './body-area-update';
import BodyAreaDeleteDialog from './body-area-delete-dialog';

const BodyAreaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BodyArea />} />
    <Route path="new" element={<BodyAreaUpdate />} />
    <Route path=":id">
      <Route index element={<BodyAreaDetail />} />
      <Route path="edit" element={<BodyAreaUpdate />} />
      <Route path="delete" element={<BodyAreaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BodyAreaRoutes;

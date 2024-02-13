import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BodyAreaConcernAssociation from './body-area-concern-association';
import BodyAreaConcernAssociationDetail from './body-area-concern-association-detail';
import BodyAreaConcernAssociationUpdate from './body-area-concern-association-update';
import BodyAreaConcernAssociationDeleteDialog from './body-area-concern-association-delete-dialog';

const BodyAreaConcernAssociationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BodyAreaConcernAssociation />} />
    <Route path="new" element={<BodyAreaConcernAssociationUpdate />} />
    <Route path=":id">
      <Route index element={<BodyAreaConcernAssociationDetail />} />
      <Route path="edit" element={<BodyAreaConcernAssociationUpdate />} />
      <Route path="delete" element={<BodyAreaConcernAssociationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BodyAreaConcernAssociationRoutes;

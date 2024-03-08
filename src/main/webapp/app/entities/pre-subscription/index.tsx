import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreSubscription from './pre-subscription';
import PreSubscriptionDetail from './pre-subscription-detail';
import PreSubscriptionUpdate from './pre-subscription-update';
import PreSubscriptionDeleteDialog from './pre-subscription-delete-dialog';

const PreSubscriptionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreSubscription />} />
    <Route path="new" element={<PreSubscriptionUpdate />} />
    <Route path=":id">
      <Route index element={<PreSubscriptionDetail />} />
      <Route path="edit" element={<PreSubscriptionUpdate />} />
      <Route path="delete" element={<PreSubscriptionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreSubscriptionRoutes;

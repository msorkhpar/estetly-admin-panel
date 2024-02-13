import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ConcernProcedureAssociation from './concern-procedure-association';
import ConcernProcedureAssociationDetail from './concern-procedure-association-detail';
import ConcernProcedureAssociationUpdate from './concern-procedure-association-update';
import ConcernProcedureAssociationDeleteDialog from './concern-procedure-association-delete-dialog';

const ConcernProcedureAssociationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ConcernProcedureAssociation />} />
    <Route path="new" element={<ConcernProcedureAssociationUpdate />} />
    <Route path=":id">
      <Route index element={<ConcernProcedureAssociationDetail />} />
      <Route path="edit" element={<ConcernProcedureAssociationUpdate />} />
      <Route path="delete" element={<ConcernProcedureAssociationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConcernProcedureAssociationRoutes;

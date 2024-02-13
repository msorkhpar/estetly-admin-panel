import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Procedure from './procedure';
import ProcedureDetail from './procedure-detail';
import ProcedureUpdate from './procedure-update';
import ProcedureDeleteDialog from './procedure-delete-dialog';

const ProcedureRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Procedure />} />
    <Route path="new" element={<ProcedureUpdate />} />
    <Route path=":id">
      <Route index element={<ProcedureDetail />} />
      <Route path="edit" element={<ProcedureUpdate />} />
      <Route path="delete" element={<ProcedureDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProcedureRoutes;

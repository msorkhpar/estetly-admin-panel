import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DoctorProcedureAssociation from './doctor-procedure-association';
import DoctorProcedureAssociationDetail from './doctor-procedure-association-detail';
import DoctorProcedureAssociationUpdate from './doctor-procedure-association-update';
import DoctorProcedureAssociationDeleteDialog from './doctor-procedure-association-delete-dialog';

const DoctorProcedureAssociationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DoctorProcedureAssociation />} />
    <Route path="new" element={<DoctorProcedureAssociationUpdate />} />
    <Route path=":id">
      <Route index element={<DoctorProcedureAssociationDetail />} />
      <Route path="edit" element={<DoctorProcedureAssociationUpdate />} />
      <Route path="delete" element={<DoctorProcedureAssociationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoctorProcedureAssociationRoutes;

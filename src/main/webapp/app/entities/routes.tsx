import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Category from './category';
import BodyArea from './body-area';
import Doctor from './doctor';
import Review from './review';
import Procedure from './procedure';
import Concern from './concern';
import BodyAreaConcernAssociation from './body-area-concern-association';
import ConcernProcedureAssociation from './concern-procedure-association';
import DoctorProcedureAssociation from './doctor-procedure-association';
import PreSubscription from './pre-subscription';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="category/*" element={<Category />} />
        <Route path="body-area/*" element={<BodyArea />} />
        <Route path="doctor/*" element={<Doctor />} />
        <Route path="review/*" element={<Review />} />
        <Route path="procedure/*" element={<Procedure />} />
        <Route path="concern/*" element={<Concern />} />
        <Route path="body-area-concern-association/*" element={<BodyAreaConcernAssociation />} />
        <Route path="concern-procedure-association/*" element={<ConcernProcedureAssociation />} />
        <Route path="doctor-procedure-association/*" element={<DoctorProcedureAssociation />} />
        <Route path="pre-subscription/*" element={<PreSubscription />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

import category from 'app/entities/category/category.reducer';
import bodyArea from 'app/entities/body-area/body-area.reducer';
import doctor from 'app/entities/doctor/doctor.reducer';
import review from 'app/entities/review/review.reducer';
import procedure from 'app/entities/procedure/procedure.reducer';
import concern from 'app/entities/concern/concern.reducer';
import bodyAreaConcernAssociation from 'app/entities/body-area-concern-association/body-area-concern-association.reducer';
import concernProcedureAssociation from 'app/entities/concern-procedure-association/concern-procedure-association.reducer';
import doctorProcedureAssociation from 'app/entities/doctor-procedure-association/doctor-procedure-association.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  category,
  bodyArea,
  doctor,
  review,
  procedure,
  concern,
  bodyAreaConcernAssociation,
  concernProcedureAssociation,
  doctorProcedureAssociation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

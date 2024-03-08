import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/category">
        <Translate contentKey="global.menu.entities.category" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/body-area">
        <Translate contentKey="global.menu.entities.bodyArea" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/doctor">
        <Translate contentKey="global.menu.entities.doctor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/review">
        <Translate contentKey="global.menu.entities.review" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/procedure">
        <Translate contentKey="global.menu.entities.procedure" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/concern">
        <Translate contentKey="global.menu.entities.concern" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/body-area-concern-association">
        <Translate contentKey="global.menu.entities.bodyAreaConcernAssociation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/concern-procedure-association">
        <Translate contentKey="global.menu.entities.concernProcedureAssociation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/doctor-procedure-association">
        <Translate contentKey="global.menu.entities.doctorProcedureAssociation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pre-subscription">
        <Translate contentKey="global.menu.entities.preSubscription" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

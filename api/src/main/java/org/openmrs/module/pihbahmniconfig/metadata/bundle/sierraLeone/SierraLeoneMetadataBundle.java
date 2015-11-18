package org.openmrs.module.pihbahmniconfig.metadata.bundle.sierraLeone;

import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.openmrs.module.pihbahmniconfig.metadata.bundle.PihMetadataBundle;
import org.springframework.stereotype.Component;

@Component
@Requires({ SierraLeoneAddressBundle.class} )
public class SierraLeoneMetadataBundle extends PihMetadataBundle {

    @Override
    public void install() throws Exception {

        // TODO scheduler username & password?

    }

}

package org.openmrs.module.pihbahmniconfig.metadata.bundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.packageFile;

/**
 * Includes utility methods that we use in many metadata bundles
 */
public abstract class PihMetadataBundle extends AbstractMetadataBundle {

	public static final String SYSTEM_PROPERTY_SKIP_METADATA_SHARING_PACKAGE_REFRESH = "skipMetadataSharingPackageRefresh";

    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    protected PlatformTransactionManager platformTransactionManager;

    @Autowired
    @Qualifier("adminService")
    protected AdministrationService administrationService;

    /**
     * Setting multiple GPs is much faster in a single transaction
     */
    protected void setGlobalProperties(final Map<String, String> properties) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (Map.Entry<String, String> entry : properties.entrySet()) {
                    setGlobalProperty(entry.getKey(), entry.getValue());
                }
            }
        });
    }

    /**
     * Update the global property with the given name to the given value, creating it if it doesn't exist
     */
    protected void setGlobalProperty(String propertyName, String propertyValue) {
        AdministrationService administrationService = Context.getAdministrationService();
        GlobalProperty gp = administrationService.getGlobalPropertyObject(propertyName);
        if (gp == null) {
            gp = new GlobalProperty(propertyName);
        }
        gp.setPropertyValue(propertyValue);
        administrationService.saveGlobalProperty(gp);
    }

    /**
     * Utility method that verifies that a particular concept mapping is set up correctly
     * @param conceptCode
     * @param conceptSource
     */
    protected void verifyConceptPresent(String conceptCode, String conceptSource) {
        if (Context.getConceptService().getConceptByMapping(conceptCode, conceptSource) == null) {
            throw new RuntimeException("No concept tagged with code " + conceptCode + " from source " + conceptSource);
        }
    }

    /**
     * Utility method to install an MDS package if it hasn't been disabled by a system property
     * @param filename
     * @param groupUuid
     * @return true if the package was installed
     */
    protected boolean installMetadataSharingPackage(String filename, String groupUuid) {
        String systemProperty = System.getProperty(SYSTEM_PROPERTY_SKIP_METADATA_SHARING_PACKAGE_REFRESH, "false");
        if (Boolean.parseBoolean(systemProperty)) {
            log.warn("Skipping refresh of MDS package: " + filename);
            return false;
        }
        log.warn("Installing Metadata Sharing package: " + filename);
        install(packageFile(filename, null, groupUuid));
        return true;
    }


}

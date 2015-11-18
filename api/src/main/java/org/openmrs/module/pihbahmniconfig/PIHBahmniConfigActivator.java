/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pihbahmniconfig;


import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleActivator;

import org.openmrs.module.metadatadeploy.api.MetadataDeployService;
import org.openmrs.module.pihbahmniconfig.metadata.bundle.sierraLeone.SierraLeoneMetadataBundle;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class PIHBahmniConfigActivator implements ModuleActivator {
	
	protected Log log = LogFactory.getLog(getClass());
		
	/**
	 * @see ModuleActivator#willRefreshContext()
	 */
	public void willRefreshContext() {
		log.info("Refreshing PIH Bahmni Config Module");
	}
	
	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	public void contextRefreshed() {
		log.info("PIH Bahmni Config Module refreshed");
	}
	
	/**
	 * @see ModuleActivator#willStart()
	 */
	public void willStart() {
		log.info("Starting PIH Bahmni Config Module");
	}
	
	/**
	 * @see ModuleActivator#started()
	 */
	public void started() {
		installMetadataBundles();
		log.info("PIH Bahmni Config Module started");
	}
	
	/**
	 * @see ModuleActivator#willStop()
	 */
	public void willStop() {
		log.info("Stopping PIH Bahmni Config Module");
	}
	
	/**
	 * @see ModuleActivator#stopped()
	 */
	public void stopped() {
		log.info("PIH Bahmni Config Module stopped");
	}


	private void installMetadataBundles() {

		MetadataDeployService deployService = Context.getService(MetadataDeployService.class);

		// make this more dynamic, not just hardcoded for Sierra Leone


			deployService.installBundle(Context.getRegisteredComponents(SierraLeoneMetadataBundle.class).get(0));


	}
}

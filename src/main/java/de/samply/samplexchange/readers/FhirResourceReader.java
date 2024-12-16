package de.samply.samplexchange.readers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Reader class for fhir resources.
 */
@Slf4j
public class FhirResourceReader {

    FhirContext ctx;

    /**
     * Constructor.
     */
    public FhirResourceReader(FhirContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Fetches all resources of type T.
     */
    public <T extends IBaseResource> List<T> fetchResources(
            Class<T> resourceType, IGenericClient client) {
        // Search
        Bundle bundle =
                client.search().forResource(resourceType).returnBundle(Bundle.class).count(500).execute();
        List<T> resourceList =
                new ArrayList<>(BundleUtil.toListOfResourcesOfType(ctx, bundle, resourceType));

        // Load the subsequent pages
        while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
            bundle = client.loadPage().next(bundle).execute();
            resourceList.addAll(BundleUtil.toListOfResourcesOfType(ctx, bundle, resourceType));
            log.debug("Fetching next page of " + resourceType.getName());
        }
        log.info(
                "Loaded " + resourceList.size() + " " + resourceType.getName() + " Resources from source");

        return resourceList;
    }

    /**
     * Fetches a resource from the fhir server.
     */
    public <T extends IBaseResource> T fetchResource(
            IGenericClient client, Class<T> resourceType, String id) {
        log.debug(
                "Reading Resource "
                        + resourceType.getName()
                        + " with ID "
                        + id
                        + " from "
                        + client.getServerBase());
        return client.read().resource(resourceType).withId(id).execute();
    }


}

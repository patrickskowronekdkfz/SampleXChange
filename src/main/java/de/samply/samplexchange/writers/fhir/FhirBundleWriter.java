package de.samply.samplexchange.writers.fhir;

import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.HTTPVerb;
import org.hl7.fhir.r4.model.Resource;

import java.util.List;
import java.util.UUID;

/**
 * Generates and writes bundles to destination.
 */
@Slf4j
public class FhirBundleWriter {

    public FhirBundleWriter() {
    }

    /**
     * Builds a bundle out of resource.
     */
    public Bundle buildResources(List<IBaseResource> resources) {
        Bundle bundleOut = new Bundle();
        bundleOut.setId(String.valueOf(UUID.randomUUID()));
        bundleOut.setType(Bundle.BundleType.TRANSACTION);

        try {
            for (IBaseResource resource : resources) {
                bundleOut
                        .addEntry()
                        .setFullUrl(resource.getIdElement().getValue())
                        .setResource((Resource) resource)
                        .getRequest()
                        .setUrl(
                                ((Resource) resource).getResourceType() + "/" + resource.getIdElement().getIdPart())
                        .setMethod(HTTPVerb.PUT);
            }

        } catch (Error e) {
            log.error(e.getMessage());
        }

        return bundleOut;
    }

}

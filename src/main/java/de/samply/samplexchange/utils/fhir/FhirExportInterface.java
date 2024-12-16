package de.samply.samplexchange.utils.fhir;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.Bundle;

/**
 * Superclass of fhir exporter.
 */
public abstract class FhirExportInterface {

    public FhirContext ctx;

    /**
     * Super export.
     */
    public abstract Boolean export(Bundle bundle);
}

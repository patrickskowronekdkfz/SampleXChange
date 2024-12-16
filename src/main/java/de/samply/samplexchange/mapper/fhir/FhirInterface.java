package de.samply.samplexchange.mapper.fhir;

import de.samply.samplexchange.configuration.Configuration;
import lombok.Getter;

/**
 * Super class.
 */
public abstract class FhirInterface {

    @Getter
    Configuration configuration;

    protected FhirInterface(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Super transfering.
     */
    public abstract void transfer() throws Exception;
}

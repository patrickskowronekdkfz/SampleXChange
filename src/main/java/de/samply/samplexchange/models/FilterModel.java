package de.samply.samplexchange.models;

import java.util.List;

/**
 * Json class for filtering attr.
 */
public class FilterModel {
    /**
     * Patient related attrs.
     */
    public Patient patient;

    /**
     * Specimen related attrs.
     */
    public Specimen specimen;

    /**
     * Class fppr specimen related attrs.
     */
    public static class Specimen {
        public List<String> ids;
        public List<String> fhirProfile;
        public List<String> orgaFilter;
    }

    /**
     * Class fppr specimen related attrs.
     */
    public static class Patient {
        public List<String> ids;
        public List<String> fhirProfile;
        public List<String> orgaFilter;
    }
}

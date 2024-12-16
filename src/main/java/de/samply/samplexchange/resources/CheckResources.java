package de.samply.samplexchange.resources;

import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;

import java.util.Objects;

/**
 * Static methods for filtering specific profiles.
 */
public class CheckResources {

    /**
     * Checks if Obersvation is a bbmri.de cause of death.
     */
    public static boolean checkBbmriCauseOfDeath(Observation observation) {
        return observation.getCode().getCodingFirstRep().getCode().equals("68343-3");
    }

    /**
     * Checks if Organization is a bbmri.de Collection.
     */
    public static boolean checkBbmriCollection(Organization organization) {
        return organization
                .getMeta()
                .getProfile()
                .equals("https://fhir.bbmri.de/StructureDefinition/Collection");
    }

    /**
     * Checks if Organization is a bbmri.de Bionbank.
     */
    public static boolean checkBbmriBiobank(Organization organization) {
        return organization
                .getMeta()
                .getProfile()
                .equals("https://fhir.bbmri.de/StructureDefinition/Biobank");
    }

    /**
     * Checks if Organization is a mii Bionbank.
     */
    public static boolean checkMmiBiobank(Organization organization) {
        return organization
                .getMeta()
                .getProfile()
                .equals(
                        "https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/StructureDefinition/Organization");
    }

    /**
     * Checks if Obersvation is a MII KDS cause of death.
     */
    public static boolean checkMiiCauseOfDeath(Condition condition) {
        return (Objects.equals(
                condition.getCategoryFirstRep().getCodingFirstRep().getCode(), "16100001")
                && Objects.equals(
                condition.getCategoryFirstRep().getCodingFirstRep().getSystem(),
                "http://snomed.info/sct"))
                || (Objects.equals(
                condition.getCategoryFirstRep().getCodingFirstRep().getSystem(), "http://loinc.org")
                && Objects.equals(
                condition.getCategoryFirstRep().getCodingFirstRep().getCode(), "79378-6"));
    }
}

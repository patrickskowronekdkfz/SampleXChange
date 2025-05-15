package de.samply.samplexchange.resources;

import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Reference;

import java.util.List;

/**
 * Collectionmappings for converting between bbmri.de and MII KDS.
 */
public class CollectionMapping extends ConvertClass<Organization, Organization> {

    String refToParent;

    Organization bbmriOrga;

    Organization miiOrga;

    BiobankMapping biobankMapping = new BiobankMapping();

    @Override
    public void fromBbmri(Organization resource) {
        refToParent = resource.getPartOf().getReference();
        this.bbmriOrga = resource;
    }

    @Override
    public void fromMii(Organization resource) {
        refToParent = resource.getPartOf().getReference();
        this.miiOrga = resource;
    }

    @Override
    public Organization toBbmri() {
        biobankMapping.fromMii(miiOrga);
        Organization toBbmri = biobankMapping.toBbmri();

        toBbmri.setMeta(new Meta().setProfile(List.of(new CanonicalType("https://fhir.bbmri.de/StructureDefinition/Collection"))));
        toBbmri.setPartOf(new Reference(this.refToParent));

        return toBbmri;
    }

    @Override
    public Organization toMii() {
        biobankMapping.fromBbmri(bbmriOrga);
        Organization toMii = biobankMapping.toMii();

        toMii.setMeta(new Meta().setProfile(List.of(new CanonicalType("https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/StructureDefinition/Organization"))));
        toMii.setPartOf(new Reference(this.refToParent));

        return toMii;
    }
}

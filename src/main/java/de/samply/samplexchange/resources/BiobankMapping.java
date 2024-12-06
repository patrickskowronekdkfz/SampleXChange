package de.samply.samplexchange.resources;

import java.util.List;
import java.util.Objects;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Organization.OrganizationContactComponent;
import org.hl7.fhir.r4.model.StringType;

/** Organizationmappings for converting between bbmri.de and MII KDS. */
public class BiobankMapping extends ConvertClass<Organization, Organization> {

  public static final String MII_BIOBANK_DESCRIPTION =
      "https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/StructureDefinition/BeschreibungSammlung";
  public static final String BBMRI_BIOBANK_DESCRIPTION =
      "https://fhir.bbmri.de/StructureDefinition/OrganizationDescription";
  public static final String BBMRI_ID = "http://www.bbmri-eric.eu/";
  String id;
  String bbmriId;

  String name;

  List<OrganizationContactComponent> contacts;

  List<StringType> alias;

  String bioBankDescription;

  private void getBbmriId(Organization organization) {

    if (Objects.nonNull(organization.getIdentifierFirstRep().getSystem()) 
        && (organization.getIdentifierFirstRep().getSystem().equals(BBMRI_ID))) {
      bbmriId = organization.getIdentifierFirstRep().getValue();
    }
  }

  @Override
  public void fromBbmri(org.hl7.fhir.r4.model.Organization resource) {
    id = resource.getId();
    getBbmriId(resource);

    name = resource.getName();
    alias = resource.getAlias();

    for (Extension extension : resource.getExtension()) {
      if (extension.getUrl().equals(BBMRI_BIOBANK_DESCRIPTION)) {
        this.bioBankDescription = extension.getValue().primitiveValue();
      }
    }

    contacts = resource.getContact();
  }

  @Override
  public void fromMii(org.hl7.fhir.r4.model.Organization resource) {
    id = resource.getId();
    getBbmriId(resource);

    name = resource.getName();
    alias = resource.getAlias();

    for (Extension extension : resource.getExtension()) {
      if (extension.getUrl().equals(MII_BIOBANK_DESCRIPTION)) {
        this.bioBankDescription = extension.getValue().primitiveValue();
      }
    }

    this.contacts = resource.getContact();
  }

  @Override
  public org.hl7.fhir.r4.model.Organization toBbmri() {
    Organization bbmri = new Organization();
    bbmri.setId(this.id);

    bbmri.setMeta(
        new Meta()
            .setProfile(
                List.of(new CanonicalType("https://fhir.bbmri.de/StructureDefinition/Biobank"))));

    if (Objects.nonNull(bbmriId)) {
      bbmri.setIdentifier(List.of(new Identifier().setSystem(BBMRI_ID).setValue(bbmriId)));
    }

    if (Objects.nonNull(bioBankDescription)) {
      bbmri.addExtension(
          new Extension()
              .setUrl(BBMRI_BIOBANK_DESCRIPTION)
              .setValue(new StringType(bioBankDescription)));
    }

    if (Objects.nonNull(name)) {
      bbmri.setName(name);
    }

    if (Objects.nonNull(alias)) {
      bbmri.setAlias(alias);
    }

    if (!this.contacts.isEmpty()) {
      for (OrganizationContactComponent contact : this.contacts) {
        OrganizationContactComponent item = bbmri.addContact();
        item.setPurpose(
            new CodeableConcept()
                .addCoding(
                    new Coding()
                        .setSystem("http://terminology.hl7.org/CodeSystem/contactentity-type")
                        .setCode("RESEARCH")));

        item.setName(
            new HumanName()
                .setFamily(contact.getName().getFamily())
                .setGiven(contact.getName().getGiven()));
        item.setTelecom(contact.getTelecom());

        item.setAddress(
            new Address()
                .setCity(contact.getAddress().getCity())
                .setCountry(contact.getAddress().getCountry())
                .setPostalCode(contact.getAddress().getPostalCode())
                .setLine(contact.getAddress().getLine()));

        for (Extension e : contact.getExtension()) {
          if (e.getUrl()
              .equals(
                  "https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/StructureDefinition/KontaktRolle")) {
            if (e.getValue().primitiveValue().equals("Direktor")) {
              e.setValue(new StringType("Director"));
            }

            item.addExtension(
                "https://fhir.bbmri.de/StructureDefinition/ContactRole", e.getValue());
            CodeableConcept codeableConcept = item.getPurpose();
            codeableConcept.getCodingFirstRep().setCode("ADMIN");
            item.setPurpose(codeableConcept);
          }
        }
      }
    }

    return bbmri;
  }

  @Override
  public org.hl7.fhir.r4.model.Organization toMii() {
    Organization mii = new Organization();
    mii.setId(this.id);

    mii.setMeta(
        new Meta()
            .setProfile(
                List.of(
                    new CanonicalType(
                        "https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/StructureDefinition/Organization"))));

    if (Objects.nonNull(bbmriId)) {
      mii.setIdentifier(List.of(new Identifier().setSystem(BBMRI_ID).setValue(bbmriId)));
    }

    if (Objects.nonNull(bioBankDescription)) {
      mii.addExtension(
          new Extension()
              .setUrl(MII_BIOBANK_DESCRIPTION)
              .setValue(new StringType(bioBankDescription)));
    }

    if (Objects.nonNull(name)) {
      mii.setName(name);
    }

    if (Objects.nonNull(alias)) {
      mii.setAlias(alias);
    }

    if (!this.contacts.isEmpty()) {
      for (OrganizationContactComponent contact : this.contacts) {
        OrganizationContactComponent item = mii.addContact();
        item.setPurpose(
            new CodeableConcept()
                .addCoding(
                    new Coding()
                        .setSystem(
                            "https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/CodeSystem/ContactType")
                        .setCode("RESEARCH")));

        item.setName(
            new HumanName()
                .setFamily(contact.getName().getFamily())
                .setGiven(contact.getName().getGiven()));
        item.setTelecom(contact.getTelecom());

        item.setAddress(
            new Address()
                .setCity(contact.getAddress().getCity())
                .setCountry(contact.getAddress().getCountry())
                .setPostalCode(contact.getAddress().getPostalCode())
                .setLine(contact.getAddress().getLine()));

        for (Extension e : contact.getExtension()) {
          if (e.getUrl().equals("https://fhir.bbmri.de/StructureDefinition/ContactRole")) {
            if (e.getValue().primitiveValue().equals("Director")) {
              e.setValue(new StringType("Direktor"));
            }
            item.addExtension(
                "https://www.medizininformatik-initiative.de/fhir/ext/modul-biobank/StructureDefinition/KontaktRolle",
                e.getValue());
          }
        }
      }
    }

    return mii;
  }
}

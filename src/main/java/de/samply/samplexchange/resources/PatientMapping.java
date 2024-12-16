package de.samply.samplexchange.resources;

import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGenderEnumFactory;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;

import java.util.Date;
import java.util.Objects;

/**
 * Patientmappings for converting between bbmri.de and MII KDS.
 */
public class PatientMapping
        extends ConvertClass<org.hl7.fhir.r4.model.Patient, org.hl7.fhir.r4.model.Patient> {

    // MII data
    String miiId = "";

    // BBMRI data
    String bbmriId = "";

    Identifier identifier;

    Date brithDate;
    boolean patientDeceased;
    Date patientDeceasedDateTime;
    String gender;

    public PatientMapping() {
    }

    public String getMiiId() {
        return miiId;
    }

    public void setMiiId(String id) {
        this.miiId = id;
    }

    public String getBbmriId() {
        return bbmriId;
    }

    public void setBbmriId(String id) {
        this.bbmriId = id;
    }

    @Override
    public void fromBbmri(org.hl7.fhir.r4.model.Patient resource) {
        this.bbmriId = resource.getId();
        this.brithDate = resource.getBirthDate();

        if (resource.hasGender()) {
            this.gender = resource.getGender().toCode();
        }

        if (resource.hasDeceased()) {
            this.patientDeceased = true;
            this.patientDeceasedDateTime = resource.getDeceasedDateTimeType().getValue();
        } else {
            this.patientDeceased = false;
        }
    }

    @Override
    public void fromMii(org.hl7.fhir.r4.model.Patient resource) {
        this.miiId = resource.getId();
        this.brithDate = resource.getBirthDate();

        if (resource.hasGender()) {
            this.gender = resource.getGender().toCode();
        }

        if (resource.getDeceasedBooleanType().equals(new BooleanType(true))) {
            this.patientDeceased = true;
            this.patientDeceasedDateTime = resource.getDeceasedDateTimeType().getValue();
        } else {
            this.patientDeceased = false;
        }
    }

    @Override
    public org.hl7.fhir.r4.model.Patient toBbmri() throws Exception {
        org.hl7.fhir.r4.model.Patient patient = new org.hl7.fhir.r4.model.Patient();
        patient.setMeta(
                new Meta().addProfile("https://fhir.simplifier.net/bbmri.de/StructureDefinition/Patient"));

        if (Objects.nonNull(this.gender)) {
            patient.setGender(new AdministrativeGenderEnumFactory().fromCode(this.gender));
        }

        if (Objects.nonNull(this.brithDate)) {
            patient.setBirthDate(brithDate);
        }

        if (bbmriId.isEmpty() && !miiId.isEmpty()) {
            this.bbmriId = this.miiId;
        }

        patient.setId(bbmriId);

        if (this.patientDeceased) {
            patient.getDeceasedDateTimeType().setValue(this.patientDeceasedDateTime);
        }

        return patient;
    }

    @Override
    public org.hl7.fhir.r4.model.Patient toMii() throws Exception {
        org.hl7.fhir.r4.model.Patient patient = new org.hl7.fhir.r4.model.Patient();
        patient.setMeta(
                new Meta()
                        .addProfile(
                                "https://www.medizininformatik-initiative.de/fhir/core/modul-person/StructureDefinition/Patient"));

        if (!bbmriId.isEmpty() && miiId.isEmpty()) {
            this.miiId = this.bbmriId;
        }

        patient.setId(miiId);

        if (Objects.nonNull(this.gender)) {
            patient.setGender(new AdministrativeGenderEnumFactory().fromCode(this.gender));
        }

        if (Objects.nonNull(this.brithDate)) {
            patient.setBirthDate(brithDate);
        }

        if (this.patientDeceased) {
            patient.getDeceasedDateTimeType().setValue(this.patientDeceasedDateTime);
        }

        return patient;
    }
}

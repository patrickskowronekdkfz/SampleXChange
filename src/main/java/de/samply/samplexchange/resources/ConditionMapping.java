package de.samply.samplexchange.resources;

import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Organizationmappings for converting between bbmri.de and MII KDS. */
@Slf4j
public class ConditionMapping
    extends ConvertClass<org.hl7.fhir.r4.model.Condition, org.hl7.fhir.r4.model.Condition> {

  private static final String ICD_10_GM_CODE_SYSTEM = "http://fhir.de/CodeSystem/bfarm/icd-10-gm";

  public ConditionMapping() {
  }

  String bbmriId = "";
  String bbmriSubject;
  Date onset;
  String diagnosisIcd10Who;
  String diagnosisSnomed;

  String diagnosisIcd10Gm;
  String diagnosisIcd9;

  String miiId = "";
  String miiSubject;


  @Override
  public void fromBbmri(org.hl7.fhir.r4.model.Condition resource) {
    this.bbmriId = resource.getId();
    this.bbmriSubject = resource.getSubject().getReference();
    this.onset = resource.getOnsetDateTimeType().getValue();

    for (Coding coding : resource.getCode().getCoding()) {
      if (Objects.equals(coding.getSystem(), "http://hl7.org/fhir/sid/icd-10")) {
        this.diagnosisIcd10Who = coding.getCode();
      } else if (Objects.equals(coding.getSystem(), ICD_10_GM_CODE_SYSTEM)) {
        this.diagnosisIcd10Gm = coding.getCode();
      } else if (Objects.equals(coding.getSystem(), "http://hl7.org/fhir/sid/icd-9")) {
        this.diagnosisIcd9 = coding.getCode();
      } else {
        log.info("Unsupported Coding");
      }
    }
  }

  @Override
  public void fromMii(org.hl7.fhir.r4.model.Condition resource) {
    this.miiId = resource.getId();

    for (Coding coding : resource.getCode().getCoding()) {
      if (Objects.equals(coding.getSystem(), ICD_10_GM_CODE_SYSTEM)) {
        this.diagnosisIcd10Gm = coding.getCode();
        break;
      }

      if (Objects.equals(coding.getSystem(), "http://snomed.info/sct")) {
        log.info("Snomed-CT diagnosis mapping not supported");
        continue;
      }
      log.info("Diagnosis found which is not supported");
    }

    this.miiSubject = resource.getSubject().getReference();


    this.onset = resource.getOnsetDateTimeType().getValue();
  }

  @Override
  public org.hl7.fhir.r4.model.Condition toBbmri() {
    org.hl7.fhir.r4.model.Condition condition = new org.hl7.fhir.r4.model.Condition();
    condition.setMeta(new Meta().addProfile("https://fhir.bbmri.de/StructureDefinition/Condition"));

      this.bbmriId = miiId;

      this.bbmriSubject = this.miiSubject;

    condition.setId(bbmriId);

    condition.setSubject(new Reference(bbmriSubject));

    condition.getOnsetDateTimeType().setValue(this.onset);

    if (Objects.nonNull(this.diagnosisIcd10Gm)) {
      condition
          .getCode()
          .getCodingFirstRep()
          .setSystem(ICD_10_GM_CODE_SYSTEM)
          .setCode(this.diagnosisIcd10Gm);
    }

    return condition;
  }

  @Override
  public org.hl7.fhir.r4.model.Condition toMii() {
    org.hl7.fhir.r4.model.Condition condition = new org.hl7.fhir.r4.model.Condition();
    condition.setMeta(
        new Meta()
            .addProfile(
                "https://www.medizininformatik-initiative.de/fhir/core/modul-diagnose/StructureDefinition/Diagnose"));

    this.miiId = bbmriId;
    condition.setId(miiId);

    this.miiSubject = this.bbmriSubject;

    condition.setSubject(new Reference(miiSubject));

    condition.getOnsetDateTimeType().setValue(this.onset);

    if (Objects.nonNull(this.diagnosisIcd10Gm)) {
      condition
          .getCode()
          .getCodingFirstRep()
          .setSystem(ICD_10_GM_CODE_SYSTEM)
          .setCode(this.diagnosisIcd10Gm);
    }
    if (Objects.isNull(this.diagnosisIcd10Gm) && Objects.nonNull(this.diagnosisIcd10Who)) {
      condition
          .getCode()
          .getCodingFirstRep()
          .setSystem(ICD_10_GM_CODE_SYSTEM)
          .setCode(this.diagnosisIcd10Who);
    }
    if (Objects.nonNull(this.diagnosisSnomed)) {
        log.error("This is not supported");
    }

    return condition;
  }
}

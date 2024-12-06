package de.samply.samplexchange.readers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Specimen;

/** Reader class for fhir Patients. */

@Slf4j
public class FhirPatientIdReader {

  FhirContext ctx;
  
  FhirSpecimenReader fhirSpecimenReader = new FhirSpecimenReader(ctx);
  
  FhirResourceReader fhirResourceReader = new FhirResourceReader(ctx);

  /** Constructor. */
  public FhirPatientIdReader(FhirContext ctx) {
    this.ctx = ctx;
  }
  
  /** Fetches all patient ids. */
  public Set<String> fetchPatientIds(IGenericClient client, String startResource) {
    if (Objects.equals(startResource, "Specimen")) {
      return this.getSpecimenPatients(client);
    } else {
      return this.getPatientRefs(client);
    }
  }
  
  /** Fetches all patient ids which have a specimen. */
  public Set<String> getSpecimenPatients(IGenericClient sourceClient) {
    List<IBaseResource> specimens = fhirSpecimenReader.fetchSpecimenResources(sourceClient);
    HashSet<String> patientRefs = new HashSet<>();
    for (IBaseResource specimen : specimens) {
      Specimen s = (Specimen) specimen;
      patientRefs.add(s.getSubject().getReference());
    }
    return patientRefs;
  }
  
  private HashSet<String> getPatientRefs(IGenericClient sourceClient) {
    List<Patient> patients = fhirResourceReader.fetchResources(Patient.class, sourceClient);
    HashSet<String> patientRefs = new HashSet<>();

    for (IBaseResource patient : patients) {
      patientRefs.add(patient.getIdElement().getValue());
    }
    return patientRefs;
  }
  
  

  

}

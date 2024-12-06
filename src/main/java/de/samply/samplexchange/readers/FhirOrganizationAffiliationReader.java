package de.samply.samplexchange.readers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;


/** Reader class for fhir OrganizationAffiliations. */
@Slf4j
public class FhirOrganizationAffiliationReader {

  FhirContext ctx;

  /** constructor. */
  public FhirOrganizationAffiliationReader(FhirContext ctx) {
    this.ctx = ctx;
  }
  
  /** Fetches all organizations resources. */
  public List<IBaseResource> fetchOrganizations(IGenericClient client) {
    List<IBaseResource> resourceList = new ArrayList<>();

    Bundle bundle =
        client.search().forResource(Organization.class).returnBundle(Bundle.class).execute();

    resourceList.addAll(BundleUtil.toListOfResources(ctx, bundle));

    while (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
      bundle = client.loadPage().next(bundle).execute();
      resourceList.addAll(BundleUtil.toListOfResources(ctx, bundle));
    }
    return resourceList;
  }



}

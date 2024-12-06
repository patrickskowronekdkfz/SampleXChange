package de.samply.samplexchange.resources;

import java.time.LocalDate;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.BaseResource;

/** Adds meta tag to fhir resource.
 * This mapping adds a tag that TransFAIR - version - date - mapping type
 */
public class MetaMapping {

  private String version;

  private String mapping;

  public MetaMapping(String version, String mapping) {
    this.version = version;
    this.mapping = mapping;
  }

  /** Adds tag. */
  public IBaseResource tagResource(BaseResource base) {
    base.getMeta().addTag()
        .setCode("TransFAIR " + version + " - " + LocalDate.now() + " - " + mapping);
    return base;
  }

}

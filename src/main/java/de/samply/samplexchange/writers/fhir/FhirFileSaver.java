package de.samply.samplexchange.writers.fhir;

import ca.uhn.fhir.context.FhirContext;
import de.samply.samplexchange.utils.fhir.FhirExportInterface;
import java.io.FileWriter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;

/** Interface to post data to local file system. */
@Slf4j
public class FhirFileSaver extends FhirExportInterface {

  String path;

  /** Filer Saver constructor. */
  public FhirFileSaver(FhirContext context, String path) {
    this.path = path;
    this.ctx = context;
  }

  /** export. */
  @Override
  public Boolean export(Bundle bundle) {

    String output = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
    try (FileWriter myWriter = new FileWriter(path + bundle.getId() + ".json")) {
      myWriter.write(output);
    } catch (IOException e) {
      log.error("An error occurred while writing output to file.");
      e.printStackTrace();
    } 

    return true;
  }
}

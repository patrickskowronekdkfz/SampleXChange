package de.samply.samplexchange.utils.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import de.samply.samplexchange.configuration.Configuration;
import de.samply.samplexchange.repository.fhir.FhirServerSaver;
import de.samply.samplexchange.utils.fhir.clients.FhirClient;
import de.samply.samplexchange.writers.fhir.FhirFileSaver;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/** Main Class for working with fhir mappings. */
@Slf4j
public class FhirComponent {

  private final FhirContext ctx;
  /** Configuration. */

  public Configuration configuration;

  /** transferController. */
  public FhirTransfer transferController;

  /** Source fhir client. */
  private FhirClient sourceFhirServer;

  /** Fhir export interface. */
  private FhirExportInterface fhirExportInterface;

  /** Constructor. */
  public FhirComponent(Configuration configuration) throws Exception {
    this.configuration = configuration;
    ctx = FhirContext.forR4();
    ctx.getRestfulClientFactory().setSocketTimeout(300 * 1000);

    this.transferController = new FhirTransfer(ctx);
  }

  /** Returns source fhir client. */
  public IGenericClient getSourceFhirServer()
      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

    if (Objects.nonNull(sourceFhirServer)) {
      return this.sourceFhirServer.getClient();
    }

    sourceFhirServer =
        new FhirClient(
            ctx, configuration.getSourceServer(), configuration.isFhirClientAcceptSsl());
    setAuth(
        sourceFhirServer,
        configuration.getSourceServerUsername(),
        configuration.getSourceServerPassword());
    log.info("Start collecting Resources from FHIR server {}", configuration.getSourceServer());

    return sourceFhirServer.getClient();
  }

  private void setAuth(FhirClient sourceClient, String user, String password) {
    if (!user.isBlank() && !password.isBlank()) {
      sourceClient.setBasicAuth(
          configuration.getSourceServerUsername(), configuration.getSourceServerPassword());
    }
  }

  /** Returns fhir export interface. */
  public FhirExportInterface getFhirExportInterface()
      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    if (Objects.nonNull(fhirExportInterface)) {
      return fhirExportInterface;
    }

    if (!configuration.getFileExportPath().isBlank()) {
      log.info("Exporting resources to file system " + configuration.getFileExportPath());
      this.fhirExportInterface = new FhirFileSaver(ctx, configuration.getFileExportPath());
    } else {
      FhirServerSaver fhirServerSaver =
          new FhirServerSaver(
              ctx, configuration.getTargetServer(), configuration.isFhirClientAcceptSsl());
      setAuth(
          fhirServerSaver.getClient(),
          configuration.getTargetServerUsername(),
          configuration.getTargetServerPassword());
      log.info("Exporting resources to FHIR server " + configuration.getTargetServer());
      fhirExportInterface = fhirServerSaver;
    }

    return fhirExportInterface;
  }
}

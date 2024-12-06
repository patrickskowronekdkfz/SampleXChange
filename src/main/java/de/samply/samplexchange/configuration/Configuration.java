package de.samply.samplexchange.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Environment configuration parameters. */
@Data
@Component
public class Configuration {

  @Value("${app.version}")
  private String appVersion;

  @Value("${tf.fhir.server.source.address}")
  private String sourceFhirServer;

  @Value("${tf.fhir.server.source.username}")
  private String sourceFhirServerUsername;

  @Value("${tf.fhir.server.source.password}")
  private String sourceFhirServerPassword;

  @Value("${tf.resources.start}")
  private String startResource;

  @Value("${tf.resources.filter}")
  private String resourcesFilter;

  @Value("${tf.profile}")
  private String profile;

  @Value("${tf.fhir.server.target.address}")
  private String targetFhirServer;

  @Value("${tf.fhir.server.target.username}")
  private String targetFhirServerUsername;

  @Value("${tf.fhir.server.target.password}")
  private String targetFhirServerPassword;

  @Value("${tf.fhir.filesystem}")
  private boolean saveToFileSystem;

  @Value("${tf.export.file.path}")
  private String exportFilePath;

  @Value("${tf.fhir.server.acceptssl}")
  private boolean fhirClientAcceptSsl;
}

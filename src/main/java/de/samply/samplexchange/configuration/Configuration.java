package de.samply.samplexchange.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Environment configuration parameters.
 */
@Data
@Component
public class Configuration {

    @Value("${app.version}")
    private String appVersion;

    @Value("${source.url}")
    private String sourceServer;

    @Value("${source.username}")
    private String sourceServerUsername;

    @Value("${source.password}")
    private String sourceServerPassword;

    @Value("${profile}")
    private String profile;

    @Value("${target.url}")
    private String targetServer;

    @Value("${target.username}")
    private String targetServerUsername;

    @Value("${target.password}")
    private String targetServerPassword;

    @Value("${fileexportpath}")
    private String fileExportPath;

    @Value("${disablessl}")
    private boolean fhirClientAcceptSsl;
}

# SampleXChange

SampleXChange is a lightweight tool designed for the conversion of FHIR Resources between [BBMRI Profiles](https://simplifier.net/bbmri.de/~resources?category=Profile) and [MII KDS Profiles](https://simplifier.net/medizininformatikinitiative-modulbiobank/~resources?category=Profile), and vice versa. This tool is designed for standardized operation between a biobank and a data integration center.

## Environment Configuration
To configure the tool, the following environment variables must be set:

### Operation Mode
- `PROFILE`: Defines the operation mode of the tool. Possible values:
    - `BBMRI2MII`: Convert BBMRI Biosamples to MII KDS Biosamples.
    - `MII2BBMRI`: Convert MII KDS Biosamples to BBMRI Biosamples.

### SSL Configuration
- `DISABLESSL`: If set to `true`, SSL verification will be disabled, allowing the tool to accept self-signed certificates. **(Use with caution in production environments!)**

### FHIR Server Configuration
#### Source FHIR Server
- `SOURCE_URL`: The URL of the source FHIR server.
- `SOURCE_USERNAME`: (Optional) Username for basic authentication.
- `SOURCE_PASSWORD`: (Optional) Password for basic authentication.

#### Target FHIR Server
- `TARGET_URL`: The URL of the target FHIR server.
- `TARGET_USERNAME`: (Optional) Username for basic authentication.
- `TARGET_PASSWORD`: (Optional) Password for basic authentication.

## Usage
Set up the required environment variables as per your use case. And run the program either locally or with docker. Please check if all resource are as diesired on the target server.

## Contributions
Contributions, issues, and feature requests are welcome! Please feel free to submit a pull request or open an issue in the repository.

## License
This project is licensed under the Apache 2.0 License. See the [LICENSE](./LICENSE) file for details.


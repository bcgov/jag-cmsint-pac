# jag-cmsint-pac

[![Lifecycle:Maturing](https://img.shields.io/badge/Lifecycle-Maturing-007EC6)](git@github.com:bcgov/jag-cmsint-pac.git)
[![Maintainability](https://api.codeclimate.com/v1/badges/5275a4eee4b22b46c836/maintainability)](https://codeclimate.com/github/bcgov/jag-cmsint-pac/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/5275a4eee4b22b46c836/test_coverage)](https://codeclimate.com/github/bcgov/jag-cmsint-pac/test_coverage)

## Build

Since the application is composed by 3 modules, you can build either all at once or individually.

To build all run:

```bash
mvn clean install
```

To build individually, use one of the provided Maven profiles (_cmsint-pac-extractor_, _cmsint-pac-loader_, or
_cmsint-pac-transformer_)

```bash
mvn clean install -P<desired profile>
```

## Test

To run tests, use

```
mvn clean verify
```

## Environment Variables

For exact values of secrets not specified here, refer to the secrets for the Dev environment in OpenShift.

### Extractor Application

| Variable                         |                                           Example Value |
|----------------------------------|--------------------------------------------------------:|
| PAC_QUEUE_NAME                   |                                               pac-queue |
| PAC_ROUTING_KEY                  |                                                     PAC |
| RABBIT_EXCHANGE_NAME             |                                                EXCHANGE |
| CMSINT_ORDS_HOST                 | http://example.com/ - make sure to include a trailing / |
| CMS_ORDS_HOST                    | http://example.com/ - make sure to include a trailing / |
| ORDS_USERNAME                    |                                                    user |
| ORDS_PASSWORD                    |                                   super-secret-password |
| RABBIT_MQ_HOST                   |               http://ecample.com - no trailing / needed |
| RABBIT_MQ_USERNAME               |                                                username |
| RABBIT_MQ_PASSWORD               |                                   super-secret-password |
| EXTRACTOR_SERVER_PORT            |                                                    8080 |
| EXTRACTOR_MANAGEMENT_SERVER_PORT |                                                    8081 |

### Transformer Application

| Variable                           |                                           Example Value |
|------------------------------------|--------------------------------------------------------:|
| PAC_QUEUE_NAME                     |                                               pac-queue |
| PAC_ROUTING_KEY                    |                                                     PAC |
| RABBIT_EXCHANGE_NAME               |                                                EXCHANGE |
| PAC_SERVICE_URL                    | http://example.com/ - make sure to include a trailing / |
| CMS_DATE_PATTERN                   |    yyyy-MM-dd (any valid Java date pattern is accepted) |
| PAC_DATE_PATTERN                   |    yyyy-MM-dd (any valid Java date pattern is accepted) |
| ICS_DATE_PATTERN                   |    yyyy-MM-dd (any valid Java date pattern is accepted) |
| ORDS_USERNAME                      |                                                    user |
| ORDS_PASSWORD                      |                                   super-secret-password |
| CMS_ORDS_HOST                      | http://example.com/ - make sure to include a trailing / |
| RABBIT_MQ_HOST                     |               http://ecample.com - no trailing / needed |
| RABBIT_MQ_USERNAME                 |                                                username |
| RABBIT_MQ_PASSWORD                 |                                   super-secret-password |
| TRANSFORMER_SERVER_PORT            |                                                    8080 |
| TRANSFORMER_MANAGEMENT_SERVER_PORT |                                                    8081 |

### Loader Application

| Variable                      |                                           Example Value |
|-------------------------------|--------------------------------------------------------:|
| PAC_QUEUE_NAME                |                                               pac-queue |
| PAC_ROUTING_KEY               |                                                     PAC |
| RABBIT_EXCHANGE_NAME          |                                                EXCHANGE |
| PAC_SERVICE_URL               | http://example.com/ - make sure to include a trailing / |
| WS_AUTH_USER                  |                              PAC_SERVICE_URL's username |
| WS_AUTH_PASS                  |                              PAC_SERVICE_URL's password |
| RABBIT_MQ_HOST                |               http://ecample.com - no trailing / needed |
| RABBIT_MQ_USERNAME            |                                                username |
| RABBIT_MQ_PASSWORD            |                                   super-secret-password |
| LOADER_SERVER_PORT            |                                                    8080 |
| LOADER_MANAGEMENT_SERVER_PORT |                                                    8081 |
| ORDS_USERNAME                 |                                                    user |
| ORDS_PASSWORD                 |                                   super-secret-password |
| CMS_ORDS_HOST                 | http://example.com/ - make sure to include a trailing / |

## Image Build for Containerized Environments

The [Dockerfile](Dockerfile) leverages the Maven profiles and it is capable of produce any of the image modules by
passing the correct Build Argument.

```bash
docker build --build-arg MVN_PROFILE=<desired maven profile> .
```

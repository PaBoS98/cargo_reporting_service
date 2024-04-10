# Cargo Reporting Service

The **Cargo Reporting Service** is a mock service designed to emulate activity and test the stability of the *Cargo Tracking Service*.

## Usage

### Swagger

The Swagger UI is available for easy API exploration. You can access it at `/swagger-ui.html`.

#### Admin API

In the "Admin API" tab, you can find a POST request for creating a client token. Use the parameter `clientId=trustedClientId` to create a trusted token for the cargo tracking service.

#### Public API

In the "Public API" tab, you can find a POST request that accepts an integer value. This value is randomly split between updating existing cargo records and creating new ones.
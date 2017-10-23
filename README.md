# Code BFF

[![Build Status](https://travis-ci.org/8451/code-bff.svg?branch=develop)](https://travis-ci.org/8451/code-bff)
[![forthebadge](http://forthebadge.com/images/badges/built-by-developers.svg)](http://forthebadge.com)

## Environment Variables

|Environment Variable |Description                                               |Example|
|---------------------|----------------------------------------------------------|-------|
|CODE_DB_CONNSTR      |The URI Connection String to connect to the Mongo Database|mongodb://user:password@host:port|
|CODE_WEB_URI         |The base address for the Client Application (ng)          |http://code-web-ui.azurewebsites.net|
|CODE_SERVICE_URI     |The URI of the running service instance                   |http://code-service.azurewebsites.net/api/v1|
|CODE_DATABASE_NAME   |The name to use for the MongoDB database                  |code_service_prod|
|JWT_PRIVATE_KEY      |Private key in PKCS8 format                               |none provided|
|JWT_PUBLIC_KEY       |Public key in X.509v3 format                              |none provided|

**Note:** the ugly DB name comes from Azure. Sorry. :(

## Deployment Instructions

**Note:** Use the azure-deploy.py script provided in the deploy repository

```sh
mvn package
python3 azure-deploy.py -d target/ --host ${AZURE_HOST} -u code-bff\\${DEPLOY_USER}
```

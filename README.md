# Code BFF

[![Build Status](https://travis-ci.org/8451/code-bff.svg?branch=develop)](https://travis-ci.org/8451/code-bff)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d350bf4f75534c88b31fe0bae3e129a1)](https://www.codacy.com/app/tomd8451/code-bff?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=8451/code-bff&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/8451/code-bff/badge.svg?branch=develop)](https://coveralls.io/github/8451/code-bff?branch=develop)  

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

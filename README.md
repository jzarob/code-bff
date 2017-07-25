# code-bff

# code-service

## Environment Variables

|Environment Variable |Description                                               |Example|
|---------------------|----------------------------------------------------------|-------|
|CUSTOMCONNSTR_code-db|The URI Connection String to connect to the Mongo Database|mongodb://user:password@host:port|
|CODE_WEB_URI         |The base address for the Client Application (ng)          |http://code-web-ui.azurewebsites.net|
|CODE_SERVICE_URI     |The URI of the running service instance                   |http://code-service.azurewebsites.net/api/v1|
|CODE_DATABASE_NAME   |The name to use for the MongoDB database                  |code_service_prod|
**Note:** the ugly DB name comes from Azure. Sorry. :(

## Deployment Instructions

**Note:** Use the azure-deploy.py script provided in the services repository

```sh
mvn package
python3 azure-deploy.py -d target/ --host ${AZURE_HOST} -u code-bff\\${DEPLOY_USER}
```

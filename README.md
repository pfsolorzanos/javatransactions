To run these microservices:

1. You might need to build all images required, the following commands were used:

`docker build -t service-client-img .`

`docker build -t service-account-img .`

You must run each command from each microservice folder, which contains a specific Dockerfile.

2. Push the images to your DockerHub, these commands were used:
3. 
`docker tag service-client-img:latest docker-user/service-client-img:latest`
`docker push docker-user/service-client-img:latest`

`docker tag service-account-img:latest docker-user/service-account-img:latest`
`docker push docker-user/service-account-img:latest`


3. Set the enviroment variables for each microservice .env file , setting the values for database authentication, and the rest of them.
4. Create an external Docker network called financial-network:

`docker network create financial-network`

5. Run the following command from each microservice folder:

   `docker-compose up -d`

6. Execute endpoints from the provided Postman .json files.

# NinjaRMM 
REST API to monitor and management devices. The backend allows the customer to create devices and select the services available
 to his/her account. The customer can check the total cost of the services and devices that he/she has 
in his/her account.

### Tech Stack
This project was developed with this tech stack. In order to run the project you need to install all of them in your
local machine, except PostgreSQL.
- Java 11
- PostgreSQL
- Spring 
- Flyway
- Gradle
- Docker (docker compose) 
- Swagger

### Docker
Docker compose downloads PostgreSQL and Adminer images. Adminer runs in http://localhost:8090/
1. System field`PostgreSQL`
2. Server `db`
3. Username `admin`
4. Password `admin`
5. Database `ninja`

### Build and Run
Execute this steps after install the tech stack, except PostgreSQL:
1. Go to `[HOME]/rmm-services-server-app`
2. Run `docker-compose up -d`
3. Run `./gradlew clean build` (This also runs tests) 
4. Run `./gradlew bootRun`

When the system runs for the first time, it will insert data in tables:
- operating_system: `Windows, Mac, Linux`
- customer: `Test Customer`
- service: `Register Device, Antivirus, Antivirus Windows, Antivirus Mac, Cloudberry, PSA, TeamViewer`

### Endpoints
You can check available endpoints in this url: http://localhost:8080/swagger-ui

- Credential to get access to endpoints:
    * User: `admin` 
    * Password: `admin` 
- *customer-service-mm-controller:* Adds, gets and deletes services from customer account
    * Only selectable services can be added to the account
- *device-controller:* Creates, updates, deletes, gets all devices and gets device by id
    * The system name of the device is the operating system, please review operating_system catalog
- *cost-controller:* Calculates the total cost of register devices and use services in the devices
- Pageable param only needs `page` and `size` values. `Sort` is not implemented
### Notes
- All the services selected by the customer are applied to all of his/her devices
- Service table is recursive due to it could have a service could have more services based on the operating system 
(e.g. Antivirus Mac, Antivirus Windows)
- Service concept is represented in the code with ServiceMM that means "Service Monitor and Management"
- I secured the API with basic authentication
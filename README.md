# Project Name

Brief description of the project.

## Prerequisites

- [Tomcat Server](https://tomcat.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [pgAdmin4](https://www.pgadmin.org/)

## Installation and Setup

1. **Tomcat Server:**
   - Download and install Tomcat Server from [here](https://tomcat.apache.org/).
   - Configure the Tomcat server in your preferred IDE.

2. **Database Setup:**
   - Install PostgreSQL and pgAdmin4.
   - Open pgAdmin4.
   - Add a new server:
     - Name: "boats"
     - Hostname: localhost
     - Password: root
   - Create a database named "boats_recipe".
   - Download the database schema from the "db" folder in the repository.
   - In pgAdmin4, navigate to the "Schema" section of the "boats_recipe" database.
   - Right-click and choose "Create Schema" to create a schema named "recipe_platform_schema".
   - Right-click on the schema again, select "Query Tool" from the context menu.
   - Paste the schema file contents into the query editor and execute it (or press F5) to create the schema.

3. **Deploying the Application:**  
   - Clone or download the project repository.  
   - Import the project into your IDE.  
   - Build the project using Maven or your IDE's build tool.  
   - Run the project with Tomcat.


## Usage

Instructions on how to use the project or any specific features.

## Testing

- Provide instructions on how to test the application, including any test data or scenarios to consider.



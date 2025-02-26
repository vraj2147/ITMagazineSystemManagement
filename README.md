Magazine Management System

Description

The Magazine Management System is designed to streamline the process of article submission, review, and publication for a magazine. Journalists can submit articles, editors review these submissions, and upon approval, the system handles the publication and payment processes. This system is built on robust object-oriented principles and includes detailed implementation of UML models, business rules, unit tests, and adherence to sustainable software engineering practices.

Installation

Prerequisites:

Java JDK 8 or later
PostgreSQL
Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse)
Setup:

Clone the repository:
bash
Copy
git clone https://github.com/vraj2147/ITMagazineSystemManagement
Navigate to the project directory:
bash
Copy
cd ITMagazineSystemManagement
Install the required dependencies:
nginx
Copy
mvn install
Configure the database settings in src/main/resources/application.properties
Build the project:
go
Copy
mvn clean package
Usage

Run the application using:

pgsql
Copy
java -jar target/magazine-management-system-1.0-SNAPSHOT.jar
Navigate to http://localhost:8080 to access the application interface.

Features

Story Submission: Journalists can submit stories for publication.
Editorial Review: Editors review submitted stories and approve or reject them.
Publication: Approved stories are published and visible to readers.
Payment Processing: Automatically handles payment processing for published stories.
User Management: Supports multiple user roles, including Journalist and Editor, with secure login and authentication.
Contributing

Contributions are welcome! Please fork the repository and submit pull requests to the main branch. For bugs or feature requests, please create an issue in the repository.

License

This project is licensed under the MIT License - see the LICENSE file for details.

Authors

Vraj Patel - Initial work
Acknowledgments

Thanks to Nasser Matoorian and the CP60019E module team for guidance and support throughout the development process.

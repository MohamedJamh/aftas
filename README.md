# Aftas Sports Club Competition Management

## Overview

Aftas Sports Club, located in Mirleft, Tiznit, offers a variety of sports and activities, including Surfing, Tennis, Quad Biking, Underwater Fishing, Paragliding, and more. The club regularly organizes underwater fishing competitions across different regions of Morocco. Members can participate in these competitions by paying a fee to confirm their participation. The club manages logistics, appoints a jury to calculate results, and displays the podium.

Aflas Club aims to modernize competition management by developing a responsive web application to meet the needs of club administration and the jury.

## Backend (Spring Boot)

### User Stories

#### Admin Features

1. As an administrator, I want to add a competition.
    - Specify a unique code for the competition.
    - Provide the date, start time, end time, number of participants, and location.

2. As an administrator, I want to view a list of competitions with filters (ongoing, closed).
    - Filter competitions that are currently ongoing.
    - Filter closed competitions.

3. As an admin, I want to register a member for a competition.
    - Search for a member by number, name, or surname.
    - Register a member for a competition if they are not already registered.

4. As a user, I want to record the result of a competition.
    - Record the number of fishes caught during the competition.
    - Confirm my participation after recording the result.

5. As a user, I want to view the podium for the top three participants in the competition.
    - View the podium after the competition ends.

#### Features

- Use Spring Boot to develop the API.
- Organize the application into layers.
- Data validation is mandatory.
- Centralized exception handling (RestControllerAdvice).
- Implement pagination for search results and competition lists.
- Write unit tests for the competition results calculation service.

#### Bonus
- App seeder (Level Seeder and Fishes Seeder) on application startup.

#### Steps to run the application
```bash
# Clone the repository
git clone https://github.com/MohamedJamh/aftas.git
```
```bash
# Go to the backend directory
cd aftas
```
```bash
# install dependencies
mvn install
```
```bash
# Run the application
mvn spring-boot:run
```
```bash
# Run the tests
mvn test
```

## Resources

[Postman Collection download ](https://api.postman.com/collections/25836392-f53bd6a3-bda9-4e2e-95fa-ae8843c4eb20?access_key=PMAT-01HJ38V92MX80PRW8M7G1T4NRB)

[Frontend repository](https://github.com/MohamedJamh/aftas-front-office)

[Canva Presentation](https://www.canva.com/design/DAF3gENoZtg/qljZ4jrwgwMl_QWbf8PmUQ/edit?utm_content=DAF3gENoZtg&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

![UML Diagrams](/src/main/java/com/aftas/_Conception/uml_diagramme_class.png)

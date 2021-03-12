# Autcards


This project was build for the Advanced Human-Computer Interaction (HCI) course @ FCSE Skopje, 2021
#### Team Members: Andrej Savatic and Bojan Spasovski

## Technologies used:
* **Backend**
  * Java 15
  * Spring Boot
  * MySQL - Local storage
  * Oauth2 - Authorization framework
  * Google Single Sign-On - Local authentication
  * Spring Security - Authorization, role management and resource limitation

* **Frontend**
  * Thymeleaf - Server-side Java template engine
  * Thymeleaf Security - FE validation and resource limitation
  * Bootstrap - General style and design
  * Javascript
  * JQuery
  * Popper.js
  * FontAwesome
  * HTML
  * CSS

* **DevOps**
  * Maven - Dependecy Management and Build Tooling
  * Docker - Database Build
  
  ## Instructions: 
  1. Place your Google `client-id`, `client-secret` and set ddl-auto to CREATE in the `application.properties` file  
  2. Create MySQL table `autcards`
  3. Run the App

**Note**: Because of github's blockage of putting `client-id` and `client-secret` in a public repository, they must be added manually when you clone the project

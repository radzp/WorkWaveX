# WorkWaveX - managing employees

## 📚 Table of Contents
- [WorkWaveX - managing employees](#workwavex---managing-employees)
  - [📚 Table of Contents](#-table-of-contents)
  - [📋 Overview](#-overview)
  - [✨ Features](#-features)
    - [📁 Project Management](#-project-management)
    - [📝 Task Management](#-task-management)
    - [👥 Employee Management](#-employee-management)
    - [📅 Calendar Integration](#-calendar-integration)
  - [🛠️ How to Run the Application](#️-how-to-run-the-application)
  - [💻 Technologies Used](#-technologies-used)
    - [Frontend](#frontend)
    - [Backend](#backend)
    - [DevOps](#devops)


## 📋 Overview
WorkWaveX is an application designed for managing employees, their tasks, and projects. It provides a comprehensive solution for project management, task tracking, and employee management. The application allows users to add projects, assign specific people to projects, add tasks, and track the progress of tasks and projects.

<img width="1440" alt="first" src="https://github.com/user-attachments/assets/75b94caa-593c-475b-8d2a-141fe414d687">
<img width="1401" alt="Zrzut ekranu 2024-11-21 o 15 57 45" src="https://github.com/user-attachments/assets/ee0a27ae-cff2-466f-8906-63800207a837">
<img width="1440" alt="5th" src="https://github.com/user-attachments/assets/46fd2da5-0245-4b81-aca4-f585c1ad8fc0">
<img width="1439" alt="third" src="https://github.com/user-attachments/assets/d5dbd93f-192d-42cc-808f-66ec57216c1f">
<img width="1440" alt="6th" src="https://github.com/user-attachments/assets/0c0941cb-6a54-445f-b65a-16c9bb44cb35">

## ✨ Features
### 📁 Project Management 
- Add Projects: Users can create new projects and provide details such as project name, description, status, start date, and end date.
- Assign People to Projects: Users can assign specific employees to projects, allowing for better organization and management of project teams.
- Add Tasks to Projects: Users can add tasks to projects, specifying details such as task name, description, status, priority, start date, and end date.

### 📝 Task Management 
- Drag and Drop Tasks: Tasks can be moved between different status blocks such as "To Do", "In Progress", "Review", and "Done" using a drag-and-drop interface.
- Track Task Progress: The progress of tasks is tracked based on the number of completed tasks compared to the total number of tasks. This helps in determining the overall progress of the project.


### 👥 Employee Management 
- Add New Employees: Admins can add new employees and provide information such as name, email, position, salary, and phone number.
- View Employee Information: Admins can view detailed information about employees, including their assigned tasks and projects.
  
### 📅 Calendar Integration 
- Task Deadlines: Employees can view upcoming task deadlines in a calendar view. This helps in reminding employees of their tasks and organizing their work efficiently.
- Task Assignment: Tasks assigned to employees are visible in the calendar, providing a clear overview of their workload and deadlines.

## 🛠️ How to Run the Application 
To run the application, follow these steps:
1. **Install Docker:** Begin by [downloading and installing Docker](https://docs.docker.com/get-docker/).
2. **Download docker-compose.yml file** by using this command: 
   ``` sh
   curl -O https://raw.githubusercontent.com/radzp/WorkWaveX/master/docker-compose.yml
   ```
3. **Start the containers using docker-compose:**
   ``` sh 
   docker-compose up
   ```
3. **Login in to the application:** <br>
   To log in for the first time, use the following credentials:
   - Email: admin@admin.com
   - Password: admin
  
## 💻 Technologies Used
### Frontend
- **HTML and CSS:** Used for structuring and styling the web pages.
- **JavaScript:** Used for adding interactivity to the web pages.
- **Fetch API:** Used for making HTTP requests to the backend API.
- **Drag and Drop API:** Used for implementing the drag-and-drop functionality for tasks.

### Backend
- **Java:** The core programming language used for developing the backend.
- **Spring Boot:** A framework used for building the backend application, providing features like dependency injection, web services, and security.
- **Spring Security:** Used for securing the application, including JWT authentication.
- **Spring Data JPA:** Used for data persistence and interaction with the PostgreSQL database.
- **PostgreSQL:** The relational database used for storing application data.

### DevOps
- **Docker:** Used for containerizing the application and its dependencies.
- **Docker Compose:** Used for defining and running multi-container Docker applications.

These technologies together provide a scalable solution for managing employees, tasks, and projects.

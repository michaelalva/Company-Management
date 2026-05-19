# Backend API Overview

| Use Case                        | Endpoint                            | Method |
| :------------------------------ | :---------------------------------- | :----- |
| 1. View company qualifications  | `/api/qualifications`               | GET    |
| 2. View workers                 | `/api/workers`                      | GET    |
| 3. View projects                | `/api/projects`                     | GET    |
| 4. View qualification details   | `/api/qualifications/{description}` | GET    |
| 5. View worker details          | `/api/workers/{name}`               | GET    |
| 6. View project details         | `/api/projects/{name}`              | GET    |
| 7. Create qualification         | `/api/qualifications/{description}` | POST   |
| 8. Create worker                | `/api/workers/{name}`               | POST   |
| 9. Create project               | `/api/projects/{name}`              | POST   |
| 10. Assign worker               | `/api/assign`                       | PUT    |
| 11. Unassign worker             | `/api/unassign`                     | PUT    |
| 12. Start project               | `/api/start`                        | PUT    |
| 13. Finish project              | `/api/finish`                       | PUT    |

# Use Cases

## Use Case 1: View company qualifications
**Actor:** Manager\
**Description:** View an alphabetical list of all qualifications currently stored in the system.\
**Precondition:** The backend server is running and manager is on the "Qualifications" page.\
**Postcondition:** A list of all qualifications is displayed to the Manager.

| Actor Action                                   | System Response                                 |
| :--------------------------------------------- | ----------------------------------------------- |
| 1. Manager navigates to the Qualifications page| 2. System sends `GET /api/qualifications`request|
|                                                | 3. System retrieves qualifications list         |
|                                                | 4. System displays each qualification           |

**Alternative flows:**
- If no qualifications exist &rarr; System displays an empty list or no qualifications available message.

## Use Case 2: View company employed workers
**Actor:** Manager\
**Description:** View an alphabetical list of all workers currently employed by the company.\
**Precondition:** The backend server is running and the manager is on any page of the application.\
**Postcondition:** A list of all workers is displayed.

|Actor-Action|System Response|
|:---|:---|
|1. Manager navigates to the Workers page|2. System sends `GET /api/workers` request|
||3. System retrieves the list of workers from the Company|
||4. System displays the list of workers in alphabetical order|

**Alternative flows:**
- If no workers are currently employed &rarr; system displays a "No workers available" message.

## Use Case 3. View Projects

| Actor Action                        | System Response                                        |
| :---------------------------------- | :----------------------------------------------------- |
| User navigates to the Projects page | System sends `GET /api/projects` request               |
| System retrieves project list       | System returns all projects                            |
| User views projects                 | System displays list of projects with their info       |

**Alternative flows:**
- If no projects exist &rarr; system displays “No projects available”

## Use Case 4. View qualification details
**Actor:** Manager\
**Description:** The manager views the details for a specific qualifification. Details include description and workers that have that particular qualification.
**Precondition:** Server is running and the manager is on the Qualification page.
**Postcondition:** The selected qualification's description and workers are displayed.

|Actor Action                                      | System Response                                                                 |
| :----------------------------------------------- | :------------------------------------------------------------------------------ |
| 1. Manager navigates to the Qualifications page. | 2. System sends `GET /api/qualifications` request                               |
| 3. Manager selects a specific qualification.     | 4. System sends `GET /api/qualifications/{description}` request.                |
|                                                  | 5. Backend returns the selected qualification details.                          |
|                                                  | 6. UI displays the qualificaion description and workers with the qualifications.|
**Alternative flows:**
- System displays qualification and no assigned workers message. 

## Use Case 5: View worker details
**Actor:** Manager\
**Description:** View a worker's details including name, qualifications, projects, workload, and salary.\
**Precondition:** The backend server is running and manager is on the "Workers" page.\
**Postcondition:** A menu is displayed to the Manager containing the worker's details.

| Actor Action                                      | System Response                                 |
| :-------------------------------------------------| ----------------------------------------------- |
| 1. Manager navigates to the Workers page          | 1. System sends `GET /api/workers`              |
| 2. Manager clicks on worker they want details for | 2. System sends `GET /api/workers/{name}`       |
|                                                   | 3. System retrieves workers details             |
|                                                   | 4. System displays worker details               |

**Alternative flows:**
- If the worker does not exist, or there are no workers, the manager will be unable to proceed without creating a worker.
## Use Case 6. View Project Details

| Actor Action                                  | System Response                                                                |
| :-------------------------------------------- | :----------------------------------------------------------------------------- |
| User selects a specific project from the list | System sends `GET /api/projects/:name`                                         |
| System retrieves project details              | System returns full project information                                        |
| User views project details                    | System displays: name, size, status, assigned workers, required qualifications |

## Use Case 7. Create new qualification
**Actor:** Manager\
**Description:** The manager adds a new qualification.
**Precondition:** The server is running and the manager is on the qualification page
**Postcondition:** A new qualification is added to the list.

| Actor Action                                  | System Response                                                                |
| :-------------------------------------------- | :----------------------------------------------------------------------------- |
| 1. Manager is on the qualification page       | 2. System displays current qualifications and a form to add a new qualification|
| 3. Manager types a qualification description. | 4. System allows the user to review or edit the typed value.                   |
| 5. Manager clicks add qualification           | 6. System sends a POST request to `/api/qualifications/{description}`          |
|                                               | 7. System creates the qualificaiton                                            |
|                                               | 8. System updates the qualifications list to include newly added qualification |

**Alternative flows:**
- Empty string entered &rarr; System displays error: Qualification description cannot be empty. No qualification added.
- Only whitespace entered &rarr; System displays error: Qualification description cannot be empty. No qualification added.
- Duplicate qualification entered &rarr; System displays error: Duplicate qualification: please enter a different qualification. No qualification added.

## Use Case 8: Create worker
**Actor:** Manager\
**Description:** Creates a worker for the company\
**Precondition:** The backend server is running and manager is on the "Workers" page.\
**Postcondition:** A worker added successfully message is displayed and worker list is updated

| Actor Action                                      | System Response                                    |
| :-------------------------------------------------| ---------------------------------------------------|
| 1. Manager navigates to the Workers page          | 1. System sends `GET /api/workers`                 |
| 2. Manager clicks on create worker                | 2. System sends `POST /api/workers/{name}`         |
| 3. Manager enters worker details and submits      | 3. System create worker and adds to company        |
|                                                   | 4. System displays new worker and complete message |

**Alternative flows:**
- Worker with name already exists &rarr; System displays error and does not create worker
- Any details missing &rarr; System displays error and does not create worker
- Salary entered as negative &rarr; System displays error and does not create worker

## Use Case 9. Create Project

| Actor Action                                                          | System Response                                  |
| :-------------------------------------------------------------------- | :----------------------------------------------- |
| User enters the project details (name, size, required qualifications) | System validates input                           |
| User creates the project                                              | System sends `POST /api/projects/:name`          |
| System processes request                                              | System creates new project                       |
| System updates project list                                           | New project becomes visible in `/api/projects`   |
| User sees confirmation                                                | System displays the project in the Projects page |

**Alternative flows:**
- If project name already exists &rarr; system returns error message
- If required fields are missing &rarr; system rejects request and shows validation error

## Use Case 10: Assign Worker
**Actor:** Manager\
**Description:** The manager assigns an available and helpful worker to a project.\
**Precondition:** The backend server is running, the manager is on the Projects page, and the target project is expanded.\
**Postcondition:** The worker is added to the project and its missing qualifications list is updated.

|Actor-Action|System Response|
|:---|:---|
|1. Manager navigates to the Projects page|2. System sends `GET /api/projects` request|
||3. System retrieves projects list|
||4. System displays the list of projects|
|5. Manager clicks a project to expand its details|6. System displays project body including size, status, and required qualifications|
|7. Manager selects a worker from the assignment interface|8. System sends `PUT /api/assign` request|
|9. Manager clicks the "Assign" button|10. System updates worker and project association in the model|
||11. System refreshes the project body to show the assigned worker and updated qualification colors|

**Alternative flows:**
- If the worker is not "helpful" (doesn't have a missing qualification) &rarr; system rejects the assignment.
- If the worker would be overloaded (workload > 12) &rarr; system rejects the assignment.
- If the project is already ACTIVE or FINISHED &rarr; system prevents assignment.

## Use Case 11. Unassign Worker

**Actor:** Manager\
**Description:** The manager unassigns a worker from a project. If the project is ACTIVE and the unassignment causes a qualification to no longer be satisfied, the project becomes SUSPENDED. If this was the worker's only project, they are moved out of the assigned workers pool.\
**Precondition:** The backend server is running, the manager is on the Projects page, and the selected worker is currently assigned to the selected project.\
**Postcondition:** The worker is unassigned from the project. If the project is ACTIVE and a required qualification is no longer satisfied, the project status changes to SUSPENDED.

| Actor Action                                              | System Response                                                                                  |
| --------------------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| Manager navigates to the Projects page.                   | System sends `GET /api/projects` request and displays the project list.                          |
| Manager selects a specific project.                       | System sends `GET /api/projects/{name}` and displays project details.                            |
| Manager selects a worker to unassign and clicks Unassign. | System sends `PUT /api/unassign` request.                                                        |
|                                                           | System removes the worker from the project.                                                      |
|                                  | If the project is ACTIVE and a required qualification is no longer satisfied, system changes project status to SUSPENDED. |
|                                                           | System refreshes and displays the updated project details.                                       |

**Alternative flows:**
- If worker is not assigned to the project &rarr; system does nothing. 
- If project is PLANNED or SUSPENDED &rarr; project status remains unchanged after unassignment.

## Use Case 12. Start Project

**Actor:** Manager\
**Description:** The manager starts a project that is currently in PLANNED or SUSPENDED status.\
**Precondition:** The backend server is running, the manager is on the Projects page, and the selected project has a PLANNED or SUSPENDED status.\
**Postcondition:** The project status is updated to ACTIVE.

| Actor Action                             | System Response                                                         |
| ---------------------------------------- | ----------------------------------------------------------------------- |
| Manager navigates to the Projects page.  | System sends `GET /api/projects` request and displays the project list. |
| Manager selects a specific project.      | System sends `GET /api/projects/{name}` and displays project details.   |
| Manager clicks the Start Project button. | System sends `PUT /api/start` request.                                  |
|                                          | System updates the project status to ACTIVE.                            |
|                                          | System refreshes and displays the updated project details.              |

**Alternative flows:**
- If project is already ACTIVE or FINISHED &rarr; system does nothing, project cannot be started.
- If project has missing qualifications &rarr; system does nothing, project remains PLANNED or SUSPENDED.

## 13. Finish Project

**Actor:** Manager\
**Description:** The manager finishes a project that is currently in ACTIVE status.\
**Precondition:** The backend server is running, the manager is on the Projects page, and the selected project has an ACTIVE status.\
**Postcondition:** The project status is updated to FINISHED and all assigned workers are unassigned from the project.

| Actor Action                                 | System Response                                                                    |
| -------------------------------------------- | ---------------------------------------------------------------------------------- |
| 1. Manager navigates to the Projects page.   | 2. System sends `GET /api/projects` request and displays the project list.         |
| 3. Manager selects a specific project.       | 4. System sends `GET /api/projects/{name}` and displays project details.           |
| 5. Manager clicks the Finish Project button. | 6. System sends `PUT /api/finish` request.                                         |
|                                              | 7. System updates the project status to FINISHED and removes all assigned workers. |
|                                              | 8. System refreshes and displays the updated project details.                      |

**Alternative flows:**
- If project is PLANNED or SUSPENDED &rarr; system does nothing, project status remains unchanged.
- If project is already FINISHED &rarr; system does nothing, project status remains unchanged.
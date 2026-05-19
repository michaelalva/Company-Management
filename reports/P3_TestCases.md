# Test Cases

## Use Case 1:

### Test Scenario: View company qualifications (Happy Path)
1. Manager clicks the "Qualifications" link in the navigation bar.
2. System sends GET /api/qualifications request.
3. Backend returns list of qualifications.
4. UI displays all qualifications.
5. Manager verifies that qualifications are visible to the list.

### Test Scenario: View company qualifications (Alternate Path)
1. Manager clicks the "Qualifications" link in the navigation bar.
2. System sends GET /api/qualifications request.
3. Backend returns empty list of qualifications.
4. UI displays "No Qualifications available".
5. Manager verifies that there are no qualifications in list.

## Use Case 2:

### Test Scenario: View company employed workers (Happy Path)
1. Manager clicks the "Workers" link in the navigation bar.
2. System sends `GET /api/workers`.
3. Backend returns a list of employed workers.
4. UI displays all workers in alphabetical order (e.g., "Benjamin Guzman" appears before "Nick Hubbard").

### Test Scenario: View company employed workers (Alternate Path)
1. Manager clicks the "Workers" link in the navigation bar.
2. System sends `GET /api/workers`.
3. Backend returns an empty list.
4. UI displays "No workers available" and the list area is empty


## Use Case 3
### Test Scenario: View projects (Happy Path)
1. Open the Projects page
2. System sends GET /api/projects request
3. System retrieves list of projects
4. UI displays all projects with basis information (name, size, status)

### Test Scenario: View projects (Alternate Path)
1. No projects exist in the system
2. System sends GET /api/projects request
3. Backend returns empty list []
4. UI displays “No projects available”

## Use Case 4
### Test Scenario: View qualification details (Happy Path)
1. Manager is on the Home page and clicks qualificaions link in the navigation bar.
2. System sends GET /api/qualifications request.
3. Backend returns list of qualifications.
4. UI displays all qualifications.
5. Mandager clicks on a qualification that is displayed
6. Backend returns details for the qualification.
7. UI displays the qualification description and workers who have that qualificaion.

### Test Scenario: View qualifiction details (Alternate Path)
1. Manager is on the Home page and clicks qualificaions link in the navigation bar.
2. System sends GET /api/qualifications request.
3. Backend returns list of qualifications.
4. UI displays all qualifications.
5. Manager clicks on a qualification that is displayed
6. Backend returns details for the qualification.
7. UI displays the qualification description and no assigned workers message.

## Use Case 5
### Test Scenario: View worker details (Happy Path)
1. Manager is on the Home page and clicks Workers link in the nav bar
2. System sends GET /api/workers request
3. Backend returns list of workers
4. UI displays all workers
5. Manager clicks on a specific worker in the list
6. System sends GET /api/workers/{workerName}
7. UI displays the workers details

### Test Scenario: View worker details (Alternate Path)
1. System has no workers
2. Manager navigates to workers page
3. System sends GET /api/workers
4. No workers are available
5. No workers are displayed

## Use Case 6
### Test Scenario: View Project Details (Happy Path)
1. User selects a project from the Projects page
2. System sends GET /api/projects/:name
3. System retrieves full project details
4. UI displays project information (name, size, status, workers, qualifications)

## Use Case 7
### Test Scenario: Create new qualification (Happy Path)
1. Manager enters valid qualification "Java"
2. System verifies that valid string is not already in list.
3. Backend creates the new qualification.
4. UI displays updated qualifications list and shows "Java"

### Test Scenario: missing or blank description (Alternate Path)
1. Manager submits the form with an empty or blank qualification description.
2. System does not create the qualification.
3. UI displays error: "Qualification description is required".
4. Qualification list is not updated

### Test Scenario: duplicate description (Alternate Path)
1. Manager enters duplicate description (regardless of capitalization).
2. System does not create the qualification.
3. UI displays error: "Duplicate Qualification: please enter a new description".
4. Qualification list is not updated.

## Use Case 8
### Test Scenario: Create Worker (Happy Path)
1. Manager presses create worker button
2. Manager fills out form for new worker
3. All details are correct and worker name is unique
4. System creates worker in the backend and adds to database
5. Successful creation with success message and worker list updated

### Test Scenario: Worker already exists (Alternate Path)
1. Manager presses create worker button
2. Manager fills out form for new worker
3. All details are correct except worker name is already present
4. System does not create worker and error message is displayed on UI

### Test Scenario: Worker Salary < 0 (Alternate Path)
1. Manager presses create worker button
2. Manager fills out form for new worker
3. All details are correct except salary is negative
4. System does not create worker and error message is displayed on UI

## Use Case 9
### Test Scenario: Create Project (Happy Path)
1. User enters project details (name, size, qualifications)
2. User creates the project
3. System sends POST /api/projects/:name request
4. Backend creates project and updates list
5. UI shows new project in Projects page

### Test Scenario: Create Project (Alternate Path)
Duplicate Project Name
1. User enters a project name that already exists
2. System sends POST /api/projects/:name request
3. Backend rejects request
4. UI shows error message (“Project already exists”)

Missing Fields
1. User submits form with missing name, size, or qualifications
2. Backend rejects request
3. UI displays validation error

## Use Case 10

### Test Scenario: Assign Worker (Happy Path)
1. Manager navigates to the Projects page.
2. Manager clicks on a project to expand it.
3. Manager identifies a Red qualification bubble (e.g., "Java").
4. Manager selects a worker who has "Java" and clicks Assign
5. System sends `PUT /api/assign`.
6. The worker appears in the project list and the "Java" bubble turns Green.

### Test Scenario: Assign Overloaded Worker (Alternative Path)
1. Manager selects a worker with 11 workload points.
2. Manager attempts to assign them to a MEDIUM (2pt) project.
3. System sends `PUT /api/assign`.
4. The assignment is rejected because the new workload (13) exceeds the MAX_WORKLOAD of 12

## Use Case 11
### Test Scenario: Unassign Worker (Happy Path)
1. Manager navigates to the Projects page.
2. System sends GET /api/projects request and displays the project list.
3. Manager selects a project with at least one assigned worker.
4. System sends GET /api/projects/{name} and displays project details.
5. Manager selects a worker to unassign and clicks Unassign.
6. System sends PUT /api/unassign request.
7. Backend removes the worker from the project.
8. UI refreshes and displays the updated project details with the worker removed.

### Test Scenario: Unassign Worker Causes Project to be SUSPENDED (Alternate Path)
1. Manager navigates to the Projects page.
2. Manager selects an ACTIVE project.
3. Manager selects a worker whose qualification is the only one satisfying a required qualification.
4. Manager clicks Unassign.
5. System sends PUT /api/unassign request.
6. Backend removes the worker and the project's qualification is no longer satisfied.
7. Backend changes project status to SUSPENDED.
8. UI refreshes and displays the updated project status as SUSPENDED.

### Test Scenario: Unassign Worker from PLANNED or SUSPENDED Project (Alternate Path)
1. Manager navigates to the Projects page.
2. Manager selects a PLANNED or SUSPENDED project with at least one assigned worker.
3. Manager selects a worker to unassign and clicks Unassign.
4. System sends PUT /api/unassign request.
5. Backend removes the worker from the project.
6. Project status remains PLANNED or SUSPENDED unchanged.
7. UI refreshes and displays the updated project details with the worker removed.

## Use Case 12
### Test Scenario: Start Project (Happy Path)
1. Manager navigates to the Projects page.
2. System sends GET /api/projects request and displays the project list.
3. Manager selects a project with PLANNED or SUSPENDED status.
4. System sends GET /api/projects/{name} and displays project details.
5. Manager clicks the Start Project button.
6. System sends PUT /api/start request.
7. Backend updates the project status to ACTIVE.
8. UI refreshes and displays the updated project status as ACTIVE.

### Test Scenario: Start Project with Missing Qualifications (Alternate Path)
1. Manager navigates to the Projects page.
2. Manager selects a project with PLANNED or SUSPENDED status that has missing qualifications.
3. Manager clicks the Start Project button.
4. System sends PUT /api/start request.
5. Backend does nothing, project remains PLANNED or SUSPENDED.
6. UI displays the project status unchanged.

### Test Scenario: Start Project that is Already ACTIVE or FINISHED (Alternate Path)
1. Manager navigates to the Projects page.
2. Manager selects a project with ACTIVE or FINISHED status.
3. Manager clicks the Start Project button.
4. System sends PUT /api/start request.
5. Backend does nothing, project status remains unchanged.
6. UI displays the project status unchanged.

## Use Case 13
### Test Scenario: Finish Project (Happy Path)
1. Manager navigates to the Projects page.
2. System sends GET /api/projects request and displays the project list.
3. Manager selects a project with ACTIVE status.
4. System sends GET /api/projects/{name} and displays project details.
5. Manager clicks the Finish Project button.
6. System sends PUT /api/finish request.
7. Backend updates the project status to FINISHED and removes all assigned workers.
8. UI refreshes and displays the updated project status as FINISHED with no assigned workers.

### Test Scenario: Finish Project that is PLANNED or SUSPENDED or already FINISHED (Alternate Path)
1. Manager navigates to the Projects page.
2. Manager selects a project with PLANNED or SUSPENDED or FINISHED status.
3. Manager clicks the Finish Project button.
4. System sends PUT /api/finish request.
5. Backend does nothing, project status remains unchanged.
6. UI displays the project status unchanged.


# Workflows

## Workflow 1: Full Project Lifecycle
1. (UC7) Create a new qualification "Machine Learning"
2. (UC1) Verify "Machine Learning" appears in the qualifications list
3. (UC8) Create a new worker "John Doe" with qualification "Machine Learning" and salary 80000
4. (UC2) Verify "John Doe" appears in the workers list
5. (UC9) Create a new project "AI Research" with size MEDIUM and qualification "Machine Learning"
6. (UC3) Verify "AI Research" appears in the projects list
7. (UC10) Assign "John Doe" to "AI Research"
8. (UC6) Verify "AI Research" shows "Machine Learning" as green (satisfied)
9. (UC12) Start "AI Research" — status changes to ACTIVE
10. (UC13) Finish "AI Research" — status changes to FINISHED and workers are unassigned

## Workflow 2: Suspended Project
1. (UC9) Create a new project "Data Pipeline" with size SMALL and qualification "Python"
2. (UC10) Assign a worker with "Python" qualification to "Data Pipeline"
3. (UC12) Start "Data Pipeline" — status changes to ACTIVE
4. (UC11) Unassign the worker — project status changes to SUSPENDED
5. (UC10) Assign the worker back to "Data Pipeline"
6. (UC12) Start "Data Pipeline" again — status changes to ACTIVE
7. (UC13) Finish "Data Pipeline" — status changes to FINISHED

## Workflow 3: Missing Qualifications
1. (UC9) Create a new project "Cyber Defense" with size BIG and qualifications "Cyber Security" and "Python"
2. (UC6) Verify both qualifications show as red (missing) since no workers are assigned
3. (UC10) Assign a worker with only "Python" qualification
4. (UC6) Verify "Python" is now green and "Cyber Security" is still red
5. (UC12) Attempt to start "Cyber Defense" — nothing happens due to missing qualifications
6. (UC10) Assign a worker with "Cyber Security" qualification
7. (UC6) Verify both qualifications are now green
8. (UC12) Start "Cyber Defense" — status changes to ACTIVE
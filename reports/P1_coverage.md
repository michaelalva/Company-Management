| Class | Method | Statement Coverage (%) | Branch Coverage (%) |
| :--- | :--- | :--- | :--- |
| Qualification | `Qualification(String)` | 100 | 100 |
| | `equals(Object)` | 100 | 100 |
| | `hashCode()` | 100 | n/a|
| | `toString()` |100 | n/a|
| | `getWorkers()` | 100 | n/a|
| | `addWorker(Worker)` | 100| 100 |
| | `removeWorker(Worker)` | 100 | 100|
| | `toDTO()` | 100 | 100 |

JaCoCo report shows 100% instruction and line coverage for the 
Qualification class, and 100% branch coverage for all methods that 
contain decision logic. getWorkers(), hashCode(), and toString() 
show branch coverage as "n/a." These methods do not contain any 
conditional statements. 

| Class | Method | Statement Coverage (%) | Branch Coverage (%) |
| :--- | :--- | :--- | :--- |
| Project | `Project()`| 100 | 100 |
| | `equals()` | 100 | 100 |
| | `hashCode()` | 100 | n/a |
| | `toString()` | 100 | n/a |
| | `getName()` | 100 | n/a |
| | `getSize()` | 100 | n/a |
| | `getStatus()` | 100 | n/a |
| | `setStatus()` | 100 | 100 |
| | `addWorker()` | 100 | 100 |
| | `removeWorker()` | 100 | 100 |
| | `getWorkers()` | 100 | n/a |
| | `removeAllWorkers()` | 100 | n/a |
| | `getRequiredQualifications()` | 100 | n/a |
| | `addQualifications()` | 100 | n/a |
| | `getMissingQualifications()` | 100 | 100 |
| | `isHelpful()` | 100 | n/a |
| | `toDTO()` | 100 | n/a |

JaCoCo report shows 100% instruction and line coverage for the 
Project class, and 100% branch coverage for all methods that 
contain decision logic. getName(), getSize(), getStatus(), removeAllWorkers(), 
getWorkers(), hashCode(), toString(), getRequiredQualifications(), addQualifications(), 
isHelpful(), and toDTO() show branch coverage as "n/a." 
These methods do not contain any conditional statements. 

| Class | Method | Statement Coverage (%) | Branch Coverage (%) |
| :--- | :--- | :--- | :--- |
| Company | `assign()` | 100 | 100 |
| | `unassign(w: Worker, p: Project)` | 100 | 93 |
| | `getQualifications()` | 100 | n/a |
| | `getProjects()` | 100 | n/a |
| | `getEmployedWorkers()` | 100 | n/a |
| | `getName()` | 100 | n/a |
| | `getUnassignedWorkers()` | 100 | n/a |
| | `getAssignedWorkers()` | 100 | n/a |
| | `getUnavailableWorkers()` | 100 | n/a |
| | `getAvailableWorkers()` | 100 | n/a |
| | `equals()` | 100 | n/a |
| | `toString()` | 100 | n/a |
| | `createQualification()` | 100 | 100 |
| | `Company()` | 100 | 100 |
| | `createWorker()` | 100 | 100 |
| | `createProject()` | 100 | 100 |
| | `start()` | 100 | 92 |
| | `finish()` | 100 | 100 |
| | `unassignAll()` | 100 | 100 |

JaCoCo report shows 100% instruction and line coverage for the 
Company class, and 98% branch coverage for all methods that 
contain decision logic. getQualifications(),
toString(), equals(), getAvailableWorkers(), getUnavailableWorkers(), 
getAssignedWorkers(), getUnassignedWorkers(), getName(), getEmployedWorkers(), 
and getProjects() show branch coverage as "n/a." 
These methods do not contain any conditional statements. 

| Class | Method | Statement Coverage (%) | Branch Coverage (%) |
| :--- | :--- | :--- | :--- |
|Worker|`Worker(String, Set, double)`|100|100|
||`equals(Object)`|100|100|
||`hashCode()`|100|n/a|
||`toString()`|100|n/a|
||`getName()`|100|n/a|
||`getSalary()`|100|n/a|
||`setSalary()`|100|n/a|
||`getQualifications()`|100|n/a|
||`addQualification(Qualification)`|100|n/a|
||`getProjects()`|100|n/a|
||`addProject(Project)`|100|100|
||`removeProject(Project)`|100|100|
||`getWorkload()`|100|100|
||`willOverload(Project)`|100|100|
||`isAvailable()`|100|100|
||`toDTO()`|100|100|

JaCoCo report shows 100% instruction and line coverage for the Worker
class, and 100% branch coverage for all methods that contain decision
logic. hashCode(), toString(), getName(), getSalary(), setSalary(),
getQualifications(), addQualification(Qualification), and getProjects()
show branch coverage as "n/a". These methods do not contain any conditional
statements.
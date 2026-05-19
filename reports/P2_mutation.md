# Pit Test Coverage Report

## Before

### Package Summary

|Name|Number of Classes|Line Coverage|Mutation Coverage|Test Strength|
|---|---|---|---|---|
|edu.colostate.cs415.model|5|98%  (282/288)|93%  (168/180)|95%  (168/177)|

### Breakdown by Class

|Name|Line Coverage|Mutation Coverage|Test Strength|
|---|---|---|---|
|Company.java|96%  (117/122)|89%  (75/84)|91%  (75/82)|
|Project.java|100%  (56/56)|100%  (35/35)|100% (35/35)|
|ProjectSize.java|83%  (5/6)|0%  (0/1)|0%  (0/0)|
|Qualification.java|100%  (33/33)|100%  (16/16)|100%  (16/16)|
|Worker.java|100%  (71/71)|95%  (42/44)|95%  (42/44)|


## After

### Package Summary

|Name|Number of Classes|Line Coverage|Mutation Coverage|Test Strength|
|---|---|---|---|---|
|edu.colostate.cs415.model|5|98% (285/286)|93% (186/187)|95% (186/186)|

### Breakdown by Class

| Class | Line Coverage | Mutation Coverage | Test Strength |
|---|---|---|---|
| Company.java | 100% (116/116) | 100% (86/86) | 100% (86/86) |
| Project.java | 100% (60/60) | 100% (37/37) | 100% (37/37) |
| ProjectSize.java | 83% (5/6) | 0% (0/1) | 0% (0/0) |
| Qualification.java | 100% (33/33) | 100% (16/16) | 100% (16/16) |
| Worker.java | 100% (71/71) | 100% (47/47) | 100% (47/47) |

## Reflection

We added and modified following tests to improve the mutation score:

| Class | Method |
| :--- | :--- |
| Company | testCreateProject_invalidName()
|| testCreateProject_invalidQualifications() |
|| testCreateProject_invalidSize() |
|| testCreateProject_valid() |
|| testCreateWorker_valid() |
|| testGetAvailableWorkers_oneWorker() |
|| testHashCode_Unique() |
|| testStart_ProjectSuspendedNoWorker() | 
|| testStart_ProjectSuspendedWorkerQualified() | 
|| testStart_ProjectInProgress() | 
|| testGetUnavailableWorkers_oneAvailableOneUnavailable() | 
|| testGetUnavailableWorkers_oneWorker() |

| Class | Method |
| :--- | :--- |
| Worker | testWillOverloadExactlyAtLimitAlreadyAssigned() |
|| testToDTOMultipleProjects |

The biggest improvements came in Company.java, which went from 89% to 100% 
mutation coverage, and Worker.java, which went from 95% to 100%. The tests 
added targeted edge cases in project and worker creation, availability checks, 
and project state transitions that were previously either untested or only 
partially covered. ProjectSize.java is excluded from this reflection as it was 
a provided class and was not subject to modification or testing.

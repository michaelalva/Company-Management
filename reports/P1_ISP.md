<a id="top"></a>
# P1 ISP Tables

## Contents
### [Qualification](#qualification-class)
### [Worker](#worker-class)
### [Project](#project-class)
### [Company](#company-class)


## Qualification Class
[Return to top](#top)

### Method `Qualification(String description)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| description | nullness| A1: Null| null|
| | Number of characters in description| A2: 0 | "" |
|  | | A3: More than 0 | "AWS" |
|  |Types of characters in description | B1: No Whitespace | "Java" |
|  |  |B2: Mix of whitespace and non whitespace | "software testing" |
|  |  |B3: Only whitespace | " ", "\n", "\t \n" |

### BCC Method `Qualification(String description)`
|BCC Test | Blocks |JUnit test name|Oracle|
| :--- | :--- | :--- | :--- |
| T1 (base test)| A3 B1|QualificationTest_setValid | toString() returns "Java"|
|T2 | A1 B1 | contructor_throws_whenNull| Throws IllegalArgumentException|
|T3| A2 B1| contructor_throws_whenEmpty| Throws IllegalArgumentException|
|T4|A3 B3| consturctor_throws_whenWhitespaceOnly| thows IllegalArgumentException|
|T5|A3 B2| QualificationTest_validWhiteSpace| Object created successfully; toSTring() returns description|

### Method `equals(o: Object)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| o | Nullness | A1: Null | Null |
|  | Type of object | A2: Not Qualification | "Java"|
|  |  | A3: Qualification | new Qualification("Java")|
| this.description vs. o.description| Comparison| B1: Same description| "Java" vs. "Java"|
|  |  |B2: Different description| "Java" vs. "Python"|
| workers| Worker set relevance| C1: Same description, different workers| {} vs {w1}|

### BCC Method `equals(o: Object)`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A3 B1 | equals_Base() | true |
|T2| A1 B1 | equals_T2 | false o == null |
|T3| A2 B1 | equals_T3 | false o != qualification |
|T4| A3 B2 | equals_T4 | false != description |
|T5| A3 A3 | equals_T5 | true (same reference(this == o))|

### Method `hashCode()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| description | content| A1: Same description | "description" vs "description" |
|  |  | A2: Different description | "description" vs "different description" |

### BCC Method `hashCode()`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A1 | hashCode_Base() | hashCode1==hashCode2 |
|T2| A2 | hashCode_T2 | hashCode1 != hashCode2 |

### Method `toString()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| description | content| A1: No Whitespace| "Java" |
|  |  | A2: contains White space | "Software testing" |

### BCC Method `toString()`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A1 | toString_Base() | no whitespace expect "Java" |
|T2| A2 | toString_T2() | contains whitespace expect "Software testing" |

### Method `getWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| Set of workers |Size | A1: Empty | {} |
|  |  | A2: One worker | {w1} |
|  |  | A3: Multiple workers | {w1, w2} |

### BCC Method `getWorkers()`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A2 | getWorkers_base()| size = 1 and contains w1|
|T2| A1| getWorkers_T2()| return empty set|
|T3| A3| getWorkers_T3()| return size 2 and contains w1 and w2|

### Method `addWorkers(w: Worker)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Null | A1: Null | Null |
|  |  | A2: Not null | w1 |
| worker set | Membership in worker set | B1: Not present | {} |
|  |  |B2: already present| {w1}|

### BCC Method `addWorkers(w: Worker)`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A2 B1| addWorker_base()| size = 1 and contains w1|
|T2| A1 B1| addWorker_T2()|illegalArgumentException|
|T3| A2 B2| addWorker_T3()| size = 1 after adding w1 twice|

### Method `removeWorker(w: Worker)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Null | A1: Null | Null |
|  |  | A2: Not null | w1 |
|this.workers| size of set| B1:Empty| {}|
|  |  |B2:one worker| {w1}|
|  |  |B3: multiple workers| {w1,w2}|
| worker set | Membership in worker set | C1: Not present | w1 is in set {w1} |
|  |  |C2: already present| w1 is not in set {w1}|

### BCC Method `removeWorker(w: Worker)`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A2 B3 C2| removeWorker_base()| removes {w1} and size is decreased by 1|
|T2| A1 B3 C2|removeWorker_T2()| throws IllegalArgumentException|
|T3| A2 B3 C1| removeWorker_T3()|nothing removed and is same contents and size|
|T4| A2 B1 C2|removeWorker_T4()|still empty after removal|
|T5| A2 B2 C2|removeWorker_T5()|empty set after removal

### Method `toDTO()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| Set of workers |Size | A1: Empty | {}|
|  |  | A2: One Worker | {w1} |
|  |  | A3: Multiple workers | {w1, w2} |

### BCC Method `toDTO()`
|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base)| A1 | toDTO_base()| dto.getDescription()=="Java" and length ==0|
|T2| A2 | toDTO_T2()| dto.getDescription()=="Java" and length == 1 and name == "Alice"|
|T3| A3| toDTO_T3| dto.getDescription()=="Java" and length == 2 and name == "Alice" and "Bob"|

## Worker Class
[Return to top](#top)

### Method `Worker (name: String, qs: Set<Qualification>, salary:double)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | nullness | A1: Null | null |
|  | number of characters | A2: 0 | "" |
|  |  | A3: More than 0 | "Name |
|  |char type| A4:only whitespace| " "|
| qs| nullness|B1: Null| null|
|  | size | B2:Empty| {}|
|  |  |B3: not empty| {Java}|
|salary| contents | C1: Negative| -1|
|  |  |C2:zero| 0|
|  |  |C3: Positive| 1|

|BCC Test | Blocks |JUnit test name|Oracle|
| :--- | :--- | :--- | :--- | 
| T1 (base) | A3 B3 C3 | Valid_inputs_constructor_Test | No exception; getName()=="Nick"; getSalary()==100.0; getQualifications().size()==1; getProjects().size()==0 |
| T2 | A1 B2 C3 | Null_Name_Test| Throws IllegalArgumentException|
|T3| A2 B2 C3 | Empty_Name_Test| Throws IllegalArgumentException|
|T4| A4 B2 C3| Whitespace_Name_Test| Throws IllegalArgumentException|
|T5| A3 B1 C3| Null_QualificationSet_Test| Throws IllegalArgumentException|
|T6| A3 B2 C3| empty_QualificationSet_Test| No exception; getQualifications().size()==0 |
|T7| A3 B2 C1| Negative_Salary_Test| Throws IllegalArgumentException|
|T8| A3 B2 C2| Zero_Salary_Test| No exception; getSalary()==0.0 |

### Method `equals(o: Object)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| o| Nullness | A1: null| null|
|  | type | A2: non-worker | "Alice"|
|  |  | A3: worker| new Worker(...)|
| name comparison| this.name vs. other.name| B1:same|"Alice" vs. "Alice"|
|  |  |B2:different| "Alice" vs. "Bob"|

|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base) | A3 B1| equals_SameName| return true|
|T2| A1 B1| equals_null| return false|
|T3| A2 B1| equals_nonWorker| return false|
|T4| A3 B2| equals_differntName| return false|

### Method `hashCode()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Equality  | A1:Same name | "John" vs. "John" |
|  |  | A2:Different name | "John" vs. "Bob" |

|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
|T1 (base) | hashCodeSameName |worker1.hashCode() == worker2.hashCode() |
|T2 | A2 |hashCodeDiffName | worker1.hashCode() != worker2.hashCode() |

### Method `toString()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| projects | Size | A1: 0 |{}  |
|  |  | A2: 1 | {p1} |
|  |  | A3: Multiple | {p1,p2} |
|qualifications| size| B1:1| {java}\
|  |  |B2:Multiple| {Java,Junit}|
|salary|Decimal handling|C1:whole number| 100.0|
|  |  |C2:Decimal (truncate)|100.99|

|BCC Test | Blocks |JUnit test name|Oracle|
|:---|:---|:---|:---|
| T1 (base)| A1 B1 C1 | toString_base |Alice:0:0:100|
|T2| A2 B1 C1 | toString_T2 |Alice:1:0:100|
|T3| A3 B1 C1 | toString_T3 |Alice:3:0:100|
|T4| A1 B2 C1 | toString_T4 |Alice:0:1:100|
|T5| A1 B3 C1 | toString_T5 |Alice:0:3:100|
|T6| A1 B1 C2(100.99)| toString_T6 |Alice:0:0:100|

### Method `getName()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|name|A) Length|A1: 0|""|
|||A2: >0|"Mario"|
||B) Content|B1: No whitespace|"Bowser"|
|||B2: Mix whitespace|"Princess Peach"|
|||B3: Only whitespace|" ", "\n", "\t\n"|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A2 B1|testGetNameNoWhitespaceB1|Name is "Bowser"|
|T2|A1 B1|testGetNameLengthZeroA1|Throws IllegalArgumentException|
|T3|A2 B2|testGetNameMixWhiteSpaceB2|Name is "Princess Peach"|
|T4|A2 B3|testGetNameOnlyWhitespaceB3_Space|Throws IllegalArgumentException|

### Method `getSalary()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|salary|A) Relationship to zero|A1: Negative|-100.00|
|||A2: Zero|0|
|||A3: Positive|160,000.00|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1|testGetSalaryNegativeA1|Throws IllegalArgumentException|
|T2|A2|testGetSalaryZeroA2|Returns 0.0|
|T3|A3|testGetSalaryPositiveA3|Returns 160000.00|

### Method `setSalary()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|salary|A) Relationship to zero|A1: Negative|-100.00|
|||A2: Zero|0|
|||A3: Positive|160,000.00|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1|testSetSalaryNegativeA1|Throws IllegalArgumentException|
|T2|A2|testSetSalaryZeroA2|Salary becomes 0.0|
|T3|A3|testSetSalaryPositiveA3|Salary becomes 160,000.00|

### Method `getQualifications()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|qualificationSet|A) Number of elements|A1: Empty|{}|
|||A2: Non-empty|{java, junit}|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1|testGetQualificationsEmpty|No exception; returns empty set|
|T2|A2|testGetQualificationsNonEmpty|No exception; returns set with size 2|

### Method `addQualifications(Qualification)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Qualification set|A) Number of elements|A1: Empty|{}|
|||A2: Non-empty|{java, junit}|
|Qualification q|B) Nullness|B1: Null|null|
|||B2: Not null|Valid Qualification ref|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1 B2|testAddQualificationToEmpty|No exception; qualifications.size() == 1|
|T2|A2 B2|testAddQualificationToNonEmpty|No exception; qualifications.size() == 2|
|T3|A1 B1|testAddQualificationNull|Throws IllegalArgumentException|
|T4|A2 B2|testAddQualificationDuplicate|No exception; size remains same|

### Method `getProjects()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Project set|A) Number of elements|A1: Empty|{}|
|||A2: One project|{ad marketing}|
|||A3: Multiple projects|{ad marketing, order tracker}|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1|testGetProjectsEmpty|No exception; return empty set|
|T2|A3|testGetProjectsMultiple|No exception; returns set with size 2|

### Method `addProject()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Project set|A) Number of elements|A1: Empty|{}|
|||A2: One project|{ad marketing}|
|||A3: Multiple projects|{ad marketing, order tracker}|
|Project p|B) Nullness|B1: Null|null|
|||B2: Not null|Valid Project ref|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1 B2|testAddProjectValid|No exception; projects.size() == 1|
|T2|A1 B1|testAddProjectNull|Throws IllegalArgumentException|

### Method `removeProject()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Project set|A) Number of elements|A1: Empty|{}|
|||A2: Non-empty|{ad marketing, order tracker}|
|Project p|B) Nullness|B1: Null|null|
|||B2: Not null|Valid Project ref|
||C) Exists in set|C1: Project in set|p exists in set|
|||C2: Project not in set|p missing in set|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A2 B2 C1|testRemoveProjectInSet|No exception; project removed; size == 0|
|T2|A2 B1 C1|testRemoveProjectNull|Throws IllegalArgumentException|
|T2|A2 B2 C2|testRemoveProjectNotInSet|No exception; size remains 1|

### Method `getWorkload()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Project set|A) Project status|A1: Only finished projects|Workload = 0|
|||A2: Mix of active and finished|Total of active|
|Workload total|B) Compute workload|B1: Zero|0|
|||B2: Equal to 12|12|
|||B3: Greater than 12|13|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1 B1|testGetWorkloadOnlyFinished|Returns 0|
|T2|A2 B1|TestGetWorkloadMixed|Returns non-finished|
|T3|A2 B2|testGetWorkloadAtLimit|Returns 12|
|T4|A2 B3|testGetWorkloadOverLimit|Returns 13|

### Method `willOverload()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Project p|A) Size|A1: SMALL|+1|
|||A2: MEDIUM|+2|
|||A3 BIG|+3|
||B) Assignment status|B1: Already assigned to worker|.contains(p)|
|||B2: Not assigned to worker|!.contains(p)|
|Project set|C) Total workload of p|C1: Less than 12|10|
|||C2: Equal to 12|12|
|||C3: Greater than 12|14|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1 B2 C1|testWillOverloadSmall|10+1=11; returns false|
|T2|A1 B2 C2|testWillOverloadAtLimit|11+1=12; returns false|
|T3|A3 B2 C3|testWillOverloadOverLimit|10+3=13; returns true|
|T4|A1 B1 C1|testWillOverloadAlreadyAssigned|Load same; returns false|

### Method `isAvailable()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|workload|A) Availability|A1: Less than 12|5 (True)|
|||A2: Equal to 12|12 (False)|
|||A3: Greater than 12|15 (False)|

|BCC Test||Junit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1|testIsAvailableTrue|11; returns true|
|T2|A2|testIsAvailableAtLimit|12; returns false|
|T3|A3|testIsAvailableOverLimit|12; returns false|

### Method `toDTO()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|Projects|A) Quantity|A1: No projects|[]|
|||A2: 1+ projects|["Ad Marketing"]|
|Qualifications|B) Quantity|B1: 1 qual|["Java"]|
|||B2: Multiple quals|["Java", "Python"]|

|BCC Test||JUnit Test Name|Oracle|
|:---|:---|:---|:---|
|T1(Base)|A1 B1|testToDTOBase|Name, Salary, Workload=0, 1 Qual, 0 Proj|
|T2|A2 B1|testToDTOWithProjects|Name, Salary, Workload=1, 1 Qual, 1 Proj|
|T3|A1 B2|testToDTOMultipleQualifications|Name, Salary, Workload=0, 2 Quals, 0 Proj|

## Project Class
[Return to top](#top)

### Method `Project(name: String, qs: Set(Qualification), size: ProjectSize)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Nullness | null | null |
| name | Nullness | not null | "CS415" |
| name | Number of characters | 0 | "" |
| name | Number of characters | more than 0 | "CS415" |
| name | Type of characters | no whitespace | "CS415" |
| name | Type of characters | mix of whitespace and non-whitespace | "Software Testing" |
| name | Type of characters | only whitespace | " ", "\n", "\t \n" |
| qs | Number of elements in set | empty | {} |
| qs | Number of elements in set | non-empty | {software, testing} |
| size | There is an enum with three possibities | SMALL | SMALL |
| size | There is an enum with three possibities | MEDIUM | MEDIUM |
| size | There is an enum with three possibities | BIG | BIG |

### BCC Method `Project(name: String, qs: Set(Qualification), size: ProjectSize)`
| Test Cases | name | qs | size | Oracle | JUnit test name | 
| :--- | :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | "CS415" | {software, testing} | SMALL | Project created, state = PLANNED | testValidProjectSmall_T1() |
| T2 | null | {software, testing} | SMALL | Exception | testNullName_T2() |
| T3 | ""  | {software, testing} | SMALL | Exception | testEmptyName_T3() |
| T4 | " " | {software, testing} | SMALL | Exception | testWhitespaceOnlyName_T4() |
| T5 | "Software Testing" | {software, testing} | SMALL | Project created | testNameWithWhitespaceInside_T5() |
| T6 | "CS415" | {} | SMALL | Exception | testEmptyQualificationSet_T6() |
| T7 | "CS415" | {software, testing} | MEDIUM | Project created | testValidProjectMedium_T7() |
| T8 | "CS415" | {software, testing} | BIG | Project created | testValidProjectBig_T8() |
| T9 | "CS415" | {software, testing} | null | Exception | testNullSize_T9() |
| T10 | "CS415" | null | SMALL | Exception | testNullQualifications_T10() |


### Method `equals(o: Object)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| o: Object | Type of o | Project | Project |
| o: Object | Type of o | Non-Project | String |
| o: Object | Nullness | Null | Null |
| o: Object | Nullness | Not null | Project |
| name | Number of characters | 0 | "" |
| name | Number of characters | more than 0 | "Gegee" |
| name | Type of characters | no whitespace | "Gegee" |
| name | Type of characters | mix of whitespace and non-whitespace | "Gegee Tsogtbaatar" |
| name | Type of characters | only whitespace | " ", "\n", "\t \n" |
| size | There is an enum with three possibities | SMALL | SMALL |
| size | There is an enum with three possibities | MEDIUM | MEDIUM |
| size | There is an enum with three possibities | BIG | BIG |
| status | There is an enum with four possibities | PLANNED | PLANNED |
| status | There is an enum with four possibities | SUSPENDED | SUSPENDED |
| status | There is an enum with four possibities | ACTIVE | ACTIVE |
| status | There is an enum with four possibities | FINISHED | FINISHED |
| Worker Set | Number of elements in set | Empty | {} |
| Worker Set | Number of elements in set | Non-empty | {Worker1, Worker2} |
| qs | Number of elements in set | empty | {} |

### BCC Method `equals(o: Object)`
| Test Cases | o Type | name(this) | name(o) | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | Project | "Gegee" | "Gegee" | true | testEquals_SameName_T1() |
| T2 | Project | "Gegee" | "Other" | false | testEquals_DifferentName_T2() |
| T3 | Project | "Gegee Tsogtbaatar" | "Gegee Tsogtbaatar" | true | testEquals_MixedWhitespaceName_T3() |
| T4 | Project | "Gegee" | "Gegee Tsogtbaatar" | false | testEquals_DifferentLengthNames_T4() |
| T5 | Non-Project(String) | "Gegee" | - | false | testEquals_NonProject_T5() |
| T6 | null | "Gegee" | - | false | testEquals_Null_T6() |
| T7 | Project| "Gegee" | "Gegee" | true | testEqualsSameReference_T7() |


### Method `hashCode()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Nullness | null | null |
| name | Nullness | not-null | "Gegee" |
| name | Number of characters | 0 | "" |
| name | Number of characters | more than 0 | "Gegee" |
| name | Type of characters | no whitespace | "Gegee" |
| name | Type of characters | mix of whitespace and non-whitespace | "Gegee Tsogtbaatar" |
| name | Type of characters | only whitespace | " ", "\n", "\t \n" |

### BCC Method `hashCode()`
| Test Cases | name | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 (Base) | "Gegee" | returns "Gegee".hashCode() | testHashCode_T1() |
| T2 | "Gegee Tsogtbaatar" | returns "Gegee Tsogtbaatar".hashCode() | testHashCode_WithWhitespace_T2 |


### Method `toString()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Nullness | null | null |
| name | Nullness | not-null | "Gegee" |
| name | Number of characters | 0 | "" |
| name | Number of characters | more than 0 | "Gegee" |
| name | Type of characters | no whitespace | "Gegee" |
| name | Type of characters | mix of whitespace and non-whitespace | "Gegee Tsogtbaatar" |
| name | Type of characters | only whitespace | " ", "\n", "\t \n" |
| Worker Set | Number of elements in set | Empty | {} |
| Worker Set | Number of elements in set | Non-empty | {Worker1, Worker2} |
| status | There is an enum with four possibities | PLANNED | PLANNED |
| status | There is an enum with four possibities | SUSPENDED | SUSPENDED |
| status | There is an enum with four possibities | ACTIVE | ACTIVE |
| status | There is an enum with four possibities | FINISHED | FINISHED |


### BCC Method `toString()` 
| Test Cases | name | worker set | status | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | "Gegee" | {} | PLANNED | Gegee:0:PLANNED | testToString_T1() |
| T2 | "Gegee" | {Worker1, Worker2} | PLANNED | Gegee:2:PLANNED | testToString_Planned_MultipleWorkers_T2() |
| T3 | "Gegee Tsogtbaatar" | {Worker1} | ACTIVE | Gegee Tsogtbaatar:1:ACTIVE | testToString_Active_T3() |
| T4 | "Gegee" | {Worker1} | SUSPENDED | Gegee:1:SUSPENDED | testToString_Suspended_T4() |
| T5 | "Gegee" | {Worker1} | FINISHED | Gegee:1:FINISHED | testToString_Finished_T5()  |


### Method `getName()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Nullness | null | null |
| name | Nullness | not-null | "Gegee" |
| name | Number of characters | 0 | "" |
| name | Number of characters | more than 0 | "Gegee" |
| name | Type of characters | no whitespace | "Gegee" |
| name | Type of characters | mix of whitespace and non-whitespace | "Gegee Tsogtbaatar" |
| name | Type of characters | only whitespace | " ", "\n", "\t \n" |

### BCC Method `getName()`
| Test Cases | Name | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 (Base) | "Gegee" | "Gegee" | testGetName_Normal_T1() |
| T2 | "" | Exception | testGetName_EmptyString_T2() |
| T3 |"Gegee Tsogtbaatar" | "Gegee Tsogtbaatar" | testGetName_MixedWhitespace_T3() |
| T4 | " " | Exception | testGetName_OnlyWhitespaceSpace_T4() |
| T5 | "\n" | Exception | testGetName_OnlyWhitespaceNewline_T5() |
| T6 | "\t \n" | Exception | testGetName_OnlyWhitespaceTabNewline_T6() |


### Method `getSize()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| size | There is an enum with three possibities | SMALL | SMALL |
| size | There is an enum with three possibities | MEDIUM | MEDIUM |
| size | There is an enum with three possibities | BIG | BIG |


### BCC Method `getSize()`
| Test Cases | Initial size | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 (Base) | SMALL | SMALL | testGetSize_Small_T1() |
| T2 | MEDIUM | MEDIUM | testGetSize_Medium_T2() |
| T3 | BIG | BIG | testGetSize_Big_T3() |


### Method `getStatus()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| status | There is an enum with four possibities | PLANNED | PLANNED |
| status | There is an enum with four possibities | SUSPENDED | SUSPENDED |
| status | There is an enum with four possibities | ACTIVE | ACTIVE |
| status | There is an enum with four possibities | FINISHED | FINISHED |


### BCC Method `getStatus()`
| Test Cases | Initial status | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 (Base) | PLANNED | PLANNED | testGetStatus_Planned_T1() |
| T2 | SUSPENDED | SUSPENDED | testGetSetStatus_Suspended_T2() |
| T3 | ACTIVE | ACTIVE | testGetSetStatus_Active_T3() |
| T4 | FINISHED | FINISHED | testGetSetStatus_Finished_T4() | 


### Method `setStatus()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| status | Nullness | null | null |
| status | There is an enum with four possibities | PLANNED | PLANNED |
| status | There is an enum with four possibities | SUSPENDED | SUSPENDED |
| status | There is an enum with four possibities | ACTIVE | ACTIVE |
| status | There is an enum with four possibities | FINISHED | FINISHED |


### BCC Method `setStatus()`
| Test Cases | Input status | Initial status | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | PLANNED | PLANNED | status becomes PLANNED | testSetStatus_Planned_T1() |
| T2 | SUSPENDED | SUSPENDED | status becomes SUSPENDED | testGetSetStatus_Suspended_T2() |
| T3 | ACTIVE | ACTIVE | status becomes ACTIVE | testGetSetStatus_Active_T3() |
| T4 | FINISHED | FINISHED | status becomes FINISHED | testGetSetStatus_Finished_T4() |
| T5 | null | PLANNED | Exception | testSetStatus_Null_T5() |


### Method `addWorker()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Nullness | null | null |
| w | Nullness | not nulll | Worker 1 |
| w | Assignment | worker already assigned to project | Worker1 |
| w | Assignment | worker not assigned | Worker2 |
| w | Load | worker not overloaded | willOverload(p) == false (totalworkload + new project <= 12) |
| w | Load | worker overloaded | willOverload(p) == true (totalworkload + new project > 12) |
| w | Availability | Available in company | worker3 (isAvailable() == true) |
| w | Availability | Unavailable in company | worker4 (isAvailable() == false) |
| Project | Status | PLANNED | PLANNED |
| Project | Status | SUSPENDED | SUSPENDED |
| Project | Status | ACTIVE | ACTIVE (can't add) |
| Project | Status | FINISHED | FINISHED (cant't add) |
| Worker Set | Number of elements in set | Empty | {} |
| Worker Set | Number of elements in set | Non-empty | {Worker1, Worker2} |


### BCC Method `addWorker()`
| Test Cases | Worker w | Worket Set | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | Worker2 | {} | Add successfully | testAddWorker_EmptySet_T1() |
| T2 | null | {} | Exception | testAddWorker_Null_T2()  |
| T3 | Worker3 | {Worker2} | Add successfully | testAddWorker_NonEmptySet_T3() | 

### Method `removeWorker()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Nullness | null | null |
| w | Nullness | not nulll | Worker 1 |
| w | Assignment | worker already assigned to project | Worker1 |
| w | Assignment | worker not assigned | Worker2 |
| w | Availability | Available in company | worker3 (isAvailable() == true) |
| w | Availability | Unavailable in company | worker4 (isAvailable() == false) |
| Worker Set | Number of elements in set | Empty | {} |
| Worker Set | Number of elements in set | Non-empty | {Worker1, Worker2} |
| w | Load | worker not overloaded | willOverload(p) == false after removal|
| w | Load | worker overloaded | willOverload(p) == true after removal |
| Project | Status | PLANNED | PLANNED |
| Project | Status | SUSPENDED | SUSPENDED |
| Project | Status | ACTIVE | ACTIVE (removal may affect qualifications) |
| Project | Status | FINISHED | FINISHED (removal may be ignored) |

### BCC Method `removeWorker()`
| Test Cases | Worker w | Assignment | Oracle | JUnit test name | 
| :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | Worker1 | in project | Worker is removed from the project | testRemoveWorker_Assigned_T1() |
| T2 | null | in project | Exception | testRemoveWorker_NullWorker_T2() |
| T3 | Worker1 | not in project | No change in worker set | testRemoveWorker_NotAssigned_T3() | 


### Method `getWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| Worker Set | Number of elements in set | Empty | {} |
| Worker Set | Number of elements in set | One worker | {Worker1} |
| Worker Set | Number of elements in set | Multiple workers | {Worker1, Worker2} |
| w | Assignment | worker already assigned to project | Worker1, Worker2 |
| w | Assignment | worker not assigned | Worker3 |

### BCC Method `getWorkers()`
| Test Cases | Worker set | Oracle | JUnit test name | 
| :--- | :--- | :--- | :--- |
| T1 (Base) | {} | returns empty set {} | testGetWorkers_EmptySet_T1() |
| T2 | {Worker1} | returns {Worker1} | testGetWorkers_OneWorker_T2() |
| T3 | {Worker1, Worker2} | returns {Worker1, Worker2} | testGetWorkers_MultipleWorkers_T3() | 


### Method `removeAllWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| Worker Set | Number of elements in set | Empty | {} |
| Worker Set | Number of elements in set | One worker | {Worker1} |
| Worker Set | Number of elements in set | Multiple workers | {Worker1, Worker2} |
| w | Assignment | worker already assigned to project | Worker1, Worker2 |
| w | Assignment | worker not assigned | Worker3 |
| Project | Status | PLANNED | Status remains PLANNED |
| Project | Status | SUSPENDED | Status remains SUSPENDED |
| Project | Status | ACTIVE | Status may need update |
| Project | Status | FINISHED | No effect |

### BCC Method `removeAllWorkers()`
| Test Cases | Worker Set | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 (Base) | {Worker1, Worker2} | All workers removed | testRemoveAllWorkers_Multiple_T1() |
| T2 | {Worker1} | All workers removed | testRemoveAllWorkers_OneWorker_T2() |
| T3 | {} | No change | testRemoveAllWorkers_Empty_T3() | 


### Method `getRequiredQualifications()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|  qualificationSet | Number of elements  | A1: Empty | {} |
|  qualificationSet | Number of elements  | A2: Non-empty | {java, junit} |

### BCC Method `getRequiredQualifications()`
| Test cases | qualificationSet | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 (Base) | {software} | {software} | testGetRequiredQualifications_T1_singleton() |
| T2 | {java, junit} | {java, junit} | testGetRequiredQualifications_T2_twoQualifications() | 



### Method `addQualifications()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|  qualificationSet | Number of elements  | A1: Empty | {} |
|  qualificationSet | Number of elements  | A2: Non-empty | {java, junit} |
|  Qualification q | Nullness  |  B1: Null | {}  |
|  Qualification q | Nullness  |  B2: Not Null | Valid Qualification ref |

### BCC Method `addQualifications()` 
| Test cases | qualificationSet | q | Oracle | Junit test name | 
| :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | Non-empty | Not null | Qualification added to set | testAddQualifications_T1_addNormal() |
| T2 | Non-empty | Already exists | Set unchanged | testAddQualifications_T2_addExisting() |
| T3 | Non-empty | Null | Exception | testAddQualifications_T3_nullQualification() |


### Method `getMissingQualifications()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|  required qualificationSet | Size  | A1: Empty | {} |
|  required qualificationSet | Size  | A2: Non-empty | {q1, q2} |
|  missingSet | Size | B1: Empty (not null set) | {} |
|  missingSet | Size | B2: Non-empty | {q2} |

### BCC Method `getMissingQualifications()` 
| Test cases | Required set | Missing set | Oracle | Junit test name |
| :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | Non-empty | Non-empty | Returns the set of qualifications still missing | testGetMissingQualifications_T1_oneMissing() | 
| T2 | Non-empty | Empty | Returns empty set | testGetMissingQualifications_T2_oneClosed() |


### Method `isHelpful()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Nullness  | A1: Null  | null |
| w | Nullness  | A1: Not  null  | w1 |
| helpful | Adds needed qualification | B1: Helpful | isHelpful(w) = true |
| helpful | Adds needed qualification | B2: Helpful | isHelpful(w) = false |

### BCC Method `isHelpful()`
| Test cases | w | helpful | Oracle | JUnit test name | 
| :--- | :--- | :--- | :--- | :--- |
| T1(base) | Not null | false | false | testIsHelpful_T1_noMissingQualifications() |
| T2 | Not null | true | true | testIsHelpful_T2_coversMissingQualification() | 


### Method `toDTO()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w projectSet | Size | A1: Empty | {} |
| w projectSet | Size | A2: One worker | {w1} |
| w projectSet | Size | A3: Multiple worker | {w1, w2} |

### BCC Method `toDTO()`
| Test Cases | w projectSet | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- |
| T1 | {w1, w2} | DTO contains correct name, size, status, all qualifications, and both worker names | testToDto() | 


## Company Class
[Return to top](#top)

### Method `Company(name: String)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name  | Nullness | A1: Null | null |
| name  | number of characters in name | A2: 0 | "" |
| name  | type of characters in name | whitespaces only | " \n\t " |
| name  | type of characters in name | no whitespaces | "Java Hills" |
| name  | type of characters in name | mix with whitespaces| "software testing & analysis" |

### BCC Method `Company(name: String)`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | name = more than 0, no whitespaces | testToString_brandNew | No exception; toString() returns "Java Hills:0:0" |
| T2 | name = 0 | testConstructor_throwsWhenEmpty | Throws IllegalArgumentException |
| T3 | name = whitespaces only | testConstructor_throwsWhenWhitespaceOnly | Throws IllegalArgumentException |
| T4 | name = mix with whitespaces | testConstructor_validWhiteSpace | No exception; toString() returns "software testing & analysis:0:0" |
| T5 | name = (null) | testConstructor_throwsWhenNull | Throws IllegalArgumentException |

### Method `equals(o: Object)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| o | Nullness | A1: Null | null |
| o | Nullness | A1: Not null | new Company("Tech Solutions") |
| o | Type of Object  | B1: Not company | "Tech Solutions" |
| o | Type of Object  | B2: Company | new Company("Tech Solutions") |
| nameMatch | this.name vs o.name | C1: match  | "Tech Solutions" vs "Tech Solutions"|
| nameMatch | this.name vs o.name | C1: not a match  | "Tech Solutions" vs "Outdated Tech"|


### BCC Method `equals(o: Object)`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | o = Company, nameMatch = match | testEquals_equal | returns true |
| T2 | o = same object | testEquals_sameObject | returns true |
| T3 | o = null | testEquals_null | returns false |
| T4 | o = Not company | testEquals_differentType | returns false |
| T5 | o = Company, nameMatch = not a match | testEquals_notEqual | returns false |


### Method `toString()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| companyName | content | A1: 0 available | "Java Hills" |
| companyName | content | A2: 1 available | "software testing & analysis" |
| availableWorkersCount | count | B1: 0 | 0 |
| projectNumbr | count | C1: 0 | 0 |


### BCC Method `toString()`
| BCC Test | Blocks | JUnit test name | Oracle |
| T1 | A1 (0 available), B1 (0 projects) | testToString_brandNew | returns "Java Hills:0:0" |

### Method `getName()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| companyName | Size | A1: Empty | n"" |
| companyName | Size | A2: more than 0 | "Java Hills" |
| companyName | content | B1: No whitespace | "TestCo" |
| companyName | content | B1: has whitespace | "Java Hills" |

### BCC Method `getName()`
| :--- | :--- | :--- | :--- |
| BCC Test | Blocks | JUnit test name | Oracle |
| T1 (base) | A2 (more than 0), B1 (no whitespace) | testGetName_normal | returns "Java Hills" |

### Method `getEmployedWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| empployedWorker set | Size | A1: Empty  | {} |
| empployedWorker set | Size | A2: One worker  | {Alice} |
| empployedWorker set | Size | A3: multiple worker  | {Alice, Bob} |

### BCC Method `getEmployedWorkers()`
| :--- | :--- | :--- | :--- |
| BCC Test | Blocks | JUnit test name | Oracle |
| T1 | A1 (Empty) | testGetEmployedWorkers_empty | returns empty set (size = 0) |
| T2 | A2 (One worker) | testCreateWorker_valid | returns set contains created worker; size = 1 |
| T3 | A3 (Multiple workers) | testGetAvailableWorkers_twoWorker | employed worker set size = 2 |

### Method `getAvailableWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| empployedWorker set | Size | A1: Empty employed  | {} |
| empployedWorker set | Size | A2: Non-empty employed  | {Alice, Bob} |
| availability | count | B1: 0 | {Alice unavailable, Bob unavailable} |
| availability | count | B2: 1 | {Alice available, Bob unavailable} |
| availability | count | B3: 1 | {Alice available, Bob available} |

### BCC Method `getAvailableWorkers()`
| :--- | :--- | :--- | :--- |
| BCC Test | Blocks | JUnit test name | Oracle |
| T1 (base) | A1 (Empty employed) | testGetAvailableWorkers_empty | returns empty set (size = 0) |

### Method `getUnavailableWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| empployedWorker set | Size | A1: Empty  | {} |
| empployedWorker set | Size | A2: Non-empty | {Alice, Bob} |
| unavailability | unavailable count | B1: 0 | {Alice available, Bob available} |
| unavailability | unavailable count | B2: 1 | {Alice available, Bob unavailable} |
| unavailability | unavailable count | B3: 2 | {Alice unavailable, Bob unavailable} |

### BCC Method `getUnavailableWorkers()`
| :--- | :--- | :--- | :--- |
| BCC Test | Blocks | JUnit test name | Oracle |
| T1 (base) | A1 (Empty) | testGetUnavailableWorkers_empty | returns empty set (size = 0) |

### Method `getAssignedWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
|AssignedWorkers  | Number of assigned workers  | A1: = 0 | No workers assigned |
|  |  | A2: = 1 | w1 assigned to p1 |
|  |  | A3: > 1 | Multiple workers assigned |

### BCC Method `getAssignedWorkers()`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A1 | testGetAssignedWorkers_empty | returns empty set (size = 0) |
| T2 | A2 | testGetAssignedWorkers_and_UnassignedWorkers | returns set contains w1 and size = 1 |

### Method `getUnassignedWorkers()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| unassigned workers |  Size | A1: Empty | nobody assigned |
|  |  | A2: One Worker | w1 employed, 0 projects |
|  |  | A3: Multiple workers | w1, w2 employed, 0 projects |

### BCC Method `getUnassignedWorkers()`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A1 | testGetUnassignedWorkers_empty | returns empty set (size = 0) |
| T2 | A2 | testGetAssignedWorkers_and_UnassignedWorkers | returns set contains w2 and size = 1 |

### Method `getProjects()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| Project set | size | A1:Empty | no projects |
|  |  | A2: One Project | p1 exists |
|  |  | A3: Multiple Projects | p1, p2 exist |

### BCC Method `getProjects()`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A1 | testGetProjects_initiallyEmpty | returns empty set (size = 0) |


### Method `getQualifications()`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| Qualification set | Size | A1:Empty | no qualifications |
|  |  | A2: One qualification | q1 exists |
|  |  |  A3: Multiple qualifications| q1, q2 exist |

### BCC Method `getQualifications()`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A1 | testGetQualifications_initiallyEmpty | returns empty set (size = 0) |
| T2 | A2 | testCreateQualification_valid | company qualifications contains q; q.toString() == "Java" |


### Method `createWorker(name: String, qs: Set<Qualification>, salary: double)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Nullness | A1:Null | null |
|  | Number of characters | A2:0 | "" |
|  |  | A3: > 0 | "Alice" |
|  | Char type | A4: Only whitespace | "" |
| qs | Nullness | B1: Null  | null |
|  | Size | B2: Empty | {} |
|  |  | B3: Non-Empty | {q1} |
| qs | Subset of company qualifications | B4: All in company | {q1} |
|  |  | B5: Not all in company | {qNotinCompany} |
| Salary | Relationship to 0 | C1: Negative | -1 |
|  |  | C2: Zero | 0 |
|  |  | C3: Positive | 50000 |

### BCC Method `createWorker(name: String, qs: Set<Qualification>, salary: double)`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A3 B3 B4 C3 | testCreateWorker_valid | returns w; company.getEmployedWorkers() contains w; q.getWorkers() contains w; w.getProjects().size()==0 |
| T2 | B2 | testCreateWorker_invalidInputs | returns null |


### Method `createQualification(description: String)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| description | Nullness | A1:Null | null |
|  | Number of characters | A2: 0 | "" |
|  |  | A3: > 0 | "AWS" |
|  | Char type | A4: Only whitespace | "" |

### BCC Method `createQualification(description: String)`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A3 | testCreateQualification_valid | returns q; company qualifications contains q; q.toString()=="Java" |
| T2 | A1 | testCreateQualification_null | throws IllegalArgumentException |


### Method `createProject(name: qs: Set(Qualification), size: ProjectSize)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| name | Nullness | A1: Null | null |
|  | Number of characters | A2: 0 | "" |
|  |  | A3: > 0 | "ProjectX" |
|  | Char type | A4: Only whitespace | " " |
| qs | Nullness | B1: Null | null |
|  | Size | B2: Empty | {} |
|  |  | B3: Non-empty | {q1} |
| qs | Subset of company qualifications | B4: All in company | {q1} |
|  |  | B5: Not all in company | {qNotInCompany} |
| size | Enum value | C1: SMALL | SMALL |
|  |  | C2: MEDIUM | MEDIUM |
|  |  | C3: BIG | BIG |

### BCC Method `createProject(name: String, qs: Set(Qualification), size: ProjectSize)`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A3 B3 C1 | testCreateProject_valid | returns p; company projects contains p |


### Method `start(p: Project)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| p | Nullness | A1: Null | null |
|  |  | A2: Not null | p1 |
| project status | Before start | B1: PLANNED | PLANNED |
|  |  | B2: SUSPENDED | SUSPENDED |
|  |  | B3: ACTIVE | ACTIVE |
|  |  | B4: FINISHED | FINISHED |
| requirements | Missing qualifications | C1: None missing | missing = {} |
|  |  | C2: Some missing | missing {q1} |

### BCC Method `start(p: Project)`
| BCC Test | Blocks | JUnit test name | Oracle |
| :--- | :--- | :--- | :--- |
| T1 (base) | A1 | testStart_nullProject_T1() | throws IllegalArgumentException |
| T2 | A2 | testStart_ProjectNotInCompany_T2() | Exception |
| T3 | A2, B1, C1 | testStart_PlannedAndQualificationsMet_T3() | sets status to ACTIVE |
| T4 | A2, B1, C2 | testStart_PlannedAndQualificationsMissing_T4() | status unchanged |
| T5 | A2, B4 | testStart_AlreadyActiveOrFinished_T5() | status unchanged |

### Method `finish(p: Project)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| p | Nullness | A1: Null | null |
|  |  | A2: Not null | p1 |
| project status | Before finish | B1: ACTIVE | ACTIVE |
|  |  | B2: PLANNED | PLANNED |
|  |  | B3: SUSPENDED | SUSPENDED |
|  |  | B4: FINISHED | FINISHED |
| workers on project | Size | C1: Empty | {} |
|  |  | C2: Non-empty | {w1, w2} |

### BCC Method `finish(p: Project)` 
| Test cases | p | Project status | Workers on project | Oracle | JUnit test name | 
| :--- | :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | Not null | ACTIVE | non empty | Sets status to FINISHED; removes all workers | testFinish_Base_ActiveProjectWithWorkers_T1() |
| T2 | Not null | ACTIVE | empty | Sets status to FINISHED; no workers to remove | testFinish_ActiveProjectNoWorkers_T2() |
| T3 | Not null | FINISHED | empty | Returns early (non-ACTIVE project) | testFinish_FinishedProjectReturnsEarly_T3()  |
| T4 | Not null | SUSPENDED | empty | Returns early (non-ACTIVE project) | testFinish_SuspendedProjectReturnsEarly_T4() |
| T5 | Not null | PLANNED | empty | Returns early (non-ACTIVE project) | testFinish_PlannedProjectReturnsEarly_T5() |
| T6 | null | - | - | Exception | testFinish_NullProjectThrows_T6() |


### Method `assign(w: Worker, p: Project)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Nullness | A1: Null | null |
|  |  | A2: Not null | w1 |
| p | Nullness | B1: Null | null |
|  |  | B2: Not null | p1 |
| worker availability | Available in company | C1: Available | w isAvailable() = true |
|  |  | C2: Not available | w isAvailable() = false |
| project status | State | D1: PLANNED | PLANNED |
|  |  | D2: SUSPENDED | SUSPENDED |
|  |  | D3: ACTIVE | ACTIVE |
|  |  | D4: FINISHED | FINISHED |
| assignment | Already assigned | E1: Already on project | p has w |
|  |  | E2: Not on project | p does not have w |
| workload | Overload | F1: Not overloaded | willOverload(p) = false |
|  |  | F2: Overloaded | willOverload(p) = true |
| helpful | Adds needed qualification | G1: Helpful | isHelpful(w) = true |
|  |  | G2: Not helpful | isHelpful(w) = false |

### BCC Method `assign(w: Worker, p: Project)`
| Test Cases | Availability | Assigned | Status | Overload | Helpful | Oracle | JUnit test name | 
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | C1 | E2 | D1 | F1 | G1 | Assignment succeeds | testAssign_BaseCase_T1() |
| T2 | C2 | E2 | D1 | F1 | G1 | No assignment | testAssign_WorkerNotAvailable_T2() |
| T3 | C1 | E1 | D1 | F1 | G1 | No assignment | testAssign_AlreadyAssigned_T3() |
| T4 | C1 | E2 | D2 | F1 | G1 | No assignment | testAssign_ProjectActive_T4() |
| T5 | C1 | E2 | D1 | F2 | G1 | No assignment | testAssign_Overloaded_T5() |
| T6 | C1 | E2 | D1 | F1 | G2 | No assignment | testAssign_NotHelpful_T6() |


### Method `unassign(w: Worker, p: Project)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Nullness | A1: Null | null |
|  |  | A2: Not null | w1 |
| p | Nullness | B1: Null | null |
|  |  | B2: Not null | p1 |
| assignment | Worker in project | C1: Assigned | p has w |
|  |  | C2: Not assigned | p does not have w |
| worker projects | After unassign | D1: 0 projects | w had only p |
|  |  | D2: Still has projects | w still has p2 |
| requirements | After removal | E1: Still met | missing = {} |
|  |  | E2: Not met | missing {q1} |

### BCC Method `unassign(w: Worker, p: Project)` 
| Test Cases | w | p | Assignment | Worker projects | Requirements | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| T1(Base) | A2 | B2 | C1 | D2 | E1 | unassign | testUnassign_BaseCase_T1() |
| T2 | A1 | B2 | C1 | D2 | E1 | Exception | testUnassign_WorkerNull_T2() |
| T3 | A2 | B1 | C1 | D2 | E1 | Exception | testUnassign_ProjectNull_T3() |
| T4 | A2 | B2 | C2 | D2 | E1 | return | testUnassign_WorkerNotAssigned_T4() |
| T5 | A2 | B2 | C1 | D1 | E1 | worker removed | testUnassign_WorkerOnlyProject_T5() |
| T6 | A2 | B2 | C1 | D2 | E2 | ACTIVE becomes SUSPENDED | testUnassign_ProjectRequirementsNotMet_T6() |

### Method `unassignAll(w: Worker)`
| Variable | Characteristic | Partition | Value |
| :--- | :--- | :--- | :--- |
| w | Nullness | A1: Null | null |
|  |  | A2: Not null | w1 |
| worker projects | Number of projects | B1: 0 | no projects |
|  |  | B2: 1 | only p1 |
|  |  | B3: Multiple | p1 and p2 |
| requirements | After removal | C1: Still met | missing = {} |
|  |  | C2: Not met | missing {q1} |

### BCC Method `unassignAll(w: Worker)`
| Test Cases | w | Worker projects | Requirements | Oracle | JUnit test name |
| :--- | :--- | :--- | :--- | :--- | :--- |
| T1 (Base) | not null | 1 | Still met | Removes worker from p1 | testUnassignAll_Base_T1() |
| T2 | not null | 0 or multiple | Still met | Removes worker from all projects | testUnassignAll_ZeroOrMultipleProjects_T2() |
| T3 | not null | 1+ | Not met | Removes worker | testUnassignAll_MissingRequirements_T3() |
| T4 | null | - | - | Exception | testUnassignAll_NullWorker_T4() |

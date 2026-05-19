package edu.colostate.cs415.server;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import edu.colostate.cs415.db.DBConnector;
import edu.colostate.cs415.dto.*;
import edu.colostate.cs415.model.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;

import com.google.gson.Gson;

public class RestControllerTest {
    final static int PORT = 7654;
    static DBConnector dbConnector;
    static Company company;
    static Qualification qualification;
    static HashSet<Qualification> qualificationSet;
    static Worker worker;
    static Project project;
    static RestController restController;
    static Gson gson;

    // Helper methods - Taken from slides
    private static void resetSpark() {
        try {
            spark.Spark.stop();
        } catch (Exception ignored) {}
        try {
            spark.Spark.awaitStop();
        } catch (Exception ignored) {}
    }

    private static String url(String path) {
        return ("http://localhost:" + PORT + path).replace(" ", "%20");
    }

    private static void setControllerCompany(Company c) {
        try {
            Field f = RestController.class.getDeclaredField("company");
            f.setAccessible(true);
            f.set(restController, c);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void assertRequestFails(Request req) throws IOException {
        boolean failed = false;
        try {
            req.execute().returnContent().asString();
        } catch (Exception e) {
            failed = true;
        }
        assertTrue(failed);
    }

    @BeforeClass
    public static void init() {

        dbConnector = mock(DBConnector.class);
        company = new Company("Test Company");
        when(dbConnector.loadCompanyData()).thenAnswer((i) -> company);

        restController = new RestController(PORT, dbConnector);
        restController.start();
        spark.Spark.awaitInitialization();

        gson = new Gson();
        qualification = company.createQualification("Java");
        qualificationSet = new HashSet<Qualification>(Arrays.asList(qualification));
        worker = company.createWorker("Johhny Appleseed", qualificationSet, 100000);
        project = company.createProject("Project", qualificationSet, ProjectSize.BIG);
    }
    @Before
    public void resetControllerCompanyBeforeEachTest() {
        setControllerCompany(company);
    }


    @AfterClass
    public static void tearDownServer() {
        try {
            if (restController != null){
                restController.stop();
            }
        } catch (Exception ignored) {}
    }


    @Test
    public void test() {
        assert (true);
    }
    //Qualifications API Test
    @Test
    public void testGetQualifications() throws IOException {
        Company testCompany = new Company("Qualifications Company");
        testCompany.createQualification("Java");
        testCompany.createQualification("Python");
        setControllerCompany(testCompany);

        QualificationDTO[] qualifications = gson.fromJson(
            Request.get(url("/api/qualifications")).execute().returnContent().asString(),
            QualificationDTO[].class);

        assertEquals(testCompany.getQualifications().size(), qualifications.length);
    }

    @Test
    public void testGetQualificationWithDescription() throws IOException {
        Company testCompany = new Company("Qualification Company");
        Qualification q = testCompany.createQualification("AWS");
        setControllerCompany(testCompany);

        QualificationDTO retrievedQualification = gson.fromJson(
            Request.get(url("/api/qualifications/AWS")).execute().returnContent().asString(),
            QualificationDTO.class);

        assertEquals(q.toDTO(), retrievedQualification);
    }

    @Test
    public void testGetQualificationNotFound() throws IOException {
        Company testCompany = new Company("Empty Qualification Company");
        setControllerCompany(testCompany);

        assertRequestFails(Request.get(url("/api/qualifications/NonExistent")));
    }

    @Test
    public void testGetQualificationInvalidDescription() throws IOException {
        Company testCompany = new Company("Invalid Qualification Company");
        setControllerCompany(testCompany);

        assertRequestFails(Request.get(url("/api/qualifications/%20")));
    }

    @Test
    public void testGetQualificationInvalidDescription2() throws IOException {
        Company testCompany = new Company("Invalid Qualification Company 2");
        setControllerCompany(testCompany);

        assertRequestFails(Request.get(url("/api/qualifications/%20%20")));
    }

    @Test
    public void testPostCreateQualification() throws IOException {
        Company testCompany = new Company("Create Qualification Company");
        setControllerCompany(testCompany);

        String description = "Docker";
        Qualification qualification2 = new Qualification(description);
        String qualificationString = gson.toJson(qualification2.toDTO());

        Request.post(url("/api/qualifications/" + description))
            .bodyString(qualificationString, ContentType.APPLICATION_JSON)
            .execute().returnContent().asString();

        QualificationDTO retrievedQualification = gson.fromJson(
            Request.get(url("/api/qualifications/" + description)).execute().returnContent().asString(),
            QualificationDTO.class);

        assertEquals(qualification2.toDTO(), retrievedQualification);
    }

    @Test
    public void testPostCreateQualificationDescriptionMismatch() throws IOException {
        Company testCompany = new Company("Mismatch Qualification Company");
        setControllerCompany(testCompany);

        Qualification qualification2 = new Qualification("DifferentDescription");
        String qualificationString = gson.toJson(qualification2.toDTO());

        assertRequestFails(Request.post(url("/api/qualifications/WrongDescription"))
            .bodyString(qualificationString, ContentType.APPLICATION_JSON));
    }

    //worker API Test
    @Test
    public void testGetWorkers() throws IOException{
        WorkerDTO[] workers = gson.fromJson(
            Request.get(url("/api/workers")).execute().returnContent().asString(),
            WorkerDTO[].class);
        assertEquals(company.getEmployedWorkers().size(), workers.length);
    }


    @Test
    public void testGetWorkerWithName() throws IOException {
        String name = "Johhny%20Appleseed";
        WorkerDTO retrievedWorker = gson.fromJson(
            Request.get(url("/api/workers/" + name)).execute().returnContent().asString(),
            WorkerDTO.class);
        assertEquals(retrievedWorker, worker.toDTO());
    }


    @Test
    public void testPostCreateWorker() throws IOException {
        String name = "Amy Parsons";
        Worker worker2 = new Worker(name, qualificationSet, 1000000);
        String worker2String = gson.toJson(worker2.toDTO());
        String response = Request.post(url("/api/workers/" + name)).bodyString(worker2String, ContentType.APPLICATION_JSON).execute().returnContent().asString();

        WorkerDTO retrievedWorker = gson.fromJson(
            Request.get(url("/api/workers/" + name)).execute().returnContent().asString(),
            WorkerDTO.class);
        assertEquals(retrievedWorker, worker2.toDTO());
    }

    @Test
    public void testGetWorkerNotFound() throws IOException {
        Company testCompany = new Company("Empty Worker Company");
        setControllerCompany(testCompany);

        assertRequestFails(Request.get(url("/api/workers/NonExistent")));
    }

    @Test
    public void testPostCreateWorkerNameMismatch() throws IOException {
        Company testCompany = new Company("Mismatch Worker Company");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        setControllerCompany(testCompany);

        Worker worker2 = new Worker("DifferentName", qSet, 50000);
        String workerString = gson.toJson(worker2.toDTO());

        assertRequestFails(Request.post(url("/api/workers/WrongName"))
            .bodyString(workerString, ContentType.APPLICATION_JSON));
    }

    @Test
    public void testPostCreateWorkerUnknownQualification() throws IOException {
        Company testCompany = new Company("No Quals Worker Company");
        setControllerCompany(testCompany);

        Qualification q = new Qualification("Unknown");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Worker worker2 = new Worker("Bob Smith", qSet, 50000);
        String workerString = gson.toJson(worker2.toDTO());

        assertRequestFails(Request.post(url("/api/workers/Bob Smith"))
            .bodyString(workerString, ContentType.APPLICATION_JSON));
    }

    // Project API tests
    @Test
    public void testGetProjects() throws IOException {
        Company testCompany = new Company("Projects Company");
        Qualification q = testCompany.createQualification("Python");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        testCompany.createProject("Alpha", qSet, ProjectSize.SMALL);
        testCompany.createProject("Beta", qSet, ProjectSize.MEDIUM);
        setControllerCompany(testCompany);

        ProjectDTO[] projects = gson.fromJson(
            Request.get(url("/api/projects")).execute().returnContent().asString(),
            ProjectDTO[].class);
        assertEquals(testCompany.getProjects().size(), projects.length);
    }

    @Test
    public void testGetProjectWithName() throws IOException {
        Company testCompany = new Company("Single Project Company");
        Qualification q = testCompany.createQualification("C++");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Project testProject = testCompany.createProject("MyProject", qSet, ProjectSize.MEDIUM);
        setControllerCompany(testCompany);

        ProjectDTO retrievedProject = gson.fromJson(
            Request.get(url("/api/projects/MyProject")).execute().returnContent().asString(),
            ProjectDTO.class);
        assertEquals(testProject.toDTO(), retrievedProject);
    }

    @Test
    public void testGetProjectNotFound() throws IOException {
        Company testCompany = new Company("Empty Company");
        setControllerCompany(testCompany);

        assertRequestFails(Request.get(url("/api/projects/NonExistent")));
    }

    @Test
    public void testPostCreateProject() throws IOException {
        Company testCompany = new Company("Create Project Company");
        Qualification q = testCompany.createQualification("Rust");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        setControllerCompany(testCompany);

        String name = "NewProject";
        Project project2 = new Project(name, qSet, ProjectSize.BIG);
        String projectString = gson.toJson(project2.toDTO());
        Request.post(url("/api/projects/" + name))
            .bodyString(projectString, ContentType.APPLICATION_JSON)
            .execute().returnContent().asString();

        ProjectDTO retrievedProject = gson.fromJson(
            Request.get(url("/api/projects/" + name)).execute().returnContent().asString(),
            ProjectDTO.class);
        assertEquals(project2.toDTO(), retrievedProject);
    }

    @Test
    public void testPostCreateProjectNameMismatch() throws IOException {
        Company testCompany = new Company("Mismatch Company");
        Qualification q = testCompany.createQualification("Go");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        setControllerCompany(testCompany);

        Project project2 = new Project("DifferentName", qSet, ProjectSize.SMALL);
        String projectString = gson.toJson(project2.toDTO());
        assertRequestFails(Request.post(url("/api/projects/WrongName"))
            .bodyString(projectString, ContentType.APPLICATION_JSON));
    }

    @Test
    public void testPostCreateProjectUnknownQualification() throws IOException {
        Company testCompany = new Company("No Quals Company");
        setControllerCompany(testCompany);

        Qualification q = new Qualification("Unknown");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Project project2 = new Project("SomeProject", qSet, ProjectSize.SMALL);
        String projectString = gson.toJson(project2.toDTO());
        
        assertRequestFails(Request.post(url("/api/projects/SomeProject"))
            .bodyString(projectString, ContentType.APPLICATION_JSON));
    }

    // Company API tests
    private String putRequest(String path, Object dto) throws IOException {
        String json = gson.toJson(dto);
        return Request.put(url(path))
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
    }

    @Test
    public void testPutAssign() throws IOException {
        Company testCompany = new Company("Assign Company");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Project p = testCompany.createProject("Alpha", qSet, ProjectSize.BIG);
        Worker w = testCompany.createWorker("Alice", qSet, 50000);

        setControllerCompany(testCompany);

        AssignmentDTO dto = new AssignmentDTO(w.getName(), p.getName());
        String response = putRequest("/api/assign", dto);

        assertEquals("OK", response);
        assertTrue(p.getWorkers().contains(w));
    }

    @Test
    public void testProjectMissingQualificationsUpdatesAfterAssign() throws IOException {
        Company testCompany = new Company("ABC Company");
        Qualification testing = testCompany.createQualification("Testing");
        Qualification security = testCompany.createQualification("Security");
        Qualification java = testCompany.createQualification("Java");

        HashSet<Qualification> projectRequirements = new HashSet<>(Arrays.asList(testing, security, java));
        HashSet<Qualification> workerSkills = new HashSet<>(Arrays.asList(testing));

        testCompany.createProject("ABC", projectRequirements, ProjectSize.SMALL);
        testCompany.createWorker("Alice", workerSkills, 65000);

        setControllerCompany(testCompany);

        AssignmentDTO assignAliceToABC = new AssignmentDTO("Alice", "ABC");

        Request.put(url("/api/assign"))
            .bodyString(gson.toJson(assignAliceToABC), ContentType.APPLICATION_JSON)
            .execute();

        ProjectDTO abcProject = gson.fromJson(
            Request.get(url("/api/projects/ABC"))
                .execute()
                .returnContent()
                .asString(),
            ProjectDTO.class);

        assertEquals(2, abcProject.getMissingQualifications().length);
    }

    @Test 
    public void testPutUnassign() throws IOException {
        company.assign(worker, project);

        AssignmentDTO dto = new AssignmentDTO(worker.getName(), project.getName());
        assertEquals("OK", putRequest("/api/unassign", dto));
        assertTrue(!project.getWorkers().contains(worker));
    }

    @Test
    public void testPutStartProject() throws IOException {
        company.assign(worker, project);

        ProjectDTO dto = new ProjectDTO();
        dto.setName(project.getName());

        assertEquals("OK", putRequest("/api/start", dto));
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
    }

    @Test
    public void testPutFinishProject() throws IOException {
        if(project.getStatus() != ProjectStatus.ACTIVE) {
            company.assign(worker, project);
            company.start(project);
        }

        ProjectDTO dto = new ProjectDTO();
        dto.setName(project.getName());

        assertEquals("OK", putRequest("/api/finish", dto));
        assertEquals(ProjectStatus.FINISHED, project.getStatus());
    }

    // helloWord() test

    @Test
    public void testGetHelloWorld() throws IOException {
        String response = Request.get(url("/helloworld"))
            .execute().returnContent().asString();
        assertEquals("Hello World!", response);
    }

    // findProject() tests

    @Test
    public void testPutAssignProjectNotFound() throws IOException {
        Company testCompany = new Company("Assign No Project Company");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Worker w = testCompany.createWorker("Alice", qSet, 50000);
        setControllerCompany(testCompany);

        AssignmentDTO dto = new AssignmentDTO(w.getName(), "NonExistentProject");
        assertRequestFails(Request.put(url("/api/assign"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    @Test
    public void testPutUnassignProjectNotFound() throws IOException {
        Company testCompany = new Company("Unassign No Project Company");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Worker w = testCompany.createWorker("Alice", qSet, 50000);
        setControllerCompany(testCompany);

        AssignmentDTO dto = new AssignmentDTO(w.getName(), "NonExistentProject");
        assertRequestFails(Request.put(url("/api/unassign"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    // findWorker() tests

    @Test
    public void testPutAssignWorkerNotFound() throws IOException {
        Company testCompany = new Company("Assign No Worker Company");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        testCompany.createProject("Alpha", qSet, ProjectSize.BIG);
        setControllerCompany(testCompany);

        AssignmentDTO dto = new AssignmentDTO("NonExistentWorker", "Alpha");
        assertRequestFails(Request.put(url("/api/assign"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    @Test
    public void testPutUnassignWorkerNotFound() throws IOException {
        Company testCompany = new Company("Unassign No Worker Company");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        testCompany.createProject("Alpha", qSet, ProjectSize.BIG);
        setControllerCompany(testCompany);

        AssignmentDTO dto = new AssignmentDTO("NonExistentWorker", "Alpha");
        assertRequestFails(Request.put(url("/api/unassign"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    // finishProject() tests

    @Test
    public void testPutFinishProjectNotFound() throws IOException {
        Company testCompany = new Company("Finish No Project Company");
        setControllerCompany(testCompany);

        ProjectDTO dto = new ProjectDTO();
        dto.setName("NonExistentProject");

        assertRequestFails(Request.put(url("/api/finish"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    // startProject() tests
    @Test
    public void testPutStart() throws IOException {
        Company testCompany = new Company("Start Missing Company Name");
        setControllerCompany(testCompany);

        ProjectDTO dto = new ProjectDTO();

        assertRequestFails(Request.put(url("/api/start"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    @Test
    public void testPutStartProjectNotFound() throws IOException {
        Company testCompany = new Company("Start No Project Company");
        setControllerCompany(testCompany);

        ProjectDTO dto = new ProjectDTO();
        dto.setName("NonExistentProject");

        assertRequestFails(Request.put(url("/api/start"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    @Test
    public void testPutFinish() throws IOException {
        Company testCompany = new Company("Finish Project Company");
        Qualification testing = testCompany.createQualification("Testing");
        HashSet<Qualification> requirements = new HashSet<>(Arrays.asList(testing));
        Worker alice = testCompany.createWorker("Alice", requirements, 65000);
        Project project = testCompany.createProject("ABC", requirements, ProjectSize.SMALL);

        testCompany.assign(alice, project);
        testCompany.start(project);

        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
        
        setControllerCompany(testCompany);

        ProjectDTO dto = new ProjectDTO();
        dto.setName("ABC");

        String response = Request.put(url("/api/finish"))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

        assertEquals("OK", response);
        assertEquals(ProjectStatus.FINISHED, project.getStatus());
    }

    // optionsCORS() tests

    @Test
    public void testOptionsCORS() throws IOException {
        String response = Request.options(url("/api/qualifications"))
            .addHeader("Access-Control-Request-Headers", "Content-Type")
            .addHeader("Access-Control-Request-Method", "GET")
            .execute().returnContent().asString();

        assertEquals("OK", response);
    }

    @Test
    public void testOptionsCORSNoHeaders() throws IOException {
        String response = Request.options(url("/api/qualifications"))
            .execute().returnContent().asString();

        assertEquals("OK", response);
    }

    @Test
    public void create_project_wrong_size() throws IOException {
        Company testCompany = new Company ("Size Test");
        setControllerCompany(testCompany);

        String name = "Bad Size";
        String malformedJson = "{\"name\":\"Bad Size\",\"size\":\"HUGE\",\"status\":\"PLANNED\",\"workers\":[],\"qualifications\":[],\"missingQualifications\":[]}";
        assertRequestFails(Request.post(url("/api/projects/" + name))
            .bodyString(malformedJson, ContentType.APPLICATION_JSON));
    }

    @Test
    public void create_worker_one_qualification() throws IOException {
        Company testCompany = new Company ("Single Qual Test");
        testCompany.createQualification("Java");
        setControllerCompany(testCompany);

        String name = "Solo Worker";
        WorkerDTO dto = new WorkerDTO(name, 50000, 0, new String[]{}, new String[]{"Java"});

        Request.post(url("/api/workers/" + name))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON)
            .execute().returnContent().asString();

        Worker retrieved = testCompany.getEmployedWorkers().stream()
            .filter(w -> w.getName().equals(name)).findFirst().get();
        
        assertEquals(1, retrieved.getQualifications().size());
    }

    @Test
    public void create_worker_wrong_salary() throws IOException {
        Company testCompany = new Company("Salary Test");
        setControllerCompany(testCompany);

        String name = "Bad Salary";
        WorkerDTO dto = new WorkerDTO(name, -50000, 0, new String[]{}, new String[]{"Java"});

        assertRequestFails(Request.post(url("/api/workers/" + name))
            .bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON));
    }

    @Test
    public void unassign_does_nothing() throws IOException {
        Company testCompany = new Company("Unassign Test");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Project p = testCompany.createProject("Alpha", qSet, ProjectSize.BIG);
        Worker w = testCompany.createWorker("Alice", qSet, 50000);
        testCompany.assign(w, p);
        setControllerCompany(testCompany);

        AssignmentDTO dto = new AssignmentDTO(w.getName(), p.getName());
        putRequest("/api/unassign", dto);

        assertTrue(!p.getWorkers().contains(w));
    }

    // TA tests
    @Test
    public void testGetWorkers1() throws IOException {
        Company testCompany = new Company("Test Co 1");
        Qualification q = testCompany.createQualification("Java");
        testCompany.createWorker("Worker1", new HashSet<>(Arrays.asList(q)), 50000);
        setControllerCompany(testCompany);
        WorkerDTO[] workers = gson.fromJson(Request.get(url("/api/workers")).execute().returnContent().asString(), WorkerDTO[].class);
        assertEquals(1, workers.length);
    }

    @Test
    public void testGetWorkersWithTwoWorkers() throws IOException {
        Company testCompany = new Company("Test Co 2");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        testCompany.createWorker("Alice", qSet, 50000);
        testCompany.createWorker("Bob", qSet, 60000);
        setControllerCompany(testCompany);
        WorkerDTO[] workers = gson.fromJson(Request.get(url("/api/workers")).execute().returnContent().asString(), WorkerDTO[].class);
        assertEquals(2, workers.length);
    }

    @Test
    public void testPostWorkersVerifyNameAndSalary() throws IOException {
        Company testCompany = new Company("Salary Test Co");
        testCompany.createQualification("Java");
        setControllerCompany(testCompany);
        String name = "Jane Doe";
        double salary = 100000.0;
        WorkerDTO dto = new WorkerDTO(name, salary, 0, new String[]{}, new String[]{"Java"});
        Request.post(url("/api/workers/" + name)).bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON).execute();
        WorkerDTO retrieved = gson.fromJson(Request.get(url("/api/workers/" + name)).execute().returnContent().asString(), WorkerDTO.class);
        assertEquals(name, retrieved.getName());
        assertEquals(salary, retrieved.getSalary(), 0.01);
    }

    @Test
    public void testPostWorkerPathBodyMismatchFailsAndDoesNotCreate() throws IOException {
        Company testCompany = new Company("Mismatch Co");
        setControllerCompany(testCompany);
        WorkerDTO dto = new WorkerDTO("BodyName", 50000, 0, new String[]{}, new String[]{"Java"});
        assertRequestFails(Request.post(url("/api/workers/URLName")).bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON)); 
        WorkerDTO[] workers = gson.fromJson(Request.get(url("/api/workers")).execute().returnContent().asString(), WorkerDTO[].class);
        assertEquals(0, workers.length);
    }

    @Test
    public void testGetWorkerReflectsProjectsAndWorkloadAfterAssign() throws IOException {
        Company testCompany = new Company("Workload Co");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        testCompany.createWorker("Alice", qSet, 50000);
        testCompany.createProject("Alpha", qSet, ProjectSize.MEDIUM);
        setControllerCompany(testCompany);
        AssignmentDTO assign = new AssignmentDTO("Alice", "Alpha");
        Request.put(url("/api/assign")).bodyString(gson.toJson(assign), ContentType.APPLICATION_JSON).execute();
        WorkerDTO retrieved = gson.fromJson(Request.get(url("/api/workers/Alice")).execute().returnContent().asString(), WorkerDTO.class);
        assertEquals(1, retrieved.getProjects().length);
        assertEquals(2, retrieved.getWorkload());
    }

    @Test
    public void testGetWorkersName2() throws IOException {
        Company testCompany = new Company("Name Test Co");
        Qualification q = testCompany.createQualification("Java");
        testCompany.createWorker("John Doe", new HashSet<>(Arrays.asList(q)), 50000);
        setControllerCompany(testCompany);
        WorkerDTO[] retrieved = gson.fromJson(Request.get(url("/api/workers")).execute().returnContent().asString(), WorkerDTO[].class);
        assertEquals("John Doe", retrieved[0].getName());
    }

    @Test
    public void testUnassignFromActiveCanSuspendProject() throws IOException {
        Company testCompany = new Company("Suspension Co");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Worker w = testCompany.createWorker("Alice", qSet, 50000);
        Project p = testCompany.createProject("Alpha", qSet, ProjectSize.SMALL);
        testCompany.assign(w, p);
        testCompany.start(p);
        setControllerCompany(testCompany);
        AssignmentDTO dto = new AssignmentDTO("Alice", "Alpha");
        Request.put(url("/api/unassign")).bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON).execute();
        ProjectDTO projectStatus = gson.fromJson(Request.get(url("/api/projects/Alpha")).execute().returnContent().asString(), ProjectDTO.class);
        assertEquals(ProjectStatus.SUSPENDED, projectStatus.getStatus());
    }

    @Test
    public void testPostWorkersVerifySalaryPrecision() throws IOException {
        Company testCompany = new Company("Salary Precision Co");
        testCompany.createQualification("Java");
        setControllerCompany(testCompany);
        String name = "Jane Doe";
        double salary = 123456.789;
        WorkerDTO dto = new WorkerDTO(name, salary, 0, new String[]{}, new String[]{"Java"});
        Request.post(url("/api/workers/" + name)).bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON).execute();
        WorkerDTO retrieved = gson.fromJson(Request.get(url("/api/workers/" + name)).execute().returnContent().asString(), WorkerDTO.class);
        assertEquals(salary, retrieved.getSalary(), 0.0001);
    }

    @Test
    public void testWorkerWorkloadCalculation() throws IOException {
        Company testCompany = new Company("Workload Test");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Worker w = testCompany.createWorker("Alice", qSet, 50000);
        Project p1 = testCompany.createProject("Alpha", qSet, ProjectSize.SMALL);
        Project p2 = testCompany.createProject("Beta", qSet, ProjectSize.MEDIUM);
        Project p3 = testCompany.createProject("Gamma", qSet, ProjectSize.BIG);
        testCompany.assign(w, p1);
        testCompany.assign(w, p2);
        testCompany.assign(w, p3);
        setControllerCompany(testCompany);
        WorkerDTO dto = gson.fromJson(Request.get(url("/api/workers/Alice")).execute().returnContent().asString(), WorkerDTO.class);
        assertEquals(6, dto.getWorkload());
    }

    @Test
    public void testAssignOverloadFails() throws IOException {
        Company testCompany = new Company("Overload Test");
        Qualification q = testCompany.createQualification("Java");
        HashSet<Qualification> qSet = new HashSet<>(Arrays.asList(q));
        Worker w = testCompany.createWorker("Alice", qSet, 50000);
        for (int i = 0; i < 4; i++) {
            Project p = testCompany.createProject("Big" + i, qSet, ProjectSize.BIG);
            testCompany.assign(w, p);
        }
        testCompany.createProject("Overflow", qSet, ProjectSize.SMALL);
        setControllerCompany(testCompany);
        AssignmentDTO dto = new AssignmentDTO("Alice", "Overflow");
        Request.put(url("/api/assign")).bodyString(gson.toJson(dto), ContentType.APPLICATION_JSON).execute().returnContent().asString();
        ProjectDTO projDto = gson.fromJson(Request.get(url("/api/projects/Overflow")).execute().returnContent().asString(), ProjectDTO.class);
        assertFalse(Arrays.asList(projDto.getWorkers()).contains("Alice"));
    }

}

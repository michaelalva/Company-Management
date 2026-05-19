package edu.colostate.cs415.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import edu.colostate.cs415.db.DBConnector;
import edu.colostate.cs415.dto.AssignmentDTO;
import edu.colostate.cs415.dto.ProjectDTO;
import edu.colostate.cs415.dto.QualificationDTO;
import edu.colostate.cs415.dto.WorkerDTO;
import edu.colostate.cs415.model.Company;
import edu.colostate.cs415.model.Project;
import edu.colostate.cs415.model.Qualification;
import edu.colostate.cs415.model.Worker;
import spark.Request;
import spark.Response;
import spark.Spark;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.redirect;

public class RestController {

	private static Logger log = Logger.getLogger(RestController.class.getName());
	private static String OK = "OK";
	private static String KO = "KO";

	private DBConnector dbConnector;
	private Company company;
	private Gson gson;

	public RestController(int port, DBConnector dbConnector) {
		port(port);
		this.dbConnector = dbConnector;
		gson = new Gson();
	}

	public void start() {
		// Load data from DB
		company = dbConnector.loadCompanyData();

		// Redirect
		redirect.get("/", "/helloworld");

		// Logging
		after("/*", (req, res) -> logRequest(req, res));
		exception(Exception.class, (exc, req, res) -> handleException(exc, res));

		// Hello World
		get("/helloworld", (req, res) -> helloWorld());

		// API
		path("/api", () -> {
			// Enable CORS
			options("/*", (req, res) -> optionsCORS(req, res));
			after("/*", (req, res) -> enableCORS(res));
			put("/assign", (req, res) -> assign(req));
			put("/unassign", (req, res) -> unassign(req));
			put("/start", (req, res) -> startProject(req));
			put("/finish", (req, res) -> finishProject(req));

			// Qualifications
			path("/qualifications", () -> {
				get("", (req, res) -> getQualifications(), gson::toJson);
				get("/:description", (req, res) -> getQualification(req.params("description")),
						gson::toJson);
				post("/:description", (req, res) -> createQualification(req));
			});

			// Worker
			path("/workers", () -> {
				get("", (req, res) -> getWorkers(), gson::toJson);
				get("/:name", (req, res) -> getWorker(req.params("name")), gson::toJson);
				post("/:name", (req, res) -> createWorker(req));
			});

			// Project
			path("/projects", () -> {
				get("", (req, res) -> getProjects(), gson::toJson);
				get("/:name", (req, res) -> getProject(req.params("name")), gson::toJson);
				post("/:name", (req, res) -> createProject(req));
			});
		});
	}

	public void stop() {
		Spark.stop();
	}

	private String helloWorld() {
		return "Hello World!";
	}

	// Qualification methods
	private QualificationDTO[] getQualifications() {
		return company.getQualifications().stream()
				.map(Qualification::toDTO)
				.toArray(QualificationDTO[]::new);
	}

	private QualificationDTO getQualification(String description) {
		return company.getQualifications().stream()
				.filter(q -> q.toString().equals(description))
				.findFirst()
				.map(Qualification::toDTO)
				.orElseThrow(() -> new RuntimeException("Qualification " + description + " not found!"));
	}

	private String createQualification(Request request) {
		QualificationDTO dto = gson.fromJson(request.body(), QualificationDTO.class);
	
		if (dto == null || dto.getDescription() == null) {
    		throw new RuntimeException("Qualification description is required.");
}
		if (!request.params("description").equals(dto.getDescription())) {
			throw new RuntimeException("Qualification descriptions do not match.");
		}
	
		Qualification q = company.createQualification(dto.getDescription());
	
		if (q == null) {
			throw new RuntimeException("Qualification already exists");
		}
	
		return OK;
	}

	// Worker methods
	private WorkerDTO[] getWorkers() {
		return company.getEmployedWorkers().stream()
				.map(Worker::toDTO)
    			.toArray(WorkerDTO[]::new);
	}

	private WorkerDTO getWorker(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new RuntimeException("Worker name is required.");
		}
		
		return company.getEmployedWorkers().stream()
			.filter(w -> w.getName().equals(name))
			.findFirst()
			.map(Worker::toDTO)
			.orElseThrow(() -> new RuntimeException("Worker " + name + " not found!"));
	}

	private String createWorker(Request request) {
		WorkerDTO workerDTO = gson.fromJson(request.body(), WorkerDTO.class);

		if (workerDTO == null || workerDTO.getName() == null || workerDTO.getQualifications() == null) {
    		throw new RuntimeException("Worker fields are required.");
		}

		if (request.params("name").equals(workerDTO.getName())) {
			Set<Qualification> qualifications = Stream.of(workerDTO.getQualifications())
		
				.map(q -> {
                  Qualification temp = new Qualification(q);
                  if (!company.getQualifications().contains(temp)) {
                      throw new RuntimeException("Unknown qualification: " + q);
                  }
                  return company.getQualifications().stream()
                          .filter(companyQ -> companyQ.equals(temp))
                          .findFirst()
                          .get();
              })
              .collect(Collectors.toSet());
			company.createWorker(workerDTO.getName(), qualifications, workerDTO.getSalary());
		} else
			throw new RuntimeException("Worker name does not match DTO worker name.");
		return OK;
	}

	// Project Methods
	private ProjectDTO[] getProjects() {
		return company.getProjects().stream()
				.map(Project::toDTO)
				.toArray(ProjectDTO[]::new);
	}

	private ProjectDTO getProject(String name) {
		if (name == null) {
		throw new RuntimeException("Project name is required.");
		}

		return company.getProjects().stream()
				.filter(p -> p.getName().equals(name))
				.findFirst()
				.map(Project::toDTO)
				.orElseThrow(() -> new RuntimeException("Project " + name + " not found!"));
	}

	private String createProject(Request request) {
		ProjectDTO projectDTO = gson.fromJson(request.body(), ProjectDTO.class);
		if (projectDTO == null || projectDTO.getName() == null || projectDTO.getQualifications() == null) {
			throw new RuntimeException("Project fields are required.");
		}
		if (request.params("name").equals(projectDTO.getName())) {
			Set<Qualification> qualifications = Stream.of(projectDTO.getQualifications())
					.map(q -> {
						Qualification temp = new Qualification(q);
						if (!company.getQualifications().contains(temp)) {
							throw new RuntimeException("Unknown qualification: " + q);
						}
						return company.getQualifications().stream()
								.filter(companyQ -> companyQ.equals(temp))
								.findFirst()
								.get();
					})
					.collect(Collectors.toSet());
			company.createProject(projectDTO.getName(), qualifications, projectDTO.getSize());
		} else
			throw new RuntimeException("Project name does not match DTO project name.");
		return OK;
	}

	// Logs every request received
	private void logRequest(Request request, Response response) {
		log.info(request.requestMethod() + " " + request.pathInfo() + "\nREQUEST:\n" + request.body() + "\nRESPONSE:\n"
				+ response.body());
	}

	// Exception handling
	private void handleException(Exception exception, Response response) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace();
		exception.printStackTrace(pw);
		log.severe(sw.toString());
		response.body(KO);
		response.status(500);
	}

	// Enable CORS
	private void enableCORS(Response response) {
		response.header("Access-Control-Allow-Origin", "*");
	}

	// Enable CORS
	private String optionsCORS(Request request, Response response) {
		String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
		if (accessControlRequestHeaders != null)
			response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

		String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
		if (accessControlRequestMethod != null)
			response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
		return OK;
	}
	private String startProject(Request request) {
    	ProjectDTO projectDTO = gson.fromJson(request.body(), ProjectDTO.class);

		if (projectDTO == null || projectDTO.getName() == null) {
			throw new RuntimeException("Project name is required.");
		}

    	Project project = company.getProjects().stream()
            	.filter(p -> p.getName().equals(projectDTO.getName()))
            	.findFirst()
            	.orElseThrow(() -> new RuntimeException("Project " + projectDTO.getName() + " not found!"));

    	company.start(project);
    	return OK;
	}
	
	private String finishProject(Request request) {
    	ProjectDTO projectDTO = gson.fromJson(request.body(), ProjectDTO.class);

		if (projectDTO == null || projectDTO.getName() == null) {
			throw new RuntimeException("Project name is required.");
		}


    	Project project = company.getProjects().stream()
            	.filter(p -> p.getName().equals(projectDTO.getName()))
            	.findFirst()
            	.orElseThrow(() -> new RuntimeException("Project " + projectDTO.getName() + " not found!"));

    	company.finish(project);
    	return OK;
	}
	
	private Worker findWorker(AssignmentDTO assignmentDTO) {
		return company.getEmployedWorkers().stream()
				.filter(w -> w.getName().equals(assignmentDTO.getWorker()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Worker " + assignmentDTO.getWorker() + " not found!"));
	}

	private Project findProject(AssignmentDTO assignmentDTO) {
		return company.getProjects().stream()
				.filter(p -> p.getName().equals(assignmentDTO.getProject()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Project " + assignmentDTO.getProject() + " not found!"));
	}

	private String assign(Request request) {
		AssignmentDTO assignmentDTO = gson.fromJson(request.body(), AssignmentDTO.class);
		if (assignmentDTO == null) {
			throw new RuntimeException("Assignment data is required.");
		}
		if (assignmentDTO.getWorker() == null || assignmentDTO.getProject() == null) {
			throw new RuntimeException("Worker and project are required.");
		}

		company.assign(findWorker(assignmentDTO), findProject(assignmentDTO));
		return OK;
	}

	private String unassign(Request request) {
		AssignmentDTO assignmentDTO = gson.fromJson(request.body(), AssignmentDTO.class);
		if (assignmentDTO == null) {
			throw new RuntimeException("Assignment data is required.");
		}

		if (assignmentDTO.getWorker() == null || assignmentDTO.getProject() == null) {
			throw new RuntimeException("Worker and project are required.");
		}
		company.unassign(findWorker(assignmentDTO), findProject(assignmentDTO));
		return OK;
	}

}
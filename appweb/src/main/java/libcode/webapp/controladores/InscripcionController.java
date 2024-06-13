package libcode.webapp.controladores;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import libcode.webapp.entidades.Alumno;
import libcode.webapp.entidades.Inscripcion;
import libcode.webapp.entidades.Materia;
import libcode.webapp.negocio.DataService;
import libcode.webapp.negocio.InscripcionService;
import libcode.webapp.negocio.MateriaService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class InscripcionController implements Serializable{

    private List<Inscripcion> inscripcionesList;
    private List<Alumno> alumnos;
    private List<Materia> materias;

    private Inscripcion inscripcion;

    @EJB
    InscripcionService inscripcionService;

    @EJB
    DataService dataService;

    @EJB
    MateriaService materiaService;

    @PostConstruct
    public void init() {
        inscripcion = new Inscripcion();
        inscripcion.setAlumno(new Alumno());
        inscripcion.setMateria(new Materia()); 

        cargarDatos();
    }

    public void cargarDatos() {
        inscripcionesList = inscripcionService.getAllInscripciones();
        alumnos = dataService.getAlumnos();
        materias = materiaService.getMaterias();
    }

    public void guardarInscripcion() {
        try {
            
            inscripcion.setFechaInscripcion(LocalDate.now());

            
            inscripcionService.guardarInscripcion(inscripcion);

            
            inscripcion = new Inscripcion();
            inscripcion.setAlumno(new Alumno());
            inscripcion.setMateria(new Materia());

            
            cargarDatos();

            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La inscripción se guardó correctamente."));
        } catch (Exception e) {
            // Manejar cualquier excepción y mostrar un mensaje de error
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la inscripción. Error: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void llenarFormEditar(Inscripcion inscripcion) {
        this.inscripcion.setId(inscripcion.getId());
        this.inscripcion.setAlumno(inscripcion.getAlumno());
        this.inscripcion.setMateria(inscripcion.getMateria());
        this.inscripcion.setCiclo(inscripcion.getCiclo());
        this.inscripcion.setAño(inscripcion.getAño());
        this.inscripcion.setFechaInscripcion(inscripcion.getFechaInscripcion());
    }

    public void eliminarInscripcion(Inscripcion inscripcion) {
        inscripcionService.eliminarInscripcion(inscripcion);
        cargarDatos();
    }

    public List<Alumno> completeAlumno(String query) {
        List<Alumno> filteredAlumnos = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            if (alumno.getNombre() != null && alumno.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredAlumnos.add(alumno);
            }
        }
        return filteredAlumnos;
    }

    public List<Materia> completeMateria(String query) {
        List<Materia> filteredMaterias = new ArrayList<>();
        for (Materia materia : materias) {
            if (materia.getNombre() != null && materia.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredMaterias.add(materia);
            }
        }
        return filteredMaterias;
    }

    // Getters y setters
    public List<Inscripcion> getInscripcionesList() {
        return inscripcionesList;
    }

    public void setInscripcionesList(List<Inscripcion> inscripcionesList) {
        this.inscripcionesList = inscripcionesList;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }
}

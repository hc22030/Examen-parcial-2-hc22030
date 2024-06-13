package libcode.webapp.negocio;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import libcode.webapp.entidades.Materia;
import java.util.List;

@Stateless
public class MateriaService {

    @PersistenceContext(unitName = "pu")
    EntityManager entityManager;

    public List<Materia> getMaterias() {
        Query query = entityManager.createQuery("SELECT e FROM Materia e ORDER BY e.id DESC");
        List<Materia> materias = query.getResultList();
        return materias;
    }

    public Materia getMateriaByNombre(String nombre) {
        Query query = entityManager.createQuery("SELECT e FROM Materia e WHERE e.nombre = :nombre");
        query.setParameter("nombre", nombre);
        List<Materia> resultados = query.getResultList();
        if (!resultados.isEmpty()) {
            return resultados.get(0); // Devuelve la primera materia encontrada con el nombre dado
        } else {
            return null; // Devuelve null si no se encuentra ninguna materia con el nombre dado
        }
    }

    @Transactional
    public void saveMateria(Materia materia) {
        entityManager.persist(materia);
    }

    @Transactional
    public void editMateria(Materia materia) {
        entityManager.merge(materia);
    }

    @Transactional
    public void deleteMateria(Materia materia) {
        Materia materiaEliminar = entityManager.find(Materia.class, materia.getId());
        entityManager.remove(materiaEliminar);
    }
    
}

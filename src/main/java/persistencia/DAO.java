/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import util.JpaUtil;

/**
 *
 * Possui métodos para CRUD de entidades.
 *
 */
public abstract class DAO<T> {

    private Class<T> entityClass;

    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * @param  entidade a ser pesistida.
     * @return Retorna TRUE se a entidade for persistida no banco de dados e
     * FALSE se houver um erro durante a persitência.
     */
    public boolean inserir(T entidade) {
        EntityManager em = Banco.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param  entidade a ser alterada.
     * @return Retorna TRUE se a entidade for persistida no banco de dados e
     * FALSE se houver um erro durante a persitência.
     */
    public boolean alterar(T entidade) {
        EntityManager em = Banco.getInstance().getEntityManager();;
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param  entidade a ser excluida.
     * @return Retorna TRUE se a entidade for persistida no banco de dados e
     * FALSE se houver um erro durante a persitência.
     */
    public boolean excluir(T entidade) {
        EntityManager em = Banco.getInstance().getEntityManager();;
        try {
            //em = getEntityManager();
            em.getTransaction().begin();
            em.remove(entidade);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param id   da entidade a ser pesquisada
     * @return Retorna uma entidade genérica caso o Id seja encontrado.
     */
    public T pesquisarPorId(long id) {
        EntityManager em = Banco.getInstance().getEntityManager();
        try {
            //em = getEntityManager();
            T t = em.find(entityClass, id);
            return t;
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @return Lista genérica de entidades
     */
    public List<T> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

}

package repositories;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.Adresses;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class AdressRepositoryImpl implements AdressRepository {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Adresses> findByClientId(Integer clientid) {
        return em.find(Clients.class, clientid).getAdressesList();  
    }
    
    @Override
    public Adresses findAdressById(int adressid) {
        Adresses adress = new Adresses();
        System.out.println("new adress is created");
        adress = em.find(Adresses.class, adressid);
        if (adress!= null){
            System.out.println("new adress is not null");
            return adress;
        } else {
            System.out.println("new adress is null");
            return null;
        }
    }

    @Override
    public Integer saveAdress(Adresses adress) {
        em.persist(adress);
        em.flush();
        return adress.getAdressid();
    }

    @Override
    public void updateAdress(Adresses adress) {
        em.merge(adress);
        em.flush();
    }

    @Override
    public void deleteAdress(Adresses adress) {
        if(!em.contains(adress)){
            adress = em.merge(adress);
        } em.remove(adress);
    }

    
}

package repositories;

import javax.ejb.Stateless;
import java.util.List;
import javax.ejb.EJB;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class ClientServiceImpl implements ClientService {
    
    @EJB
    ClientRepository repo;

    @Override
    public List<Clients> findAllClients() {
        List<Clients> client = repo.findAllClients();
        System.out.println("clients.size = " + client.size());
        return client;
    }

    @Override
    public Clients findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Integer saveClient(Clients client) {
        return repo.saveClient(client);
    }

    @Override
    public void updateClient(Clients client) {
        repo.updateClient(client);
    }

    @Override
    public List<Clients> findByClientname(String clientname) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Clients> findByClientaddress(String clientaddress) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void deleteClient(Clients client) {
        repo.deleteClient(client);
    }

    @Override
    public int getMaxId() {
        return repo.getMaxId();
    }
}

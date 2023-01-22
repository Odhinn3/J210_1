
package repositories;

import java.util.List;
import javax.ejb.Local;
import models.Clients;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */

@Local
public interface ClientRepository {
    
    List<Clients> findAllClients();
    
    List<Clients> findByClientname(String clientname);
    
    List<Clients> findByClientaddress(String clientaddress);
    
    Clients findById(Integer id);
    
    Integer saveClient(Clients client);
    
    void updateClient(Clients client);
    
    void deleteClient(Clients client);
    
    int getMaxId();
}


package repositories;

import models.Clients;
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */

@Local
public interface ClientService {
    
    List<Clients> findAllClients();
    
    List<Clients> findByClientname(String clientname);
    
    List<Clients> findByClientaddress(String clientaddress);
    
    Clients findById(Integer id);
    
    Integer saveClient(Clients client);
    
    void updateClient(Clients client);
    
    void deleteClient(Clients client);

}

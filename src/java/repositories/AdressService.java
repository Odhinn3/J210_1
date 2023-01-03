/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositories;

import java.util.List;
import models.Adresses;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
public interface AdressService { 
    
    List<Adresses> findByClientId(Integer clientid);
    
    Adresses findAdressById(Integer adressid);
    
    Integer saveAdress(Adresses adress);
    
    void updateAdress(Adresses adress);
    
    void deleteAdress(Adresses adress);
}
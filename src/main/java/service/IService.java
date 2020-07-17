package service;

import model.Service;

import javax.ejb.Local;
import java.util.List;
@Local
public interface IService {
    public List<Service> findAll();
    public Service findById(long id);
}

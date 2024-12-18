package DAO;

import java.util.List;

import Model.Employe;

public interface EmployeDAOI {
    public void add(Employe emp);
    public void update(Employe emp);
    public void delete(int id);
    public List<Employe> employes();
}

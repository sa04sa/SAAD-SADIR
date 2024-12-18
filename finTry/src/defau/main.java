package defau;

import Controller.EmployeController;
import DAO.EmployeDAOImp;
import Model.EmployeModel;
import View.EmployeView;
import java.sql.Connection;
import DAO.connexion;
public class main {

    public static void main(String[] args) {

        EmployeDAOImp dao = new EmployeDAOImp();
        EmployeModel model = new EmployeModel(dao);
        EmployeView view=new EmployeView();
        new EmployeController(view,model);
        view.setVisible(true);
    }

}
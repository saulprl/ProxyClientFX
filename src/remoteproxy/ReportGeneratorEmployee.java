package remoteproxy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ReportGeneratorEmployee implements ReportGenerator {

    private static ReportGenerator reportGenerator;

    public ReportGeneratorEmployee() {
        ReportGeneratorEmployee.main(null);
    }

    public ReportGeneratorEmployee(int a) {

    }

    public static void main(String[] args) {
        ReportGeneratorEmployee employee = new ReportGeneratorEmployee(1);
        employee.init();
        reportGenerator = employee.getReportGenerator();
    }

    @Override
    public ArrayList<Report> getReports() {
        return reportGenerator.getReports();
    }

    @Override
    public Report generateDailyReport() {
        return reportGenerator.generateDailyReport();
    }

    @Override
    public Report generateDailyReport(int id) {
        return reportGenerator.generateDailyReport(id);
    }

    @Override
    public ArrayList<Store> getStores() {
        return reportGenerator.getStores();
    }

    @Override
    public Store getStore(int branch) {
        return reportGenerator.getStore(branch);
    }

    @Override
    public boolean login(String user, String pass) {
        return reportGenerator.login(user, pass);
    }

    @Override
    public ArrayList<User> getUsers() {
        return reportGenerator.getUsers();
    }

    @Override
    public User getLogged() {
        return reportGenerator.getLogged();
    }

    @Override
    public boolean addUser(User user) {
        return reportGenerator.addUser(user);
    }

    @Override
    public boolean addStore(Store store) {
        return reportGenerator.addStore(store);
    }

    @Override
    public boolean updateStore(Store store) {
        return reportGenerator.updateStore(store);
    }

    @Override
    public void init() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 9001);
            reportGenerator = (ReportGenerator) (registry.lookup("PizzaCoRemoteGenerator"));
        } catch (RemoteException rEx) {

        } catch (NotBoundException nbEx) {

        }
    }

    public ReportGenerator getReportGenerator() {
        return reportGenerator;
    }

    public String getMessage() {
        return reportGenerator.getMessage();
    }

    public void setDatabaseEngine(int databaseEngine) {
        reportGenerator.setDatabaseEngine(databaseEngine);
    }

}

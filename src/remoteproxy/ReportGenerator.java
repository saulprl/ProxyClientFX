package remoteproxy;

import java.util.ArrayList;

public interface ReportGenerator {

	public ArrayList<Report> getReports();
	public Report generateDailyReport();
	public Report generateDailyReport(int id);
	public ArrayList<Store> getStores();
	public Store getStore(int branch);
	public ArrayList<User> getUsers();
	public User getLogged();
	public boolean login(String user, String pass);
	public boolean addUser(User user);
	public boolean addStore(Store store);
	public boolean updateStore(Store store);
	public String getMessage();
	public void setDatabaseEngine(int databaseEngine);
	public void init();

}

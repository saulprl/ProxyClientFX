//package remoteproxy;//package remoteproxy;
//
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.Scanner;
//
//public class ReportGeneratorClient implements ReportGenerator {
//	ReportGenerator reportGenerator;
//
//	public static void main(String[] args) {
//		ReportGeneratorClient client = new ReportGeneratorClient();
//		client.init();
//		System.out.print("Ingresa un número de sucursal: ");
//		Scanner scn = new Scanner(System.in);
//		int id = scn.nextInt();
//		System.out.println(client.getStores());
//	}
//
//	public void init() {
//		try {
//			Registry registry = LocateRegistry.getRegistry("localhost", 9000);
//			reportGenerator = (ReportGenerator) registry.lookup("PizzaCoRemoteGenerator");
//		} catch (RemoteException rEx) {
//
//		} catch (NotBoundException nBEx) {
//
//		}
//	}
//
//	public String generateDailyReport() {
//		return null;
//	}
//
//	public String getStores() {
//		return reportGenerator.getStores();
//	}
//
//}

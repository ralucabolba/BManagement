package Controller;
import View.Gui;
import Model.*;
import java.util.*;
public class Main {

	public static void main(String[] args) 
	{
		//Bank bank = Bank.getInstance();
		//bank.setHash(new Hashtable<Integer, List<Account>>());
		Gui gui = new Gui();
		Controller controller = new Controller(gui);
	}
}

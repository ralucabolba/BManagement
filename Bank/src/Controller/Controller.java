package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import View.Gui;
import Model.*;

public class Controller 
{
	private Gui gui;
	private Bank bank;
	
	private static int accountId = 1;
	private static String ibanBank = "RO00BRDEROBU0000000000000001";
	
	private Person person = null;
	
	public Controller(Gui gui)
	{
		this.gui = gui;
		this.bank = Bank.getInstance();
		
		gui.addListenerCreateAccount(new CreateAccountListener());
		gui.addListenerGoToBank(new GoToBankListener());
		gui.addListenerCloseWindow(new CloseWindowListener());
		gui.addListenerCloseBankWindow(new CloseWindowListener());
		gui.addListenerCloseMyAccountWindow(new CloseWindowListener());
		gui.addListenerSearchPerson(new SearchPersonListener());
		gui.addListenerSearchAccount(new SearchAccountListener());
		gui.addListenerRemoveAccount(new RemoveAccountListener());
		gui.addListenerGoToMyAccount(new GoToMyAccountListener());
		gui.addListenerOk(new OkListener());
		gui.addListenerWithdraw(new WithdrawListener());
		gui.addListenerAdd(new AddListener());
		//gui.addListenerGoBack(new GoBackListener());
		gui.addListenerRemoveMyAccount(new RemoveMyAccountListener());
	}
	class CreateAccountListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			String choice = gui.getRadioButtonChoice();
			
			String[] info = gui.getPersonInformation();
			
			try
			{
				if(info[0].equals("") || info[2].equals(""))
				{
					throw new NumberFormatException("You must introduce your name and address to create an account.");
				}
				if(info[1].length() != 13 || (info[1].charAt(0) != '1' && info[1].charAt(0) != '2'))
				{
					throw new NumberFormatException("Cnp invalid.");
				}
				
				Person person = new Person(info[1], info[0], info[2]);
				
				List<Account> list = bank.getAccounts(info[1]);
				
				if(list!=null)
				{
					for(Account a : list)
					{
						if(a.getType().equals(choice))
						{
							throw new Exception("You already have a " +  choice + ".");
						}
					}
				}
				Account account;
				
				while(bank.getAccount(ibanBank) != null)
				{
					String ibanSubstring = new Integer(new Random().nextInt(100)).toString();
					
					if(ibanSubstring.length() != 2)
					{
						ibanSubstring = "0" + ibanSubstring;
					}
					
					String ibanSubstring2 = String.valueOf(accountId + 1);
					while(ibanSubstring2.length() < 16)
					{
						ibanSubstring2 = "0" + ibanSubstring2;
					}
			
					ibanBank = ibanBank.substring(0, 2) + ibanSubstring + ibanBank.substring(4, 12) + ibanSubstring2;
					accountId++;
				}
				//accountId++;
				
				if(choice.equals("Saving account"))
				{
					account = new SavingAccount(accountId, ibanBank, person, 0);
				}
				else
				{
					account = new SpendingAccount(accountId, ibanBank, person, 0);
				}
				
				bank.addAccount(account);
				JOptionPane.showMessageDialog(null, "Your account was created.");
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "The values are invalid. " + e.getMessage());
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
	}
	
	class GoToBankListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			DefaultTableModel model = gui.getAccountModel();
			addAccountsToModel(model);
			gui.setAccountModel(model);
			gui.bankManagement();
		}
	}
	
	class CloseWindowListener implements WindowListener
	{

		@Override
		public void windowActivated(WindowEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) 
		{
			//JOptionPane.showMessageDialog(null, "The window is closing");
			bank = Bank.getInstance();
			bank.writeAccounts(bank.getHash());
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			person = null;
		}

		@Override
		public void windowDeactivated(WindowEvent e) 
		{
			bank = Bank.getInstance();
			bank.writeAccounts(bank.getHash());
			person = null;
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) 
		{
			try 
			{
				bank.setHash(bank.readAccounts());
			} 
			catch (IOException act) 
			{
				// TODO Auto-generated catch block
				act.printStackTrace();
			}
		}
		
	}

	class SearchPersonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			String text = "";
			String cnp = gui.getSearchPersonCnp();
			
			try
			{
				if(cnp.length() != 13 || (cnp.charAt(0) != '1' && cnp.charAt(0) != '2'))
				{
					throw new NumberFormatException();
				}
				List<Account> list = bank.getAccounts(cnp);
				if(list != null)
				{
					text += "Person " + list.get(0).getOwner().toString() + " owns the following accounts: \n";

					text += getAccountInformation(list);
					
					gui.setConsoleText(text);
				}
				else
				{
					throw new Exception();
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "The cnp introduced is invalid.");
				gui.setConsoleText("");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "The person with the introduced cnp does not own an account.");
				gui.setConsoleText("");
			}
		}
		
	}
	
	class SearchAccountListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String text = "";
			Account accountSearched = null;
			String iban = gui.getSearchAccountIban();
			try
			{
				if(iban.length() != ibanBank.length())
				{
					throw new Exception();
				}
				accountSearched = bank.getAccount(iban);
				if(accountSearched == null)
				{
					throw new NumberFormatException();
				}
				
				List<Account> l = new ArrayList<Account>();
				l.add(accountSearched);
				
				text = getAccountInformation(l);	
				gui.setConsoleText(text);
			}
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "It does not exist an account with this iban.");
				gui.setConsoleText("");
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "The iban introduced is invalid. " + ex.getMessage());
				gui.setConsoleText("");
			}
		}
		
	}
	
	class RemoveAccountListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String iban = gui.getRemovedAccountIban();
			DefaultTableModel model = new DefaultTableModel();
			
			try
			{
				int id = Integer.parseInt(iban.substring(12));
				List<Account> list;
				
				if(bank.getAccounts(id % 113) != null)
				{
					list = new ArrayList<Account>(bank.getAccounts(id % 13));
				}
				else
				{
					throw new Exception();
				}
				
				if(list != null)
				{
					for(Account a : list)
					{
						if(a.getIban().equals(iban))
						{
							if(!bank.removeAccount(a))
							{
								throw new Exception();
							}
							
							JOptionPane.showMessageDialog(null, "The account was removed.");
							
							model = gui.getAccountModel();
							addAccountsToModel(model);
							gui.setAccountModel(model);
							break;
						}
						
					}
					
				}
				
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "The account could not be removed. " + ex.getMessage());
			}
		}
	}
	class GoToMyAccountListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			gui.MyAccount();
		}
		
	}
	
	class OkListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
				String cnp = gui.getMyCnp();
				String text = "";
				JComboBox<String> box;
				
				box = gui.getComboBox();
				box.removeAllItems();
				
				try
				{
					if(cnp.length()!= 13 || (cnp.charAt(0) != '1' && cnp.charAt(0) != '2'))
					{
						throw new NumberFormatException();
					}
					List<Account> list = bank.getAccounts(cnp);
					if(list == null)
					{
						throw new Exception();
					}
					person = list.get(0).getOwner();
					
					gui.setName(person.getName());
					gui.setCnp(person.getCnp());
					gui.setAddress(person.getAddress());
					
					
					for(Account a : list)
					{
						box.addItem(a.getType());
					}
					
					text = getAccountInformation(list);
					
					gui.setComboBox(box);
					gui.setAccountsArea(text);
				}
				catch(NumberFormatException ex )
				{
					JOptionPane.showMessageDialog(null, "The cnp is invalid.");
					gui.setComboBox(box);
					gui.setAccountsArea("");
					person = null;
				}
				catch(Exception ex )
				{
					JOptionPane.showMessageDialog(null, "You do not own an account.");
					gui.setComboBox(box);
					gui.setAccountsArea("");
					person = null;
				}
		}	
	}
	
	class WithdrawListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
				if(person != null)
				{
					String value = gui.getMoneyWithdraw();
					String accountType = gui.getChosenAccount();
					
					try
					{
						float v = Float.parseFloat(value);
						
						if(v < 0)
						{
							throw new NumberFormatException();
						}
						
						List<Account> list = bank.getAccounts(person.getCnp());
						
						if(list != null)
						{
							for(Account a : list)
							{
								if(a.getType().equals(accountType))
								{
									a.withdrawMoney(v);
									JOptionPane.showMessageDialog(null, v + " lei were withdrawn from your account.");
								}
							}
							gui.setAccountsArea(getAccountInformation(list));
						}
					}
					catch(NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null, "The amount of money you want to withdraw is invalid.");
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error withdrawing. " + e.getMessage());
					}
				}
		}
		
	}
	
	class AddListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(person != null)
			{
				String value = gui.getMoneyAdd();
				String accountType = gui.getChosenAccount();
				
				try
				{
					float v = Float.parseFloat(value);
					
					if(v < 0)
					{
						throw new NumberFormatException();
					}
					List<Account> list = bank.getAccounts(person.getCnp());
					
					if(list != null)
					{
						for(Account a : list)
						{
							if(a.getType().equals(accountType))
							{
								a.addMoney(v);
								JOptionPane.showMessageDialog(null, v + " lei were added to your account.");
							}
						}
						gui.setAccountsArea(getAccountInformation(list));
					}
					
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "The amount of money you want to add is invalid.");
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Error adding money. " + ex.getMessage());
				}
			}
		}
	}
	
	class RemoveMyAccountListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			JComboBox box = gui.getComboBox();
			if(person != null)
			{
				String choice = gui.getChosenAccount();
				int opt = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove your " + choice + " ?");
				
				if(opt == JOptionPane.YES_OPTION)
				{
					List<Account> list = bank.getAccounts(person.getCnp());
					
					for(Account a : list)
					{
						if(a.getType().equals(choice))
						{
							bank.removeAccount(a);
							box.removeItem(a.getType());
							
							JOptionPane.showMessageDialog(null, "Your account was removed.");
							gui.setAccountsArea(getAccountInformation(bank.getAccounts(person.getCnp())));
							gui.setComboBox(box);
							break;
						}
					}
				}
			}
		}	
	}
	public String getAccountInformation(List<Account> list)
	{
		String text = "";
		for(Account a : list)
		{
			if(a != null)
			{
				text+= "Id account : " + a.getId() + "\n";
				text+= "Iban account : " + a.getIban() + "\n"; 
				text+= "Status account : " + a.getStatus() + "\n";
				text+= "Type account : " + a.getType() + "\n";
				text+= "Balance account : " + a.getValue() + "\n\n\n";
			}
		}
		return text;
	}
	public void addAccountsToModel(DefaultTableModel model)
	{
		int idAccount, numberCount;
		String iban, status, name, address, cnp, type;
		float balance;
		LocalDateTime lastDateCount;
		
		try
		{
		//bank = Bank.getInstance();
		Hashtable<Integer, List<Account>> hash = bank.getHash();
		
		//System.out.println(bank.toString());
		
		model.setRowCount(0);
		for(List<Account> list : hash.values())
		{
			if(!list.isEmpty())
			{
				for(Account a : list)
				{
					idAccount	  = a.getId();
					iban		  = a.getIban();
					status		  = a.getStatus();
					type		  = a.getType();
					balance		  = a.getValue();
					Person p	= a.getOwner();
					name		= p.getName();
					address		= p.getAddress();
					cnp			= p.getCnp();
					
					model.addRow(new Object[]{idAccount, type, status, iban, balance, name, cnp, address});
				}
			}
		}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}
	

}

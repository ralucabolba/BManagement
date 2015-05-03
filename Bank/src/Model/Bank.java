package Model;
import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;

public class Bank implements BankProc, Serializable
{
	private static final long serialVersionUID = 3;
	private static Bank instance = new Bank();
	
	private Hashtable<Integer, List<Account>> accounts = new Hashtable<Integer, List<Account>>();

	private Bank()
	{
		//do nothing
	}
	
	public static Bank getInstance()
	{
		return instance;
	}
	
	/**
	 * Reads the hash table of accounts from file and returns it
	 * @return	the hash table of accounts from file
	 * @pre		true
	 * @post	@nochange
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Hashtable<Integer, List<Account>> readAccounts() throws IOException
	{
		FileInputStream file = null;
		try
		{
			file = new FileInputStream("bank.ser");
			ObjectInputStream in = new ObjectInputStream(file);
			accounts = (Hashtable<Integer, List<Account>>)in.readObject();
			return accounts;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error when deserializing " + e);
		}
		finally
		{
			if(file != null)
			{
				file.close();
			}
		}
		return null;
	}
	
	
	/**
	 * Writes the hash table of accounts in a file
	 * @param the hash table of accounts to be written
	 * @pre   true
	 * @post  @nochange
	 */
	@Override
	public void writeAccounts(Hashtable<Integer, List<Account>> accounts) 
	{
		try 
		{
			FileOutputStream file = new FileOutputStream("bank.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(accounts);
			out.close();
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Error when serializing " + e);
		}
		
	}
	
	/**
	 * Adds a new account to the hash table of the accounts
	 * @param account - the account to be added
	 * @pre   account != null && !accounts.contains(account)
	 * @post  getHash().contains(account)
	 * @invariant isWellFormed
	 */
	@Override
	public void addAccount(Account account) 
	{
		assert account != null && !accounts.contains(account);
		
		int hash = account.hashCode();
		if(accounts.containsKey(hash))
		{
			List<Account> list = accounts.get(hash);
			list.add(account);
		}
		else
		{
			List<Account> list = new ArrayList<Account>();
			list.add(account);
			accounts.put(hash, list);
		}
		
		assert getHash().contains(account);
		assert isWellFormed();
	}

	/**
	 * Removes the account from the hash table of accounts
	 * @param	account - the account to be removed
	 * @return	true if the account could be removed, false otherwise
	 * @pre		account != null && getHash().contains(account)
	 * @post	getHash().contains(account) == false
	 * @invariant isWellFormed()
	 */
	@Override
	public boolean removeAccount(Account account) 
	{
		assert account != null && getHash().contains(account);
		
		int hash = account.hashCode();
		if(accounts.containsKey(hash))
		{
			List<Account> list = accounts.get(hash);
			list.remove(account);
			accounts.put(hash, list);
			return true;
		}
		
		assert !getHash().contains(account);
		assert isWellFormed();
		return false;
	}

	/**
	 * Sets the hash table with a new one
	 * @param accounts
	 * @pre   true
	 * @post  getHash().equals(accounts)
	 * @invariant isWellFormed()
	 */
	public void setHash(Hashtable<Integer, List<Account>> accounts)
	{
		this.accounts = accounts;
		
		assert getHash().equals(accounts);
		assert isWellFormed();
	}
	
	/**
	 * Returns the hash table
	 * @return	the hash table containing the accounts of the bank
	 * @pre		true
	 * @post	@nochange
	 */
	public Hashtable<Integer, List<Account>> getHash()
	{
		return this.accounts;
	}
	
	/**
	 * Returns the list of accounts having the hash code equal to hashcode from hash table
	 * @param 	hashcode
	 * @return 	list of account from hash table having the hash code equal to hashcode
	 * @pre		hashcode >= 0
	 * @post	@nochange
	 */
	public List<Account> getAccounts(int hashcode)
	{
		assert hashcode >= 0;
		
		if(accounts.containsKey(hashcode))
		{
			return accounts.get(hashcode);
		}
		
		return null;
	}

	/**
	 * Returns a list of account from the hash table
	 * @param		the cnp of the owner whose accounts we want to return
	 * @return		list of accounts of the owner identified by the cnp
	 * @pre			cnp != null && cnp.length() == 13 && (cnp.charAt(0) == '1' || cnp.charAt(0) == '2')
	 * @post		@nochange
	 */
	public List<Account> getAccounts(String cnp)
	{
		assert cnp != null && cnp.length() == 13 && (cnp.charAt(0) == '1' || cnp.charAt(0) == '2');
		
		List<Account> l = new ArrayList<Account>();
		for(List<Account> list : accounts.values())
		{
			for(Account a : list)
			{
				if(a.getOwner().getCnp().equals(cnp))
				{
					l.add(a);
				}
			}
		}
		return l;
	}
	
	/**
	 * Returns the account with the specified iban from the hash table, is exists
	 * @param		iban
	 * @return		the account from hash table with the specified iban, if exist, null otherwise
	 * @pre			iban != null && iban.length() == 27 && iban.substring(0, 2).equals("RO") && iban.substring(4, 12).equals("BRDEROBU")
	 * @post		@nochange
	 */
	public Account getAccount(String iban)
	{
		assert iban != null && iban.length() == 27 && iban.substring(0, 2).equals("RO") && iban.substring(4, 12).equals("BRDEROBU");
		
		int id = Integer.parseInt(iban.substring(12));
		if(accounts.containsKey(id % 113))
		{
			for(Account a : accounts.get(id % 113))
			{
				if(a.getIban().equals(iban))
				{
					return a;
				}
			}
		}
		return null;
	}
	
	/**
	 * Generates and returns a string representation of the bank
	 * @return	the string representation of the bank
	 * @pre		true
	 * @post	@nochange
	 */
	public String toString()
	{
		String string = "";
		for(List<Account> list : accounts.values())
		{
			if(! list.isEmpty())
			{
				string = string + list.toString() + ",\n";
			}
		}
		return string;
	}
	
	/**
	 * The @invariant of the Bank class
	 * @return true is the class is well formed, false otherwise
	 * @pre    true
	 * @post   @nochange
	 */
	public boolean isWellFormed()
	{
		if(instance == null)
		{
			return false;
		}
		for(List<Account> list : accounts.values())
		{
			if(!list.isEmpty())
			{
				for(Account a : list)
				{
					if(a == null || a.getOwner() == null)
					{
						return false;
					}
					if(accounts.get(a.hashCode()).contains(a) == false)
					{
						return false;
					}
				}
			}
		}
			
		return true;
	}
}

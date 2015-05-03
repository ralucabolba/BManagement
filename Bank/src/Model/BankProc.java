package Model;
import java.io.IOException;
import java.util.*;

/**
 * 
 * @author Bolba Raluca
 * Interface that defines the methods and the behavior of a bank
 *
 */
public interface BankProc 
{
	/**
	 * Adds a new account to the hash table of the accounts
	 * @param account - the account to be added
	 * @pre   account != null && !accounts.contains(account)
	 * @post  getHash().contains(account)
	 * @invariant isWellFormed
	 */
	public void addAccount(Account account);
	
	/**
	 * Removes the account from the hash table of accounts
	 * @param	account - the account to be removed
	 * @return	true if the account could be removed, false otherwise
	 * @pre		account != null && getHash().contains(account)
	 * @post	getHash().contains(account) == false
	 * @invariant isWellFormed()
	 */
	public boolean removeAccount(Account account);

	/**
	 * Reads the hash table of accounts from file and returns it
	 * @return	the hash table of accounts from file
	 * @pre		true
	 * @post	@nochange
	 * @throws IOException
	 */
	public Hashtable<Integer, List<Account>> readAccounts() throws IOException;
	
	/**
	 * Writes the hash table of accounts in a file
	 * @param the hash table of accounts to be written
	 * @pre   true
	 * @post  @nochange
	 */
	public void writeAccounts(Hashtable<Integer, List<Account>> accounts);
}

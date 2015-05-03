package Model;

import java.io.*;



/**
 * 
 * @author Raluca Bolba
 * Class that represents an account of a person. The account is characterized by
 * an id, by its iban, by a status, by the owner of it and by the amount of money.
 *
 */
public abstract class Account implements Serializable
{
	private static final long serialVersionUID = 1;
	protected int id;
	protected String status;
	protected String iban;
	protected Person person;
	protected float value;
	
	/**
	 * Constructor of the class
	 * @param id	- the account's id
	 * @param iban	- the unique identifier of the account, also called iban
	 * @param person- the owner of the account
	 * @param value - the amount of money in the account
	 */
	public Account(int id, String iban, Person person, float value)
	{
		this.id = id;
		this.iban = iban;
		this.person = person;
		this.value = value;
		this.status = "inactive";
	}
	/**
	 * Returns the account's id
	 * @return 	id
	 * @pre		true
	 * @post	@nochange
	 * */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Returns the account's iban
	 * @return iban
	 * @pre    true
	 * @post   @nochange
	 */
	public String getIban()
	{
		return this.iban;
	}
	
	/**
	 * Returns the account's status
	 * @return status
	 * @pre    true
	 * @post   @nochange
	 */
	public String getStatus()
	{
		return this.status;
	}
	
	/**
	 * Returns the owner of the account
	 * @pre    true
	 * @post   @nochange
	 * @return person - the owner of the account
	 */
	public Person getOwner()
	{
		return this.person;
	}
	
	/**Returns the amount of money in the account
	 * @return	value - value of money in account
	 * @pre		true
	 * @post	@nochange
	 */
	public float getValue()
	{
		return this.value;
	}
	
	/**Sets the amount of money in the account with
	 * a new amount
	 * @param 	newValue - the new value of money in account
	 * @pre		true
	 * @post	getValue() == newValue
	 */
	public void setValue(float newValue)
	{
		this.value = newValue;
		
		assert getValue() == newValue;
	}
	
	/**
	 * Activates the account by setting the status field to "active"
	 * @pre		true
	 * @post	getStatus().equals("active")
	 */
	public void activateAccount()
	{
		this.status = "active";
		
		assert getStatus().equals("active");
	}
	/**
	 * Abstract method that withdraws money from the account. The method
	 * must be implemented by the classes that extend the current class i.e. 
	 * the SavingAccount and the SpendingAccount classes.
	 * @param 	amountMoney	- the amount of money to be withdraw
	 * @throws	Exception
	 */
	public abstract void withdrawMoney(float amountMoney) throws Exception;
	
	/**Adds an amount of money to the existing one in account
	 * @param	amountToAdd	- the amount of money to add
	 * @pre		amountToAdd > 0
	 * @post	getValue() == getValue()@pre + amountToAdd
	 */
	public void addMoney(float amountToAdd)
	{
		assert amountToAdd > 0;
		
		float oldValue = getValue();
		
		setValue(this.value + amountToAdd);
		
		assert getValue() == oldValue + amountToAdd;
	}
	
	/**
	 * Generates and returns a hash code for the instance of the class, based on its id
	 * @pre		true
	 * @post	@nochange
	 */
	@Override
	public int hashCode()
	{
		return this.id % 113;
	}
	
	/**
	 * Checks if the instance of the class is equal to the parameter a
	 * @param	a the object to compare with the instance of the class
	 * @pre		this != null
	 * @post	@nochange
	 */
	@Override
	public boolean equals(Object a)
	{
		assert this != null;
		
		if(a == null)
		{
			return false;
		}
		
		if(a instanceof Account)
		{
			Account account = (Account)a;
			
			return (this.iban == account.iban);
		}
		
		return false;
	}
	
	
	/**
	 * Returns the type of the account
	 * @return the type of the account
	 * @pre	   this != null
	 * @post   @nochange
	 */
	public String getType()
	{
		assert this != null;
		
		return "Account";
	}
}

package Model;

/**
 * 
 * @author Raluca Bolba
 * Class represents the spending account of a person. The spending account
 * is defined by the owner of it, the amount of money in account, the status
 * and the iban.
 *
 */
public class SpendingAccount extends Account
{
	
	private static final long serialVersionUID = 4;

	/**
	 * Constructor of the class
	 * @param iban
	 * @param person
	 * @param value
	 */
	public SpendingAccount(int id, String iban, Person person, float value)
	{
		super(id, iban, person, value);
		super.activateAccount();
	}

	/**
	 * Method which withdraws money from the account. A person can withdraw money until
	 * the balance of the account is bigger or equal to -2000 lei
	 * @param amountMoney	- the amount of money to be withdrawn
	 * @pre	  amountMoney > 0 && super.status.equals("active")
	 * @post	(super.value - amountMoney >= -2000) => getValue() == getValue()@pre - amountMoney
	 * @throws Exception if the amount of money to be withdrawn is too big
	 */
	@Override
	public void withdrawMoney(float amountMoney) throws Exception 
	{
		assert amountMoney > 0 && super.status.equals("active");
		
		if(super.value - amountMoney >= -2000)
		{
			float oldValue = super.getValue();
			super.value -= amountMoney;
			
			assert super.getValue() == oldValue - amountMoney;
		}
		else
		{
			throw new Exception("You cannot withdraw more then " + (super.value + 2000) + " lei from your account.");
		}
	}
	
	
	/**
	 * Returns the type of the account, i.e. spending account
	 * @return	the type of the account
	 * @pre		this != null
	 * @post	@nochange
	 */
	public String getType()
	{
		assert this != null;
		
		return "Spending account";
	}
}

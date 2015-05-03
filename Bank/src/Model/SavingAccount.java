package Model;

/**
 * 
 * @author Raluca Bolba
 * Class that represents the saving account of a person. Beside the owner of it,
 * the iban, the amount of money in the account, the number of days since the account was
 * created, the class is characterized also by a minimum balance and an interest rate.
 *
 */
public class SavingAccount extends Account
{

	private static final long serialVersionUID = 5;
	private static float minimumBalance = (float) 1000.0;
	private static float interestRate = (float) 10.0; 		/* the interest rate is 10% */
	
	/**
	 * The constructor of the class
	 * @param iban
	 * @param person
	 * @param value
	 */
	public SavingAccount(int id, String iban, Person person, float value)
	{
		super(id, iban, person, value);
		if(value >= minimumBalance)
		{
			super.status = "active";
		}
		else
		{
			super.status = "inactive";
		}
	}
	/**
	 * Method that activates the account by setting the status to "active" if the value of the account
	 * is bigger or equals than the minimum balance
	 */
	public void activateAccount()
	{
		if(super.status.equals("inactive") && super.value >= minimumBalance)
		{
			super.status = "active";
		}
	}
	
	/**
	 * Method that deactivates the account by setting the status to "inactive" if the value of the account
	 * is less than the minimum balance
	 */
	public void deactivateAccount()
	{
		if(super.status.equals("active") && super.value < minimumBalance)
		{
			super.status = "inactive";
		}
	}
	
	/**Adds an amount of money + interestRate to the existing one in account.
	 * @param	amountToAdd	- the amount of money to add
	 * @pre		amountToAdd > 0
	 * @post	getValue() == getValue()@pre + amountToAdd + (interestRate*amountToAdd)/100.0
	 */
	@Override
	public void addMoney(float amountToAdd)
	{
		assert amountToAdd > 0;
		
		float oldValue = super.getValue();
		super.addMoney((float)(amountToAdd + (interestRate * amountToAdd)/100.0));
		this.activateAccount();
		
		assert super.getValue() == oldValue + amountToAdd + (interestRate*amountToAdd)/100.0 ;
	}
	
	/**
	 * Withdraws money from the account.
	 * @param 	amountMoney	- the amount of money to be withdraw
	 * @pre		amountMoney > 0 && super.status.equals("active")
	 * @post	(amountMoney <= super.value) => getValue() == getValue()@pre - amountMoney 
	 * @throws	Exception if the amount of money to be withdraw is too big
	 */
	@Override
	public void withdrawMoney(float amountMoney) throws Exception
	{
		assert amountMoney > 0 && super.status.equals("active");
		
		if(super.status.equals("active"))
		{
			if(amountMoney <= super.value)
			{
				float oldValue = super.getValue();
				
				super.setValue(super.value - amountMoney);
				this.deactivateAccount();
				
				assert getValue() == oldValue - amountMoney;
			}
			else
			{
				throw new Exception("You cannot withdraw an amount of money bigger than the balance of your account.");
			}
		}
		else
		{
			throw new Exception("Your account is inactive. You can not withdraw money yet.");
		}
	}
	
	/**
	 * Returns the type of the account, i.e. saving account
	 * @return	the type of the account
	 * @pre		this != null
	 * @post	@nochange
	 */
	public String getType()
	{
		assert this != null;
		
		return "Saving account";
	}
}

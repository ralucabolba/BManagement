package Model;
import java.io.*;

/**
 * 
 * @author Raluca Bolba
 * Class that represents a person. A person is characterized
 * by its cnp, name, and address
 *
 */
public class Person implements Serializable
{

	private static final long serialVersionUID = 2;
	private String cnp;
	private String name;
	private String address;
	
	/**
	 * Constructor of the class
	 * @param	cnp 	- the unique identifier of the person
	 * @param	name 	- the person's name
	 * @param	address	- the person's address
	 */
	public Person(String cnp, String name, String address)
	{
		this.cnp = cnp;
		this.name = name;
		this.address = address;
	}
	
	/**
	 * Returns the person's unique identifier called cnp
	 * @pre		true
	 * @post	@nochange
	 * @return	cnp - the unique identifier of the person
	 */
	public String getCnp()
	{
		return this.cnp;
	}
	
	/**
	 * Returns the person's name
	 * @pre		true
	 * @post	@nochange
	 * @return	name - the person's name
	 */
	public String getName()
	{
		return this.name;
	}
	
	
	/**
	 * Returns the person's address
	 * @pre		true
	 * @post	@nochange
	 * @return	address - the person's address
	 */
	public String getAddress()
	{
		return this.address;
	}
	
	/**
	 * Generates and returns a string representation of the person
	 * @return 	string representation of the person
	 * @pre		this != null
	 * @post	@nochange
	 */
	public String toString()
	{
		assert this != null;
		
		String string = name + ", " + cnp + ", " + " " + address;
		return string;
	}
	
	/**
	 * Compares two persons and checks if they are equal, based on their cnp
	 * @param	obj the person to compare with the instance of the class
	 * @return	true if the person have the same cnp, false otherwise
	 * @pre		this != null
	 * @post	@nochange
	 */
	@Override
	public boolean equals(Object obj)
	{
		assert this != null;
		
		if(obj == null)
		{
			return false;
		}
		if(obj instanceof Person)
		{
			String cnp = ((Person) obj).getCnp();
			return cnp.equals(this.cnp);
		}
		return false;
	}
}

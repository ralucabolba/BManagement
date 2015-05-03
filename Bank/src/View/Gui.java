package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame implements ActionListener
{
	private JFrame mainWindow;
	private JFrame myAccountWindow;
	private JFrame bankWindow;
	
	private JButton createAccount;
	private JButton goToMyAccount;
	private JButton goToBank;
	private JButton searchPersonButton;
	private JButton searchAccountButton;
	private JButton removeAccountButton;
	private JButton ok;
	private JButton add;
	private JButton withdraw;
	private JButton goBack;
	private JButton goBack2;
	private JButton removeMyAccount;
	
	private JRadioButton savingAccountOption;
	private JRadioButton spendingAccountOption;
	
	private ButtonGroup groupOption;
	
	private String opt;
	
	private JTextField personName;
	private JTextField personCnp;
	private JTextField personAddress;
	private JTextField searchCnpField;
	private JTextField searchIbanField;
	private JTextField removeIbanField;
	private JTextField myCnpField;
	private JTextField moneyField;
	private JTextField addMoneyField;
	
	private JTextArea consoleField;
	private JTextArea accountsArea;
	
	private JTable accountsTable;
	
	private JScrollPane accountsScroll;
	
	private DefaultTableModel accountsModel;
	
	private JComboBox choseAccount;
	
	private JLabel name;
	private JLabel cnp;
	private JLabel address;
	
	public Gui()
	{
		mainWindow = this;
		mainWindow.setTitle("Bank Management");
		bankWindow = new JFrame("Bank management");
		myAccountWindow = new JFrame("My account");
		
		JDesktopPane mainPanel = new JDesktopPane();
		mainPanel.setBackground(new Color(168, 74, 92));
		
		JDesktopPane panel = new JDesktopPane();
		panel.setBounds(100, 100, 600, 400);
		panel.setOpaque(true);
		panel.setBackground(new Color(255, 255, 255, 100));
		mainPanel.add(panel);
		
	 	JLabel image = new JLabel();
	 	image.setIcon(new javax.swing.ImageIcon(getClass().getResource("image.jpg")));
	 	image.setBounds(0, 0, 800, 650);
	 	mainPanel.add(image);
		
		JLabel optionLabel = new JLabel("Please choose an option :");
		optionLabel.setFont(new Font("Georgia", 2, 15));
		optionLabel.setBounds(210, 10, 200, 25);
		panel.add(optionLabel);
		
		JLabel createAccountLabel = new JLabel("Create a new account");
		createAccountLabel.setFont(new Font("Georgia", 2, 14));
		createAccountLabel.setBounds(220, 70, 200, 25);
		panel.add(createAccountLabel);
		
		JLabel nameLabel = new JLabel("Name :");
		nameLabel.setFont(new Font("Georgia", 2, 13));
		nameLabel.setBounds(75, 100, 200, 25);
		panel.add(nameLabel);
		
		personName = new JTextField();
		personName.setBounds(325, 100, 200, 25);
		panel.add(personName);
		
		JLabel cnpLabel = new JLabel("Cnp :");
		cnpLabel.setFont(new Font("Georgia", 2, 13));
		cnpLabel.setBounds(75, 130, 200, 25);
		panel.add(cnpLabel);
		
		personCnp = new JTextField();
		personCnp.setBounds(325, 130, 200, 25);
		panel.add(personCnp);
		
		JLabel addressLabel = new JLabel("Address :");
		addressLabel.setFont(new Font("Georgia", 2, 13));
		addressLabel.setBounds(75, 160, 200, 25);
		panel.add(addressLabel);
		
		personAddress = new JTextField();
		personAddress.setBounds(325, 160, 200, 25);
		panel.add(personAddress);
		
		savingAccountOption = new JRadioButton("Saving account", true);
		savingAccountOption.setActionCommand("Saving account");
		savingAccountOption.setFont(new Font("Georgia", 2, 14));
		savingAccountOption.setBounds(75, 220, 200, 25);
		panel.add(savingAccountOption);
		
		spendingAccountOption = new JRadioButton("Spending account");
		spendingAccountOption.setActionCommand("Spending account");
		spendingAccountOption.setFont(new Font("Georgia", 2, 14));
		spendingAccountOption.setBounds(325, 220, 200, 25);
		panel.add(spendingAccountOption);
		
		groupOption = new ButtonGroup();
		groupOption.add(savingAccountOption);
		groupOption.add(spendingAccountOption);
		
		opt = "Saving account";
		
		savingAccountOption.addActionListener(this);
		spendingAccountOption.addActionListener(this);
		
		createAccount = new JButton("Create account");
		createAccount.setBounds(200, 280, 200, 25);
		panel.add(createAccount);
		
		goToMyAccount = new JButton("I have an account");
		goToMyAccount.setBounds(200, 310, 200, 25);
		panel.add(goToMyAccount);
		
		goToBank = new JButton("Bank Management");
		goToBank.setBounds(200, 340, 200, 25);
		panel.add(goToBank);
	    
		accountsModel = new DefaultTableModel();
		accountsModel.setColumnIdentifiers(new String[]{"Id account", "Account type", "Status account", "Iban", "Balance", "Name owner", "Cnp owner", "Address owner"});
		
		searchPersonButton = new JButton("Search person");
		searchAccountButton = new JButton("Search account");
		removeAccountButton = new JButton("Remove account");
		ok = new JButton("Ok");
		add = new JButton("Add money");
		withdraw = new JButton("Withdraw money");
		goBack = new JButton("Back");
		goBack2= new JButton("Back");
		removeMyAccount = new JButton("Remove my account");
		
		consoleField = new JTextArea("");
		accountsArea = new JTextArea("");
		
		myCnpField = new JTextField();
		moneyField = new JTextField();
		addMoneyField = new JTextField();
		
		choseAccount = new JComboBox();
		
		this.add(mainPanel);
		this.setSize(800, 650);
		this.setVisible(true);
	
	}
	
	public void bankManagement()
	{
		mainWindow.setVisible(false);
		
		bankWindow = new JFrame("Bank management");
		consoleField.setText("");
		
		JDesktopPane bankPanel = new JDesktopPane();
		bankPanel.setBackground(new Color(168, 74, 92));
		
		accountsTable = new JTable();
		accountsTable.setModel(accountsModel);
		accountsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		accountsTable.setFillsViewportHeight(true);
		accountsTable.setPreferredScrollableViewportSize(new Dimension(1366, 300));
		
	    accountsScroll = new JScrollPane(accountsTable);
	    accountsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    accountsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    accountsScroll.setBounds(0, 0, 1366, 300);
	    
	    
	    bankPanel.add(accountsScroll);
	    
	    JLabel searchAccount = new JLabel("Search an account :");
	    searchAccount.setForeground(Color.white);
	    searchAccount.setFont(new Font("Georgia", 2, 14));
	    searchAccount.setBounds(10, 350, 150, 25);
	    bankPanel.add(searchAccount);
	    
	    JLabel searchPerson = new JLabel("Search a person :");
	    searchPerson.setForeground(Color.white);
	    searchPerson.setFont(new Font("Georgia", 2, 14));
	    searchPerson.setBounds(10, 380, 150, 25);
	    bankPanel.add(searchPerson);
	    
	    JLabel removeAccount = new JLabel("Remove an account :");
	    removeAccount.setForeground(Color.white);
	    removeAccount.setFont(new Font("Georgia", 2, 14));
	    removeAccount.setBounds(10, 410, 150, 25);
	    bankPanel.add(removeAccount);
	    
	    
	    searchCnpField = new JTextField();
	    searchCnpField.setBounds(210, 380, 250, 25);
	    searchCnpField.setToolTipText("Type the person's cnp");
	    bankPanel.add(searchCnpField);
	    
	    searchIbanField = new JTextField();
	    searchIbanField.setBounds(210, 350, 250, 25);
	    searchIbanField.setToolTipText("Type the account's iban");
	    bankPanel.add(searchIbanField);
	    
	    removeIbanField = new JTextField();
	    removeIbanField.setBounds(210, 410, 250, 25);
	    removeIbanField.setToolTipText("Type the account's iban");
	    bankPanel.add(removeIbanField);
	    
	    searchPersonButton.setBounds(500, 380, 150, 25);
	    bankPanel.add(searchPersonButton);
	    
	    searchAccountButton.setBounds(500, 350, 150, 25);
	    bankPanel.add(searchAccountButton);
	    
	    removeAccountButton.setBounds(500, 410, 150, 25);
	    bankPanel.add(removeAccountButton);
	    
	   
	    consoleField.setBounds(700, 350, 500, 300);
	    consoleField.setOpaque(true);
	    consoleField.setEditable(false);
	    JScrollPane scroll = new JScrollPane(consoleField);
	    scroll.setBounds(700, 350, 500, 300);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    bankPanel.add(scroll);
	    
	    goBack2.setBounds(10, 600, 100, 25);
	    bankPanel.add(goBack2);
	    goBack2.addActionListener(this);
	    
	    JLabel image = new JLabel();
	 	image.setIcon(new javax.swing.ImageIcon(getClass().getResource("image.jpg")));
	 	image.setBounds(0, 0, 1366, 768);
	 	bankPanel.add(image);
	 	
	 	
	    bankWindow.add(bankPanel);
	    bankWindow.setSize(1366, 768);
	    bankWindow.setVisible(true);
	}
	
	public void MyAccount()
	{
		mainWindow.setVisible(false);
		
		myAccountWindow = new JFrame("My account");
		accountsArea.setText("");
		
		JDesktopPane panel = new JDesktopPane();
		panel.setBackground(new Color(168, 74, 92));
		
		JDesktopPane left = new JDesktopPane();
		left.setBackground(new Color(223, 188, 195));
		//left.setBackground(new Color(255, 255, 255, 100));
		left.setBounds(50, 30, 600, 200);
		panel.add(left);
		
		JDesktopPane right = new JDesktopPane();
		right.setBackground(new Color(223, 188, 195));
		right.setBounds(700, 30, 600, 200);
		panel.add(right);
		
		JLabel myInfo = new JLabel("Personal information");
		myInfo.setFont(new Font("Georgia", 2, 14));
		myInfo.setBounds(220, 10, 150, 25);
		left.add(myInfo);
		
		name = new JLabel ("Name : ");
		name.setFont(new Font("Georgia", 2, 13));
		name.setBounds(10, 30, 590, 25);
		left.add(name);
		
		cnp = new JLabel ("Cnp : ");
		cnp.setFont(new Font("Georgia", 2, 13));
		cnp.setBounds(10, 60, 590, 25);
		left.add(cnp);
		
		address = new JLabel ("Address : ");
		address.setFont(new Font("Georgia", 2, 13));
		address.setBounds(10, 90, 590, 25);
		left.add(address);
		
		JLabel myAccount = new JLabel("My accounts");
		myAccount.setFont(new Font("Georgia", 2, 14));
		myAccount.setBounds(250, 10, 100, 25);
		right.add(myAccount);
		
		
		accountsArea.setBounds(50, 40, 500, 120);
		JScrollPane scroll = new JScrollPane(accountsArea);
		scroll.setBounds(50, 40, 500, 120);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		right.add(scroll);

		JLabel cnpLabel = new JLabel("Introduce your cnp :");
		cnpLabel.setFont(new Font("Georgia", 2, 14));
		cnpLabel.setForeground(Color.pink);
		cnpLabel.setBounds(50, 250, 200, 25);
		panel.add(cnpLabel);
		
		myCnpField.setBounds(260, 250, 200, 25);
		panel.add(myCnpField);
		
		ok.setBounds(500, 250, 150, 25);
		panel.add(ok);
		
		JLabel withdrawLabel = new JLabel("Withdraw money from your account");
		withdrawLabel.setFont(new Font("Georgia", 2, 16));
		withdrawLabel.setForeground(Color.white);
		withdrawLabel.setBounds(50, 300, 300, 25);
		panel.add(withdrawLabel);
		
		JLabel moneyLabel = new JLabel("Introduce the amount :");
		moneyLabel.setFont(new Font("Georgia", 2, 13));
		moneyLabel.setForeground(Color.pink);
		moneyLabel.setBounds(50, 350, 200, 25);
		panel.add(moneyLabel);
		
		moneyField.setBounds(260, 350, 100, 25);
		panel.add(moneyField);
		
		withdraw.setBounds(500, 350, 150, 25);
		panel.add(withdraw);
		
		JLabel addLabel = new JLabel("Add money to your account");
		addLabel.setFont(new Font("Georgia", 2, 16));
		addLabel.setForeground(Color.white);
		addLabel.setBounds(50, 400, 250, 25);
		panel.add(addLabel);
		
		JLabel addMoneyLabel = new JLabel("Introduce the amount :");
		addMoneyLabel.setFont(new Font("Georgia", 2, 13));
		addMoneyLabel.setForeground(Color.pink);
		addMoneyLabel.setBounds(50, 450, 200, 25);
		panel.add(addMoneyLabel);
		
		addMoneyField.setBounds(260, 450, 100, 25);
		panel.add(addMoneyField);
		
		add.setBounds(500, 450, 150, 25);
		panel.add(add);
		
		goBack.setBounds(50, 600, 150, 25);
		goBack.addActionListener(this);
		
		panel.add(goBack);
		
		
		removeMyAccount.setBounds(250, 600, 150, 25);
		panel.add(removeMyAccount);
		
		JLabel choseLabel = new JLabel("Choose the account you are working with :");
		choseLabel.setFont(new Font("Georgia", 2, 14));
		choseLabel.setForeground(Color.pink);
		choseLabel.setBounds(700, 250, 300, 25);
		panel.add(choseLabel);
		
		choseAccount.setBounds(700, 280, 300, 25);
		panel.add(choseAccount);
		
		JLabel image = new JLabel();
		image.setIcon(new javax.swing.ImageIcon(getClass().getResource("image.jpg")));
		image.setBounds(0, 0, 1366, 768);
		panel.add(image);
		 	
		 	
		myAccountWindow.add(panel);
		myAccountWindow.setVisible(true);
		myAccountWindow.setSize(1366, 768);
		
	}
	
	public DefaultTableModel getAccountModel()
	{
		return this.accountsModel;
	}
	
	public void setAccountModel(DefaultTableModel model)
	{
		this.accountsModel = model;
	}
	
 	public String[] getPersonInformation()
	{
		String[] info = new String[]{personName.getText(), personCnp.getText(), personAddress.getText()};
		
		return info;
	}
	
 	public String getSearchPersonCnp()
 	{
 		return this.searchCnpField.getText();
 	}
 	
 	public String getSearchAccountIban()
 	{
 		return this.searchIbanField.getText();
 	}
 	
 	public String getRemovedAccountIban()
 	{
 		return this.removeIbanField.getText();
 	}
 	
 	public void setConsoleText(String text)
 	{
 		this.consoleField.setText(text);
 	}
 	
 	public String getMyCnp()
 	{
 		return this.myCnpField.getText();
 	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == goBack)
		{
			myAccountWindow.setVisible(false);
			//init();
			this.setVisible(true);
		}
		if(e.getSource() == goBack2)
		{
			bankWindow.setVisible(false);
			//init();
			this.setVisible(true);
		}
		else
		{
			opt = e.getActionCommand();
		}
	}
	public String getRadioButtonChoice()
	{
		return this.opt;
	}
	
	public String getChosenAccount()
	{
		return this.choseAccount.getSelectedItem().toString();
	}
	
	public JComboBox getComboBox()
	{
		return this.choseAccount;
	}
	
	public void setComboBox(JComboBox list)
	{
		this.choseAccount = list;
	}
	
	public void setName(String name)
	{
		this.name.setText("Name : " + name);
	}
	public void setCnp(String cnp)
	{
		this.cnp.setText("Cnp : " + cnp);
	}
	
	public void setAddress(String address)
	{
		this.address.setText("Address : " + address);
	}
	public void setAccountsArea(String text)
	{
		this.accountsArea.setText(text);
	}
	public String getMoneyWithdraw()
	{
		return this.moneyField.getText();
	}
	public String getMoneyAdd()
	{
		return this.addMoneyField.getText();
	}
	
	public void addListenerCreateAccount(ActionListener act)
	{
		createAccount.addActionListener(act);
	}
	public void addListenerGoToMyAccount(ActionListener act)
	{
		goToMyAccount.addActionListener(act);
	}
	public void addListenerGoToBank(ActionListener act)
	{
		goToBank.addActionListener(act);
	}
	public void addListenerCloseWindow(WindowListener act)
	{
		mainWindow.addWindowListener(act);
	}
	public void addListenerCloseBankWindow(WindowListener act)
	{
		bankWindow.addWindowListener(act);
	}
	public void addListenerCloseMyAccountWindow(WindowListener act)
	{
		myAccountWindow.addWindowListener(act);
	}
	public void addListenerSearchPerson(ActionListener act)
	{
		searchPersonButton.addActionListener(act);
	}
	public void addListenerSearchAccount(ActionListener act)
	{
		searchAccountButton.addActionListener(act);
	}
	public void addListenerRemoveAccount(ActionListener act)
	{
		removeAccountButton.addActionListener(act);
	}
	public void addListenerOk(ActionListener act)
	{
		ok.addActionListener(act);
	}
	public void addListenerWithdraw(ActionListener act)
	{
		withdraw.addActionListener(act);
	}
	public void addListenerAdd(ActionListener act)
	{
		add.addActionListener(act);
	}
	public void addListenerRemoveMyAccount(ActionListener act)
	{
		removeMyAccount.addActionListener(act);
	}
}

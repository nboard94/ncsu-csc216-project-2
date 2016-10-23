package edu.ncsu.csc216.tracker.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.tracker.command.Command;
import edu.ncsu.csc216.tracker.command.Command.CommandValue;
import edu.ncsu.csc216.tracker.command.Command.Flag;
import edu.ncsu.csc216.tracker.ticket.TrackedTicket;
import edu.ncsu.csc216.tracker.ticket_tracker.TicketTrackerModel;

/**
 * Container for the TicketTracker that has the menu options for new ticket 
 * tracking files, loading existing files, saving files and quitting.
 * Depending on user actions, other {@link JPanel}s are loaded for the
 * different ways users interact with the UI.
 * 
 * @author Dr. Sarah Heckman (heckman@csc.ncsu.edu)
 */
public class TicketTrackerGUI extends JFrame implements ActionListener {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Ticket Tracker";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New Ticket XML menu item. */
	private static final String NEW_XML_TITLE = "New";
	/** Text for the Load Ticket XML menu item. */
	private static final String LOAD_XML_TITLE = "Load";
	/** Text for the Save menu item. */
	private static final String SAVE_XML_TITLE = "Save";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new file containing {@link TrackedTicket}s. */
	private JMenuItem itemNewTicketXML;
	/** Menu item for loading a file containing {@link TrackedTicket}s. */
	private JMenuItem itemLoadTicketXML;
	/** Menu item for saving the ticket list. */
	private JMenuItem itemSaveTicketXML;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	/** Panel that will contain different views for the application. */
	private JPanel panel;
	/** Constant to identify TicketListPanel for {@link CardLayout}. */
	private static final String TICKET_LIST_PANEL = "TicketListPanel";
	/** Constant to identify NewPanel for {@link CardLayout}. */
	private static final String NEW_PANEL = "NewPanel";
	/** Constant to identify AssignedPanel for {@link CardLayout}. */
	private static final String ASSIGNED_PANEL = "AssignedPanel";
	/** Constant to identify WorkingPanel for {@link CardLayout}. */
	private static final String WORKING_PANEL = "WorkingPanel";
	/** Constant to identify FeedbackPanel for {@link CardLayout}. */
	private static final String FEEDBACK_PANEL = "FeedbackPanel";
	/** Constant to identify ClosedPanel for {@link CardLayout}. */
	private static final String CLOSED_PANEL = "ClosedPanel";
	/** Constant to identify CreateTicketPanel for {@link CardLayout}. */
	private static final String CREATE_TICKET_PANEL = "CreateTicketPanel";
	/** Ticket List panel - we only need one instance, so it's final. */
	private final TicketListPanel pnlTicketList = new TicketListPanel();
	/** New panel - we only need one instance, so it's final. */
	private final NewPanel pnlNew = new NewPanel();
	/** Assigned panel - we only need one instance, so it's final. */
	private final AssignedPanel pnlAssigned = new AssignedPanel();
	/** Working panel - we only need one instance, so it's final. */
	private final WorkingPanel pnlWorking = new WorkingPanel();
	/** Feedback panel - we only need one instance, so it's final. */
	private final FeedbackPanel pnlFeedback = new FeedbackPanel();
	/** Closed panel - we only need one instance, so it's final. */
	private final ClosedPanel pnlClosed = new ClosedPanel();
	/** Add Ticket panel - we only need one instance, so it's final. */
	private final CreateTicketPanel pnlCreateTicket = new CreateTicketPanel();
	/** Reference to {@link CardLayout} for panel.  Stacks all of the panels. */
	private CardLayout cardLayout;
	
	
	/**
	 * Constructs a {@link TicketTrackerGUI} object that will contain a {@link JMenuBar} and a
	 * {@link JPanel} that will hold different possible views of the data in
	 * the {@link TicketTrackerModel}.
	 */
	public TicketTrackerGUI() {
		super();
		
		//Set up general GUI info
		setSize(500, 700);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();
		
		//Create JPanel that will hold rest of GUI information.
		//The JPanel utilizes a CardLayout, which stack several different
		//JPanels.  User actions lead to switching which "Card" is visible.
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(pnlTicketList, TICKET_LIST_PANEL);
		panel.add(pnlNew, NEW_PANEL);
		panel.add(pnlAssigned, ASSIGNED_PANEL);
		panel.add(pnlWorking, WORKING_PANEL);
		panel.add(pnlFeedback, FEEDBACK_PANEL);
		panel.add(pnlClosed, CLOSED_PANEL);
		panel.add(pnlCreateTicket, CREATE_TICKET_PANEL);
		cardLayout.show(panel, TICKET_LIST_PANEL);
		
		//Add panel to the container
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Makes the GUI Menu bar that contains options for loading a file
	 * containing tickets or for quitting the application.
	 */
	private void setUpMenuBar() {
		//Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNewTicketXML = new JMenuItem(NEW_XML_TITLE);
		itemLoadTicketXML = new JMenuItem(LOAD_XML_TITLE);
		itemSaveTicketXML = new JMenuItem(SAVE_XML_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNewTicketXML.addActionListener(this);
		itemLoadTicketXML.addActionListener(this);
		itemSaveTicketXML.addActionListener(this);
		itemQuit.addActionListener(this);
		
		//Start with save button disabled
		itemSaveTicketXML.setEnabled(false);
		
		//Build Menu and add to GUI
		menu.add(itemNewTicketXML);
		menu.add(itemLoadTicketXML);
		menu.add(itemSaveTicketXML);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Performs an action based on the given {@link ActionEvent}.
	 * @param e user event that triggers an action.
	 */
	public void actionPerformed(ActionEvent e) {
		//Use TicketTrackerModel's singleton to create/get the sole instance.
		TicketTrackerModel model = TicketTrackerModel.getInstance();
		if (e.getSource() == itemNewTicketXML) {
			//Create a new ticket list
			model.createNewTicketList();
			itemSaveTicketXML.setEnabled(true);
			pnlTicketList.updateTable(null, null);
			cardLayout.show(panel, TICKET_LIST_PANEL);
			validate();
			repaint();			
		} else if (e.getSource() == itemLoadTicketXML) {
			//Load an existing ticket list
			try {
				model.loadTicketsFromFile(getFileName(true));
				itemSaveTicketXML.setEnabled(true);
				pnlTicketList.updateTable(null, null);
				cardLayout.show(panel, TICKET_LIST_PANEL);
				validate();
				repaint();
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to load ticket file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemSaveTicketXML) {
			//Save current ticket list
			try {
				model.saveTicketsToFile(getFileName(false));
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save ticket file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemQuit) {
			//Quit the program
			try {
				model.saveTicketsToFile(getFileName(false));
				System.exit(0);  //Ignore FindBugs warning here - this is the only place to quit the program!
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save ticket file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		}
	}
	
	/**
	 * Returns a file name generated through interactions with a {@link JFileChooser}
	 * object.
	 * @return the file name selected through {@link JFileChooser}
	 */
	private String getFileName(boolean chooserType) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChoose to current working directory
		fc.setApproveButtonText("Select");
		int returnVal = Integer.MIN_VALUE;
		if (chooserType) {
			fc.setDialogTitle("Load Course Catalog");
			returnVal = fc.showOpenDialog(this);
		} else {
			fc.setDialogTitle("Save Schedule");
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File file = fc.getSelectedFile();
		return file.getAbsolutePath();
	}

	/**
	 * Starts the GUI for the TicketTracker application.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new TicketTrackerGUI();
	}
	
	/**
	 * Inner class that creates the look and behavior for the {@link JPanel} that 
	 * shows the list of tickets.
	 * 
	 * @author Dr. Sarah Heckman (heckman@csc.ncsu.edu)
	 */
	private class TicketListPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button for creating a new Ticket */
		private JButton btnAddTicket;
		/** Button for deleting the selected ticket in the list */
		private JButton btnDeleteTicket;
		/** Button for editing the selected ticket in the list */
		private JButton btnEditTicket;
		/** Text field for a user to enter an owner name to filter ticket list */
		private JTextField txtFilterByOwner;
		/** Button for starting filter of list by owner */
		private JButton btnFilterByOwner;
		/** Text field for a user to enter an submitter name to filter ticket list */
		private JTextField txtFilterBySubmitter;
		/** Button for starting filter of list by submitter */
		private JButton btnFilterBySubmitter;
		/** Button that will show all ticket that are currently tracked */
		private JButton btnShowAllTickets;
		/** JTable for displaying the list of ticket */
		private JTable table;
		/** TableModel for tickets */
		private TicketTableModel ticketTableModel;
		
		/**
		 * Creates the ticket list.
		 */
		public TicketListPanel() {
			super(new GridBagLayout());
			
			//Set up the JPanel that will hold action buttons
			JPanel pnlActions = new JPanel();
			btnAddTicket = new JButton("Add New Ticket");
			btnAddTicket.addActionListener(this);
			btnDeleteTicket = new JButton("Delete Selected Ticket");
			btnDeleteTicket.addActionListener(this);
			btnEditTicket = new JButton("Edit Selected Ticket");
			btnEditTicket.addActionListener(this);
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Ticket Actions");
			pnlActions.setBorder(border);
			pnlActions.setToolTipText("Ticket Actions");
			
			pnlActions.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlActions.add(btnAddTicket, c);
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlActions.add(btnDeleteTicket, c);
			c = new GridBagConstraints();
			c.gridx = 2;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlActions.add(btnEditTicket, c);
			
			//Set up the JPanel that handles search
			JPanel pnlSearch = new JPanel();
			txtFilterByOwner = new JTextField(10);
			btnFilterByOwner = new JButton("Filter List by Owner");
			btnFilterByOwner.addActionListener(this);
			txtFilterBySubmitter = new JTextField(10);
			btnFilterBySubmitter = new JButton("Filter List by Submitter");
			btnFilterBySubmitter.addActionListener(this);
			btnShowAllTickets = new JButton("Show All Tickets");
			btnShowAllTickets.addActionListener(this);
			
			border = BorderFactory.createTitledBorder(lowerEtched, "Ticket Search");
			pnlSearch.setBorder(border);
			pnlSearch.setToolTipText("Ticket Search");
			
			pnlSearch.setLayout(new GridBagLayout());
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlSearch.add(txtFilterByOwner, c);
			c = new GridBagConstraints();
			c.gridx = 2;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlSearch.add(btnFilterByOwner, c);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 1;
			c.gridheight = 1;
			c.gridwidth = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlSearch.add(txtFilterBySubmitter, c);
			c = new GridBagConstraints();
			c.gridx = 2;
			c.gridy = 1;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlSearch.add(btnFilterBySubmitter, c);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 2;
			c.gridheight = 1;
			c.gridwidth = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			pnlSearch.add(btnShowAllTickets, c);
						
			//Set up table
			ticketTableModel = new TicketTableModel();
			table = new JTable(ticketTableModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setPreferredScrollableViewportSize(new Dimension(500, 500));
			table.setFillsViewportHeight(true);
			
			JScrollPane listScrollPane = new JScrollPane(table);
			
			border = BorderFactory.createTitledBorder(lowerEtched, "Ticket List");
			listScrollPane.setBorder(border);
			listScrollPane.setToolTipText("Ticket List");
			
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = .1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlActions, c);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 1;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = .1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlSearch, c);
			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 2;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.ipady = 400;
			c.weightx = 1;
			c.weighty = .8;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(listScrollPane, c);
		}

		/**
		 * Performs an action based on the given {@link ActionEvent}.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddTicket) {
				//If the add button is pressed switch to the createTicketPanel
				cardLayout.show(panel,  CREATE_TICKET_PANEL);
			} else if (e.getSource() == btnDeleteTicket) {
				//If the delete button is pressed, delete the ticket
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(TicketTrackerGUI.this, "No ticket selected");
				} else {
					try {
						int ticketId = Integer.parseInt(ticketTableModel.getValueAt(row, 0).toString());
						TicketTrackerModel.getInstance().deleteTicketById(ticketId);
					} catch (NumberFormatException nfe ) {
						JOptionPane.showMessageDialog(TicketTrackerGUI.this, "Invalid ticket id");
					}
				}
				updateTable(null, null);
			} else if (e.getSource() == btnEditTicket) {
				//If the edit button is pressed, switch panel based on state
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(TicketTrackerGUI.this, "No ticket selected");
				} else {
					try {
						int ticketId = Integer.parseInt(ticketTableModel.getValueAt(row, 0).toString());
						String stateName = TicketTrackerModel.getInstance().getTicketById(ticketId).getStateName();
						 
						if (stateName.equals(TrackedTicket.NEW_NAME)) {
							cardLayout.show(panel, NEW_PANEL);
							pnlNew.setTicketInfo(ticketId);
						} 
						if (stateName.equals(TrackedTicket.ASSIGNED_NAME)) {
							cardLayout.show(panel, ASSIGNED_PANEL);
							pnlAssigned.setTicketInfo(ticketId);
						} 
						if (stateName.equals(TrackedTicket.WORKING_NAME)) {
							cardLayout.show(panel, WORKING_PANEL);
							pnlWorking.setTicketInfo(ticketId);
						} 
						if (stateName.equals(TrackedTicket.FEEDBACK_NAME)) {
							cardLayout.show(panel, FEEDBACK_PANEL);
							pnlFeedback.setTicketInfo(ticketId);
						}
						if (stateName.equals(TrackedTicket.CLOSED_NAME)) {
							cardLayout.show(panel, CLOSED_PANEL);
							pnlClosed.setTicketInfo(ticketId);
						} 
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(TicketTrackerGUI.this, "Invalid ticket id");
					} catch (NullPointerException npe) {
						JOptionPane.showMessageDialog(TicketTrackerGUI.this, "Invalid ticket id");
					}
				}
			} else if (e.getSource() == btnFilterByOwner) {
				String owner = txtFilterByOwner.getText();
				txtFilterByOwner.setText("");
				updateTable(owner, null);
			} else if (e.getSource() == btnFilterBySubmitter) {
				String submitter = txtFilterBySubmitter.getText();
				txtFilterBySubmitter.setText("");
				updateTable(null, submitter);
			} else if (e.getSource() == btnShowAllTickets) {
				updateTable(null, null);
			}
			TicketTrackerGUI.this.repaint();
			TicketTrackerGUI.this.validate();
		}
		
		public void updateTable(String owner, String submitter) {
			if (owner == null && submitter == null) {
				ticketTableModel.updateData();
			} else if (owner != null) {
				ticketTableModel.updateDataWithOwner(owner);
			} else if (submitter != null) {
				ticketTableModel.updateDataWithSubmitter(submitter);
			}
		}
		
		/**
		 * {@link TicketTableModel} is the object underlying the {@link JTable} object that displays
		 * the list of {@link TrackedTicket}s to the user.
		 * @author Dr. Sarah Heckman (heckman@csc.ncsu.edu)
		 */
		private class TicketTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Ticket ID", "Ticket State", "Ticket Title"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the {@link TicketTableModel} by requesting the latest information
			 * from the {@link TicketTrackerModel}.
			 */
			public TicketTableModel() {
				updateData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				return data[row][col];
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row location to modify the data.
			 * @param column location to modify the data.
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with {@link TrackedTicket} information from the {@link TicketTrackerModel}.
			 */
			private void updateData() {
				TicketTrackerModel m = TicketTrackerModel.getInstance();
				data = m.getTicketListAsArray();
			}
			
			/**
			 * Updates the given model with {@link TrackedTicket} information for the 
			 * given owner from the {@link TicketTrackerModel}.
			 * @param owner developer id to search for.
			 */
			private void updateDataWithOwner(String owner) {
				try {
					TicketTrackerModel m = TicketTrackerModel.getInstance();
					data = m.getTicketListByOwnerAsArray(owner);
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(TicketTrackerGUI.this, "Invalid owner");
				}
			}
			
			/**
			 * Updates the given model with {@link TrackedTicket} information for the 
			 * given submitter from the {@link TicketTrackerModel}.
			 * @param submitter submitter id to search for.
			 */
			private void updateDataWithSubmitter(String submitter) {
				try {
					TicketTrackerModel m = TicketTrackerModel.getInstance();
					data = m.getTicketListBySubmitterAsArray(submitter);
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(TicketTrackerGUI.this, "Invalid submitter");
				}
			}
		}
	}
	
	/**
	 * Inner class that creates the look and behavior for the {@link JPanel} that 
	 * interacts with an new ticket.
	 * 
	 * @author Dr. Sarah Heckman (heckman@csc.ncsu.edu)
	 */
	private class NewPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** {@link TicketInfoPanel} that presents the {@TrackedTicket}'s information to the user */
		private TicketInfoPanel pnlTicketInfo;
		/** Note author label for the state update */
		private JLabel lblNoteAuthor;
		/** Note author for the state update */
		private JTextField txtNoteAuthor;
		/** Note label for the state update */
		private JLabel lblNote;
		/** Note for the state update */
		private JTextArea txtNote;
		/** Label for 

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;
import java.awt.List;
import java.awt.Choice;
import java.awt.TextField;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.StringUtils;

import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.DropMode;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import java.awt.Font;
public class CashierSearchAndDelete {

	private JFrame frmEztravel;
	private JTextField TicketNumber;
	private JTable table;
	public DefaultTableModel model = new DefaultTableModel(new String[]{"ID","CustomerName", "CustomerLastName", "IDNumber","Departure Date", "Return Date","Bus Number","Seat Number", "Receipt Type"}, 0);

	/**
	 * Launch the application.
	 */
	public static void CashierSearchAndDelete() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CashierSearchAndDelete window = new CashierSearchAndDelete();
					window.frmEztravel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CashierSearchAndDelete() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEztravel = new JFrame();
		frmEztravel.setTitle("EZTravel");
		frmEztravel.setBounds(100, 100, 690, 510);
		frmEztravel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEztravel.getContentPane().setLayout(null);
		
		JLabel lblWelcomeAdmin = new JLabel("\u03A0\u03BB\u03B7\u03BA\u03C4\u03C1\u03BF\u03BB\u03BF\u03B3\u03B5\u03AF\u03C3\u03C4\u03B5 \u03C4\u03BF\u03BD \"\u0391\u03C1\u03B9\u03B8\u03BC\u03CC \u0395\u03B9\u03C3\u03B9\u03C4\u03B7\u03C1\u03AF\u03BF\u03C5\" \u03B3\u03B9\u03B1 \u03BD\u03B1 \u03B4\u03B5\u03AF\u03C4\u03B5 \u03C4\u03BF \u03B5\u03B9\u03C3\u03B9\u03C4\u03AE\u03C1\u03B9\u03BF");
		lblWelcomeAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeAdmin.setBounds(10, 11, 637, 22);
		frmEztravel.getContentPane().add(lblWelcomeAdmin);
		
		JLabel AddaCashier = new JLabel("\u0391\u03C1\u03B9\u03B8\u03BC\u03BF\u03C2 \u0395\u03B9\u03C3\u03B9\u03C4\u03B7\u03C1\u03B9\u03BF\u03C5");
		AddaCashier.setBounds(10, 92, 138, 14);
		frmEztravel.getContentPane().add(AddaCashier);
		
		TicketNumber = new JTextField();
		TicketNumber.setHorizontalAlignment(SwingConstants.CENTER);
		TicketNumber.setBounds(10, 119, 138, 20);
		frmEztravel.getContentPane().add(TicketNumber);
		TicketNumber.setBorder(new LineBorder(Color.CYAN, 2, true));
		TicketNumber.setColumns(10);
		
		JButton btnLogOut = new JButton("Back");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmEztravel.setVisible(false);
				CashierScreen.CashierScreen();
			}
		});
		btnLogOut.setBounds(575, 435, 89, 23);
		frmEztravel.getContentPane().add(btnLogOut);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 184, 654, 102);
		frmEztravel.getContentPane().add(scrollPane);
		
		//create table with model in mind :D 
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setToolTipText("Select a row and press delete to delete it!! ");
		table.setCellSelectionEnabled(false);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("*Select a row and press delete to delete the selected user");
		btnDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				//get selected row
				int row = table.getSelectedRow();
				//if administrator selected a row then proceed
				if (table.isRowSelected(row)){
					//get the first value(id) so we can delete the selected user.
					Object selectedID = GetDataFromTable(row,0);
					//show messages(error/success)
					if (Tickets.DeleteTicket(selectedID)){
						int modelRow = table.convertRowIndexToModel( row );
						model.removeRow( modelRow );
						JOptionPane.showMessageDialog(frmEztravel, "~Ticket~\nId: " + selectedID + "\nTicketNumber:"+selectedID+"\nHas been removed successfully!!", "Success!!", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(frmEztravel, "Couldn't delete the ticket with id: "+selectedID+".\nSomething went wrong.\nContact info@bustickets.gr","Error..", JOptionPane.ERROR_MESSAGE);
					}
					refreshTable();
					
				}
					
			}
		});
		btnDelete.setBounds(10, 316, 89, 23);
		frmEztravel.getContentPane().add(btnDelete);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable();
				String number = TicketNumber.getText().toString().trim();
				boolean isINT = StringUtils.isStrictlyNumeric(number);
				if (isINT){
					//parse them to int
					int id = Integer.parseInt(number);
					Tickets.PrintTableTickets(frmEztravel,model, id);
				}
				else
				{
					JOptionPane.showMessageDialog(frmEztravel, "You didn't give a number!","Please insert a number", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnSearch.setBounds(10, 150, 89, 23);
		frmEztravel.getContentPane().add(btnSearch);
		//JScrollPane scrollPane = new JScrollPane(table);
		
	}
	public void refreshTable(){
		model.setRowCount(0);
		
	}
	public Object GetDataFromTable(int row_index, int col_index){
		
		return table.getModel().getValueAt(row_index, col_index);
		
	}
}



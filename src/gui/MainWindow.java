package gui;
// 60
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Core;
import util.Util;
import xml.ServiceArgument;
import xml.Configuration;
import xml.ParseService;
import xml.ReadConfiguration;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame {
	
	public JFrame frame;
	private JLayeredPane panelProcess;
	
	private static final long serialVersionUID = 1L;
	private String processName = "";
	private JTextField sequenceNumber;
	private JLabel lblProcessname_1;
	private JTextField createdProcess;
	// 53
	public MainWindow() {
		
		LinkedList<String> listOfServices = Util.getFilesInDirectory("services/");
		LinkedList<String> listOfProcesses = Util.getFilesInDirectory("processes/");
		
		int listOfProcessesLength = listOfProcesses.size();
		for (int i = 0; i < listOfProcessesLength; i++) {
			
			String item = listOfProcesses.get(i);
			
			if (item.endsWith(".wsdl")) {
				listOfProcesses.remove(i);
				
				i = i - 1;
				listOfProcessesLength = listOfProcessesLength - 1;
			}
		}
		
		Configuration configuration = ReadConfiguration.readConfiguration();
		
		panelProcess = new JLayeredPane();
		panelProcess.setBounds(10, 10, 460, 430);
		panelProcess.setLayout(null);
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panelProcess);
		
		JLabel lblSequenceNumber = new JLabel("Sequence Number:");
		lblSequenceNumber.setBounds(220, 200, 150, 15);
		panelProcess.add(lblSequenceNumber);
		
		sequenceNumber = new JTextField();
		sequenceNumber.setBounds(220, 220, 125, 25);
		panelProcess.add(sequenceNumber);
		sequenceNumber.setColumns(10);
		
		lblProcessname_1 = new JLabel("Selected Process:");
		lblProcessname_1.setBounds(75, 150, 125, 15);
		panelProcess.add(lblProcessname_1);
		
		JComboBox<String> selectedProcess = new JComboBox<String>();
		addStringItemsToComboBox(selectedProcess, listOfProcesses);
		selectedProcess.setBounds(220, 150, 150, 25);
		panelProcess.add(selectedProcess);
		
		JButton executeProcessStep = new JButton("Execute Process Step");
		executeProcessStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sequenceNumberText = sequenceNumber.getText();
				
				if (!sequenceNumberText.equals("")) {
					
					int number = -1;
					try {
						number = Integer.parseInt(sequenceNumberText);
					
						if (number == (int)number && number > 0) {
							
							String process = (String) selectedProcess.getSelectedItem();
							
							Core.executeProcessStep(process, sequenceNumberText);
							
						} else {
							JOptionPane.showMessageDialog(null, "Sequencenumber must be integer greater than zero");
						}
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Seuqncenumber must be an integer");
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Enter Sequencenumber before executing step");
				}
				
			}
		});
		
		executeProcessStep.setBounds(220, 250, 175, 25);
		panelProcess.add(executeProcessStep);
		
		
		JLabel lblServices = new JLabel("Services:");
		lblServices.setBounds(10, 40, 75, 15);
		panelProcess.add(lblServices);
		
		JLabel lblProcessname = new JLabel("Processname: " + processName);
		lblProcessname.setBounds(10, 15, 100, 15);
		panelProcess.add(lblProcessname);
		
		
		JLabel lblServiceList = new JLabel("");
		lblServiceList.setBounds(90, 40, 150, 50);
		panelProcess.add(lblServiceList);
		
		JComboBox<String> newService = new JComboBox<>();
		newService.setBounds(275, 10, 175, 25);
		panelProcess.add(newService);
		addStringItemsToComboBox(newService, listOfServices);
		
		JButton btnNewButton = new JButton("Add Service");
		btnNewButton.setBounds(275, 40, 175, 25);
		panelProcess.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!processName.equals("")) {
					String selectedService = (String) newService.getSelectedItem();
					
					selectedService = Core.copyFileFromSourceToDestination(configuration.getPathToServices() + selectedService, "processes/" + processName + "/" + selectedService);
					
					addServiceFrame(processName, selectedService);
					
					String previousText = lblServiceList.getText();
					lblServiceList.setText(previousText + ";" + selectedService);
				} else {
					JOptionPane.showMessageDialog(null, "Enter Name of Process before adding servics");
				}
			}
		});
		
		JButton btnExecuteProcess = new JButton("Execute Process");
		btnExecuteProcess.setBounds(50, 250, 150, 25);
		btnExecuteProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String process = (String) selectedProcess.getSelectedItem();
				Core.executeProcess(configuration.getPathToProcesses() + process);
				
			}
		});
		panelProcess.add(btnExecuteProcess);
		
		
		createdProcess = new JTextField();
		createdProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				processName = createdProcess.getText();
				
				Util.createDirectory(configuration.getPathToProcesses(), processName);
				
			}
		});
		createdProcess.setBounds(110, 10, 150, 25);
		panelProcess.add(createdProcess);
		createdProcess.setColumns(10);
		
		JButton btnSaveProcess = new JButton("Save Process");
		btnSaveProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!processName.equals("")) {
					
					String servicesList = lblServiceList.getText();
					Core.saveProcess(processName, servicesList);
					
					selectedProcess.addItem(processName);
					
					processName = "";
					lblServiceList.setText("");
					createdProcess.setText("");
					
				} else {
					JOptionPane.showMessageDialog(null, "Enter Name of Process before saving it");
				}	
					
			}
		});
		btnSaveProcess.setBounds(275, 70, 175, 25);
		panelProcess.add(btnSaveProcess);
		
	}
	// 26
	private static void addServiceFrame(String processName, String serviceName) {
		
		JFrame frame = new JFrame("Add Service to " + processName);
		
        JButton btnCustomizeInputArguments = new JButton("Customize Input");
        btnCustomizeInputArguments.setBounds(20, 50, 200, 25);
        btnCustomizeInputArguments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ParseService service = Core.parseService(processName, serviceName);
				LinkedList<ServiceArgument> inputs = service.getInputsFromDocument();
				
				customizeArguments(processName, serviceName, inputs);
				
			}
		});
        
        JButton btnCustomizeOutputArguments = new JButton("Customize Output");
        btnCustomizeOutputArguments.setBounds(20, 150, 200, 25);
        btnCustomizeOutputArguments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ParseService service = Core.parseService(processName, serviceName);
				LinkedList<ServiceArgument> outputs = service.getOutputsFromDocument();
				
				customizeArguments(processName, serviceName, outputs);
				
			}
		});
        
        JButton btnSaveChangesAndClose = new JButton("Save Changes and close Window");
        btnSaveChangesAndClose.setBounds(20, 300, 250, 25);
        btnSaveChangesAndClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
		});
        
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.add(btnCustomizeInputArguments);
		contentPane.add(btnCustomizeOutputArguments);
		contentPane.add(btnSaveChangesAndClose);
        
		
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
        frame.setBounds(0, 0, 300, 400);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(false);
	}
	// 18
	public static void customizeArguments(String processName, String serviceName, LinkedList<ServiceArgument> serviceArguments) {
		
        int numberOfArguments = serviceArguments.size();
		int x = 20;
		int y = 20;
		int width = 300;
		int height = 20;
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JFrame frame = new JFrame("Customize Arguments of " + serviceName);
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
        frame.setBounds(0, 0, 600, 800);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setResizable(true);
		
		JButton btnSaveChangesAndClose = new JButton("Save Changes and close Window");
		btnSaveChangesAndClose.setBounds(345, 10, 200, 25);
		btnSaveChangesAndClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Core.updateServiceArguments("processes/" + processName + "/" + serviceName, serviceArguments);
				
				frame.dispose();
			}
		});
		contentPane.add(btnSaveChangesAndClose);
		
		
        for(int i = 0; i < numberOfArguments; i++) {
        	ServiceArgument argument = serviceArguments.get(i);
        	
        	JLabel nameDescription = new JLabel();
        	nameDescription.setText("Name");
        	nameDescription.setBounds(x, y, width, height);
        	contentPane.add(nameDescription);
        	JLabel name = new JLabel();
        	name.setText(argument.getName());
        	name.setBounds(x, y + 20, width, height);
        	contentPane.add(name);
        	
        	y += 50;
        	
        	JLabel datatypeDescription = new JLabel();
        	datatypeDescription.setText("Datatype");
        	datatypeDescription.setBounds(x, y, width, height);
        	contentPane.add(datatypeDescription);        	
        	JLabel datatype = new JLabel();
        	datatype.setText(argument.getDatatype());
        	datatype.setBounds(x, y + 20, width, height);
        	contentPane.add(datatype);
        	
        	y += 50;
        	
        	JLabel valueDescription = new JLabel();
        	valueDescription.setText("Value");
        	valueDescription.setBounds(x, y, width, height);
        	contentPane.add(valueDescription);
        	
        	JComboBox<String> values = new JComboBox<>();
        	values.setEditable(true);
        	values.setBounds(x, y + 20, width, height);
        	LinkedList<String> valuesItems = new LinkedList<>();
        	valuesItems = Core.getOutputOfServices(processName);
        	addStringItemsToComboBox(values, valuesItems);
        	values.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent arg0) {
    				
    				String selectedItem = (String) values.getSelectedItem();
    				if (selectedItem.startsWith("HWD_")) {
						argument.setDatatype("ServiceLink");
					}
    				
    				argument.setValue(selectedItem);
    				
    				for (int j = 0; j < serviceArguments.size(); j++) {
						
    					if (serviceArguments.get(j).getName().equals(argument.getName())) {
							serviceArguments.set(j, argument);
						}
    					
					}
    				
    			}
    		});
        	contentPane.add(values);
        	
        	y += 50;
        	
        }
	}
	
	private static JComboBox<String> addStringItemsToComboBox(JComboBox<String> comboBox, LinkedList<String> items) {
		
		for (Iterator<String> iterator = items.iterator(); iterator.hasNext();) {
			String item = (String) iterator.next();
			
			comboBox.addItem(item);
		}
		return comboBox;
	}
}

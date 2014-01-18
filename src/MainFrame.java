import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private StringBuilder path;
	private String output;
	private String simplifiedOutput;
	
	private JButton item1;	
	private JButton item2;
	private JButton item3;	
	private JButton item4;
	private JButton item8;
	private JLabel  item5;
	private JPanel  item6;
	private JPanel  item7;
	
	
	private Dimension ss = Toolkit.getDefaultToolkit ().getScreenSize ();
	

	public MainFrame()
	{
		super("States Creator");		
		setLayout(new GridBagLayout());
		
		Dimension frameSize = new Dimension ( 300, 100 );
	    setBounds ( ss.width / 2 - frameSize.width / 2, 
                    ss.height / 2 - frameSize.height / 2,
                    frameSize.width, frameSize.height );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );				
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 10, 10);
		
		item1 = new JButton("Import input file");
		add(item1,c);
		
		item2 = new JButton("About Authors");
		add(item2);
		
		Handler hnd1 = new Handler();
		item1.addActionListener(hnd1);
		item2.addActionListener(hnd1);
	}
	
	private class SeconderyFrame extends JFrame
	{						
		private static final long serialVersionUID = 1L;

		public SeconderyFrame(StringBuilder str)
		{
			super("Output");					
			
			Dimension frameSize = new Dimension ( 500, 120 );
		    setBounds ( ss.width / 2 - frameSize.width / 2, 
	                    ss.height / 2 - frameSize.height / 2,
	                    frameSize.width, frameSize.height );
			
			item3 = new JButton("Output");
			item4 = new JButton("Simplified Output");
			item8 = new JButton("Generate Output File");
			item5 = new JLabel("Chosen File: " + str.toString());
			item6 = new JPanel();
			item7 = new JPanel(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			
			item6.add(item5,c);
			item7.add(item3,c);
			item7.add(item4);
			item7.add(item8,c);
			
			add(item6,BorderLayout.NORTH);
			add(item7,BorderLayout.SOUTH);
			
			Handler hnd2 = new Handler();
			item3.addActionListener(hnd2);
			item4.addActionListener(hnd2);
			item8.addActionListener(hnd2);
		}
	}
	
	private class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			path = new StringBuilder();
			
			if(event.getSource()==item1)
			{
				JFileChooser chooser = new JFileChooser();				
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Choose the input file");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter txtType = new FileNameExtensionFilter("text document (.txt)", "txt");
				chooser.addChoosableFileFilter(txtType);
	
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{				  
					//system.out.println("File URL : " + chooser.getSelectedFile());
					path.append(chooser.getSelectedFile());
					  
					TextInputReader tir = new TextInputReader(path.toString());
						
						
					Circuit circuit = new Circuit();
					circuit.createCicuit(tir);
					try
					{
						output = circuit.generateAllOutputs();
					} 
					catch (Exception e)
					{						 
						e.printStackTrace();
					}
					  					
					simplifiedOutput = circuit.generateSimplifiedOutput();
					try 
					{
						circuit.drawCircuit(output, "output_normal.png");
						circuit.drawCircuit(simplifiedOutput, "output_simplified.png");
					}
					catch (Exception e) {}
					
					SeconderyFrame sec = new SeconderyFrame(path);
	    			  
	    			sec.setResizable(false);    			 
					sec.setVisible(true);
					  
				} else {
					path.append("No Selection");
					JOptionPane.showMessageDialog(null, path);
				}							
				
				
			}
			else if(event.getSource()==item2)
			{
				String cpal = new String();
				cpal = "Programmed and Revised By: \n\nAhmed Fathy Aly - Section 1\nAhmed Fathy Abdel Mageed - Section 1\nAhmed Fathy Hussain Fathy - Section 1\nAhmed Ra'fat Elsayed Al Alfy - Section 1\nMohamed Ahmed Anwer - Section 7\n\n2nd year Electrical - Logic Project\n� 2013-2014";
				JOptionPane.showMessageDialog(null, cpal);
			}
			else if(event.getSource()==item3)
			{
				JOptionPane.showMessageDialog(null, output);
			}
			else if(event.getSource()==item4)
			{
				JOptionPane.showMessageDialog(null, simplifiedOutput);
			}
			else if(event.getSource()==item8)
			{
				try
				{
					PrintWriter writer = new PrintWriter("Output.txt", "UTF-8");											    
					String[] out = output.split("\n");
					String[] simpOut = simplifiedOutput.split("\n");
					
					writer.println("Normal Output:");
					writer.println();
					
					for (int i = 0; i < out.length; i++)
					{
						writer.println(out[i]);							
					}
					
					writer.println("------------------------");
					writer.println("Simplified Output:");
					writer.println();
					
					for (int i = 0; i < simpOut.length; i++)
					{
						writer.println(simpOut[i]);							
					}
					
					writer.close();
				} catch (FileNotFoundException e)
				{				
					e.printStackTrace();
				} catch (UnsupportedEncodingException e)
				{					
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Output file was generated in application directory");
			}
			
			
		}
	}
	
}
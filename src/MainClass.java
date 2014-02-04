import javax.swing.UIManager;


/**
 * The main class of the application
 * 
 * @author A.Abd el Megeed, A.Aly, A.alfy, M.Anwer
 */
public class MainClass
{	
	public static void main(String[] args) throws Exception 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MainFrame mf = new MainFrame();		
						
		mf.setVisible(true);
		mf.setResizable(false);				
	}

}

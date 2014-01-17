import javax.swing.UIManager;


/**
 * @author A.Abd el Megeed, A.Aly, A.alfy, M.Anwer
 * The main class of the application
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

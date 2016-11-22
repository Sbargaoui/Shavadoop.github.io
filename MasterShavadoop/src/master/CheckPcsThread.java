package master;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * @author Benseddik Mohammed
 * @author Sami Bergaoui
 * @version 1.0.1
 * 
 */
public class CheckPcsThread extends Thread{


	public String pcName;
	private boolean activePc = false;
	static String userName = ConstantsMaster.userName;
	
	/**
	 * Constructor of the thread, gets the pc name
	 * @param pcName
	 */
	public CheckPcsThread(String pcName)
	{
		this.pcName = pcName;
	}

	/**
	 * DoWork function in the thread, checks if the pc is active or not
	 * @throws IOException
	 */
	public void checkPcIsActif() throws IOException
	{
		Vector<String> commands = new Vector<String>();
		commands.add("bash");
		commands.add("-c");
		commands.add("ssh "+ userName + pcName + " \"echo $((1))\"");

		ProcessBuilder p = new ProcessBuilder(commands);
		Process p2 = p.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p2.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line1 = null;
		while ( (line1 = reader.readLine()) != null) {
			builder.append(line1);
		}
		String result = builder.toString();

		if(result.equalsIgnoreCase("1"))
		{
			activePc = true;
		}
	}

	/**
	 * Returns the state of the pc
	 * @return
	 */
	public boolean isActivePc() {
		return activePc;
	}

	/**
	 * Getter of PcName for the specific thread
	 * @return
	 */
	public String getPcName() {
		return pcName;
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			checkPcIsActif();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

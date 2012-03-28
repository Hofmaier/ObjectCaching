package ch.hsr.objectCaching.interfaces;

public class IncrementAction extends Action 
{
	private static final long serialVersionUID = 1L;
	private int balanceResult;
	private float factor;
	private long delay;
	public IncrementAction(long delay, float factor)
	{
		this.factor = factor;
		this.delay = delay;
	}
	@Override
	public void execute(Account account) 
	{
		balanceResult = account.getBalance();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account.setBalance((int)(balanceResult * factor));
	}

}

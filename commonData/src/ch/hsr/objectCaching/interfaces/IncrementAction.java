package ch.hsr.objectCaching.interfaces;

public class IncrementAction extends Action 
{
	private int balanceResult;
	private float factor;
	private long delay;
	public IncrementAction(float factor, long delay)
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

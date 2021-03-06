package ch.hsr.objectCaching.rmiWithCacheClient;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.objectCaching.account.Account;
import ch.hsr.objectCaching.account.AccountService;
import ch.hsr.objectCaching.dto.MethodCall;
import ch.hsr.objectCaching.dto.ReturnValue;

public class TestAccountServiceStub {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAllAccounts() {
		AccountServiceStub service = new AccountServiceStub();
		MessageManagerFake messageManager = new MessageManagerFake();
		ReturnValue returnValue = new ReturnValue();
		Collection<Integer> value = new ArrayList<Integer>();
		Integer objectID = 23;
		value.add(objectID);
		returnValue.setValue(value);
		messageManager.returnValue = returnValue;
		service.setMessageManager(messageManager);
		Collection<Account> accounts = service.getAllAccounts();
		MethodCall methodCall = (MethodCall) messageManager.transferObject;
		assertEquals(AccountService.class.getName(), methodCall.getClassName());
		for(Account account:accounts){
			AccountStub accountStub = (AccountStub) account;
			assertEquals((int)objectID, accountStub.getObjectID());
		}
	}
	
}

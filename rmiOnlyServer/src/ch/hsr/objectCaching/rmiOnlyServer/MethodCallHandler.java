package ch.hsr.objectCaching.rmiOnlyServer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MethodCallHandler extends DefaultHandler {

	private MethodCall methodCall;
	private String currentValue;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("invokeMethod")){
		methodCall = new MethodCall();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equalsIgnoreCase("MethodName")){
			methodCall.setMethodName(currentValue);
		}
		
		if(qName.equalsIgnoreCase("ObjectID")){
			methodCall.setObjectID(currentValue);
		}
	}
	

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentValue = new String(ch, start, length);
	}

	public MethodCall getMethodCall() {
		return methodCall;
	}
	
	
	
	

}

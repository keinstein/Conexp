package conexp.frontend.io.objattrlist;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;

import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.SetRelation;
import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import conexp.frontend.io.ConExpXMLWriter;
import conexp.frontend.io.ContextCreator;


public class ObjectListContextCreator implements ContextCreator, DocumentLoader {
	String separator =";";
	boolean debug = false;
	/** attributes*/
	Hashtable attributes = null;
	/** objects*/
	Hashtable objects= null;

	public ObjectListContextCreator(){
		attributes =new Hashtable();
		objects = new Hashtable();
	}

	public	ObjectListContextCreator(String separator){
		this.separator = separator;
		attributes =new Hashtable();
	}

	public Reader getReaderForFileOrURL(String inputnameOrURL){
	 	Reader reader = null;
	 	URL url = null;
		
		try {
			url= new URL(inputnameOrURL);
			InputStream in = url.openStream();		 
			reader = new InputStreamReader(in);
			return reader;
		} catch (MalformedURLException e) {
			if (debug)	System.out.println(inputnameOrURL+" is not an URL");
		} catch (IOException e) {
			System.out.println( "an exception Occured while trying to read "+ inputnameOrURL);
		}
		
		try {
				reader = new FileReader(inputnameOrURL);
			} catch (FileNotFoundException e) {
			if (debug)	System.out.println("File "+ e + " couldn't be opened");
			}
				
	return reader;	
	}

	/**
	*/
	public Context createContextfromReader(Reader reader){
		Context context = 
			new Context(new SetRelation(0, 0));
		attributes = new Hashtable();
		objects = new Hashtable();
				 	
		BufferedReader buff =new BufferedReader(reader);
		String input;
		int lastobjindex =-1;
		try {boolean attrset=false;
			String separator=":";
			StringTokenizer tokenizer =null;
			int objnumber;
			while ((input = buff.readLine()) != null){
				input.trim();
				input = input.substring(0, input.length()-1);//TODO check whether there is no mistake if there is ; 
				tokenizer = new StringTokenizer(input,separator, false);
				if (tokenizer.hasMoreTokens()) 
								objnumber =addObject(tokenizer.nextToken(),context);
				else {
					System.out.println("ACHTUNGH Line: "+input +" wurde in Account genommen");
					continue;
					}
				String nextToken = tokenizer.nextToken();
				nextToken.trim();
				String delim= " ";
				tokenizer = new StringTokenizer(nextToken, delim);	
				int attrnumber = -1;
				while (tokenizer.hasMoreTokens())	{
					attrnumber = addAttribute(tokenizer.nextToken(delim),context);	
					context.setRelationAt(objnumber, attrnumber, true);
				}
			}
		} catch (IOException e) {
			System.out.println(
			"Exception occured while reading file"+e );
		}
		return context;
	}

	int attrcount = -1;
	int objcount =-1;

	public int addObject(String obj, Context context){
		if (objects.containsKey(obj)) return ((Integer)objects.get(obj)).intValue();
		else  {
			ContextEntity contobj = new ContextEntity (obj, true);
			context.addObject(contobj); objcount ++;
			objects.put(obj, new Integer(objcount));
			return objcount;	
		}
	}

	public int addAttribute(String obj, Context context){
		if (attributes.containsKey(obj)) return ((Integer)attributes.get(obj)).intValue();
		else  {
			ContextEntity contobj = new ContextEntity (obj, false);
			context.addAttribute(contobj); attrcount ++;			
			attributes.put(obj, new Integer(attrcount));
			return attrcount;	
		}
	}


	public void createContext(String inputnameOrURL, String outputnameOrURL){
	 	Reader reader = getReaderForFileOrURL(inputnameOrURL);
		Context	context = createContext(reader);
		ContextDocument contdoc = new ContextDocument(context);
		try {
			(new ConExpXMLWriter()).storeDocument(
					contdoc, 
					new FileWriter(outputnameOrURL));
		} catch (IOException e) {
			System.out.print("An Exception Occured while trying to Store Document");
		}
	}
	
	public void setAttributes(String input, Context context){	
		if ((input.charAt(0) == '%' ) || (input.equals(""))) return ;
		StringTokenizer tokenizer = new StringTokenizer(input,separator);
		String tok;
		int attnum = 0;
		// right now do nothing with the first one
		
		while (tokenizer.hasMoreTokens()){
			tok =tokenizer.nextToken();
			System.out.println(tok + " " + attnum++);
			context.addAttribute(new ContextEntity(tok,false));
		}		
		
	}
 
	public int addCompleteAttribute(Context context, int i ){
		int index = -1;
		ContextEntity attribute;
		if (attributes.contains(new Integer(i)))
			attribute = (ContextEntity) attributes.get(new Integer(i));
		else {
			attribute = context.getAttribute(i);
			attribute = new ContextEntity(attribute.getName(),false);
			context.addAttribute(attribute);
			index = context.getAttributeCount()-1;
			attributes.put(new Integer(i), attribute);
		}
		return index;
	}

	/**
	*/
	public Context createContext(Object obj ){
		try {
			if (obj instanceof Reader) return createContextfromReader((Reader) obj);
			return createContext(new FileReader((String)obj));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		ObjectListContextCreator cc = null;
		if (args.length == 3) cc = new ObjectListContextCreator(args[2]);
		else cc = new ObjectListContextCreator();
		cc.createContext(args[0],args[1]);		
	}
	
	public ContextDocument loadDocument(Reader reader ){
		return new ContextDocument(createContext(reader));
	}
	
	public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorhandler ){
		return new ContextDocument(createContext(reader));
	}
}
package conexp.frontend.io.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.StringTokenizer;

import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.SetRelation;
import conexp.frontend.io.ContextCreator;

/** CVSContextCreator creates a Context or loads a Document from a ContextCreator 
*/
public class CSVContextCreator implements ContextCreator {

	protected String ONE_MARK="1";
	protected String separator =";";

	protected boolean debug = false;
	protected Hashtable attributes = null;
//	TODO possibility to add Object to Context from Reader by setting this to null
//	this would also mean that there is no new Attributes
	protected boolean attrset=false;  

	public CSVContextCreator(){
		attributes =new Hashtable();
	}

	public	CSVContextCreator(String separator){
		this.separator = separator;
		attributes =new Hashtable();
	}

	public Context createContext(Reader reader){
		Context context = 
			new Context(new SetRelation(0, 0));
				 	
		BufferedReader buff =new BufferedReader(reader);
		String input ;
		int lastobjindex =-1;
		try {
			input = buff.readLine();
			while (!attrset) {
				input.trim();
				setAttributes(input,context); attrset = true;
				input = buff.readLine();
			}
			while (input != null) {
				input.trim();	
				  if ( addObjectInfos(input, lastobjindex, context)) lastobjindex++;
				input = buff.readLine();	
			}
		} catch (IOException e) {
			System.out.println(
			"Exception occured while reading file"+e );
		}
		attributes = null;
		return context;
	}

	public Context createContext(Object obj){
		return createContext((Reader)obj);
	}
	
	protected void setAttributes(String input, Context context){	
		if ((input.charAt(0) == '%' ) || (input.equals(""))) return ;
		StringTokenizer tokenizer = new StringTokenizer(input,separator);
		String tok;
		int attnum = 0;
		// right now do nothng with the first one
		
		while (tokenizer.hasMoreTokens()){
			tok =tokenizer.nextToken();
			context.addAttribute(new ContextEntity(tok,false));
		}		
	}
 
	protected boolean addObjectInfos(String input, int lastobjindex,  Context context){
		if ((input.equals(""))||(input.charAt(0) == '%' )) return false;
		StringTokenizer tokenizer = new StringTokenizer(input,separator);
		String tok = tokenizer.nextToken();
		context.addObject(new ContextEntity(tok,true));
		lastobjindex++;
		int attrindex = 0;
		while (tokenizer.hasMoreTokens()){		
			tok = tokenizer.nextToken();
			tok.trim();
			if (tok.equals(ONE_MARK))	
				context.setRelationAt( lastobjindex, attrindex,true);
			attrindex++;
		}		
		return true;
	}
	/**
	 * get the string Value which sets the cross 
	 * @return
	 */
	public String getONE_MARK() {
		return ONE_MARK;
	}

	/**
	 * get the value of the separator  
	 * @return
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * @param string
	 */
	public void setONE_MARK(String string) {
		ONE_MARK = string;
	}

	/**
	 * set the value of the field separator
	 * @param string
	 */
	public void setSeparator(String string) {
		separator = string;
	}

}
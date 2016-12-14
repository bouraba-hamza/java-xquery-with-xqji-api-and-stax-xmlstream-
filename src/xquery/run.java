package xquery;

import java.io.File;

import javax.xml.stream.XMLStreamException;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQResultSequence;

public class run {

	
	public static void main(String[] args) {

		try {

			File xml = new File("C:\\Users\\HP\\Desktop\\xml\\file.xml");

			String expression = "declare variable $doc external;" 
			                  + "for $c in $doc/livres/livre "
					          + "return <result>{$c/titre}</result>";
			
			XQueryReader xquery = new XQueryReader(expression, xml);

			xquery.execute();

			xquery.read();
			
			XQResultSequence xq = xquery.getResult(); 
			
			xquery.close();
			

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

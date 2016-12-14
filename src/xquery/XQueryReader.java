package xquery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.apache.xmlbeans.impl.piccolo.xml.XMLInputReader;

import oracle.xml.xquery.OXQDataSource;
import oracle.xml.xquery.util.FileUtils;

public class XQueryReader {

	private XQDataSource datasource;

	private XQConnection connection;

	private XQPreparedExpression expression;

	private InputStream inpt;

	private XQResultSequence result;

	private XMLStreamReader XMLReader; // Stax

	public XQueryReader(String expression, File file) throws XQException, FileNotFoundException {

		this.datasource = new OXQDataSource(); 

		this.connection = datasource.getConnection();

		this.expression = connection.prepareExpression(expression);

		this.inpt = new FileInputStream(file);

		//this.expression.bindDocument(new QName("doc"), inpt, null, null);

	}

	public void execute() throws Exception {

		this.result = this.expression.executeQuery();

	}

	public void read() throws XMLStreamException, XQException {

		while (result.next()) {

			// XQItemType type = result.getItemType();

			this.XMLReader = result.getItemAsStream();

			while (XMLReader.hasNext()) {
				int event = XMLReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					System.out.println("----Element : " + XMLReader.getLocalName());
				}

				else if (event == XMLStreamConstants.CHARACTERS) {

					System.out.println("--Value : " + XMLReader.getText());
				}

				else if (event == XMLStreamConstants.END_ELEMENT) {

					System.out.println("----END : " + XMLReader.getLocalName());
				}

			}

		}
	}

	public void close() throws XQException, IOException {

		this.connection.close();

		this.inpt.close();

		this.expression.close();

	}

	public XQResultSequence getResult() {

		return this.result;
	}

}

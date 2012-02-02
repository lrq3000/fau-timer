package output;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import reportingTool.Measurement;

/**
 * This class creates the xml file of the measurement.
 * 
 * @author  Isabell Schmitt
 */
public class WriteXML extends Write {

	public void writeFile() {

		String sep = File.separator;

		// create output stream.
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(Measurement.getInstance().getOutputFile() + sep + "measurement.xml");

			// create XML encoder.
			XMLEncoder xenc = new XMLEncoder(fos);

			xenc.setPersistenceDelegate(Measurement.class, new MeasurementPersistenceDelegate());

			// write object
			xenc.writeObject(Measurement.getInstance());
			xenc.flush();
			xenc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

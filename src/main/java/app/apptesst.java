package app;

import java.util.Locale;

import imnet.ft.indexing.IndexBuilder.IndexSchema;
import imnet.ft.sid.schema.FrenchSchema;

public class apptesst {

	public static void main(String[] args) {
	
			IndexSchema s = new FrenchSchema().initFrenchSchema();
			System.out.println(s.indexDefaultInit().bytes().utf8ToString());
	}

}

package cz.upol.fapapp.fa.mains;

import java.util.List;

import cz.upol.fapapp.core.misc.AppsMainsTools;
import cz.upol.fapapp.fa.press.DataPressureDataset;
import cz.upol.fapapp.fa.press.DataPressurePerformer;
import cz.upol.fapapp.fa.press.TIMFDPDatasetParser;
import cz.upol.fapapp.fa.press.TIMFDPDatesetComposer;

public class DataPressureApp {


	public static void main(String[] args) {
//		args = new String[] {
//				"--tnorm", "product", //
//			"test-data/press/iris-setosa-1.timf", //
//				"test-data/press/output.timf" //
//		};
		
		List<String> argsList = AppsMainsTools.checkArgs(args, 2, 2, () -> printHelp());
		
		String infileName = argsList.get(0);
		String outfileName = argsList.get(1);
		
		run(infileName, outfileName);	
	}

	private static void run(String infileName, String outfileName) {
		DataPressureDataset dataset = AppsMainsTools.runParser(infileName, new TIMFDPDatasetParser());
		if (dataset == null) {
			return;
		}
		
		DataPressurePerformer performer = new DataPressurePerformer();
		performer.run(dataset);
		
		AppsMainsTools.runComposer(outfileName, dataset, new TIMFDPDatesetComposer());	
	}

	private static void printHelp() {
		System.out.println("DataPressureApp");
		System.out.println("Usage: DataPressureApp INFILE.TIMF OUTFILE.TIMF");
	}

}

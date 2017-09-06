package cz.upol.fapapp.cfa.comp;

import cz.upol.fapapp.cfa.conf.CFAConfTIMComposer;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;
import cz.upol.fapapp.core.timfile.TIMObjectParserComposerTools;

@Deprecated
public class CFACompTIMComposer extends TIMObjectComposer<CFAComputation> {

	private final CFAConfTIMComposer confComp;
	
	public CFACompTIMComposer() {
		super(CFACompTIMParser.TYPE);
		
		confComp = new CFAConfTIMComposer();
	}

	@Override
	protected void process(CFAComputation computation, TIMFileData data) {
		processConfig(computation.getConfig(), data);
		processGeneration(computation.getGeneration(), data);
	}

	/**************************************************************************/

	private void processConfig(CFAConfiguration config, TIMFileData data) {
		confComp.process(config, data);
	}

	private void processGeneration(int generation, TIMFileData data) {
		LineElements line = TIMObjectParserComposerTools.intsToLine(generation);
		data.add("generation", line);
	}
}

package cz.upol.feda.lingvar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import cz.upol.fapapp.core.misc.Logger;
import cz.upol.fapapp.core.timfile.LineElements;
import cz.upol.fapapp.core.timfile.TIMFileData;
import cz.upol.fapapp.core.timfile.TIMObjectComposer;

/**
 * {@link TIMObjectComposer} for {@link Set} of {@link LingvisticVariable}. Sett
 * {@link LingVarsTIMParser} for format.
 * 
 * @author martin
 *
 */
public class LingVarsTIMComposer extends TIMObjectComposer<Set<LingvisticVariable>> {

	private String itemName;

	public LingVarsTIMComposer(String type, String itemName) {
		super(type);

		this.itemName = itemName;
	}

	@Override
	public void process(Set<LingvisticVariable> vars, TIMFileData data) {
		for (LingvisticVariable var : vars) {
			process(var, data);
		}
	}

	private void process(LingvisticVariable var, TIMFileData data) {
		for (BaseLingVarLabel label : var.getLabels()) {
			process(var, label, data);
		}
	}

	private void process(LingvisticVariable var, BaseLingVarLabel label, TIMFileData data) {

		LineElements line;
		if (label instanceof LinearLingVarLabel) {
			LinearLingVarLabel linear = (LinearLingVarLabel) label;

			line = linearToLine(var, label, data, linear);
		} else if (label instanceof UnaryVarLabel) {
			line = unaryToLine(var, label, data);
		} else {
			throw new IllegalArgumentException("Unknown label type " + label.getClass().getName());
		}

		data.add(itemName, line);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private LineElements linearToLine(LingvisticVariable var, BaseLingVarLabel label, TIMFileData data,
			LinearLingVarLabel linear) {
		LineElements line;
		String startStr = Double.toString(linear.getActivationStart().getValue());
		String topStr = Double.toString(linear.getActivationTop().getValue());
		String decreaseStr = Double.toString(linear.getActivationDecrease().getValue());
		String finishStr = Double.toString(linear.getActivationFinish().getValue());

		line = toItem(var, label, data, "linear", startStr, topStr, decreaseStr, finishStr);
		return line;
	}

	private LineElements unaryToLine(LingvisticVariable var, BaseLingVarLabel label, TIMFileData data) {
		LineElements line;
		line = toItem(var, label, data, "unary");
		return line;
	}

	private LineElements toItem(LingvisticVariable var, BaseLingVarLabel label, TIMFileData data, String... spec) {
		String varName = var.getName();
		String labelName = label.getLabel();

		if (varName.contains(" ")) {
			Logger.get().warning("LingVar name contains space: " + varName + ", won't be parseable");
		}
		if (labelName.contains(" ")) {
			Logger.get().warning("LingVarLabel name contains space: " + labelName + ", won't be parseable");
		}

		List<String> list = new ArrayList<>();
		list.add(varName);
		list.add("is");
		list.add(labelName);
		list.add("if");
		list.addAll(Arrays.asList(spec));

		LineElements line = new LineElements(list);
		return line;
	}

}

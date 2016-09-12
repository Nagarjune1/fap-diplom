package cz.upol.jfa.viewer.painting;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.upol.automaton.automata.ingredients.HasDelta;
import cz.upol.automaton.fuzzyLogic.Degree;
import cz.upol.automaton.misc.State;
import cz.upol.automaton.misc.edges.AbstractEdge;
import cz.upol.jfa.automata.AutomatonDesc;
import cz.upol.jfa.automata.BaseAutomatonToGUI;
import cz.upol.jfa.config.Configuration;
import cz.upol.jfa.viewer.Position;
import cz.upol.jfa.viewer.colors.AbstractColorsDirectory;

public class ViewerPainter {
	private final DrawingUtilities utils;
	private final AbstractColorsDirectory colors;

	private final Configuration config = Configuration.get();

	private final BaseAutomatonToGUI automaton;

	public ViewerPainter(BaseAutomatonToGUI automaton,
			AbstractColorsDirectory colors) {

		this.automaton = automaton;
		this.utils = new DrawingUtilities();
		this.colors = colors;
	}

	/**
	 * Vykreslí komponentu JFA, kterou dostal v konstruktoru.
	 * 
	 * @param graphics
	 */
	public void paintJFA(Graphics2D graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		paintBackground(graphics);

		Set<AbstractEdge<?>> edges = ((HasDelta) automaton).getEdges();

		for (AbstractEdge<?> edge : edges) {
			if (edge.hasSomeTransition()) {
				paintTransitionEdge(graphics, edge);
			}
		}

		Map<State, Position> states = automaton.getProvider()
				.getStatesPositions();

		for (Entry<State, Position> stateEntry : states.entrySet()) {
			paintState(graphics, stateEntry);
		}
	}

	/**
	 * Vykreslí pozadí komponenty.
	 * 
	 * @param graphics
	 */
	private void paintBackground(Graphics2D graphics) {
		graphics.setColor(colors.getBackground());
		Dimension size = automaton.getProvider().getSize().toDimension();
		graphics.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
	}

	/**
	 * Vykreslí stav.
	 * 
	 * @param graphics
	 * @param stateEntry
	 *            stav a jeho pozice
	 */
	private void paintState(Graphics2D graphics,
			Entry<State, Position> stateEntry) {
		State state = stateEntry.getKey();
		Position position = stateEntry.getValue();

		if (AutomatonDesc.hasInitialAndFiniteDegree(automaton)) {
			Degree initialIn = AutomatonDesc.initialDegree(automaton, state);
			Degree finiteIn = AutomatonDesc.finiteDegree(automaton, state);

			paintState(graphics, state, initialIn, finiteIn, position);
		} else if (AutomatonDesc.hasOnlyFiniteDegree(automaton)) {
			boolean isInitial = AutomatonDesc.isInitial(automaton, state);
			Degree finiteIn = AutomatonDesc.finiteDegree(automaton, state);

			paintState(graphics, state, isInitial, finiteIn, position);
		}
	}

	/**
	 * Vykreslí hranu s přechody
	 * 
	 * @param graphics
	 * @param edge
	 */
	private void paintTransitionEdge(Graphics2D graphics, AbstractEdge<?> edge) {

		Position from = automaton.getProvider().findPosition(edge.getFrom());
		Position to = automaton.getProvider().findPosition(edge.getTo());

		String label = edge.getLabel();

		paintTransitionArrow(graphics, label, edge, from, to);
	}

	/**
	 * Vykreslí stav.
	 * 
	 * @param graphics
	 * @param state
	 * @param initialIn
	 * @param finiteIn
	 * @param position
	 */
	private void paintState(Graphics2D graphics, State state, Degree initialIn,
			Degree finiteIn, Position position) {
		ViewerConfig params = config.getViewerParams();

		paintStateCirc(graphics, state, position, params);

		Font originalFont = graphics.getFont();

		Position initialDgrPos = new Position(position.x
				+ params.getStateRadius(), position.y - params.getStateRadius());

		graphics.setFont(originalFont.deriveFont(Font.ITALIC));
		utils.drawLeftAlignedText(graphics, colors.getInitialText(state),
				colors.getLabelBg(), initialDgrPos, "→" + initialIn.toPrint());

		Position finiteDgrPos = new Position(position.x
				+ params.getStateRadius(), position.y + params.getStateRadius());
		graphics.setFont(originalFont.deriveFont(Font.BOLD));
		utils.drawLeftAlignedText(graphics, colors.getFiniteText(state),
				colors.getLabelBg(), finiteDgrPos, finiteIn.toPrint());

		graphics.setFont(originalFont);
	}

	private void paintState(Graphics2D graphics, State state,
			boolean isInitial, Degree finiteIn, Position position) {
		ViewerConfig params = config.getViewerParams();

		paintStateCirc(graphics, state, position, params);

		Font originalFont = graphics.getFont();

		if (isInitial) {
			Position initialDgrPos = new Position(position.x
					+ params.getStateRadius(), position.y
					- params.getStateRadius());
			graphics.setFont(originalFont.deriveFont(Font.ITALIC));
			utils.drawLeftAlignedText(graphics, colors.getInitialText(state),
					colors.getLabelBg(), initialDgrPos, "→");
		}

		Position finiteDgrPos = new Position(position.x
				+ params.getStateRadius(), position.y + params.getStateRadius());
		graphics.setFont(originalFont.deriveFont(Font.BOLD));
		utils.drawLeftAlignedText(graphics, colors.getFiniteText(state),
				colors.getLabelBg(), finiteDgrPos, finiteIn.toPrint());

		graphics.setFont(originalFont);

	}

	private void paintStateCirc(Graphics2D graphics, State state,
			Position position, ViewerConfig params) {

		utils.drawDoubleCircle(graphics, colors.getStateBg(state),
				colors.getStateCircle(state), position, params.getStateRadius()
						- params.getStateMidcircDistance(),
				params.getStateRadius());

		utils.drawCenteredText(graphics, colors.getStateLabel(state), null,
				position, state.getLabel());
	}

	/**
	 * Vykreslí přechod (buď jako úsečku nebo jako smyčku).
	 * 
	 * @param graphics
	 * @param label
	 * @param edge
	 * @param from
	 * @param to
	 */
	private void paintTransitionArrow(Graphics2D graphics, String label,
			AbstractEdge<?> edge, Position from, Position to) {

		ViewerConfig params = config.getViewerParams();

		if (automaton.getProvider().getPositioning().isLoop(edge)) {
			utils.drawLoopWithLabel(graphics, colors.getEdgeArrow(edge),
					colors.getEdgeLabel(edge), colors.getLabelBg(), from,
					params.getArrowPadding(), params.getLoopCenter(),
					params.getLoopRadius(), params.getArrowSize(),
					params.getLoopLabelPading(), label);

		} else {
			utils.drawArrowWithLabel(graphics, colors.getEdgeArrow(edge),
					colors.getEdgeLabel(edge), colors.getLabelBg(), from, to,
					params.getArrowPadding(), params.getArrowSize(),
					params.getArrowLabelPadding(), label);
		}
	}
}
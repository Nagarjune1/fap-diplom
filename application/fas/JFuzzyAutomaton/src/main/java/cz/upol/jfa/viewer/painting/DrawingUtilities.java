package cz.upol.jfa.viewer.painting;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import cz.upol.jfa.viewer.Position;

public class DrawingUtilities {
	private static final GeometricUtilities GEOM = new GeometricUtilities();

	public DrawingUtilities() {
	}

	/**
	 * Vykreslí dvojitý kruh.
	 * 
	 * @param graphics
	 * @param background
	 *            Barva pozadí kruhu
	 * @param foreground
	 *            Barva popředí (kružnic) kruhu
	 * @param center
	 *            Souřadnice středu
	 * @param innerRadius
	 *            Poloměr vnitřní kružnice
	 * @param outerRadius
	 *            Poloměr vnější kružnice
	 */
	public void drawDoubleCircle(Graphics2D graphics, Color background,
			Color foreground, Position center, int innerRadius, int outerRadius) {

		graphics.setColor(background);
		graphics.fillOval(center.getIntX() - outerRadius, center.getIntY()
				- outerRadius, 2 * outerRadius, 2 * outerRadius);

		graphics.setColor(foreground);
		graphics.drawOval(center.getIntX() - outerRadius, center.getIntY()
				- outerRadius, 2 * outerRadius, 2 * outerRadius);
		graphics.drawOval(center.getIntX() - innerRadius, center.getIntY()
				- innerRadius, 2 * innerRadius, 2 * innerRadius);
	}

	/**
	 * Vykreslí šipku s popiskem
	 * 
	 * @param graphics
	 * @param arrowColor
	 *            Barva šipky
	 * @param labelColor
	 *            Barva popisku
	 * @param labelBgOrNull
	 *            Pozadí popisku (pokud má být)
	 * @param from
	 *            Výchozí bod šipky (ocas)
	 * @param to
	 *            Koncový bod šipky (hrot)
	 * @param padding
	 *            vzdálenost začátku a konce šipky od bodů from a to
	 * @param arrowSize
	 *            velikost ostnů šipky
	 * @param labelOffset
	 *            Pozice levého dolního rohu popisku od počátečního bodu (při
	 *            šipce == x+)
	 * @param label
	 *            popisek
	 */
	public void drawArrowWithLabel(Graphics2D graphics, Color arrowColor,
			Color labelColor, Color labelBgOrNull, Position from, Position to,
			int padding, int arrowSize, Position labelOffset, String label) {

		double angle = GEOM.positionsAngle(from, to);
		double length = GEOM.positionsDistance(from, to);

		AffineTransform transform = graphics.getTransform();
		graphics.rotate(angle, from.x, from.y);

		int toX = (int) (from.x + length - padding);
		int toY = from.getIntY();

		Position arrowHeadPos = new Position(toX, toY);

		graphics.setColor(arrowColor);
		graphics.drawLine(from.getIntX() + padding, toY, toX, toY);
		drawArrowHead(graphics, arrowSize, arrowHeadPos);

		Position labelPosition = new Position(from.x + labelOffset.x, toY
				+ labelOffset.y);
		drawMiddleLeftText(graphics, labelColor, labelBgOrNull, labelPosition,
				label);

		graphics.setTransform(transform);
	}

	/**
	 * Vykreslí smyčku (šiku ve tvaru kruhové výseče) s popiskem.
	 * 
	 * @param graphics
	 * @param arrowColor
	 *            Barva šipky
	 * @param labelColor
	 *            Barva popisku
	 * @param labelBg
	 *            Barva pozadí popisku (pokud je)
	 * @param fromTo
	 *            Bod, ke terému se šipka váže.
	 * @param paddingRadius
	 *            Vzálenost od fromTo, kde šipka má končit a začínat
	 * @param loopCenterOffset
	 *            Pozice středu smyčky vzhledem k fromTo
	 * @param loopRadius
	 *            Poloměr smyčky
	 * @param arrowSize
	 *            Pelikost šipky (hrotů)
	 * @param lblPadding
	 *            Pozice popisku vzhledem k fromTo
	 * @param label
	 *            Popisek
	 */
	public void drawLoopWithLabel(Graphics2D graphics, Color arrowColor,
			Color labelColor, Color labelBg, Position fromTo,
			double paddingRadius, Position loopCenterOffset, int loopRadius,
			int arrowSize, Position lblPadding, String label) {

		Position loopCenter = GEOM.move(fromTo, loopCenterOffset);

		double axisAngle = -GEOM.positionsAngle(loopCenter, fromTo);
		double loopEndDivergence = GEOM.angleToIntersection(loopCenter,
				loopRadius, fromTo, paddingRadius);

		double arrowRotationAngle = axisAngle + loopEndDivergence - Math.PI / 2;
		double arrowLengthAngle = 2 * Math.PI - 2 * loopEndDivergence;

		Position arrowHeadPosition = new Position(loopCenter.x, loopCenter.y
				- loopRadius);

		int arcLength = (int) GeometricUtilities.radToDeg(arrowLengthAngle);

		AffineTransform transform = graphics.getTransform();
		graphics.rotate(-arrowRotationAngle, loopCenter.x, loopCenter.y);

		graphics.setColor(arrowColor);

		graphics.drawArc(loopCenter.getIntX() - loopRadius,
				loopCenter.getIntY() - loopRadius, 2 * loopRadius,
				2 * loopRadius, 90, arcLength);
		drawArrowHead(graphics, arrowSize, arrowHeadPosition);

		graphics.setTransform(transform);

		Position labelPosition = GEOM.move(fromTo, lblPadding);
		drawCenteredText(graphics, labelColor, labelBg, labelPosition, label);
	}

	/**
	 * Vykreslí text zarovnaný doleva dolů.
	 * 
	 * @param graphics
	 * @param textColor
	 *            Barva textu
	 * @param bgColorOrNull
	 *            Barva pozadí textu (má-li být)
	 * @param position
	 *            Levý dolní bod textu
	 * @param text
	 */
	public void drawMiddleLeftText(Graphics2D graphics, Color textColor,
			Color bgColorOrNull, Position position, String text) {

		FontMetrics metrics = graphics.getFontMetrics();
		Rectangle2D bounds = metrics.getStringBounds(text, graphics);
		fillRect(graphics, bgColorOrNull, position, bounds);

		int textX = position.getIntX();
		int textY = position.getIntY();

		graphics.setColor(textColor);
		graphics.drawString(text, textX, textY);
	}

	/**
	 * Vykreslí text zarovnaný doleva na střed.
	 * 
	 * @param graphics
	 * @param textColor
	 *            Barva textu
	 * @param bgColorOrNull
	 *            Barva pozadí textu (má-li být)
	 * @param position
	 *            Levý střední bod textu
	 * @param text
	 */
	public void drawLeftAlignedText(Graphics2D graphics, Color textColor,
			Color bgColorOrNull, Position position, String text) {

		FontMetrics metrics = graphics.getFontMetrics();

		int height = (int) metrics.getAscent();

		int textX = position.getIntX();
		int textY = position.getIntY() + height / 2;

		Rectangle2D bounds = metrics.getStringBounds(text, graphics);
		fillRect(graphics, bgColorOrNull, new Position(textX, textY), bounds);

		graphics.setColor(textColor);
		graphics.drawString(text, textX, textY);
	}

	/**
	 * Vykreslí horizontálně i vertikálně vystředěný text na zadané pozici.
	 * 
	 * @param graphics
	 * @param textColor
	 *            Barva textu
	 * @param bgColorOrNull
	 *            Barva pozadí textu (má-li být)
	 * @param position
	 *            Střed textu
	 * @param text
	 */
	public void drawCenteredText(Graphics2D graphics, Color textColor,
			Color bgColorOrNull, Position position, String text) {

		FontMetrics metrics = graphics.getFontMetrics();

		Rectangle2D bounds = metrics.getStringBounds(text, graphics);

		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();

		int textX = position.getIntX() - width / 2;
		int textY = position.getIntY() + height / 2;

		Position rectPosition = new Position(textX, textY);
		fillRect(graphics, bgColorOrNull, rectPosition, bounds);

		graphics.setColor(textColor);
		graphics.drawString(text, textX, textY);

	}

	/**
	 * Vykreslí hroty šipky se špičkou v position.
	 * 
	 * @param graphics
	 * @param arrowSize
	 * @param position
	 */
	private void drawArrowHead(Graphics2D graphics, int arrowSize,
			Position position) {
		graphics.drawLine(position.getIntX() - arrowSize, position.getIntY()
				- arrowSize, position.getIntX(), position.getIntY());
		graphics.drawLine(position.getIntX() - arrowSize, position.getIntY()
				+ arrowSize, position.getIntX(), position.getIntY());
	}

	/**
	 * Pokud je uvedena barva (colorOrNull != null) vykreslí touto barvou
	 * obdélník bounds posunutý do position (v position bude jeho levý dolní
	 * roh).
	 * 
	 * @param graphics
	 * @param colorOrNull
	 * @param position
	 * @param bounds
	 */
	private void fillRect(Graphics2D graphics, Color colorOrNull,
			Position position, Rectangle2D bounds) {

		if (colorOrNull != null) {
			int width = (int) bounds.getWidth();
			int height = (int) bounds.getHeight();
			int x = (int) (position.getIntX() + bounds.getMinX());
			int y = (int) (position.getIntY() + bounds.getMinY());

			graphics.setColor(colorOrNull);
			graphics.fillRect(x, y, width, height);
		}
	}

}
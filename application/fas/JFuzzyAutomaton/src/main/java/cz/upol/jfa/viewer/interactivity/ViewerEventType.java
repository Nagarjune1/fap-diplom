package cz.upol.jfa.viewer.interactivity;

public enum ViewerEventType {
	LMB_CLICK, RMB_CLICK, OTHER_CLICK, LMB_DOUBLECLICK, RMB_DOUBLECLICK, OTHER_DOUBLECLICK, KEYPRESS;

	private ViewerEventType() {
	}

	public boolean isClick() {
		return this.equals(LMB_CLICK) || this.equals(RMB_CLICK)
				|| this.equals(OTHER_CLICK);
	}

	public boolean isDoubleclick() {
		return this.equals(LMB_DOUBLECLICK) || this.equals(RMB_DOUBLECLICK)
				|| this.equals(OTHER_DOUBLECLICK);
	}

	public boolean isLeftButton() {
		return this.equals(LMB_CLICK) || this.equals(LMB_DOUBLECLICK);
	}

	public boolean isRightButton() {
		return this.equals(RMB_CLICK) || this.equals(RMB_DOUBLECLICK);
	}

	public static ViewerEventType get(boolean isLeft, boolean isRight,
			boolean isDoubleclick) {

		if (isLeft && !isDoubleclick) {
			return LMB_CLICK;
		} else if (isRight && !isDoubleclick) {
			return RMB_CLICK;
		} else if (isLeft && isDoubleclick) {
			return LMB_DOUBLECLICK;
		} else if (isRight && isDoubleclick) {
			return RMB_DOUBLECLICK;
		} else if (isDoubleclick) {
			return OTHER_DOUBLECLICK;
		} else {
			return OTHER_CLICK;
		}
	}
}

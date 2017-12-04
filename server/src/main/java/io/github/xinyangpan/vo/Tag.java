package io.github.xinyangpan.vo;

public class Tag {
	private String text;
	private String style;
	
	public Tag(String text, String style) {
		super();
		this.text = text;
		this.style = style;
	}

	@Override
	public String toString() {
		return String.format("Tag [text=%s, style=%s]", text, style);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}

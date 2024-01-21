package cc.newex.dax.boss.report.model.control;

/**
 * @author newex-team
 * @date 2017-03-18
 **/
public class HtmlTextBox extends HtmlFormElement {
    private final String value;

    public HtmlTextBox(final String name, final String text, final String value) {
        super(name, text);
        this.type = "textbox";
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

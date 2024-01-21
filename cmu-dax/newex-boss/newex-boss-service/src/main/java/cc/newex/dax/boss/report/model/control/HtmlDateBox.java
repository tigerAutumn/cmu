package cc.newex.dax.boss.report.model.control;

/**
 * @author newex-team
 * @date 2017-03-18
 **/
public class HtmlDateBox extends HtmlFormElement {
    private final String value;

    public HtmlDateBox(final String name, final String text, final String value) {
        super(name, text);
        this.type = "datebox";
        this.value = value;
        this.dataType = "date";
    }

    public String getValue() {
        return this.value;
    }
}

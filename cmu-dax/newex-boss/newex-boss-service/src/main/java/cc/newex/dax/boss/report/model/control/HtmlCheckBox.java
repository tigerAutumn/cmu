package cc.newex.dax.boss.report.model.control;

/**
 * @author newex-team
 * @date 2017-03-18
 **/
public class HtmlCheckBox extends HtmlFormElement {
    private final String value;
    private boolean checked;

    public HtmlCheckBox(final String name, final String text, final String value) {
        super(name, text);
        this.type = "checkbox";
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(final boolean checked) {
        this.checked = checked;
    }
}

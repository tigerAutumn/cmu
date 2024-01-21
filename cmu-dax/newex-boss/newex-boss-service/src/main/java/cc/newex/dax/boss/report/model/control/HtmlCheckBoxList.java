package cc.newex.dax.boss.report.model.control;

import java.util.List;

/**
 * @author newex-team
 * @date 2017-03-18
 **/
public class HtmlCheckBoxList extends HtmlFormElement {
    private final List<HtmlCheckBox> value;

    public HtmlCheckBoxList(final String name, final String text, final List<HtmlCheckBox> value) {
        super(name, text);
        this.type = "checkboxlist";
        this.value = value;
    }

    public List<HtmlCheckBox> getValue() {
        return this.value;
    }
}

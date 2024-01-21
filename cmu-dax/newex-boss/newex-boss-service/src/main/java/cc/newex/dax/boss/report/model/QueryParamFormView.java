package cc.newex.dax.boss.report.model;

import cc.newex.commons.lang.pair.NameTextPair;
import cc.newex.dax.boss.report.model.control.HtmlFormElement;

import java.util.List;
import java.util.Map;

/**
 * 报表的查询参数表单视图接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface QueryParamFormView {

    List<NameTextPair> getTextList(List<HtmlFormElement> formElements);

    Map<String, String> getTextMap(List<HtmlFormElement> formElements);

    String getFormHtmlText(HtmlFormElement formElement);

    String getFormHtmlText(List<HtmlFormElement> formElements);
}

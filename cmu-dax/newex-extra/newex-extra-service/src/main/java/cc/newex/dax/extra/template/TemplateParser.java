package cc.newex.dax.extra.template;

/**
 * 模板解析引擎，将HTML模板中对应的标签替换成需要显示的数据
 *
 * @author liutiejun
 * @date 2018-08-07
 */
public interface TemplateParser {

    String parse(String locale, String uid);

}

package cc.newex.dax.extra.common.html;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 转换HTML转义字符
 *
 * @author liutiejun
 * @date 2018-08-06
 */
public class HTMLDecoder {

    public static final HashMap<String, String> allEscapeChar = new HashMap<>();

    public static String decode(String htmlSource) {
        if (StringUtils.isBlank(htmlSource)) {
            return null;
        }

        for (final Map.Entry<String, String> entry : allEscapeChar.entrySet()) {
            htmlSource = StringUtils.replace(htmlSource, entry.getKey(), entry.getValue());
        }

        return htmlSource;
    }

    static {
        allEscapeChar.put("&quot;", String.valueOf((char) 34));
        allEscapeChar.put("&amp;", String.valueOf((char) 38));
        allEscapeChar.put("&lt;", String.valueOf((char) 60));
        allEscapeChar.put("&gt;", String.valueOf((char) 62));
        allEscapeChar.put("&nbsp;", String.valueOf((char) 160));
        allEscapeChar.put("&iexcl;", String.valueOf((char) 161));
        allEscapeChar.put("&cent;", String.valueOf((char) 162));
        allEscapeChar.put("&pound;", String.valueOf((char) 163));
        allEscapeChar.put("&curren;", String.valueOf((char) 164));
        allEscapeChar.put("&yen;", String.valueOf((char) 165));
        allEscapeChar.put("&brvbar;", String.valueOf((char) 166));
        allEscapeChar.put("&sect;", String.valueOf((char) 167));
        allEscapeChar.put("&uml;", String.valueOf((char) 168));
        allEscapeChar.put("&copy;", String.valueOf((char) 169));
        allEscapeChar.put("&ordf;", String.valueOf((char) 170));
        allEscapeChar.put("&laquo;", String.valueOf((char) 171));
        allEscapeChar.put("&not;", String.valueOf((char) 172));
        allEscapeChar.put("&shy;", String.valueOf((char) 173));
        allEscapeChar.put("&reg;", String.valueOf((char) 174));
        allEscapeChar.put("&macr;", String.valueOf((char) 175));
        allEscapeChar.put("&deg;", String.valueOf((char) 176));
        allEscapeChar.put("&plusmn;", String.valueOf((char) 177));
        allEscapeChar.put("&sup2;", String.valueOf((char) 178));
        allEscapeChar.put("&sup3;", String.valueOf((char) 179));
        allEscapeChar.put("&acute;", String.valueOf((char) 180));
        allEscapeChar.put("&micro;", String.valueOf((char) 181));
        allEscapeChar.put("&para;", String.valueOf((char) 182));
        allEscapeChar.put("&middot;", String.valueOf((char) 183));
        allEscapeChar.put("&cedil;", String.valueOf((char) 184));
        allEscapeChar.put("&sup1;", String.valueOf((char) 185));
        allEscapeChar.put("&ordm;", String.valueOf((char) 186));
        allEscapeChar.put("&raquo;", String.valueOf((char) 187));
        allEscapeChar.put("&frac14;", String.valueOf((char) 188));
        allEscapeChar.put("&frac12;", String.valueOf((char) 189));
        allEscapeChar.put("&frac34;", String.valueOf((char) 190));
        allEscapeChar.put("&iquest;", String.valueOf((char) 191));
        allEscapeChar.put("&Agrave;", String.valueOf((char) 192));
        allEscapeChar.put("&Aacute;", String.valueOf((char) 193));
        allEscapeChar.put("&circ;", String.valueOf((char) 194));
        allEscapeChar.put("&Atilde;", String.valueOf((char) 195));
        allEscapeChar.put("&Auml", String.valueOf((char) 196));
        allEscapeChar.put("&ring;", String.valueOf((char) 197));
        allEscapeChar.put("&AElig;", String.valueOf((char) 198));
        allEscapeChar.put("&Ccedil;", String.valueOf((char) 199));
        allEscapeChar.put("&Egrave;", String.valueOf((char) 200));
        allEscapeChar.put("&Eacute;", String.valueOf((char) 201));
        allEscapeChar.put("&Ecirc;", String.valueOf((char) 202));
        allEscapeChar.put("&Euml;", String.valueOf((char) 203));
        allEscapeChar.put("&Igrave;", String.valueOf((char) 204));
        allEscapeChar.put("&Iacute;", String.valueOf((char) 205));
        allEscapeChar.put("&Icirc;", String.valueOf((char) 206));
        allEscapeChar.put("&Iuml;", String.valueOf((char) 207));
        allEscapeChar.put("&ETH;", String.valueOf((char) 208));
        allEscapeChar.put("&Ntilde;", String.valueOf((char) 209));
        allEscapeChar.put("&Ograve;", String.valueOf((char) 210));
        allEscapeChar.put("&Oacute;", String.valueOf((char) 211));
        allEscapeChar.put("&Ocirc;", String.valueOf((char) 212));
        allEscapeChar.put("&Otilde;", String.valueOf((char) 213));
        allEscapeChar.put("&Ouml;", String.valueOf((char) 214));
        allEscapeChar.put("&times;", String.valueOf((char) 215));
        allEscapeChar.put("&Oslash;", String.valueOf((char) 216));
        allEscapeChar.put("&Ugrave;", String.valueOf((char) 217));
        allEscapeChar.put("&Uacute;", String.valueOf((char) 218));
        allEscapeChar.put("&Ucirc;", String.valueOf((char) 219));
        allEscapeChar.put("&Uuml;", String.valueOf((char) 220));
        allEscapeChar.put("&Yacute;", String.valueOf((char) 221));
        allEscapeChar.put("&THORN;", String.valueOf((char) 222));
        allEscapeChar.put("&szlig;", String.valueOf((char) 223));
        allEscapeChar.put("&agrave;", String.valueOf((char) 224));
        allEscapeChar.put("&aacute;", String.valueOf((char) 225));
        allEscapeChar.put("&acirc;", String.valueOf((char) 226));
        allEscapeChar.put("&atilde;", String.valueOf((char) 227));
        allEscapeChar.put("&auml;", String.valueOf((char) 228));
        allEscapeChar.put("&aring;", String.valueOf((char) 229));
        allEscapeChar.put("&aelig;", String.valueOf((char) 230));
        allEscapeChar.put("&ccedil;", String.valueOf((char) 231));
        allEscapeChar.put("&egrave;", String.valueOf((char) 232));
        allEscapeChar.put("&eacute;", String.valueOf((char) 233));
        allEscapeChar.put("&ecirc;", String.valueOf((char) 234));
        allEscapeChar.put("&euml;", String.valueOf((char) 235));
        allEscapeChar.put("&igrave;", String.valueOf((char) 236));
        allEscapeChar.put("&iacute;", String.valueOf((char) 237));
        allEscapeChar.put("&icirc;", String.valueOf((char) 238));
        allEscapeChar.put("&iuml;", String.valueOf((char) 239));
        allEscapeChar.put("&ieth;", String.valueOf((char) 240));
        allEscapeChar.put("&ntilde;", String.valueOf((char) 241));
        allEscapeChar.put("&ograve;", String.valueOf((char) 242));
        allEscapeChar.put("&oacute;", String.valueOf((char) 243));
        allEscapeChar.put("&ocirc;", String.valueOf((char) 244));
        allEscapeChar.put("&otilde;", String.valueOf((char) 245));
        allEscapeChar.put("&ouml;", String.valueOf((char) 246));
        allEscapeChar.put("&divide;", String.valueOf((char) 247));
        allEscapeChar.put("&oslash;", String.valueOf((char) 248));
        allEscapeChar.put("&ugrave;", String.valueOf((char) 249));
        allEscapeChar.put("&uacute;", String.valueOf((char) 250));
        allEscapeChar.put("&ucirc;", String.valueOf((char) 251));
        allEscapeChar.put("&uuml;", String.valueOf((char) 252));
        allEscapeChar.put("&yacute;", String.valueOf((char) 253));
        allEscapeChar.put("&thorn;", String.valueOf((char) 254));
        allEscapeChar.put("&yuml;", String.valueOf((char) 255));
    }

}

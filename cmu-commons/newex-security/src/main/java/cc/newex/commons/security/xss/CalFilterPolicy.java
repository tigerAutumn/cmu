package cc.newex.commons.security.xss;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML filtering utility for protecting against XSS (Cross Site Scripting).
 * <p>
 * This code is licensed LGPLv3
 * <p>
 * This code is a Java port of the original work in PHP by Cal Hendersen.
 * http://code.iamcal.com/php/lib_filter/
 * <p>
 * The trickiest part of the translation was handling the differences in regex handling
 * between PHP and Java.  These resources were helpful in the process:
 * <p>
 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * http://us2.php.net/manual/en/reference.pcre.pattern.modifiers.php
 * http://www.regular-expressions.info/modifiers.html
 * <p>
 * A note on naming conventions: instance variables are prefixed with a "v"; global
 * constants are in all caps.
 * <p>
 * Sample use:
 * String input = ...
 * String clean = new HTMLFilter().filter( input );
 * <p>
 * The class is not thread safe. Create a new instance if in doubt.
 * <p>
 * If you find bugs or have suggestions on improvement (especially regarding
 * performance), please contact us.  The latest version of this
 * source, and our contact details, can be found at http://xss-html-filter.sf.net
 *
 * @author Joseph O'Connell
 * @author Cal Hendersen
 * @author Michael Semb Wever
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
public final class CalFilterPolicy implements FilterPolicy {

    /**
     * regex flag union representing /si modifiers in php
     **/
    private static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
    private static final Pattern P_COMMENTS = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
    private static final Pattern P_COMMENT = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
    private static final Pattern P_TAGS = Pattern.compile("<(.*?)>", Pattern.DOTALL);
    private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
    private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
    private static final Pattern P_QUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
    private static final Pattern P_UNQUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
    private static final Pattern P_PROTOCOL = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
    private static final Pattern P_ENTITY = Pattern.compile("&#(\\d+);?");
    private static final Pattern P_ENTITY_UNICODE = Pattern.compile("&#x([0-9a-f]+);?");
    private static final Pattern P_ENCODE = Pattern.compile("%([0-9a-f]{2});?");
    private static final Pattern P_VALID_ENTITIES = Pattern.compile("&([^&;]*)(?=(;|&|$))");
    private static final Pattern P_VALID_QUOTES = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);
    private static final Pattern P_END_ARROW = Pattern.compile("^>");
    private static final Pattern P_BODY_TO_END = Pattern.compile("<([^>]*?)(?=<|$)");
    private static final Pattern P_XML_CONTENT = Pattern.compile("(^|>)([^<]*?)(?=>)");
    private static final Pattern P_STRAY_LEFT_ARROW = Pattern.compile("<([^>]*?)(?=<|$)");
    private static final Pattern P_STRAY_RIGHT_ARROW = Pattern.compile("(^|>)([^<]*?)(?=>)");
    private static final Pattern P_AMP = Pattern.compile("&");
    private static final Pattern P_QUOTE = Pattern.compile("\"");
    private static final Pattern P_LEFT_ARROW = Pattern.compile("<");
    private static final Pattern P_RIGHT_ARROW = Pattern.compile(">");
    private static final Pattern P_BOTH_ARROWS = Pattern.compile("<>");

    /**
     * could grow large... maybe use sesat's ReferenceMap
     **/
    private static final ConcurrentMap<String, Pattern> P_REMOVE_PAIR_BLANKS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Pattern> P_REMOVE_SELF_BLANKS = new ConcurrentHashMap<>();

    /**
     * set of allowed html elements, along with allowed attributes for each element
     **/
    private final Map<String, List<String>> vAllowed;
    /**
     * counts of open tags for each (allowable) html element
     **/
    private final Map<String, Integer> vTagCounts = new HashMap<>();

    /**
     * html elements which must always be self-closing (e.g. "<img />")
     **/
    private final String[] vSelfClosingTags;
    /**
     * html elements which must always have separate opening and closing tags (e.g. "<b></b>")
     **/
    private final String[] vNeedClosingTags;
    /**
     * set of disallowed html elements
     **/
    private final String[] vDisallowed;
    /**
     * attributes which should be checked for valid protocols
     **/
    private final String[] vProtocolAtts;
    /**
     * allowed protocols
     **/
    private final String[] vAllowedProtocols;
    /**
     * tags which should be removed if they contain no content (e.g. "<b></b>" or "<b />")
     **/
    private final String[] vRemoveBlanks;
    /**
     * entities allowed within html markup
     **/
    private final String[] vAllowedEntities;
    /**
     * flag determining whether comments are allowed in input String.
     */
    private final boolean stripComment;
    private final boolean encodeQuotes;
    /**
     * flag determining whether to try to make tags when presented with "unbalanced"
     * angle brackets (e.g. "<b text </b>" becomes "<b> text </b>").  If set to false,
     * unbalanced angle brackets will be html escaped.
     */
    private final boolean alwaysMakeTags;

    /**
     * Default constructor.
     */
    public CalFilterPolicy() {
        this.vAllowed = new HashMap<>();

        final ArrayList<String> aAtts = new ArrayList<>();
        aAtts.add("href");
        aAtts.add("target");
        this.vAllowed.put("a", aAtts);

        final ArrayList<String> imgAtts = new ArrayList<>();
        imgAtts.add("src");
        imgAtts.add("width");
        imgAtts.add("height");
        imgAtts.add("alt");
        this.vAllowed.put("img", imgAtts);

        final ArrayList<String> noAtts = new ArrayList<>();
        this.vAllowed.put("b", noAtts);
        this.vAllowed.put("strong", noAtts);
        this.vAllowed.put("i", noAtts);
        this.vAllowed.put("em", noAtts);

        this.vSelfClosingTags = new String[]{"img"};
        this.vNeedClosingTags = new String[]{"a", "b", "strong", "i", "em"};
        this.vDisallowed = new String[]{};
        this.vAllowedProtocols = new String[]{"http", "mailto", "https"}; // no ftp.
        this.vProtocolAtts = new String[]{"src", "href"};
        this.vRemoveBlanks = new String[]{"a", "b", "strong", "i", "em"};
        this.vAllowedEntities = new String[]{"amp", "gt", "lt", "quot"};
        this.stripComment = true;
        this.encodeQuotes = true;
        this.alwaysMakeTags = true;
    }

    /**
     * my versions of some PHP library functions
     *
     * @param decimal a int.
     * @return a {@link java.lang.String} object.
     */
    public static String chr(final int decimal) {
        return String.valueOf((char) decimal);
    }

    /**
     * <p>htmlSpecialChars.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String htmlSpecialChars(final String s) {
        String result = s;
        result = regexReplace(P_AMP, "&amp;", result);
        result = regexReplace(P_QUOTE, "&quot;", result);
        result = regexReplace(P_LEFT_ARROW, "&lt;", result);
        result = regexReplace(P_RIGHT_ARROW, "&gt;", result);
        return result;
    }

    private static String regexReplace(final Pattern pattern, final String replacement, final String s) {
        final Matcher m = pattern.matcher(s);
        return m.replaceAll(replacement);
    }

    private static boolean inArray(final String s, final String[] array) {
        for (final String item : array) {
            if (item != null && item.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private void reset() {
        this.vTagCounts.clear();
    }

    /**
     * {@inheritDoc}
     * <p>
     * given a user submitted input String, filter out any invalid or restricted
     * html.
     */
    @Override
    public String filter(final String input) {
        reset();
        String s = input;

        log.debug("************************************************");
        log.debug("              INPUT: " + input);

        s = escapeComments(s);
        log.debug("     escapeComments: " + s);

        s = balanceHTML(s);
        log.debug("        balanceHTML: " + s);

        s = checkTags(s);
        log.debug("          checkTags: " + s);

        s = processRemoveBlanks(s);
        log.debug("processRemoveBlanks: " + s);

        s = validateEntities(s);
        log.debug("    validateEntites: " + s);

        log.debug("************************************************\n\n");
        return s;
    }

    /**
     * <p>isAlwaysMakeTags.</p>
     *
     * @return a boolean.
     */
    public boolean isAlwaysMakeTags() {
        return this.alwaysMakeTags;
    }

    /**
     * <p>isStripComments.</p>
     *
     * @return a boolean.
     */
    public boolean isStripComments() {
        return this.stripComment;
    }

    private String escapeComments(final String s) {
        final Matcher m = P_COMMENTS.matcher(s);
        final StringBuffer buf = new StringBuffer();
        if (m.find()) {
            //(.*?)
            final String match = m.group(1);
            m.appendReplacement(buf, Matcher.quoteReplacement("<!--" + htmlSpecialChars(match) + "-->"));
        }
        m.appendTail(buf);

        return buf.toString();
    }

    private String balanceHTML(String s) {
        if (this.alwaysMakeTags) {
            //
            // try and form html
            //
            s = regexReplace(P_END_ARROW, "", s);
            s = regexReplace(P_BODY_TO_END, "<$1>", s);
            s = regexReplace(P_XML_CONTENT, "$1<$2", s);

        } else {
            //
            // escape stray brackets
            //
            s = regexReplace(P_STRAY_LEFT_ARROW, "&lt;$1", s);
            s = regexReplace(P_STRAY_RIGHT_ARROW, "$1$2&gt;<", s);

            //
            // the last regexp causes '<>' entities to appear
            // (we need to do a lookahead assertion so that the last bracket can
            // be used in the next pass of the regexp)
            //
            s = regexReplace(P_BOTH_ARROWS, "", s);
        }

        return s;
    }

    private String checkTags(String s) {
        final Matcher m = P_TAGS.matcher(s);

        final StringBuffer buf = new StringBuffer();
        while (m.find()) {
            String replaceStr = m.group(1);
            replaceStr = processTag(replaceStr);
            m.appendReplacement(buf, Matcher.quoteReplacement(replaceStr));
        }
        m.appendTail(buf);

        s = buf.toString();

        // these get tallied in processTag
        // (remember to reset before subsequent calls to filter method)
        for (final String key : this.vTagCounts.keySet()) {
            for (int ii = 0; ii < this.vTagCounts.get(key); ii++) {
                s += "</" + key + ">";
            }
        }

        return s;
    }

    private String processRemoveBlanks(final String s) {
        String result = s;
        for (final String tag : this.vRemoveBlanks) {
            if (!P_REMOVE_PAIR_BLANKS.containsKey(tag)) {
                P_REMOVE_PAIR_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?></" + tag + ">"));
            }
            result = regexReplace(P_REMOVE_PAIR_BLANKS.get(tag), "", result);
            if (!P_REMOVE_SELF_BLANKS.containsKey(tag)) {
                P_REMOVE_SELF_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?/>"));
            }
            result = regexReplace(P_REMOVE_SELF_BLANKS.get(tag), "", result);
        }

        return result;
    }

    private String processTag(final String s) {
        // ending tags
        Matcher m = P_END_TAG.matcher(s);
        if (m.find()) {
            final String name = m.group(1).toLowerCase();
            if (allowed(name)) {
                if (!inArray(name, this.vSelfClosingTags)) {
                    if (this.vTagCounts.containsKey(name)) {
                        this.vTagCounts.put(name, this.vTagCounts.get(name) - 1);
                        return "</" + name + ">";
                    }
                }
            }
        }

        // starting tags
        m = P_START_TAG.matcher(s);
        if (m.find()) {
            final String name = m.group(1).toLowerCase();
            final String body = m.group(2);
            String ending = m.group(3);

            //debug( "in a starting tag, name='" + name + "'; body='" + body + "'; ending='" + ending + "'" );
            if (allowed(name)) {
                final StringBuffer params = new StringBuffer("");

                final Matcher m2 = P_QUOTED_ATTRIBUTES.matcher(body);
                final Matcher m3 = P_UNQUOTED_ATTRIBUTES.matcher(body);
                final List<String> paramNames = new ArrayList<>();
                final List<String> paramValues = new ArrayList<>();
                while (m2.find()) {
                    //([a-z0-9]+)
                    paramNames.add(m2.group(1));
                    //(.*?)
                    paramValues.add(m2.group(3));
                }
                while (m3.find()) {
                    //([a-z0-9]+)
                    paramNames.add(m3.group(1));
                    //([^\"\\s']+)
                    paramValues.add(m3.group(3));
                }

                String paramName, paramValue;
                for (int ii = 0; ii < paramNames.size(); ii++) {
                    paramName = paramNames.get(ii).toLowerCase();
                    paramValue = paramValues.get(ii);

                    if (allowedAttribute(name, paramName)) {
                        if (inArray(paramName, this.vProtocolAtts)) {
                            paramValue = processParamProtocol(paramValue);
                        }
                        //
                        //params += " " + paramName + "=\"" + paramValue + "\"";
                        params.append(" ");
                        params.append(paramName);
                        params.append("=\"");
                        params.append(paramValue);
                        params.append("\"");
                    }
                }

                if (inArray(name, this.vSelfClosingTags)) {
                    ending = " /";
                }

                if (inArray(name, this.vNeedClosingTags)) {
                    ending = "";
                }

                if (ending == null || ending.length() < 1) {
                    if (this.vTagCounts.containsKey(name)) {
                        this.vTagCounts.put(name, this.vTagCounts.get(name) + 1);
                    } else {
                        this.vTagCounts.put(name, 1);
                    }
                } else {
                    ending = " /";
                }
                return "<" + name + params.toString() + ending + ">";
            } else {
                return "";
            }
        }

        // comments
        m = P_COMMENT.matcher(s);
        if (!this.stripComment && m.find()) {
            return "<" + m.group() + ">";
        }

        return "";
    }

    private String processParamProtocol(String s) {
        s = decodeEntities(s);
        final Matcher m = P_PROTOCOL.matcher(s);
        if (m.find()) {
            final String protocol = m.group(1);
            if (!inArray(protocol, this.vAllowedProtocols)) {
                // bad protocol, turn into local anchor link instead
                s = "#" + s.substring(protocol.length() + 1, s.length());
                if (s.startsWith("#//")) {
                    s = "#" + s.substring(3, s.length());
                }
            }
        }

        return s;
    }

    private String decodeEntities(String s) {
        StringBuffer buf = new StringBuffer();

        Matcher m = P_ENTITY.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.decode(match);
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
        }
        m.appendTail(buf);
        s = buf.toString();

        buf = new StringBuffer();
        m = P_ENTITY_UNICODE.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.valueOf(match, 16);
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
        }
        m.appendTail(buf);
        s = buf.toString();

        buf = new StringBuffer();
        m = P_ENCODE.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.valueOf(match, 16);
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
        }
        m.appendTail(buf);
        s = buf.toString();

        s = validateEntities(s);
        return s;
    }

    private String validateEntities(final String s) {
        final StringBuffer buf = new StringBuffer();

        // validate entities throughout the string
        final Matcher m = P_VALID_ENTITIES.matcher(s);
        while (m.find()) {
            //([^&;]*)
            final String one = m.group(1);
            //(?=(;|&|$))
            final String two = m.group(2);
            m.appendReplacement(buf, Matcher.quoteReplacement(checkEntity(one, two)));
        }
        m.appendTail(buf);

        return encodeQuotes(buf.toString());
    }

    private String encodeQuotes(final String s) {
        if (this.encodeQuotes) {
            final StringBuffer buf = new StringBuffer();
            final Matcher m = P_VALID_QUOTES.matcher(s);
            while (m.find()) {
                //(>|^)
                final String one = m.group(1);
                //([^<]+?)
                final String two = m.group(2);
                //(<|$)
                final String three = m.group(3);
                m.appendReplacement(buf, Matcher.quoteReplacement(one + regexReplace(P_QUOTE, "&quot;", two) + three));
            }
            m.appendTail(buf);
            return buf.toString();
        } else {
            return s;
        }
    }

    private String checkEntity(final String preamble, final String term) {
        return ";".equals(term) && isValidEntity(preamble)
                ? '&' + preamble
                : "&amp;" + preamble;
    }

    private boolean isValidEntity(final String entity) {
        return inArray(entity, this.vAllowedEntities);
    }

    private boolean allowed(final String name) {
        return (this.vAllowed.isEmpty() || this.vAllowed.containsKey(name)) && !inArray(name, this.vDisallowed);
    }

    private boolean allowedAttribute(final String name, final String paramName) {
        return allowed(name) && (this.vAllowed.isEmpty() || this.vAllowed.get(name).contains(paramName));
    }
}

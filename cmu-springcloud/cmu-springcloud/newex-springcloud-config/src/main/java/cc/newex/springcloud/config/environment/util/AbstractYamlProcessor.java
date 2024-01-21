/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.newex.springcloud.config.environment.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.CollectionFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Base class for YAML factories.
 *
 * @author Dave Syer
 * @author Juergen Hoeller
 * @author newex-team
 * @see {@link org.springframework.beans.factory.config.YamlProcessor}
 * @since 4.1
 */
public abstract class AbstractYamlProcessor {

    private final Log logger = LogFactory.getLog(this.getClass());

    private ResolutionMethod resolutionMethod = ResolutionMethod.OVERRIDE;

    private Resource[] resources = new Resource[0];

    private List<DocumentMatcher> documentMatchers = Collections.emptyList();

    private boolean matchDefault = true;


    /**
     * A map of document matchers allowing callers to selectively use only
     * some of the documents in a YAML resource. In YAML documents are
     * separated by <code>---<code> lines, and each document is converted
     * to properties before the match is made. E.g.
     * <pre class="code">
     * environment: dev
     * url: http://dev.bar.com
     * name: Developer Setup
     * ---
     * environment: prod
     * url:http://foo.bar.com
     * name: My Cool App
     * </pre>
     * when mapped with
     * <pre class="code">
     * setDocumentMatchers(properties ->
     *     ("prod".equals(properties.getProperty("environment")) ? MatchStatus.FOUND : MatchStatus.NOT_FOUND));
     * </pre>
     * would end up as
     * <pre class="code">
     * environment=prod
     * url=http://foo.bar.com
     * name=My Cool App
     * </pre>
     */
    public void setDocumentMatchers(final DocumentMatcher... matchers) {
        this.documentMatchers = Arrays.asList(matchers);
    }

    /**
     * Flag indicating that a document for which all the
     * {@link #setDocumentMatchers(DocumentMatcher...) document matchers} abstain will
     * nevertheless match. Default is {@code true}.
     */
    public void setMatchDefault(final boolean matchDefault) {
        this.matchDefault = matchDefault;
    }

    /**
     * Method to use for resolving resources. Each resource will be converted to a Map,
     * so this property is used to decide which map entries to keep in the final output
     * from this factory. Default is {@link ResolutionMethod#OVERRIDE}.
     */
    public void setResolutionMethod(final ResolutionMethod resolutionMethod) {
        Assert.notNull(resolutionMethod, "ResolutionMethod must not be null");
        this.resolutionMethod = resolutionMethod;
    }

    /**
     * Set locations of YAML {@link Resource resources} to be loaded.
     *
     * @see ResolutionMethod
     */
    public void setResources(final Resource... resources) {
        this.resources = resources;
    }


    /**
     * Provide an opportunity for subclasses to process the Yaml parsed from the supplied
     * resources. Each resource is parsed in turn and the documents inside checked against
     * the {@link #setDocumentMatchers(DocumentMatcher...) matchers}. If a document
     * matches it is passed into the callback, along with its representation as Properties.
     * Depending on the {@link #setResolutionMethod(ResolutionMethod)} not all of the
     * documents will be parsed.
     *
     * @param callback a callback to delegate to once matching documents are found
     * @see #createYaml()
     */
    protected void process(final MatchCallback callback) {
        final Yaml yaml = this.createYaml();
        for (final Resource resource : this.resources) {
            final boolean found = this.process(callback, yaml, resource);
            if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND && found) {
                return;
            }
        }
    }

    protected void process(final MatchCallback callback, final InputStream in) {
        final Yaml yaml = this.createYaml();
        this.process(callback, yaml, in);
    }

    /**
     * Create the {@link Yaml} instance to use.
     */
    protected Yaml createYaml() {
        return new Yaml(new StrictMapAppenderConstructor());
    }

    protected boolean process(final MatchCallback callback, final Yaml yaml, final Resource resource) {
        int count = 0;
        try {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Loading from YAML: " + resource);
            }
            final Reader reader = new UnicodeReader(resource.getInputStream());
            try {
                for (final Object object : yaml.loadAll(reader)) {
                    if (object != null && this.process(this.asMap(object), callback)) {
                        count++;
                        if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND) {
                            break;
                        }
                    }
                }
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Loaded " + count + " document" + (count > 1 ? "s" : "") +
                            " from YAML resource: " + resource);
                }
            } finally {
                reader.close();
            }
        } catch (final IOException ex) {
            this.handleProcessError(resource, ex);
        }
        return (count > 0);
    }

    protected boolean process(final MatchCallback callback, final Yaml yaml, final InputStream in) {
        int count = 0;
        try {
            final Reader reader = new UnicodeReader(in);
            try {
                for (final Object object : yaml.loadAll(reader)) {
                    if (object != null && this.process(this.asMap(object), callback)) {
                        count++;
                        if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND) {
                            break;
                        }
                    }
                }
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Loaded " + count + " document" + (count > 1 ? "s" : ""));
                }
            } finally {
                reader.close();
            }
        } catch (final IOException ex) {
            this.handleProcessError(null, ex);
        }
        return (count > 0);
    }

    private void handleProcessError(final Resource resource, final IOException ex) {
        if (this.resolutionMethod != ResolutionMethod.FIRST_FOUND &&
                this.resolutionMethod != ResolutionMethod.OVERRIDE_AND_IGNORE) {
            throw new IllegalStateException(ex);
        }
        if (this.logger.isWarnEnabled()) {
            this.logger.warn("Could not load map from " + resource + ": " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(final Object object) {
        // YAML can have numbers as keys
        final Map<String, Object> result = new LinkedHashMap<>();
        if (!(object instanceof Map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        final Map<Object, Object> map = (Map<Object, Object>) object;
        for (final Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = this.asMap(value);
            }
            final Object key = entry.getKey();
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                // It has to be a map key in this case
                result.put("[" + key.toString() + "]", value);
            }
        }
        return result;
    }

    private boolean process(final Map<String, Object> map, final MatchCallback callback) {
        final Properties properties = CollectionFactory.createStringAdaptingProperties();
        properties.putAll(this.getFlattenedMap(map));

        if (this.documentMatchers.isEmpty()) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Merging document (no matchers set): " + map);
            }
            callback.process(properties, map);
            return true;
        }

        MatchStatus result = MatchStatus.ABSTAIN;
        for (final DocumentMatcher matcher : this.documentMatchers) {
            final MatchStatus match = matcher.matches(properties);
            result = MatchStatus.getMostSpecific(match, result);
            if (match == MatchStatus.FOUND) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Matched document with document matcher: " + properties);
                }
                callback.process(properties, map);
                return true;
            }
        }

        if (result == MatchStatus.ABSTAIN && this.matchDefault) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Matched document with default matcher: " + map);
            }
            callback.process(properties, map);
            return true;
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Unmatched document: " + map);
        }
        return false;
    }

    /**
     * Return a flattened version of the given map, recursively following any nested Map
     * or Collection values. Entries from the resulting map retain the same order as the
     * source. When called with the Map from a {@link MatchCallback} the result will
     * contain the same values as the {@link MatchCallback} Properties.
     *
     * @param source the source map
     * @return a flattened map
     * @since 4.1.3
     */
    protected final Map<String, Object> getFlattenedMap(final Map<String, Object> source) {
        final Map<String, Object> result = new LinkedHashMap<>();
        this.buildFlattenedMap(result, source, null);
        return result;
    }

    private void buildFlattenedMap(final Map<String, Object> result, final Map<String, Object> source, final String path) {
        for (final Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            final Object value = entry.getValue();
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                // Need a compound key
                final Map<String, Object> map = (Map<String, Object>) value;
                this.buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                // Need a compound key
                final Collection<Object> collection = (Collection<Object>) value;
                int count = 0;
                for (final Object object : collection) {
                    this.buildFlattenedMap(result,
                            Collections.singletonMap("[" + (count++) + "]", object), key);
                }
            } else {
                result.put(key, (value != null ? value : ""));
            }
        }
    }


    /**
     * Callback interface used to process the YAML parsing results.
     */
    public interface MatchCallback {

        /**
         * Process the given representation of the parsing results.
         *
         * @param properties the properties to process (as a flattened
         *                   representation with indexed keys in case of a collection or map)
         * @param map        the result map (preserving the original value structure
         *                   in the YAML document)
         */
        void process(Properties properties, Map<String, Object> map);
    }


    /**
     * Strategy interface used to test if properties match.
     */
    public interface DocumentMatcher {

        /**
         * Test if the given properties match.
         *
         * @param properties the properties to test
         * @return the status of the match
         */
        MatchStatus matches(Properties properties);
    }


    /**
     * Status returned from {@link DocumentMatcher#matches(Properties)}
     */
    public enum MatchStatus {

        /**
         * A match was found.
         */
        FOUND,

        /**
         * No match was found.
         */
        NOT_FOUND,

        /**
         * The matcher should not be considered.
         */
        ABSTAIN;

        /**
         * Compare two {@link MatchStatus} items, returning the most specific status.
         */
        public static MatchStatus getMostSpecific(final MatchStatus a, final MatchStatus b) {
            return (a.ordinal() < b.ordinal() ? a : b);
        }
    }


    /**
     * Method to use for resolving resources.
     */
    public enum ResolutionMethod {

        /**
         * Replace values from earlier in the list.
         */
        OVERRIDE,

        /**
         * Replace values from earlier in the list, ignoring any failures.
         */
        OVERRIDE_AND_IGNORE,

        /**
         * Take the first resource in the list that exists and use just that.
         */
        FIRST_FOUND
    }


    /**
     * A specialized {@link Constructor} that checks for duplicate keys.
     */
    protected static class StrictMapAppenderConstructor extends Constructor {

        // Declared as public for use in subclasses
        public StrictMapAppenderConstructor() {
            super();
        }

        @Override
        protected Map<Object, Object> constructMapping(final MappingNode node) {
            try {
                return super.constructMapping(node);
            } catch (final IllegalStateException ex) {
                throw new ParserException("while parsing MappingNode",
                        node.getStartMark(), ex.getMessage(), node.getEndMark());
            }
        }

        @Override
        protected Map<Object, Object> createDefaultMap() {
            final Map<Object, Object> delegate = super.createDefaultMap();
            return new AbstractMap<Object, Object>() {
                @Override
                public Object put(final Object key, final Object value) {
                    if (delegate.containsKey(key)) {
                        throw new IllegalStateException("Duplicate key: " + key);
                    }
                    return delegate.put(key, value);
                }

                @Override
                public Set<Entry<Object, Object>> entrySet() {
                    return delegate.entrySet();
                }
            };
        }
    }

}

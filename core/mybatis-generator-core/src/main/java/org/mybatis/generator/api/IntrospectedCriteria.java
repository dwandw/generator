/*
 *  Copyright 2006 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mybatis.generator.api;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaCriteriaGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PropertyHolder;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.rules.ConditionalModelRules;
import org.mybatis.generator.internal.rules.FlatModelRules;
import org.mybatis.generator.internal.rules.HierarchicalModelRules;
import org.mybatis.generator.internal.rules.Rules;

/**
 * Base class for all code generator implementations. This class provides many
 * of the housekeeping methods needed to implement a code generator, with only
 * the actual code generation methods left unimplemented.
 * 
 * @author Jeff Butler
 * 
 */
public abstract class IntrospectedCriteria {

    /**
     * The Enum TargetRuntime.
     */
    public enum TargetRuntime {

        /** The IBATI s2. */
        IBATIS2,
        /** The MYBATI s3. */
        MYBATIS3
    }

    /**
     * The Enum InternalAttribute.
     */
    protected enum InternalAttribute {

        ATTR_BASE_CRITERIA,

        ATTR_CRITERIA,

        ATTR_CRITERION, ATTR_BASE
    }

    /** The context. */
    protected Context context;

    /** The rules. */
    protected Rules rules;

    /** The target runtime. */
    protected TargetRuntime targetRuntime;

    /**
     * Attributes may be used by plugins to capture table related state between
     * the different plugin calls.
     */
    protected Map<String, Object> attributes;

    /**
     * Internal attributes are used to store commonly accessed items by all code
     * generators.
     */
    protected Map<IntrospectedCriteria.InternalAttribute, String> internalAttributes;

    /**
     * Instantiates a new introspected table.
     *
     * @param targetRuntime
     *            the target runtime
     */
    public IntrospectedCriteria(TargetRuntime targetRuntime) {
        super();
        this.targetRuntime = targetRuntime;
        attributes = new HashMap<String, Object>();
        internalAttributes = new HashMap<IntrospectedCriteria.InternalAttribute, String>();
    }

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public Rules getRules() {
        return rules;
    }

    public String getBaseCriteriaType() {
        return internalAttributes.get(InternalAttribute.ATTR_BASE_CRITERIA);
    }

    public String getCriteriaType() {
        return internalAttributes.get(InternalAttribute.ATTR_CRITERIA);
    }

    public String getCriterionType() {
        return internalAttributes.get(InternalAttribute.ATTR_CRITERION);
    }

    public String getBase() {
        return internalAttributes.get(InternalAttribute.ATTR_BASE);
    }

    /**
     * Sets the context.
     *
     * @param context
     *            the new context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Gets the attribute.
     *
     * @param name
     *            the name
     * @return the attribute
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Removes the attribute.
     *
     * @param name
     *            the name
     */
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * Sets the attribute.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    /**
     * Calculate java model package.
     *
     * @return the string
     */
    protected String calculateJavaCriteriaPackage() {
        JavaCriteriaGeneratorConfiguration config = context.getJavaCriteriaGeneratorConfiguration();

        StringBuilder sb = new StringBuilder();
        sb.append(config.getTargetPackage());

        return sb.toString();
    }

    /**
     * Calculate model attributes.
     */
    protected void calculateModelAttributes() {
        String pakkage = calculateJavaCriteriaPackage();

        StringBuilder sb = new StringBuilder();

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append("base");
        sb.append('.');
        sb.append("BaseCriteria");
        setBaseCriteriaType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append("base");
        sb.append('.');
        sb.append("Criteria");
        setCriteriaType(sb.toString());

        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append("base");
        sb.append('.');
        sb.append("Criterion");
        setCriterionType(sb.toString());

    }

    /**
     * This method can be used to initialize the generators before they will be
     * called.
     * 
     * This method is called after all the setX methods, but before
     * getNumberOfSubtasks(), getGeneratedJavaFiles, and getGeneratedXmlFiles.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    public abstract void calculateGenerators(List<String> warnings, ProgressCallback progressCallback);

    /**
     * This method should return a list of generated Java files that are base
     * criteria files
     * 
     * @return the list of generated Java files for this table
     */
    public abstract List<GeneratedJavaFile> getGeneratedJavaFiles();

    /**
     * Denotes whether generated code is targeted for Java version 5.0 or
     * higher.
     * 
     * @return true if the generated code makes use of Java5 features
     */
    public abstract boolean isJava5Targeted();

    /**
     * This method should return the number of progress messages that will be
     * send during the generation phase.
     * 
     * @return the number of progress messages
     */
    public abstract int getGenerationSteps();

    /**
     * This method exists to give plugins the opportunity to replace the
     * calculated rules if necessary.
     *
     * @param rules
     *            the new rules
     */
    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public void setBaseCriteriaType(String exampleType) {
        internalAttributes.put(InternalAttribute.ATTR_BASE_CRITERIA, exampleType);
    }

    public void setCriteriaType(String exampleType) {
        internalAttributes.put(InternalAttribute.ATTR_CRITERIA, exampleType);
    }

    public void setCriterionType(String exampleType) {
        internalAttributes.put(InternalAttribute.ATTR_CRITERION, exampleType);
    }

    public void setBase(String exampleType) {
        internalAttributes.put(InternalAttribute.ATTR_BASE, exampleType);
    }

    /**
     * Gets the target runtime.
     *
     * @return the target runtime
     */
    public TargetRuntime getTargetRuntime() {
        return targetRuntime;
    }

    /**
     * Should return true if an XML generator is required for this table. This
     * method will be called during validation of the configuration, so it
     * should not rely on database introspection. This method simply tells the
     * validator if an XML configuration is normally required for this
     * implementation.
     *
     * @return true, if successful
     */
    public abstract boolean requiresXMLGenerator();

    /**
     * Gets the context.
     *
     * @return the context
     */
    public Context getContext() {
        return context;
    }
}

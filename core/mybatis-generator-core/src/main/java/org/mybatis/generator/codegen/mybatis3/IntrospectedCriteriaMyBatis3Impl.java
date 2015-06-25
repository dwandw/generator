/*
 *  Copyright 2009 The Apache Software Foundation
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
package org.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedCriteria;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.criteria.BaseCriteriaGenerator;
import org.mybatis.generator.codegen.mybatis3.criteria.CriteriaGenerator;
import org.mybatis.generator.codegen.mybatis3.criteria.CriterionGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.ObjectFactory;

/**
 * The Class IntrospectedTableMyBatis3Impl.
 *
 * @author Jeff Butler
 */
public class IntrospectedCriteriaMyBatis3Impl extends IntrospectedCriteria {

    /** The base criteria generators. */
    protected List<AbstractJavaGenerator> javaBaseCriteriaGenerators;

    /**
     * Instantiates a new introspected table my batis3 impl.
     */
    public IntrospectedCriteriaMyBatis3Impl() {
        super(TargetRuntime.MYBATIS3);
        javaBaseCriteriaGenerators = new ArrayList<AbstractJavaGenerator>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.mybatis.generator.api.IntrospectedTable#calculateGenerators(java.
     * util.List, org.mybatis.generator.api.ProgressCallback)
     */
    @Override
    public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {

        calculateBaseCriteriaGenerators(warnings, progressCallback);

    }

    /**
     * Calculate java base criteria generators.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void calculateBaseCriteriaGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new BaseCriteriaGenerator();
        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        javaBaseCriteriaGenerators.add(javaGenerator);

        AbstractJavaGenerator javaGenerator1 = new CriteriaGenerator();
        initializeAbstractGenerator(javaGenerator1, warnings, progressCallback);
        javaBaseCriteriaGenerators.add(javaGenerator1);

        AbstractJavaGenerator javaGenerator2 = new CriterionGenerator();
        initializeAbstractGenerator(javaGenerator2, warnings, progressCallback);
        javaBaseCriteriaGenerators.add(javaGenerator2);
    }

    /**
     * Initialize abstract generator.
     *
     * @param abstractGenerator
     *            the abstract generator
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void initializeAbstractGenerator(AbstractGenerator abstractGenerator, List<String> warnings, ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }

        abstractGenerator.setContext(context);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.IntrospectedTable#getGeneratedJavaFiles()
     */
    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

        for (AbstractJavaGenerator javaGenerator : javaBaseCriteriaGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit, context.getJavaModelGeneratorConfiguration().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        return answer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.IntrospectedTable#getGenerationSteps()
     */
    @Override
    public int getGenerationSteps() {
        return javaBaseCriteriaGenerators.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.IntrospectedTable#isJava5Targeted()
     */
    @Override
    public boolean isJava5Targeted() {
        return true;
    }

    @Override
    public boolean requiresXMLGenerator() {
        // TODO Auto-generated method stub
        return false;
    }

}

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
package org.mybatis.generator.codegen.mybatis3.criteria;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class CriterionGenerator extends AbstractJavaGenerator {

    public CriterionGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.19", "criterion")); //$NON-NLS-1$
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedCriteria.getCriterionType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setFinal(true);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        Field field;
        Method method;

        field = new Field();
        field.setName("condition"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("value"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getObjectInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("secondValue"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getObjectInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("noValue"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("singleValue"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("betweenValue"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("listValue"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        field = new Field();
        field.setName("typeHandler"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getGetter(field));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); //$NON-NLS-1$
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); //$NON-NLS-1$
        method.addBodyLine("super();"); //$NON-NLS-1$
        method.addBodyLine("this.condition = condition;"); //$NON-NLS-1$
        method.addBodyLine("this.typeHandler = null;"); //$NON-NLS-1$
        method.addBodyLine("this.noValue = true;"); //$NON-NLS-1$
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); //$NON-NLS-1$
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "typeHandler")); //$NON-NLS-1$
        method.addBodyLine("super();"); //$NON-NLS-1$
        method.addBodyLine("this.condition = condition;"); //$NON-NLS-1$
        method.addBodyLine("this.value = value;"); //$NON-NLS-1$
        method.addBodyLine("this.typeHandler = typeHandler;"); //$NON-NLS-1$
        method.addBodyLine("if (value instanceof List<?>) {"); //$NON-NLS-1$
        method.addBodyLine("this.listValue = true;"); //$NON-NLS-1$
        method.addBodyLine("} else {"); //$NON-NLS-1$
        method.addBodyLine("this.singleValue = true;"); //$NON-NLS-1$
        method.addBodyLine("}"); //$NON-NLS-1$
        topLevelClass.addMethod(method);
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); //$NON-NLS-1$
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); //$NON-NLS-1$
        method.addBodyLine("this(condition, value, null);"); //$NON-NLS-1$
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); //$NON-NLS-1$
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "secondValue")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "typeHandler")); //$NON-NLS-1$
        method.addBodyLine("super();"); //$NON-NLS-1$
        method.addBodyLine("this.condition = condition;"); //$NON-NLS-1$
        method.addBodyLine("this.value = value;"); //$NON-NLS-1$
        method.addBodyLine("this.secondValue = secondValue;"); //$NON-NLS-1$
        method.addBodyLine("this.typeHandler = typeHandler;"); //$NON-NLS-1$
        method.addBodyLine("this.betweenValue = true;"); //$NON-NLS-1$
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); //$NON-NLS-1$
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "secondValue")); //$NON-NLS-1$
        method.addBodyLine("this(condition, value, secondValue, null);"); //$NON-NLS-1$
        topLevelClass.addMethod(method);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }
}

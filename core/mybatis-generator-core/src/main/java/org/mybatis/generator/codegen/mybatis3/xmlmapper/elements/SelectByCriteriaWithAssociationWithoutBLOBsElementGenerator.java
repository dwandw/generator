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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getValidPropertyName;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Association;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class SelectByCriteriaWithAssociationWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator {

    public SelectByCriteriaWithAssociationWithoutBLOBsElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        if (introspectedTable.getTableConfiguration().getAssociations().size() > 0) {
            String fqjt = introspectedTable.getExampleType();

            XmlElement answer = new XmlElement("select");

            answer.addAttribute(new Attribute("id", introspectedTable.getSelectByCrietriaWithAssociationStatementId()));
            answer.addAttribute(new Attribute("resultMap", introspectedTable.getAssociationResultMapId()));
            answer.addAttribute(new Attribute("parameterType", fqjt));

            context.getCommentGenerator().addComment(answer);

            answer.addElement(new TextElement("select"));
            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", "distinct"));
            ifElement.addElement(new TextElement("distinct"));
            answer.addElement(ifElement);

            StringBuilder sb = new StringBuilder();
            if (stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
                sb.append('\'');
                sb.append(introspectedTable.getSelectByExampleQueryId());
                sb.append("' as QUERYID,");
                answer.addElement(new TextElement(sb.toString()));
            }
            answer.addElement(getBaseColumnListElement());

            sb.setLength(0);
            for (Association association : introspectedTable.getTableConfiguration().getAssociations()) {
                answer.addElement(new TextElement(","));
                XmlElement resultElement = new XmlElement("include");
                resultElement.addAttribute(new Attribute("refid", String.format("%s.%s", association.getIntrospectedTable().getMyBatis3SqlMapNamespace(), association
                        .getIntrospectedTable().getBaseColumnListId())));
                answer.addElement(resultElement);
                if (association.getJoinType() != null) {
                    sb.append(" ").append(association.getJoinType());
                } else {
                    sb.append(" inner");
                }
                sb.append(" join ");
                sb.append(association.getIntrospectedTable().getAliasedFullyQualifiedTableNameAtRuntime());
                sb.append(" on ");
                if (association.getLeftTable() != null) {
                    sb.append(association.getLeftIntrospectedTable().getFullyQualifiedTable().getAlias()).append(".").append(association.getLeftColumn());
                } else {
                    sb.append(introspectedTable.getFullyQualifiedTable().getAlias()).append(".").append(association.getLeftColumn());
                }
                sb.append(" = ");
                sb.append(association.getIntrospectedTable().getFullyQualifiedTable().getAlias()).append(".").append(association.getColumn());
            }

            String associationString = sb.toString();

            sb.setLength(0);
            sb.append("from ");
            sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
            sb.append(associationString);
            answer.addElement((new TextElement(sb.toString())));
            answer.addElement(getCriteriaWhereWithAssociationIncludeElement());

            ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", "orderByClause != null"));
            ifElement.addElement(new TextElement("order by ${orderByClause}"));
            answer.addElement(ifElement);

            ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", "limitClause != null"));
            ifElement.addElement(new TextElement("${limitClause}"));
            answer.addElement(ifElement);

            parentElement.addElement(answer);
        }
    }
}

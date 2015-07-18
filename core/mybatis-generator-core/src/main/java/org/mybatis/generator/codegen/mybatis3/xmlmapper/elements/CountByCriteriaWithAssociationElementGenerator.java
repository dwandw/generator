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

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Association;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class CountByCriteriaWithAssociationElementGenerator extends AbstractXmlElementGenerator {

    public CountByCriteriaWithAssociationElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        if (introspectedTable.getTableConfiguration().getAssociations().size() > 0) {
            XmlElement answer = new XmlElement("select");

            String fqjt = introspectedTable.getExampleType();

            answer.addAttribute(new Attribute("id", introspectedTable.getCountByCriteriaWithAssociationStatementId()));
            answer.addAttribute(new Attribute("parameterType", fqjt));
            answer.addAttribute(new Attribute("resultType", "java.lang.Integer"));

            context.getCommentGenerator().addComment(answer);

            StringBuilder sb = new StringBuilder();
            sb.append("select count(*) from ");
            sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

            for (Association association : introspectedTable.getTableConfiguration().getAssociations()) {
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
            answer.addElement(new TextElement(sb.toString()));
            answer.addElement(getCriteriaWhereWithAssociationIncludeElement());

            parentElement.addElement(answer);
        }
    }
}

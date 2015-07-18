/*
 *  Copyright 2005 The Apache Software Foundation
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
package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class Association {
    private String type;

    private String column;

    private String leftColumn;

    private String ref;

    private String joinType;

    private String leftTable;

    private IntrospectedTable introspectedTable;

    private IntrospectedTable leftIntrospectedTable;

    public Association(String type, String ref, String column, String leftColumn, String joinType, String leftTable) {
        super();
        this.type = type == null ? "association" : type;
        this.column = column;
        this.leftColumn = leftColumn;
        this.ref = ref;
        this.joinType = joinType;
        this.leftTable = leftTable;
    }

    public String getType() {
        return type;
    }

    public String getColumn() {
        return column;
    }

    public String getLeftColumn() {
        return leftColumn;
    }

    public String getRef() {
        return ref;
    }

    public String getJoinType() {
        return joinType;
    }

    public String getLeftTable() {
        return leftTable;
    }

    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public IntrospectedTable getLeftIntrospectedTable() {
        return leftIntrospectedTable;
    }

    public void setLeftIntrospectedTable(IntrospectedTable leftIntrospectedTable) {
        this.leftIntrospectedTable = leftIntrospectedTable;
    }

    /**
     * To xml element.
     *
     * @return the xml element
     */
    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("association");
        xmlElement.addAttribute(new Attribute("column", column));
        xmlElement.addAttribute(new Attribute("leftColumn", leftColumn));
        xmlElement.addAttribute(new Attribute("ref", ref));
        xmlElement.addAttribute(new Attribute("joinType", joinType));
        xmlElement.addAttribute(new Attribute("leftTable", leftTable));

        return xmlElement;
    }

    /**
     * Validate.
     *
     * @param errors
     *            the errors
     * @param tableName
     *            the table name
     */
    public void validate(List<String> errors, String tableName) {
        if (!stringHasValue(column)) {
            errors.add(getString("ValidationError.7", tableName));
        }
        if (!stringHasValue(leftColumn)) {
            errors.add(getString("ValidationError.7", tableName));
        }
        if (!stringHasValue(ref)) {
            errors.add(getString("ValidationError.7", tableName));
        }
    }
}

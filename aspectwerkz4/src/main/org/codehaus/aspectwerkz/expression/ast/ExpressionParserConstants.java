/* Generated By:JJTree&JavaCC: Do not edit this line. ExpressionParserConstants.java */
/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.expression.ast;

public interface ExpressionParserConstants {

    int EOF = 0;
    int COMMA = 3;
    int DOT = 4;
    int WILDCARD = 5;
    int ARRAY = 6;
    int EAGER_WILDCARD = 7;
    int AND = 8;
    int OR = 9;
    int NOT = 10;
    int EXECUTION = 11;
    int CALL = 12;
    int SET = 13;
    int GET = 14;
    int HANDLER = 15;
    int WITHIN = 16;
    int WITHIN_CODE = 17;
    int STATIC_INITIALIZATION = 18;
    int CFLOW = 19;
    int CFLOW_BELOW = 20;
    int ARGS = 21;
    int TARGET = 22;
    int THIS = 23;
    int IF = 24;
    int HAS_METHOD = 25;
    int HAS_FIELD = 26;
    int POINTCUT_REFERENCE_WITH_ARGS = 27;
    int POINTCUT_REFERENCE = 28;
    int CLASS_PRIVATE = 29;
    int CLASS_PROTECTED = 30;
    int CLASS_PUBLIC = 31;
    int CLASS_STATIC = 32;
    int CLASS_ABSTRACT = 33;
    int CLASS_FINAL = 34;
    int CLASS_NOT = 35;
    int CLASS_ATTRIBUTE = 36;
    int CLASS_PATTERN = 37;
    int CLASS_EXACT_IDENTIFIER = 38;
    int CLASS_IDENTIFIER = 39;
    int CLASS_JAVA_NAME_LETTER = 40;
    int CLASS_POINTCUT_END = 41;
    int METHOD_PUBLIC = 42;
    int METHOD_PROTECTED = 43;
    int METHOD_PRIVATE = 44;
    int METHOD_STATIC = 45;
    int METHOD_ABSTRACT = 46;
    int METHOD_FINAL = 47;
    int METHOD_NATIVE = 48;
    int METHOD_SYNCHRONIZED = 49;
    int METHOD_NOT = 50;
    int METHOD_ANNOTATION = 51;
    int METHOD_IDENTIFIER = 52;
    int METHOD_CLASS_PATTERN = 53;
    int METHOD_ARRAY_CLASS_PATTERN = 54;
    int METHOD_ATTRIBUTE_EXACT_IDENTIFIER = 55;
    int PARAMETER_START = 56;
    int PARAMETER_END = 57;
    int METHOD_JAVA_NAME_LETTER = 58;
    int FIELD_PRIVATE = 59;
    int FIELD_PROTECTED = 60;
    int FIELD_PUBLIC = 61;
    int FIELD_STATIC = 62;
    int FIELD_ABSTRACT = 63;
    int FIELD_FINAL = 64;
    int FIELD_TRANSIENT = 65;
    int FIELD_NOT = 66;
    int FIELD_ANNOTATION = 67;
    int FIELD_IDENTIFIER = 68;
    int FIELD_CLASS_PATTERN = 69;
    int FIELD_ARRAY_CLASS_PATTERN = 70;
    int FIELD_ATTRIBUTE_EXACT_IDENTIFIER = 71;
    int FIELD_JAVA_NAME_LETTER = 72;
    int FIELD_POINTCUT_END = 73;
    int PARAMETER_IDENTIFIER = 74;
    int PARAMETER_CLASS_PATTERN = 75;
    int PARAMETER_ARRAY_CLASS_PATTERN = 76;
    int PARAMETER_ANNOTATION = 77;
    int PARAMETER_JAVA_NAME_LETTER = 78;
    int PARAMETER_NOT = 79;
    int ARG_IDENTIFIER = 80;
    int ARG_PATTERN = 81;
    int ARG_ARRAY_PATTERN = 82;
    int ARG_JAVA_NAME_LETTER = 83;
    int ARGS_END = 84;

    int IN_ARGS = 0;
    int PARAMETERS = 1;
    int FIELD = 2;
    int METHOD = 3;
    int CLASS = 4;
    int DEFAULT = 5;

    String[] tokenImage = {
        "<EOF>",
        "\" \"",
        "\"\\t\"",
        "\",\"",
        "\".\"",
        "\"*\"",
        "\"[]\"",
        "<EAGER_WILDCARD>",
        "<AND>",
        "<OR>",
        "\"!\"",
        "\"execution(\"",
        "\"call(\"",
        "\"set(\"",
        "\"get(\"",
        "\"handler(\"",
        "\"within(\"",
        "\"withincode(\"",
        "\"staticinitialization(\"",
        "\"cflow(\"",
        "\"cflowbelow(\"",
        "\"args(\"",
        "\"target(\"",
        "\"this(\"",
        "\"if(\"",
        "\"hasmethod(\"",
        "\"hasfield(\"",
        "<POINTCUT_REFERENCE_WITH_ARGS>",
        "<POINTCUT_REFERENCE>",
        "\"private\"",
        "\"protected\"",
        "\"public\"",
        "\"static\"",
        "\"abstract\"",
        "\"final\"",
        "\"!\"",
        "<CLASS_ATTRIBUTE>",
        "<CLASS_PATTERN>",
        "<CLASS_EXACT_IDENTIFIER>",
        "<CLASS_IDENTIFIER>",
        "<CLASS_JAVA_NAME_LETTER>",
        "\")\"",
        "\"public\"",
        "\"protected\"",
        "\"private\"",
        "\"static\"",
        "\"abstract\"",
        "\"final\"",
        "\"native\"",
        "\"synchronized\"",
        "\"!\"",
        "<METHOD_ANNOTATION>",
        "<METHOD_IDENTIFIER>",
        "<METHOD_CLASS_PATTERN>",
        "<METHOD_ARRAY_CLASS_PATTERN>",
        "<METHOD_ATTRIBUTE_EXACT_IDENTIFIER>",
        "\"(\"",
        "\")\"",
        "<METHOD_JAVA_NAME_LETTER>",
        "\"private\"",
        "\"protected\"",
        "\"public\"",
        "\"static\"",
        "\"abstract\"",
        "\"final\"",
        "\"transient\"",
        "\"!\"",
        "<FIELD_ANNOTATION>",
        "<FIELD_IDENTIFIER>",
        "<FIELD_CLASS_PATTERN>",
        "<FIELD_ARRAY_CLASS_PATTERN>",
        "<FIELD_ATTRIBUTE_EXACT_IDENTIFIER>",
        "<FIELD_JAVA_NAME_LETTER>",
        "\")\"",
        "<PARAMETER_IDENTIFIER>",
        "<PARAMETER_CLASS_PATTERN>",
        "<PARAMETER_ARRAY_CLASS_PATTERN>",
        "<PARAMETER_ANNOTATION>",
        "<PARAMETER_JAVA_NAME_LETTER>",
        "\"!\"",
        "<ARG_IDENTIFIER>",
        "<ARG_PATTERN>",
        "<ARG_ARRAY_PATTERN>",
        "<ARG_JAVA_NAME_LETTER>",
        "\")\"",
        "\"(\"",
        "\")\"",
    };

}

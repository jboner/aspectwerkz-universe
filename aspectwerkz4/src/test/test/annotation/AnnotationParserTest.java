/**************************************************************************************
 * Copyright (c) Jonas Bon?r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package test.annotation;


import org.codehaus.aspectwerkz.annotation.expression.ast.AnnotationParser;
import org.codehaus.aspectwerkz.annotation.expression.AnnotationVisitor;
import org.codehaus.aspectwerkz.annotation.Java5AnnotationInvocationHandler;
import org.codehaus.aspectwerkz.annotation.AnnotationElement;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

/**
 * @author <a href="mailto:alex@gnilux.com">Alexandre Vasseur</a>
 * @author <a href="mailto:the_mindstorm@evolva.ro">Alex Popescu</a>
 */
public class AnnotationParserTest extends TestCase {

	protected static final AnnotationParser s_parser = Helper.getAnnotationParser();

	private Object getElementValue(Object o) {
		AnnotationElement element = (AnnotationElement) o;
		return element.resolveValueHolderFrom(AnnotationParserTest.class.getClassLoader());

	}

	private void check(Map elements, String key, Object expected) {
		Object o = elements.get(key);
		if(o == null) {
			fail("No such element - " + key);
		} else {
			assertEquals(expected, getElementValue(o));
		}
	}

	public void testSimple() {
		try {
			Map elements = new HashMap();
			AnnotationVisitor.parse(elements, "@Simple(val=\"foo\")", Simple.class);
			check(elements, "val", "foo");
			AnnotationVisitor.parse(elements, "@Simple(val=\"foo bar\")", Simple.class);
			AnnotationVisitor.parse(elements, "@Simple (val=\"foo bar\")", Simple.class);
			AnnotationVisitor.parse(elements, "@Simple(val=\"foo bar\"       )", Simple.class);

			elements = new HashMap();
			AnnotationVisitor.parse(elements, "@Simple(s=\"foo\")", Simple.class);
			check(elements, "s", "foo");

			AnnotationVisitor.parse(elements, "@Simple(val=\"hello \\\" alex\")", Simple.class);
			check(elements, "val", "hello \\\" alex");

			AnnotationVisitor.parse(elements, "@Simple(s=\"foo bar\")", Simple.class);
			AnnotationVisitor.parse(elements, "@Simple (s=\"foo bar\")", Simple.class);
			AnnotationVisitor.parse(elements, "@Simple(s=\"foo bar\"       )", Simple.class);

			elements = new HashMap();
			AnnotationVisitor.parse(elements, "@Simple(s=\"foo\", val=\"bar\")", Simple.class);
			check(elements, "s", "foo");
			check(elements, "val", "bar");

			elements = new HashMap();
			AnnotationVisitor.parse(elements, "@Void()", VoidTyped.class);
			assertEquals(0, elements.size());
			AnnotationVisitor.parse(elements, "@Void", VoidTyped.class);
			assertEquals(0, elements.size());
		} catch(Throwable t) {
			fail(t.toString());
		}
	}

	public void testSimpleNested() {
		try {
			Map elements = new HashMap();
			AnnotationVisitor.parse(elements, "@SimpleNested(nested=@Simple(val=\"foo\"))",
					SimpleNested.class);
			Simple nested = (Simple) getElementValue(elements.get("nested"));
			assertEquals("foo", nested.val());

			elements = new HashMap();
			AnnotationVisitor.parse(elements,
					"@SimpleNested(nested=@Simple( s=\"bar\", val=\"foo\"))", SimpleNested.class);
			nested = (Simple) getElementValue(elements.get("nested"));
			assertEquals("bar", nested.s());
			assertEquals("foo", nested.val());

			elements = new HashMap();
			AnnotationVisitor
					.parse(
							elements,
							"@SimpleStringArrayNested(nested=@StringArray(i=42, ss={\"one\", \"two\", \"three\"}))",
							SimpleStringArrayNested.class);
			StringArray sarr = (StringArray) getElementValue(elements.get("nested"));
			assertEquals(42, sarr.i());
			assertEquals("one", sarr.ss()[0]);
			assertEquals("two", sarr.ss()[1]);
			assertEquals("three", sarr.ss()[2]);
		} catch(Throwable t) {
			fail(t.toString());
		}
	}

	public void testComplexNested() {
		try {
			Map elements = new HashMap();
			AnnotationVisitor.parse(elements,
					"@ComplexNested(nesteds={@Simple(val=\"foo\"), @Simple(val=\"bar\")})",
					ComplexNested.class);
			Simple[] nesteds = (Simple[]) getElementValue(elements.get("nesteds"));
			assertEquals("foo", nesteds[0].val());
			assertEquals("bar", nesteds[1].val());

			elements = new HashMap();
			AnnotationVisitor.parse(elements,
					"@ComplexNested(nesteds={@Simple(s=\"bar\", val=\"foo\"), @Simple(val=\"bar\")})",
					ComplexNested.class);
			nesteds = (Simple[]) getElementValue(elements.get("nesteds"));
			assertEquals("foo", nesteds[0].val());
			assertEquals("bar", nesteds[0].s());
			assertEquals("bar", nesteds[1].val());

		} catch(Throwable t) {
			fail(t.toString());
		}
	}

	public void testDefault() {
		try {
			Map elements = new HashMap();
			AnnotationVisitor.parse(elements, "@DefaultString(\"foo\")", DefaultString.class);
			check(elements, "value", "foo");

			elements = new HashMap();
			AnnotationVisitor.parse(elements, "@DefaultInt(3)", DefaultInt.class);
			check(elements, "value", new Integer(3));

			elements = new HashMap();
			AnnotationVisitor.parse(elements, "@SimpleDefaultNested(nested=@DefaultString(\"bar\"))",
					SimpleDefaultNested.class);
			DefaultString ds = (DefaultString) getElementValue(elements.get("nested"));
			assertEquals("bar", ds.value());

			elements = new HashMap();
			AnnotationVisitor.parse(elements, "@SimpleValueDefaultNested(@DefaultString(\"bar\"))",
					SimpleValueDefaultNested.class);
			check(elements, "value", "bar");
		} catch(Throwable t) {
			fail(t.toString());
		}
	}

	// note that for correct long type handling, it is mandatory to have the l or L suffix
	public void testComplex() {
		try {
			Map elements = new HashMap();
			AnnotationVisitor.parse(elements,
					"@Complex(i=3,  ls={1l,2l,6L},  klass=java.lang.String.class)", Complex.class);
			check(elements, "i", new Integer(3));
			long[] ls = new long[] {1L, 2L, 6L};
			long[] lsGet = (long[]) getElementValue(elements.get("ls"));
			for(int i = 0; i < ls.length; i++) {
				assertEquals(ls[i], lsGet[i]);
			}
			// TODO change when support for LazyClass is there
			check(elements, "klass", String.class);
		} catch(Throwable t) {
			fail(t.toString());
		}
	}

	public void testStringArray() {
		try {
			Map elements = new HashMap();
			AnnotationVisitor.parse(elements, "@StringArray(i=3,  ss={\"hello\", \"foo\"})",
					StringArray.class);
			check(elements, "i", new Integer(3));
			String[] ss = new String[] {"hello", "foo"};
			String[] ssGet = (String[]) getElementValue(elements.get("ss"));
			for(int i = 0; i < ss.length; i++) {
				assertEquals(ss[i], ssGet[i]);
			}
		} catch(Throwable t) {
			fail(t.toString());
		}
	}

	public static class Helper extends AnnotationVisitor {
		public Helper(final Map annotationValues, final Class annotationClass) {
			super(annotationValues, annotationClass);
		}

		public static AnnotationParser getAnnotationParser() {
			return PARSER;
		}
	}

	public static interface VoidTyped {
	}

	public static interface Simple {

		public String val();

		public String s();
	}

	public static interface SimpleNested {

		public Simple nested();
	}

	public static interface SimpleDefaultNested {
		public DefaultString nested();
	}

	public static interface SimpleValueDefaultNested {
		public DefaultString value();
	}

	public static interface SimpleStringArrayNested {
		public StringArray nested();
	}

	public static interface ComplexNested {

		public Simple[] nesteds();
	}

	public static interface DefaultString {

		public String value();
	}

	public static interface DefaultInt {

		public int value();
	}

	public static interface Complex {

		public int i();

		public long[] ls();

		public Class klass();

		public Class[] klass2();

		public int field();

	}

	public static interface StringArray {
		public int i();

		public String[] ss();
	}

	public static interface Untyped {
		public String value();
	}
}
package org.codehaus.aspectwerkz.attribdef.definition;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * The signature of a method that is available from the BCEL library uses
 * descriptors as defined in Section 4.3 of the Java Virtual Machine
 * specificaiton.  Javadoc and Java do not use signatures in this same format.
 * This class converts the Javadoc/Java signature format to that used by
 * the JVM spec.
 * To summarize the descriptors
 * <code>
 * A method descriptor represents the parameters that the method takes
 * and the value that it returns:
 *
 * MethodDescriptor:
 *   ( ParameterDescriptor* ) ReturnDescriptor
 *
 * A parameter descriptor represents a parameter passed to a method:
 *
 * ParameterDescriptor:
 *   FieldType
 *
 * A return descriptor represents the type of the value returned from a method.
 * It is a series of characters generated by the grammar:
 *
 * ReturnDescriptor:
 *   FieldType
 *   V
 *
 * The character V indicates that the method returns no value
 * (its return type is void).
 *</code>
 *
 *<code>
 * A field descriptor represents the type of a class, instance, or local
 * variable. It is a series of characters generated by the grammar:
 *
 * FieldDescriptor:
 *   FieldType
 *
 * ComponentType:
 *   FieldType
 *
 * FieldType:
 *   BaseType
 *   ObjectType
 *   ArrayType
 *
 * BaseType:
 *   B
 *   C
 *   D
 *   F
 *   I
 *   J
 *   S
 *   Z
 *
 * ObjectType:
 *   L <classname> ;
 *
 * ArrayType:
 *   [ ComponentType
 *
 * The characters of BaseType, the L and ; of ObjectType, and the [ of
 * ArrayType are all ASCII characters. The <classname> represents a fully
 * qualified class or interface name. For historical reasons it is encoded in
 * internal form (4.2).  The interpretation of the field types is as shown
 * in Table 4.2.
 *
 * BaseType Character  Type       Interpretation
 * ----------------------------------------------
 * B                   byte       signed byte
 * C                   char       Unicode character
 * D                   double     double-precision floating-point value
 * F                   float      single-precision floating-point value
 * I                   int        integer
 * J                   long       long integer
 * L<classname>;       reference  an instance of class <classname>
 * S                   short      signed short
 * Z                   boolean    true or false
 * [                   reference  one array dimension
 *
 *
 *
 * @author <a href="mailto:mpollack@speakeasy.org">Mark Pollack</a>
 */
public class DescriptorUtil {

    private static Map _paramTypeMap = new HashMap();
    private static Map _returnTypeMap = new HashMap();

    static {
        _paramTypeMap.put("byte", "B");
        _paramTypeMap.put("char", "C");
        _paramTypeMap.put("double", "D");
        _paramTypeMap.put("float", "F");
        _paramTypeMap.put("int", "I");
        _paramTypeMap.put("long", "J");
        //todo: make generic...look for 'dots' of package.  that algorithm
        //doesn't handle packageless (default package) classes though..
        //        _paramTypeMap.put("object reference","L<classname>")
        _paramTypeMap.put("java.lang.Object", "Ljava/lang/Object;");
        _paramTypeMap.put("short", "S");
        _paramTypeMap.put("boolean", "Z");
        //todo
        _paramTypeMap.put("array reference", "[");

        _returnTypeMap.put("void", "V");

    }

    /**
     * Converts from the Java/Javadoc method signature the JVM spec format.
     *
     * TODO This class is poorly implemented and the code base no
     * longer depends on it.  Think about removing it.
     *
     * @param javadocSig method signature as returned via Javadoc API.
     * @param javadocReturnType return type as returned via Javadoc API.
     * @return mtehod signature as defined in the JVM spec.
     */
    public static String convert(String javadocSig, String javadocReturnType) {
        //remove the leading and trailing parens
        String javadocSigTrim =
                javadocSig.substring(1, javadocSig.length() - 1);
        StringTokenizer st = new StringTokenizer(javadocSigTrim, ",");
        StringBuffer jvmBuff = new StringBuffer("(");
        while (st.hasMoreTokens()) {
            //remove the leading space character.
            String sigElement = st.nextToken().trim();
            if (_paramTypeMap.containsKey(sigElement)) {
                jvmBuff.append(_paramTypeMap.get(sigElement));
            }
        }
        jvmBuff.append(")");
        if (_returnTypeMap.containsKey(javadocReturnType)) {
            jvmBuff.append(_returnTypeMap.get(javadocReturnType));
        }
        return jvmBuff.toString();
    }

    /**
     * Convert a method signature as definedin the JVM spec to that used
     * in the Javadoc API.  The BCEL library gives method signatures in
     * the JVM format.
     *
     * @param bcelSignature The JVM format of a method signature.
     * @return a <code>String[]</code> containing the method parameter as elements of the array.
     */
    public static String[] convertToJavaFormat(String bcelSignature) {
        int i = 0;
        if (bcelSignature.charAt(i) != '(') {
            return null;
        }

        int j = 0;
        StringBuffer stringbuffer = new StringBuffer();
        for (i++; i < bcelSignature.length();) {
            if (bcelSignature.charAt(i) == ')') {
                i++;
                break; //we are at the end of the signature.
            }
            if (i > 1) {
                //put in spaces to later tokenize on.
                stringbuffer.append(" ");
            }
            i = jvmFormatToJavaFormat(bcelSignature, i, stringbuffer);
            //count number of elements parsed.
            j++;
        }

        //convert to string array.
        String convertedString = stringbuffer.toString();
        String as[] = new String[j];

        int k = 0;
        StringTokenizer st = new StringTokenizer(convertedString);
        while (st.hasMoreTokens()) {
            as[k++] = st.nextToken();
        }
        return as;
    }

    /**
     * The utility method that does the real work of parsing through
     * the JVM formatted string and adding an converted method parameter
     * description to the StringBuffer.
     *
     * @param s The JVM formatted string that is being parsed.
     * @param i The offset into the string being parsed.
     * @param stringbuffer The storage for building the converted method
     * signature.
     * @return new offset location for parsing.
     */
    private static int jvmFormatToJavaFormat(
            String jvmFormat,
            int i,
            StringBuffer stringbuffer) {
        String s1 = "";
        //arrays.
        for (; jvmFormat.charAt(i) == '['; i++) {
            s1 = s1 + "[]";
        }

        startover : switch (jvmFormat.charAt(i)) {
            case 66: // 'B'
                stringbuffer.append("byte");
                break;

            case 67: // 'C'
                stringbuffer.append("char");
                break;

            case 68: // 'D'
                stringbuffer.append("double");
                break;

            case 70: // 'F'
                stringbuffer.append("float");
                break;

            case 73: // 'I'
                stringbuffer.append("int");
                break;

            case 74: // 'J'
                stringbuffer.append("long");
                break;

            case 83: // 'S'
                stringbuffer.append("short");
                break;

            case 90: // 'Z'
                stringbuffer.append("boolean");
                break;

            case 86: // 'V'
                stringbuffer.append("void");
                break;

            case 76: // 'L'
                //special case for objects.
                for (i++; i < jvmFormat.length(); i++) {
                    if (jvmFormat.charAt(i) == '/') {
                        //convert to period
                        stringbuffer.append('.');
                    }
                    else {
                        if (jvmFormat.charAt(i) == ';') {
                            //we reached the end
                            break startover;
                        }
                        //copy contents.
                        stringbuffer.append(jvmFormat.charAt(i));
                    }
                }
                break;

            default :
                return jvmFormat.length();
        }
        stringbuffer = stringbuffer.append(s1);
        return ++i;
    }
}

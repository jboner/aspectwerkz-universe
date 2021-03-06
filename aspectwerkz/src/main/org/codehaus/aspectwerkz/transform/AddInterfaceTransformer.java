/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.transform;

import java.util.List;
import java.util.Iterator;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ClassGen;

import org.codehaus.aspectwerkz.definition.AspectWerkzDefinition;
import org.codehaus.aspectwerkz.definition.DefinitionLoader;
import org.codehaus.aspectwerkz.metadata.ClassMetaData;
import org.codehaus.aspectwerkz.metadata.BcelMetaDataMaker;

/**
 * Adds an interfaces to classes.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public final class AddInterfaceTransformer implements AspectWerkzInterfaceTransformerComponent {

    /**
     * The definitions.
     */
    private final List m_definitions;

    /**
     * Retrieves the weave model.
     */
    public AddInterfaceTransformer() {
        super();
        m_definitions = DefinitionLoader.getDefinitionsForTransformation();
    }

    /**
     * Adds an interfaces to the classes specified.
     *
     * @param context the transformation context
     * @param klass the class
     */
    public void transformInterface(final Context context, final Klass klass) {
        // loop over all the definitions
        for (Iterator it = m_definitions.iterator(); it.hasNext();) {
            AspectWerkzDefinition definition = (AspectWerkzDefinition)it.next();

            definition.loadAspects(context.getLoader());

            final ClassGen cg = klass.getClassGen();
            final ConstantPoolGen cpg = cg.getConstantPool();

            ClassMetaData classMetaData = BcelMetaDataMaker.createClassMetaData(context.getJavaClass(cg));

            if (classFilter(cg, classMetaData, definition)) {
                return;
            }
            if (definition.isAttribDef()) {
                org.codehaus.aspectwerkz.attribdef.transform.IntroductionTransformer.addInterfaceIntroductions(
                        definition, cg, cpg, context, classMetaData
                );
            }
            else if (definition.isXmlDef()) {
                org.codehaus.aspectwerkz.xmldef.transform.IntroductionTransformer.addInterfaceIntroductions(
                        definition, cg, cpg, context
                );
            }
        }
    }

    /**
     * Filters the classes to be transformed.
     *
     * @param cg the class to filter
     * @param classMetaData the class meta-data
     * @param definition the definition
     * @return boolean true if the method should be filtered away
     */
    private boolean classFilter(final ClassGen cg,
                                final ClassMetaData classMetaData,
                                final AspectWerkzDefinition definition) {
        if (cg.isInterface() ||
                TransformationUtil.hasSuperClass(classMetaData, "org.codehaus.aspectwerkz.attribdef.aspect.Aspect") ||
                TransformationUtil.hasSuperClass(classMetaData, "org.codehaus.aspectwerkz.xmldef.advice.AroundAdvice") ||
                TransformationUtil.hasSuperClass(classMetaData, "org.codehaus.aspectwerkz.xmldef.advice.PreAdvice") ||
                TransformationUtil.hasSuperClass(classMetaData, "org.codehaus.aspectwerkz.xmldef.advice.PostAdvice")) {
            return true;
        }
        String className = cg.getClassName();
        if (definition.inExcludePackage(className)) {
            return true;
        }
        if (definition.inIncludePackage(className) &&
                definition.hasIntroductions(classMetaData)) {
            return false;
        }
        return true;
    }

    /**
     * Callback method. Is being called before each transformation.
     */
    public void sessionStart() {
    }

    /**
     * Callback method. Is being called after each transformation.
     */
    public void sessionEnd() {
    }

    /**
     * Callback method. Prints a log/status message at
     * each transformation.
     *
     * @return a log string
     */
    public String verboseMessage() {
        return this.getClass().getName();
    }
}

/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the QPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.attribdef.transform;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

import org.codehaus.aspectwerkz.metadata.ClassMetaData;
import org.codehaus.aspectwerkz.metadata.MethodMetaData;
import org.codehaus.aspectwerkz.transformj.Context;
import org.codehaus.aspectwerkz.transformj.AddImplementationTransformer;
import org.codehaus.aspectwerkz.attribdef.definition.AspectWerkzDefinitionImpl;
import org.codehaus.aspectwerkz.definition.AspectWerkzDefinition;
import org.codehaus.aspectwerkz.attribdef.definition.IntroductionDefinition;
import org.codehaus.aspectwerkz.attribdef.definition.InterfaceIntroductionDefinition;
import org.codehaus.aspectwerkz.exception.WrappedRuntimeException;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Handles the attribdef specific algorithms for adding the introductions.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 * @author <a href="mailto:alex@gnilux.com">Alexandre Vasseur</a>
 */
public class IntroductionTransformerJ {

    /**
     * Adds the interface introductions to the class.
     *
     * @param definition the definition
     * @param cg the class gen
     * @param cpg the constant pool gen
     * @param classMetaData the class meta-data
     */
    public static void addInterfaceIntroductions(final AspectWerkzDefinition definition,
                                                 final CtClass cg,
                                                 final Context context,
                                                 final ClassMetaData classMetaData) {
        AspectWerkzDefinitionImpl def = (AspectWerkzDefinitionImpl)definition;

        CtClass[] interfaces = null;
        try {
            interfaces = cg.getInterfaces();
        } catch (NotFoundException e) {
            throw new WrappedRuntimeException(e);
        }

        boolean isClassAdvised = false;
        List introDefs = def.getInterfaceIntroductions(classMetaData);
        for (Iterator it = introDefs.iterator(); it.hasNext();) {
            InterfaceIntroductionDefinition introductionDef = (InterfaceIntroductionDefinition)it.next();
            for (Iterator iit = introductionDef.getInterfaceClassNames().iterator(); iit.hasNext();) {
                String className = (String) iit.next();

                boolean addInterface = true;
                for (int l = 0; l < interfaces.length; l++) {
                    if (implementsInterface(cg, className)) {
                        addInterface = false;
                        break;
                    }
                }
                if (addInterface && className != null) {
                    try {
                    cg.addInterface(cg.getClassPool().get(className));
                    } catch (NotFoundException e) {
                        throw new WrappedRuntimeException(e);
                    }
                    isClassAdvised = true;
                }
            }
        }

        if (isClassAdvised) {
            context.markAsAdvised();
        }
    }

    /**
     * Adds introductions to the class.
     *
     * @param definition the definition
     * @param context the transformation context
     * @param classMetaData the class meta-data
     * @param cg the class gen
     * @param cpg the constant pool gen
     * @param factory the instruction objectfactory
     * @param transformer the transformer
     */
    public static void addMethodIntroductions(final AspectWerkzDefinition definition,
                                              final Context context,
                                              final ClassMetaData classMetaData,
                                              final CtClass cg,
                                              final AddImplementationTransformer transformer) {
        AspectWerkzDefinitionImpl def = (AspectWerkzDefinitionImpl)definition;

        List introductionDefs = def.getIntroductionDefinitions(classMetaData);
        boolean isClassAdvised = false;
        for (Iterator it = introductionDefs.iterator(); it.hasNext();) {
            IntroductionDefinition introDef = (IntroductionDefinition) it.next();
            int methodIndex = 0;
            for (Iterator mit = introDef.getMethodIntroductions().iterator(); mit.hasNext(); methodIndex++) {
                int mixinIndex = def.getMixinIndexByName(introDef.getName());
                isClassAdvised = true;
                //TODO any use case for a method already implemented ?
                transformer.createProxyMethod(
                        cg,
                        (MethodMetaData)mit.next(),
                        mixinIndex,
                        methodIndex,
                        def.getUuid()
                );
            }
        }

        if (isClassAdvised) {
            context.markAsAdvised();
        }
    }

    /**
     * Checks if a class implements an interface.
     *
     * @param cu ConstantUtf8 constant
     * @return true if the class implements the interface
     */
    private static boolean implementsInterface(final CtClass cu, final String interfaceName) {
        try {
        CtClass[] interfaces = cu.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].getName().equals(interfaceName))
                return true;
        }
        return false;
        } catch (NotFoundException e) {
            throw new WrappedRuntimeException(e);
        }
    }
}
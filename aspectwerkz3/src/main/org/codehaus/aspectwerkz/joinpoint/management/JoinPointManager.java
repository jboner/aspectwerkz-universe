/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.joinpoint.management;

import org.codehaus.aspectwerkz.transform.inlining.JoinPointCompiler;
import org.codehaus.aspectwerkz.transform.inlining.AsmHelper;
import org.codehaus.aspectwerkz.transform.TransformationConstants;
import org.codehaus.aspectwerkz.AdviceInfo;
import org.codehaus.aspectwerkz.DeploymentModel;
import org.codehaus.aspectwerkz.ContextClassLoader;
import org.codehaus.aspectwerkz.definition.SystemDefinitionContainer;
import org.codehaus.aspectwerkz.definition.SystemDefinition;
import org.codehaus.aspectwerkz.definition.AspectDefinition;
import org.codehaus.aspectwerkz.definition.AdviceDefinition;
import org.codehaus.aspectwerkz.util.Strings;
import org.codehaus.aspectwerkz.aspect.management.Pointcut;
import org.codehaus.aspectwerkz.aspect.management.Aspects;
import org.codehaus.aspectwerkz.aspect.AdviceType;
import org.codehaus.aspectwerkz.expression.PointcutType;
import org.codehaus.aspectwerkz.expression.ExpressionContext;
import org.codehaus.aspectwerkz.expression.ExpressionInfo;
import org.codehaus.aspectwerkz.reflect.ClassInfo;
import org.codehaus.aspectwerkz.reflect.ReflectionInfo;
import org.codehaus.aspectwerkz.reflect.impl.java.JavaClassInfo;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

/**
 * Manages the join point compilation, loading and instantiation for the target classes.
 * This implementation relies on the SystemDefinitionContainer.
 *
 * @author <a href="mailto:alex@gnilux.com">Alexandre Vasseur </a>
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r </a>
 */
public class JoinPointManager {

    /**
     * Ensures that the specific joinPoint class for the given target class and joinPoint info is generated. This call
     * is added to the weaved class as a "clinit" block
     *
     * @param joinPointType
     * @param callerClass
     * @param callerMethodName
     * @param callerMethodDesc
     * @param callerMethodModifiers
     * @param calleeClassName
     * @param calleeMemberName
     * @param calleeMemberDesc
     * @param calleeMemberModifiers
     * @param joinPointSequence
     * @param joinPointHash
     * @param joinPointClassName
     */
    public static void loadJoinPoint(final int joinPointType,
                                     final Class callerClass,
                                     final String callerMethodName,
                                     final String callerMethodDesc,
                                     final int callerMethodModifiers,
                                     final String calleeClassName,
                                     final String calleeMemberName,
                                     final String calleeMemberDesc,
                                     final int calleeMemberModifiers,
                                     final int joinPointSequence,
                                     final int joinPointHash,
                                     final String joinPointClassName) {

        Class calleeClass = null;
        try {
            if (calleeClassName != null) {
                calleeClass = callerClass.getClassLoader().loadClass(calleeClassName.replace('/', '.'));
            }
        } catch (ClassNotFoundException calleeNotFound) {
            throw new RuntimeException(
                    "callee class [" + calleeClassName + "] can not be found in class loader [" +
                    callerClass.getClassLoader() +
                    "]"
            );
        }

        // check if the JP is already loaded
        // this can occurs if user packaged its JIT classes, or if we are using multiweaving
        final ClassLoader classLoader = callerClass.getClassLoader();
        boolean generateJoinPoint = false;
        try {
            if (calleeClass == null) {
                throw new RuntimeException("callee class [" + calleeClassName + "] is NULL");
            }
            ContextClassLoader.loadClass(classLoader, joinPointClassName.replace('/', '.'));
        } catch (ClassNotFoundException e) {
            generateJoinPoint = true;
        }
        if (!generateJoinPoint) {
            return;
        }

        //Aspects.initialize(calleeClassLoader);

        ClassInfo calleeClassInfo = JavaClassInfo.getClassInfo(calleeClass);
        ReflectionInfo reflectionInfo = null;

        switch (joinPointType) {
            case JoinPointType.METHOD_EXECUTION:
                reflectionInfo = calleeClassInfo.getMethod(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.METHOD_EXECUTION,
                        PointcutType.EXECUTION,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;

            case JoinPointType.METHOD_CALL:
                reflectionInfo = calleeClassInfo.getMethod(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.METHOD_CALL,
                        PointcutType.CALL,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;
            case JoinPointType.FIELD_GET:
                reflectionInfo = calleeClassInfo.getField(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.FIELD_GET,
                        PointcutType.GET,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;

            case JoinPointType.FIELD_SET:
                reflectionInfo = calleeClassInfo.getField(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.FIELD_SET,
                        PointcutType.SET,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;

            case JoinPointType.CONSTRUCTOR_EXECUTION:
                reflectionInfo = calleeClassInfo.getConstructor(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.CONSTRUCTOR_EXECUTION,
                        PointcutType.EXECUTION,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;

            case JoinPointType.CONSTRUCTOR_CALL:
                reflectionInfo = calleeClassInfo.getConstructor(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.CONSTRUCTOR_CALL,
                        PointcutType.CALL,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;

            case JoinPointType.HANDLER:
                // FIXME wrong reflection info
                reflectionInfo = calleeClassInfo.getMethod(joinPointHash);
                doLoadJoinPoint(
                        joinPointClassName,
                        JoinPointType.HANDLER,
                        PointcutType.HANDLER,
                        callerClass,
                        callerMethodName,
                        callerMethodDesc,
                        callerMethodModifiers,
                        calleeClass,
                        calleeMemberName,
                        calleeMemberDesc,
                        calleeMemberModifiers,
                        joinPointSequence,
                        joinPointHash,
                        reflectionInfo,
                        calleeClassInfo
                );
                break;

            case JoinPointType.STATIC_INITALIZATION:
                throw new UnsupportedOperationException(
                        "join point type handling is not implemented: " + joinPointType
                );
        }
    }

    /**
     * Loads the join point.
     *
     * @param joinPointClassName
     * @param joinPointType
     * @param pointcutType
     * @param callerClass
     * @param callerMethodName
     * @param callerMethodDesc
     * @param callerMethodModifiers
     * @param calleeClass
     * @param calleeMemberName
     * @param calleeMemberDesc
     * @param calleeMemberModifiers
     * @param joinPointSequence
     * @param joinPointHash
     * @param reflectionInfo
     * @param thisClassInfo
     */
    private static void doLoadJoinPoint(final String joinPointClassName,
                                        final int joinPointType,
                                        final PointcutType pointcutType,
                                        final Class callerClass,
                                        final String callerMethodName,
                                        final String callerMethodDesc,
                                        final int callerMethodModifiers,
                                        final Class calleeClass,
                                        final String calleeMemberName,
                                        final String calleeMemberDesc,
                                        final int calleeMemberModifiers,
                                        final int joinPointSequence,
                                        final int joinPointHash,
                                        final ReflectionInfo reflectionInfo,
                                        final ClassInfo thisClassInfo) {

        ClassInfo callerClassInfo = JavaClassInfo.getClassInfo(callerClass);
        ReflectionInfo withinInfo = null;
        // FIXME: refactor getMethod in INFO so that we can apply it on "<init>" and that it delegates to ctor
        // instead of checking things here.
        switch (joinPointType) {
            case JoinPointType.CONSTRUCTOR_EXECUTION:
                withinInfo = callerClassInfo.getConstructor(AsmHelper.calculateConstructorHash(callerMethodDesc));
                break;
            default:
                // TODO - support for withincode <clinit>
                if (TransformationConstants.INIT_METHOD_NAME.equals(callerMethodName)) {
                    withinInfo = callerClassInfo.getConstructor(AsmHelper.calculateConstructorHash(callerMethodDesc));
                } else {
                    withinInfo = callerClassInfo.getMethod(AsmHelper.calculateMethodHash(callerMethodName, callerMethodDesc));
                }
        }

        AdviceInfoStruct adviceInfos = getAdviceInfosForJoinPoint(callerClass.getClassLoader(), pointcutType, reflectionInfo, withinInfo);

        Class clazz = JoinPointCompiler.loadJoinPoint(
                joinPointClassName,
                joinPointType,
                joinPointHash,

                callerClass.getName(),
                callerMethodName,
                callerMethodDesc,
                callerMethodModifiers,

                calleeClass.getName(),
                calleeMemberName,
                calleeMemberDesc,
                calleeMemberModifiers,

                adviceInfos,
                calleeClass.getClassLoader(),
                joinPointSequence
        );
    }

    /**
     * Retrieves the join point metadata.
     *
     * @param type
     * @param reflectInfo
     * @param withinInfo
     */
    public static AdviceInfoStruct getAdviceInfosForJoinPoint(final ClassLoader loader,
                                                                final PointcutType type,
                                                                final ReflectionInfo reflectInfo,
                                                                final ReflectionInfo withinInfo) {

        // FIXME XXX handle cflow

        ExpressionContext exprCtx = new ExpressionContext(type, reflectInfo, withinInfo);

        List beforeAdvices = new ArrayList();
        List aroundAdvices = new ArrayList();
        List afterFinallyAdvices = new ArrayList();
        List afterReturningAdvices = new ArrayList();
        List afterThrowingAdvices = new ArrayList();

        List systemDefinitions = SystemDefinitionContainer.getHierarchicalDefs(loader);
        for (Iterator iterator = systemDefinitions.iterator(); iterator.hasNext();) {
            SystemDefinition systemDefinition = (SystemDefinition) iterator.next();
            Collection aspects = systemDefinition.getAspectDefinitions();
            for (Iterator iterator1 = aspects.iterator(); iterator1.hasNext();) {
                AspectDefinition aspectDefinition = (AspectDefinition) iterator1.next();
                //TODO - do we care about non bounded pointcut ?
                for (Iterator iterator2 = aspectDefinition.getAdviceDefinitions().iterator(); iterator2.hasNext();) {
                    AdviceDefinition adviceDefinition = (AdviceDefinition) iterator2.next();
                    if (adviceDefinition.getExpressionInfo().getExpression().match(exprCtx)) {
                        // compute the target method to advice method arguments map
                        adviceDefinition.getExpressionInfo().getArgsIndexMapper().match(exprCtx);

                        // create a lightweight representation of the bounded advices to pass to the compiler
                        AdviceInfo info = new AdviceInfo(aspectDefinition.getClassName(),
                                                         DeploymentModel.getDeploymentModelAsInt(aspectDefinition.getDeploymentModel()),
                                                         adviceDefinition.getMethod(),
                                                         adviceDefinition.getType(),
                                                         adviceDefinition.getSpecialArgumentType(),
                                                         adviceDefinition.getName()
                        );

                        setMethodArgumentIndexes(adviceDefinition.getExpressionInfo(), exprCtx, info);

                        if (AdviceType.BEFORE.equals(adviceDefinition.getType())) {
                            beforeAdvices.add(info);
                        } else if (AdviceType.AROUND.equals(adviceDefinition.getType())) {
                            aroundAdvices.add(info);
                        } else if (AdviceType.AFTER_FINALLY.equals(adviceDefinition.getType())) {
                            afterFinallyAdvices.add(info);
                        } else if (AdviceType.AFTER_RETURNING.equals(adviceDefinition.getType())) {
                            afterReturningAdvices.add(info);
                        } else if (AdviceType.AFTER_THROWING.equals(adviceDefinition.getType())) {
                            afterThrowingAdvices.add(info);
                        } else if (AdviceType.AFTER.equals(adviceDefinition.getType())) {
                            afterReturningAdvices.add(info);//special case for "after only"
                        }
                    }
                }
            }
        }

        AdviceInfoStruct adviceInfo = new AdviceInfoStruct(
                aroundAdvices,
                beforeAdvices,
                afterFinallyAdvices,
                afterReturningAdvices,
                afterThrowingAdvices
        );
        return adviceInfo;
    }

    /**
     * Get the parameter names from a "method declaration" signature like pc(type a, type2 b) => 0:a, 1:b
     *
     * @param adviceName
     * @return the parameter names
     */
    public static String[] getParameterNames(final String adviceName) {
        int paren = adviceName.indexOf('(');
        List paramNames = new ArrayList();
        if (paren > 0) {
            String params = adviceName.substring(paren + 1, adviceName.lastIndexOf(')')).trim();
            String[] javaParameters = Strings.splitString(params, ",");
            for (int i = 0; i < javaParameters.length; i++) {
                String javaParameter = Strings.replaceSubString(javaParameters[i], "  ", " ").trim();
                String[] paramInfo = Strings.splitString(javaParameter, " ");
                paramNames.add(paramInfo[1]);
            }
        }
        String[] paramNamesArray = new String[paramNames.size()];
        int index = 0;
        for (Iterator it = paramNames.iterator(); it.hasNext(); index++) {
            paramNamesArray[index] = (String) it.next();
        }
        return paramNamesArray;
    }

    /**
     * Sets the method argument indexes.
     *
     * @param expressionInfo
     * @param ctx
     * @param adviceInfo
     */
    private static void setMethodArgumentIndexes(final ExpressionInfo expressionInfo,
                                                 final ExpressionContext ctx,
                                                 final AdviceInfo adviceInfo) {

        // grab the parameters names
        String[] adviceArgNames = getParameterNames(adviceInfo.getName());

        // map them from the ctx info
        int[] adviceToTargetArgs = new int[adviceArgNames.length];
        for (int k = 0; k < adviceArgNames.length; k++) {
            String adviceArgName = adviceArgNames[k];
            int exprArgIndex = expressionInfo.getArgumentIndex(adviceArgName);
            if (exprArgIndex >= 0 && ctx.m_exprIndexToTargetIndex.containsKey(exprArgIndex)) {
                adviceToTargetArgs[k] = ctx.m_exprIndexToTargetIndex.get(exprArgIndex);
            } else {
                adviceToTargetArgs[k] = -1;
            }
        }
        adviceInfo.setMethodToArgIndexes(adviceToTargetArgs);
    }
}
/**************************************************************************************
 * Copyright (c) Jonas Bon?r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package test.thistarget;

import org.codehaus.aspectwerkz.Pointcut;
import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:alex@gnilux.com">Alexandre Vasseur</a>
 */
public class ThisTargetAspect {

    //------------------------- Method execution

    /** @Expression execution(* test.thistarget.*.target()) */
    Pointcut exe_target;

    /** @Expression execution(* test.thistarget.*.targetAbstract()) */
    Pointcut exe_targetAbstract;

    // interface

    /** @Before exe_target && target(t) */
    public void beforeITarget(ITarget t) {
        validate(t, ITarget.class);
        TargetTest.log("before_ITarget");
    }
    /** @Around exe_target && target(t) */
    public Object aroundITarget(JoinPoint jp, ITarget t) throws Throwable {
        validate(t, ITarget.class);
        TargetTest.log("pre_ITarget");
        Object o = jp.proceed();
        TargetTest.log("post_ITarget");
        return o;
    }
    /** @After exe_target && target(t) */
    public void afterITarget(ITarget t) {
        validate(t, ITarget.class);
        TargetTest.log("after_ITarget");
    }

    // interface implementation

    /** @Before exe_target && target(t) && this(callee) */
    public void beforeTargetIWithThis(TargetI t, Object callee) {
        validate(t, TargetI.class);
        validate(callee, TargetI.class);
        TargetTest.log("before_TargetI");
    }
    /** @Around exe_target && target(t) */
    public Object aroundTargetI(JoinPoint jp, TargetI t) throws Throwable {
        validate(t, TargetI.class);
        TargetTest.log("pre_TargetI");
        Object o = jp.proceed();
        TargetTest.log("post_TargetI");
        return o;
    }
    /** @After exe_target && target(t) */
    public void afterTargetI(TargetI t) {
        validate(t, TargetI.class);
        TargetTest.log("after_TargetI");
    }

    // super class

    /** @Before exe_target && target(t) */
    public void beforeSuperTarget(SuperTarget t) {
        validate(t, SuperTarget.class);
        TargetTest.log("before_SuperTarget");
    }
    /** @Around exe_target && target(t) */
    public Object aroundSuperTarget(JoinPoint jp, SuperTarget t) throws Throwable {
        validate(t, SuperTarget.class);
        TargetTest.log("pre_SuperTarget");
        Object o = jp.proceed();
        TargetTest.log("post_SuperTarget");
        return o;
    }
    /** @After exe_target && target(t) */
    public void afterSuperTarget(SuperTarget t) {
        validate(t, SuperTarget.class);
        TargetTest.log("after_SuperTarget");
    }

    // super class abstract method

    /** @Before exe_targetAbstract && target(t) */
    public void beforeSuperTargetA(SuperTarget t) {
        validate(t, SuperTarget.class);
        TargetTest.log("before_SuperTargetA");
    }
    /** @Around exe_targetAbstract && target(t) */
    public Object aroundSuperTargetA(JoinPoint jp, SuperTarget t) throws Throwable {
        validate(t, SuperTarget.class);
        TargetTest.log("pre_SuperTargetA");
        Object o = jp.proceed();
        TargetTest.log("post_SuperTargetA");
        return o;
    }
    /** @After exe_targetAbstract && target(t) */
    public void afterSuperTargetA(SuperTarget t) {
        validate(t, SuperTarget.class);
        TargetTest.log("after_SuperTargetA");
    }

    //------------------------- Ctor call

    /** @Expression this(caller) && call(test.thistarget.*.new()) && withincode(* test.*.*.testConstructorCallTarget(..)) */
    Pointcut cctor_target(TargetTest caller) {return null;}


    // interface

    /** @Before cctor_target(caller) && target(t) */
    public void beforeITarget(ITarget t, Object caller) {
        //t is null - validate(t, ITarget.class);
        validate(caller, TargetTest.class);
        TargetTest.log("before_ITarget");
    }
    /** @Around cctor_target(caller) && target(t) */
    public Object aroundITarget(JoinPoint jp, ITarget t, Object caller) throws Throwable {
        //t is null - validate(t, ITarget.class);
        validate(caller, TargetTest.class);
        TargetTest.log("pre_ITarget");
        Object o = jp.proceed();
        validate(o, ITarget.class);
        validate(t, ITarget.class);
        TargetTest.log("post_ITarget");
        return o;
    }
    /** @After cctor_target(caller) && target(t) */
    public void afterITarget(ITarget t, Object caller) {
        validate(t, ITarget.class);
        validate(caller, TargetTest.class);
        TargetTest.log("after_ITarget");
    }

    // interface implementation

    /** @Before cctor_target(caller) && target(t) */
    public void beforeTargetI(TargetI t, Object caller) {
        // is null - validate(t, TargetI.class);
        validate(caller, TargetTest.class);
        TargetTest.log("before_TargetI");
    }
    /** @Around cctor_target(caller) && target(t) */
    public Object aroundTargetI(JoinPoint jp, TargetI t, Object caller) throws Throwable {
        // is null - validate(t, TargetI.class);
        validate(caller, TargetTest.class);
        TargetTest.log("pre_TargetI");
        Object o = jp.proceed();
        validate(o, TargetI.class);
        validate(t, TargetI.class);
        TargetTest.log("post_TargetI");
        return o;
    }
    /** @After cctor_target(caller) && target(t) */
    public void afterTargetI(TargetI t, Object caller) {
        validate(t, TargetI.class);
        validate(caller, TargetTest.class);
        TargetTest.log("after_TargetI");
    }

    // super class

    /** @Before cctor_target(caller) && target(t) */
    public void beforeSuperTarget(SuperTarget t, Object caller) {
        // is null - validate(t, SuperTarget.class);
        validate(caller, TargetTest.class);
        TargetTest.log("before_SuperTarget");
    }
    /** @Around cctor_target(caller) && target(t) */
    public Object aroundSuperTarget(JoinPoint jp, SuperTarget t, Object caller) throws Throwable {
        // is null - validate(t, SuperTarget.class);
        validate(caller, TargetTest.class);
        TargetTest.log("pre_SuperTarget");
        Object o = jp.proceed();
        validate(o, SuperTarget.class);
        validate(t, SuperTarget.class);
        TargetTest.log("post_SuperTarget");
        return o;
    }
    /** @After cctor_target(caller) && target(t) */
    public void afterSuperTarget(SuperTarget t, Object caller) {
        validate(t, SuperTarget.class);
        validate(caller, TargetTest.class);
        TargetTest.log("after_SuperTarget");
    }











    /**
     * We need to validate the bounded this/target since if the indexing is broken, we may have
     * the joinpoint instance instead etc, and if not used, the VM will not complain.
     *
     * @param t
     * @param checkCast
     */
    private static void validate(Object t, Class checkCast) {
        if (! checkCast.isAssignableFrom(t.getClass())) {
            TestCase.fail("t " + t.getClass().getName() + " is not instance of " + checkCast.getName());
        }
    }
}
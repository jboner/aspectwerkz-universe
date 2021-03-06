/* Generated By:JJTree&JavaCC: Do not edit this line. ExpressionParser.java */
package org.codehaus.aspectwerkz.definition.expression.ast;

import java.io.Reader;
import java.io.StringReader;

public class ExpressionParser /*@bgen(jjtree)*/
    implements ExpressionParserTreeConstants, ExpressionParserConstants
{ /*@bgen(jjtree)*/

    static private int[] jj_la1_0;

    static
    {
        jj_la1_0();
    }

    protected JJTExpressionParserState jjtree = new JJTExpressionParserState();
    public ExpressionParserTokenManager token_source;
    SimpleCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    public boolean lookingAhead = false;
    private boolean jj_semLA;
    private int jj_gen;
    final private int[] jj_la1 = new int[4];
    final private JJCalls[] jj_2_rtns = new JJCalls[1];
    private boolean jj_rescan = false;
    private int jj_gc = 0;
    final private LookaheadSuccess jj_ls = new LookaheadSuccess();
    private java.util.Vector jj_expentries = new java.util.Vector();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;

    public ExpressionParser(java.io.InputStream stream)
    {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new ExpressionParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 4; i++)
        {
            jj_la1[i] = -1;
        }

        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    public ExpressionParser(java.io.Reader stream)
    {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new ExpressionParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 4; i++)
        {
            jj_la1[i] = -1;
        }

        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    public ExpressionParser(ExpressionParserTokenManager tm)
    {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;

        for (int i = 0; i < 4; i++)
        {
            jj_la1[i] = -1;
        }

        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    public SimpleNode parse(Reader reader)
        throws ParseException
    {
        ReInit(reader);

        SimpleNode tree = ExpressionScript();

        return tree;
    }

    public SimpleNode parse(String expression)
        throws ParseException
    {
        return parse(new StringReader(expression));
    }

    //---------------------------------------------------------------------

    /**
     * Syntax entry
     */
    final public SimpleNode ExpressionScript()
        throws ParseException
    {
        /*@bgen(jjtree) ExpressionScript */
        ExpressionScript jjtn000 = new ExpressionScript(this,
                JJTEXPRESSIONSCRIPT);
        boolean jjtc000 = true;

        jjtree.openNodeScope(jjtn000);

        try
        {
            Expression();
            jj_consume_token(0);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;

            if (true)
            {
                return jjtn000;
            }
        }
        catch (Throwable jjte000)
        {
            if (jjtc000)
            {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            }
            else
            {
                jjtree.popNode();
            }

            if (jjte000 instanceof RuntimeException)
            {
                if (true)
                {
                    throw (RuntimeException) jjte000;
                }
            }

            if (jjte000 instanceof ParseException)
            {
                if (true)
                {
                    throw (ParseException) jjte000;
                }
            }

            if (true)
            {
                throw (Error) jjte000;
            }
        }
        finally
        {
            if (jjtc000)
            {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }

        throw new Error("Missing return statement in function");
    }

    /**
     * Expression
     * NOT > AND > OR priority
     */
    final public void Expression()
        throws ParseException
    {
        AndExpression();
label_1: 
        while (true)
        {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
            case OR:
                ;

                break;

            default:
                jj_la1[0] = jj_gen;

                break label_1;
            }

            jj_consume_token(OR);

            OrNode jjtn001 = new OrNode(this, JJTORNODE);
            boolean jjtc001 = true;

            jjtree.openNodeScope(jjtn001);

            try
            {
                AndExpression();
            }
            catch (Throwable jjte001)
            {
                if (jjtc001)
                {
                    jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                }
                else
                {
                    jjtree.popNode();
                }

                if (jjte001 instanceof RuntimeException)
                {
                    if (true)
                    {
                        throw (RuntimeException) jjte001;
                    }
                }

                if (jjte001 instanceof ParseException)
                {
                    if (true)
                    {
                        throw (ParseException) jjte001;
                    }
                }

                if (true)
                {
                    throw (Error) jjte001;
                }
            }
            finally
            {
                if (jjtc001)
                {
                    jjtree.closeNodeScope(jjtn001, 2);
                }
            }
        }
    }

    final public void AndExpression()
        throws ParseException
    {
        UnaryExpression();
label_2: 
        while (true)
        {
            if (jj_2_1(2))
            {
                ;
            }
            else
            {
                break label_2;
            }

            jj_consume_token(AND);

            AndNode jjtn001 = new AndNode(this, JJTANDNODE);
            boolean jjtc001 = true;

            jjtree.openNodeScope(jjtn001);

            try
            {
                AndExpression();
            }
            catch (Throwable jjte001)
            {
                if (jjtc001)
                {
                    jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                }
                else
                {
                    jjtree.popNode();
                }

                if (jjte001 instanceof RuntimeException)
                {
                    if (true)
                    {
                        throw (RuntimeException) jjte001;
                    }
                }

                if (jjte001 instanceof ParseException)
                {
                    if (true)
                    {
                        throw (ParseException) jjte001;
                    }
                }

                if (true)
                {
                    throw (Error) jjte001;
                }
            }
            finally
            {
                if (jjtc001)
                {
                    jjtree.closeNodeScope(jjtn001, 2);
                }
            }
        }
    }

    final public void UnaryExpression()
        throws ParseException
    {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
        {
        case NOT:
            jj_consume_token(NOT);

            NotNode jjtn001 = new NotNode(this, JJTNOTNODE);
            boolean jjtc001 = true;

            jjtree.openNodeScope(jjtn001);

            try
            {
                UnaryExpression();
            }
            catch (Throwable jjte001)
            {
                if (jjtc001)
                {
                    jjtree.clearNodeScope(jjtn001);
                    jjtc001 = false;
                }
                else
                {
                    jjtree.popNode();
                }

                if (jjte001 instanceof RuntimeException)
                {
                    if (true)
                    {
                        throw (RuntimeException) jjte001;
                    }
                }

                if (jjte001 instanceof ParseException)
                {
                    if (true)
                    {
                        throw (ParseException) jjte001;
                    }
                }

                if (true)
                {
                    throw (Error) jjte001;
                }
            }
            finally
            {
                if (jjtc001)
                {
                    jjtree.closeNodeScope(jjtn001, 1);
                }
            }

            break;

        case 1:
        case 3:
        case 4:
        case ANONYMOUS:
        case IDENTIFIER:
            PrimaryExpression();

            break;

        default:
            jj_la1[1] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
    }

    final public void PrimaryExpression()
        throws ParseException
    {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
        {
        case 3:
        case 4:
            BooleanLiteral();

            break;

        case IDENTIFIER:
            Identifier();

            break;

        case ANONYMOUS:
            Anonymous();

            break;

        case 1:
            jj_consume_token(1);
            Expression();
            jj_consume_token(2);

            break;

        default:
            jj_la1[2] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
    }

    //---------------------------------------------------------------------

    /**
     * Identifier
     */
    final public void Identifier()
        throws ParseException
    {
        /*@bgen(jjtree) Identifier */
        Identifier jjtn000 = new Identifier(this, JJTIDENTIFIER);
        boolean jjtc000 = true;

        jjtree.openNodeScope(jjtn000);

        Token t;

        try
        {
            t = jj_consume_token(IDENTIFIER);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.name = t.image;
        }
        finally
        {
            if (jjtc000)
            {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /**
     *
     */
    final public void Anonymous()
        throws ParseException
    {
        /*@bgen(jjtree) Anonymous */
        Anonymous jjtn000 = new Anonymous(this, JJTANONYMOUS);
        boolean jjtc000 = true;

        jjtree.openNodeScope(jjtn000);

        Token t;

        try
        {
            t = jj_consume_token(ANONYMOUS);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.name = t.image;
        }
        finally
        {
            if (jjtc000)
            {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /**
     * CflowPattern
     *
    void CflowPattern() :
    {
       Token t;
    }
    {
        t=<ENTITY> "(" <ENTITY_LIST> ")"
        {
           jjtThis.pattern = t.image;
        }
    }
    **/
    /**
     * Boolean literal - only lower case is supported for now
     */
    final public void BooleanLiteral()
        throws ParseException
    {
        /*@bgen(jjtree) BooleanLiteral */
        BooleanLiteral jjtn000 = new BooleanLiteral(this, JJTBOOLEANLITERAL);
        boolean jjtc000 = true;

        jjtree.openNodeScope(jjtn000);

        try
        {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
            {
            case 3:

                TrueNode jjtn001 = new TrueNode(this, JJTTRUENODE);
                boolean jjtc001 = true;

                jjtree.openNodeScope(jjtn001);

                try
                {
                    jj_consume_token(3);
                }
                finally
                {
                    if (jjtc001)
                    {
                        jjtree.closeNodeScope(jjtn001, true);
                    }
                }

                break;

            case 4:

                FalseNode jjtn002 = new FalseNode(this, JJTFALSENODE);
                boolean jjtc002 = true;

                jjtree.openNodeScope(jjtn002);

                try
                {
                    jj_consume_token(4);
                }
                finally
                {
                    if (jjtc002)
                    {
                        jjtree.closeNodeScope(jjtn002, true);
                    }
                }

                break;

            default:
                jj_la1[3] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        finally
        {
            if (jjtc000)
            {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    final private boolean jj_2_1(int xla)
    {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;

        try
        {
            return !jj_3_1();
        }
        catch (LookaheadSuccess ls)
        {
            return true;
        }
        finally
        {
            jj_save(0, xla);
        }
    }

    final private boolean jj_3R_3()
    {
        if (jj_3R_4())
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_14()
    {
        if (jj_scan_token(ANONYMOUS))
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_11()
    {
        if (jj_scan_token(1))
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_10()
    {
        if (jj_3R_14())
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_9()
    {
        if (jj_3R_13())
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_7()
    {
        Token xsp;

        xsp = jj_scanpos;

        if (jj_3R_8())
        {
            jj_scanpos = xsp;

            if (jj_3R_9())
            {
                jj_scanpos = xsp;

                if (jj_3R_10())
                {
                    jj_scanpos = xsp;

                    if (jj_3R_11())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    final private boolean jj_3R_8()
    {
        if (jj_3R_12())
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_6()
    {
        if (jj_3R_7())
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_16()
    {
        if (jj_scan_token(4))
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_4()
    {
        Token xsp;

        xsp = jj_scanpos;

        if (jj_3R_5())
        {
            jj_scanpos = xsp;

            if (jj_3R_6())
            {
                return true;
            }
        }

        return false;
    }

    final private boolean jj_3R_5()
    {
        if (jj_scan_token(NOT))
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_15()
    {
        if (jj_scan_token(3))
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_13()
    {
        if (jj_scan_token(IDENTIFIER))
        {
            return true;
        }

        return false;
    }

    final private boolean jj_3R_12()
    {
        Token xsp;

        xsp = jj_scanpos;

        if (jj_3R_15())
        {
            jj_scanpos = xsp;

            if (jj_3R_16())
            {
                return true;
            }
        }

        return false;
    }

    final private boolean jj_3_1()
    {
        if (jj_scan_token(AND))
        {
            return true;
        }

        if (jj_3R_3())
        {
            return true;
        }

        return false;
    }

    private static void jj_la1_0()
    {
        jj_la1_0 = new int[] { 0x800, 0x701a, 0x601a, 0x18, };
    }

    public void ReInit(java.io.InputStream stream)
    {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jjtree.reset();
        jj_gen = 0;

        for (int i = 0; i < 4; i++)
        {
            jj_la1[i] = -1;
        }

        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(java.io.Reader stream)
    {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jjtree.reset();
        jj_gen = 0;

        for (int i = 0; i < 4; i++)
        {
            jj_la1[i] = -1;
        }

        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(ExpressionParserTokenManager tm)
    {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jjtree.reset();
        jj_gen = 0;

        for (int i = 0; i < 4; i++)
        {
            jj_la1[i] = -1;
        }

        for (int i = 0; i < jj_2_rtns.length; i++)
        {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    final private Token jj_consume_token(int kind)
        throws ParseException
    {
        Token oldToken;

        if ((oldToken = token).next != null)
        {
            token = token.next;
        }
        else
        {
            token = token.next = token_source.getNextToken();
        }

        jj_ntk = -1;

        if (token.kind == kind)
        {
            jj_gen++;

            if (++jj_gc > 100)
            {
                jj_gc = 0;

                for (int i = 0; i < jj_2_rtns.length; i++)
                {
                    JJCalls c = jj_2_rtns[i];

                    while (c != null)
                    {
                        if (c.gen < jj_gen)
                        {
                            c.first = null;
                        }

                        c = c.next;
                    }
                }
            }

            return token;
        }

        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    final private boolean jj_scan_token(int kind)
    {
        if (jj_scanpos == jj_lastpos)
        {
            jj_la--;

            if (jj_scanpos.next == null)
            {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source
                            .getNextToken();
            }
            else
            {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        }
        else
        {
            jj_scanpos = jj_scanpos.next;
        }

        if (jj_rescan)
        {
            int i = 0;
            Token tok = token;

            while ((tok != null) && (tok != jj_scanpos))
            {
                i++;
                tok = tok.next;
            }

            if (tok != null)
            {
                jj_add_error_token(kind, i);
            }
        }

        if (jj_scanpos.kind != kind)
        {
            return true;
        }

        if ((jj_la == 0) && (jj_scanpos == jj_lastpos))
        {
            throw jj_ls;
        }

        return false;
    }

    final public Token getNextToken()
    {
        if (token.next != null)
        {
            token = token.next;
        }
        else
        {
            token = token.next = token_source.getNextToken();
        }

        jj_ntk = -1;
        jj_gen++;

        return token;
    }

    final public Token getToken(int index)
    {
        Token t = lookingAhead ? jj_scanpos : token;

        for (int i = 0; i < index; i++)
        {
            if (t.next != null)
            {
                t = t.next;
            }
            else
            {
                t = t.next = token_source.getNextToken();
            }
        }

        return t;
    }

    final private int jj_ntk()
    {
        if ((jj_nt = token.next) == null)
        {
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        }
        else
        {
            return (jj_ntk = jj_nt.kind);
        }
    }

    private void jj_add_error_token(int kind, int pos)
    {
        if (pos >= 100)
        {
            return;
        }

        if (pos == (jj_endpos + 1))
        {
            jj_lasttokens[jj_endpos++] = kind;
        }
        else if (jj_endpos != 0)
        {
            jj_expentry = new int[jj_endpos];

            for (int i = 0; i < jj_endpos; i++)
            {
                jj_expentry[i] = jj_lasttokens[i];
            }

            boolean exists = false;

            for (java.util.Enumeration e = jj_expentries.elements();
                e.hasMoreElements();)
            {
                int[] oldentry = (int[]) (e.nextElement());

                if (oldentry.length == jj_expentry.length)
                {
                    exists = true;

                    for (int i = 0; i < jj_expentry.length; i++)
                    {
                        if (oldentry[i] != jj_expentry[i])
                        {
                            exists = false;

                            break;
                        }
                    }

                    if (exists)
                    {
                        break;
                    }
                }
            }

            if (!exists)
            {
                jj_expentries.addElement(jj_expentry);
            }

            if (pos != 0)
            {
                jj_lasttokens[(jj_endpos = pos) - 1] = kind;
            }
        }
    }

    public ParseException generateParseException()
    {
        jj_expentries.removeAllElements();

        boolean[] la1tokens = new boolean[21];

        for (int i = 0; i < 21; i++)
        {
            la1tokens[i] = false;
        }

        if (jj_kind >= 0)
        {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }

        for (int i = 0; i < 4; i++)
        {
            if (jj_la1[i] == jj_gen)
            {
                for (int j = 0; j < 32; j++)
                {
                    if ((jj_la1_0[i] & (1 << j)) != 0)
                    {
                        la1tokens[j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < 21; i++)
        {
            if (la1tokens[i])
            {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.addElement(jj_expentry);
            }
        }

        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);

        int[][] exptokseq = new int[jj_expentries.size()][];

        for (int i = 0; i < jj_expentries.size(); i++)
        {
            exptokseq[i] = (int[]) jj_expentries.elementAt(i);
        }

        return new ParseException(token, exptokseq, tokenImage);
    }

    final public void enable_tracing()
    {
    }

    final public void disable_tracing()
    {
    }

    final private void jj_rescan_token()
    {
        jj_rescan = true;

        for (int i = 0; i < 1; i++)
        {
            JJCalls p = jj_2_rtns[i];

            do
            {
                if (p.gen > jj_gen)
                {
                    jj_la = p.arg;
                    jj_lastpos = jj_scanpos = p.first;

                    switch (i)
                    {
                    case 0:
                        jj_3_1();

                        break;
                    }
                }

                p = p.next;
            }
            while (p != null);
        }

        jj_rescan = false;
    }

    final private void jj_save(int index, int xla)
    {
        JJCalls p = jj_2_rtns[index];

        while (p.gen > jj_gen)
        {
            if (p.next == null)
            {
                p = p.next = new JJCalls();

                break;
            }

            p = p.next;
        }

        p.gen = (jj_gen + xla) - jj_la;
        p.first = token;
        p.arg = xla;
    }

    static private final class LookaheadSuccess extends java.lang.Error
    {
    }

    static final class JJCalls
    {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }
}

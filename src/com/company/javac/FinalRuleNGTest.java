/*
package com.company.javac;


import org.junit.Test;

public class FinalRuleNGTest {

    public FinalRuleNGTest() {
    }

    @Test
    public void testSimpleFieldRegExp() throws Exception {
        RuleTest.create()
                .input("com.company.javac/Test.java",
                       "package com.company.javac;\n" +
                       "import com.company.javac.RegExp;\n" +
                       "public class Test {\n" +
                       "        public final String DEF_MASK = \"(\";\n" +
                       "        @RegExp private final String MASK = DEF_MASK;\n" +
                       "}\n")
                .run(RegExpRule.class)
                .assertWarnings("\"/com/company/javac/Test.java:121-124: Unclosed group.");
    }

    @Test
    public void testNotAllowedImplementor() throws Exception {
        RuleTest.create()
                .input("com.company.javac/Test.java",
                        "package com.company.javac;\n" +
                                "public class Test implements API {\n" +
                                "}\n")
                .input("com.company.javac/API.java," +
                        "package com.company.javac;\n" +
                        "import com.company.javac.Final;\n" +
                        "@Final(implementors={})\n" +
                        "public interface API {\n" +
                        "}\n")
                .runProcessor(FinalRule.class)
                .assertWarning("/com/company/javac/Test.java:14-50:Cannot implement: test.API, not its listed implementor.");
    }
}
*/

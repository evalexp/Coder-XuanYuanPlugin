import org.junit.jupiter.api.Assertions;
import top.evalexp.tools.impl.plugin.JTestContext;
import top.evalexp.tools.impl.plugin.JTestResult;
import top.evalexp.tools.plugins.Coder;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws NoSuchFieldException, IllegalAccessException {
        JTestContext context = new JTestContext();
        JTestResult result = new JTestResult();
        Coder coder = new Coder();
        coder.setup(context);
        JTestContext.setValue(coder, "mode", "Encode");
        JTestContext.setValue(coder, "input_format", "Raw");
        JTestContext.setValue(coder, "output_format", "Raw");
        JTestContext.setValue(coder, "algorithm", "Base64");
        JTestContext.setValue(coder, "input", "123");
        coder.handle(result);
        System.out.println(result.getResult());
        Assertions.assertEquals(result.getResult(), "MTIz\n");
    }
}

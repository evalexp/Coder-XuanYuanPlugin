package top.evalexp.tools.plugins;

import top.evalexp.tools.interfaces.component.IComponent;
import top.evalexp.tools.interfaces.plugin.IContext;
import top.evalexp.tools.interfaces.plugin.IPlugin;
import top.evalexp.tools.interfaces.plugin.IResult;
import top.evalexp.tools.plugins.coders.CodingDispatcher;
import top.evalexp.tools.plugins.enums.Algorithm;
import top.evalexp.tools.plugins.enums.Format;
import top.evalexp.tools.plugins.enums.Mode;
import top.evalexp.tools.plugins.util.ConverterDispatcher;
import top.evalexp.tools.plugins.util.EnumUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Coder implements IPlugin {
    private IComponent<Mode> mode;
    private IComponent<Format> input_format;
    private IComponent<Format> output_format;
    private IComponent<Algorithm> algorithm;
    private IComponent<String> input;
    private byte[] data;
    private byte[] out;
    @Override
    public void setup(IContext context) {
        IComponent<List<String>> fileList = context.List("Files", "fs", "File List", new ArrayList<>(){{
            add("1");
            add("2");
        }});
        this.mode = context.Enumerate("Mode", "m", "Mode for plugin", EnumUtil.convertToPluginEnumerates(Mode.values()));
        this.input_format = context.Enumerate("Input-Format", "if", "Input format", EnumUtil.convertToPluginEnumerates(Format.values()), Format.Raw.name());
        this.output_format = context.Enumerate("Output-Format", "of", "Output format", EnumUtil.convertToPluginEnumerates(Format.values()), Format.Raw.name());
        this.algorithm = context.Enumerate("Algorithm", "a", "Using algorithm to encode/code", EnumUtil.convertToPluginEnumerates(Algorithm.values()));
        this.input = context.Text("Input", "i", "Input data");
    }

    @Override
    public void handle(IResult result) {
        this.data = ConverterDispatcher.dispatch(this.input_format.get(), Format.Raw, this.input.get().getBytes());
        this.out = CodingDispatcher.dispatch(this.mode.get(), this.algorithm.get(), this.data);
        this.out = ConverterDispatcher.dispatch(Format.Raw, this.output_format.get(), this.out);
        result.writeln(new String(this.out));
    }
}
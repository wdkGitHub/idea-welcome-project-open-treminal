package dekun.wang.utils;

import com.intellij.openapi.diagnostic.Logger;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author WangDeKun
 *
 * <p>
 */
public class ExecuteCommand {
    private static final Logger LOG = Logger.getInstance(ExecuteCommand.class);

    public static void execute(String directoryPath, CommandOrApp.App app) {
        execute(directoryPath, app, null, null);
    }

    public static void execute(String directoryPath, CommandOrApp.Command command) {
        execute(directoryPath, null, command, null);
    }

    public static void execute(String directoryPath, List<String> commandList) {
        execute(directoryPath, null, null, commandList);
    }

    private static void execute(String directoryPath, CommandOrApp.App app, CommandOrApp.Command command, List<String> commandList) {
        Assert.assertNotNull("directoryPath is null", directoryPath);
        File file = new File(directoryPath);
        ProcessBuilder executeCommand = null;
        if (app != null && command == null && commandList == null) {
            executeCommand = new ProcessBuilder(app.getCommand()).directory(file);
        } else if (command != null && app == null && commandList == null) {
            executeCommand = new ProcessBuilder(command.getCommand()).directory(file);
        } else {
            if (commandList == null || commandList.isEmpty())
                throw new RuntimeException("输入命令错误");
            executeCommand = new ProcessBuilder(commandList).directory(file);
        }
        try {
            executeCommand.start();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

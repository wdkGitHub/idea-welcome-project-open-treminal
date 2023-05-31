package dekun.wang.utils;

import com.intellij.openapi.diagnostic.Logger;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CommandUtils {
    private static final Logger LOG = Logger.getInstance(CommandUtils.class);

//    public static void main(String[] args) throws IOException {
//        System.out.println(whichCommand(Fork_Command.getCommandName()));
//        System.out.println(whichCommand(SourceTree_Command.getCommandName()));
//        System.out.println(whichCommand(GitOpen_Command.getCommandName()));
//        System.out.println(whichCommand("ABCDE"));
//        System.out.println(checkFile(iTerm_APP.getAppInstallPath(), false));
//        System.out.println(checkFile(Terminal_APP.getAppInstallPath(), false));
//
//    }

    public static String whichCommand(String commandName) throws IOException {
        Assert.assertNotNull("commandName is null", commandName);
        ProcessBuilder executeCommand = new ProcessBuilder("/usr/bin/which", commandName);
        Process start = executeCommand.start();
        List<String> whichCommand = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(start.getInputStream(), StandardCharsets.UTF_8));) {
            String line = null;
            while ((line = br.readLine()) != null) {
                whichCommand.add(line);
                LOG.info(line);
            }
        }
        return whichCommand.size() > 0 ? whichCommand.get(0) : null;
    }

    public static boolean checkFile(String appInstallPath, boolean isFile) {
        Assert.assertNotNull("App install path is null", appInstallPath);
        File file = new File(appInstallPath);
        if (isFile) {
            return file.exists() && file.isFile();
        } else {
            return file.exists() && file.isDirectory();
        }
    }
}

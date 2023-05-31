package dekun.wang.utils;

import com.intellij.openapi.diagnostic.Logger;
import org.junit.Assert;

import java.io.File;

public class CommandUtils {
    private static final Logger LOG = Logger.getInstance(CommandUtils.class);


    public static boolean commandNotExists(CommandOrApp.Command command) {
        return !checkFile(command.getCommandPath(), true);
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

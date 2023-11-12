package dekun.wang.utils;


import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WangDeKun
 *
 * <p>
 */
public final class CommandOrApp {
    private static final Logger LOG = Logger.getInstance(CommandOrApp.class);
    private static final String POINT = ".";

    private static final String FORK_PATH;
    private static final String SOURCETREE_PATH;
    private static final String GIT_OPEN_PATH;

    static {
        if (SystemInfo.isMac) {
            FORK_PATH = "/usr/local/bin/fork";
            SOURCETREE_PATH = "/usr/local/bin/stree";
            GIT_OPEN_PATH = System.getProperty("user.home") + "/.oh-my-zsh/custom/plugins/git-open/git-open";
        } else if (SystemInfo.isWindows) {
            FORK_PATH = null;
            SOURCETREE_PATH = null;
            GIT_OPEN_PATH = null;
            throw new RuntimeException("系统暂不支持");
        } else {
            FORK_PATH = null;
            SOURCETREE_PATH = null;
            GIT_OPEN_PATH = null;
            throw new RuntimeException("系统暂不支持");
        }
    }

    //use com.intellij.openapi.util.SystemInfo
    public enum Command {
        /**
         *
         */
        Fork_Command(FORK_PATH, POINT),
        SourceTree_Command(SOURCETREE_PATH, POINT),
        GitOpen_Command(GIT_OPEN_PATH);
        private final String commandPath;
        private final List<String> parameters;

        Command(String commandPath, String... args) {
            this.commandPath = commandPath;
            parameters = new ArrayList<>(args.length);
            parameters.addAll(Arrays.asList(args));
        }

        public String getCommandPath() {
            return commandPath;
        }

        public List<String> getCommand() {
            int size = 1 + parameters.size();
            List<String> command = new ArrayList<>(size);
            command.add(commandPath);
            if (!parameters.isEmpty()) command.addAll(parameters);
            return command;
        }

    }

    public enum App {
        /**
         *
         */
        iTerm_APP("/Applications/iTerm.app"),
        Terminal_APP("/System/Applications/Utilities/Terminal.app");
        private final String appInstallPath;

        App(String appInstallPath) {
            this.appInstallPath = appInstallPath;
        }

        public static List<String> getDefaultTerminalAppCommand() {
            if (SystemInfo.isMac) {
                if (CommandUtils.checkFile(iTerm_APP.getAppInstallPath(), false)) {
                    return iTerm_APP.getCommand();
                }
                return Terminal_APP.getCommand();
            } else if (SystemInfo.isWindows) {
                throw new RuntimeException("系统暂不支持");
            }
            throw new RuntimeException("系统暂不支持");
        }

        public String getAppInstallPath() {
            return appInstallPath;
        }

        public List<String> getCommand() {
            ArrayList<String> command = new ArrayList<>(3);
            command.add("open");
            command.add("-a");
            command.add(getAppInstallPath());
            command.add(POINT);
            return command;
        }

    }
}

package dekun.wang.utils;


import com.intellij.openapi.diagnostic.Logger;

import java.io.IOException;
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

//    public static void main(String[] args) {
//        System.out.println(App.iTerm_APP);
//        System.out.println(Command.Fork_Command);
//    }

    public enum Command {
        Fork_Command("fork", POINT),
        SourceTree_Command("stree", POINT),
        GitOpen_Command("git-open");
        private final String commandName;
        private final String commandPath;
        private final List<String> parameters;


        Command(String commandName, String... args) {
            this.commandName = commandName;
            try {
                this.commandPath = CommandUtils.whichCommand(commandName);
            } catch (IOException e) {
                LOG.error(e.getMessage());
                throw new RuntimeException(e);
            }
            parameters = new ArrayList<>(args.length);
            parameters.addAll(Arrays.asList(args));
        }

        public String getCommandName() {
            return commandName;
        }

        public String getCommandPath() {
            return commandPath;
        }

        public List<String> getParameters() {
            return parameters;
        }

        public List<String> getCommand() {
            int size = 1 + parameters.size();
            List<String> command = new ArrayList<>(size);
            command.add(commandPath);
            if (parameters.size() > 0) command.addAll(parameters);
            return command;
        }

        @Override
        public String toString() {
            return "Command{" +
                    "commandPath='" + commandPath + '\'' +
                    ", parameters=" + parameters +
                    '}';
        }
    }

    public enum App {
        iTerm_APP("/Applications/iTerm.app"),
        Terminal_APP("/System/Applications/Utilities/Terminal.app");
        private final String appInstallPath;

        App(String appInstallPath) {
            this.appInstallPath = appInstallPath;
        }

        public static List<String> getDefaultTerminalAppCommand() {
            if (CommandUtils.checkFile(iTerm_APP.getAppInstallPath(), false)) {
                return iTerm_APP.getCommand();
            }
            return Terminal_APP.getCommand();
        }

        public String getAppInstallPath() {
            return appInstallPath;
        }

        @Override
        public String toString() {
            return "App{" +
                    "appInstallPath='" + appInstallPath + '\'' +
                    ",command=" + getCommand() +
                    '}';
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

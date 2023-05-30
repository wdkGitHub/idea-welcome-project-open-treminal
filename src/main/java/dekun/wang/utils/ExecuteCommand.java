package dekun.wang.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author WangDeKun
 *
 * <p>
 */
public class ExecuteCommand {
    public static void OpenInFork(String projectPath) {
        // 命令非全路径，报错如下。系统自带命令不会报错
            /*
            Cannot run program "fork" (in directory "/A/B/C"): error=2, No such file or directory
             */
        ProcessBuilder executeCommand = new ProcessBuilder("/usr/local/bin/fork", ".").directory(new File(projectPath));
        try {
            executeCommand.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void OpenInSourcetree(String projectPath) {
        ProcessBuilder executeCommand = new ProcessBuilder("/usr/local/bin/stree", ".").directory(new File(projectPath));
        try {
            executeCommand.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void OpenInTerminal(String projectPath) {
        ProcessBuilder executeCommand = new ProcessBuilder("open", "-a", "/Applications/iTerm.app", projectPath);
        try {
            executeCommand.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void GitOpen(String projectPath) {
        //brew search git-open
        String command = System.getProperty("user.home") + "/.oh-my-zsh/custom/plugins/git-open/git-open";
        ProcessBuilder executeCommand = new ProcessBuilder(command).directory(new File(projectPath));
        try {
            executeCommand.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

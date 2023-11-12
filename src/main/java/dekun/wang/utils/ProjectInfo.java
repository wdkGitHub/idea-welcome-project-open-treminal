package dekun.wang.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangDeKun
 *
 * <p>
 */
public class ProjectInfo {
    private static final Logger LOG = Logger.getInstance(ProjectInfo.class);

    private static Object getRecentProjectItem(AnActionEvent event) throws RuntimeException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> Companion = Class.forName("com.intellij.openapi.wm.impl.welcomeScreen.projectActions.RecentProjectsWelcomeScreenActionBase$Companion");
        Constructor<?> CompanionConstructor = Companion.getDeclaredConstructor();
        CompanionConstructor.setAccessible(true);
        Object companion = CompanionConstructor.newInstance();
        Method intellijPlatformIdeImpl = Companion.getMethod("getSelectedItem$intellij_platform_ide_impl", AnActionEvent.class);
        return intellijPlatformIdeImpl.invoke(companion, event);
    }

    public static String getProjectPath(AnActionEvent event) {
        try {
            Object invoke = getRecentProjectItem(event);
            Method getProjectPath = invoke.getClass().getMethod("getProjectPath");
            return String.valueOf(getProjectPath.invoke(invoke));
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | ClassNotFoundException |
                 InvocationTargetException e) {
            throw new RuntimeException("GetRecentProjectItem Exception (getProjectPath)");
        }
    }

    public static boolean isRecentProjectItem(AnActionEvent event) {
        try {
            Object recentProjectItem = getRecentProjectItem(event);
            return recentProjectItem != null && "com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem".equals(recentProjectItem.getClass().getName());
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | ClassNotFoundException |
                 InvocationTargetException e) {
            throw new RuntimeException("GetRecentProjectItem Exception (isRecentProjectItem)");
        }
    }

    public static boolean isGitRepository(AnActionEvent event) {
        File file = new File(getProjectPath(event).replaceAll("(.)/$", "$1") + "/.git");
        return file.exists() && file.isDirectory();
    }

    public static boolean isGitRepository(VirtualFile virtualFile) {
        if (virtualFile != null && virtualFile.isDirectory()) {
            return new File(virtualFile.getPath() + "/.git").exists();
        } else {
            return false;
        }
    }

    public static boolean hasRemoteRepository(AnActionEvent event) {
        List<String> list = new ArrayList<>();
        ProcessBuilder executeCommand = new ProcessBuilder("/usr/bin/git", "remote", "-v").directory(new File(getProjectPath(event)));
        Process start = null;
        try {
            start = executeCommand.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String regex = "^origin\\shttp[s]*://.*\\s\\(((push)|(fetch))\\)";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream(), StandardCharsets.UTF_8));) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(regex)) list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOG.info(list.toString());
        return !list.isEmpty();
    }

}

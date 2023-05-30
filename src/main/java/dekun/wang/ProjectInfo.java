package dekun.wang;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;

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
    public static boolean hasRemoteRepository(AnActionEvent event) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, IOException {
        List<String> list = new ArrayList<>();
        ProcessBuilder executeCommand = new ProcessBuilder("/usr/bin/git", "remote", "-v").directory(new File(getProjectPath(event)));
        Process start = executeCommand.start();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream(), StandardCharsets.UTF_8));) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        }
        LOG.info(list.toString());
        return list.size() > 0;
    }

    public static boolean isGitRepository(AnActionEvent event) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        File file = new File (getProjectPath (event).replaceAll ("(.)/$", "$1") + "/.git");
        return file.exists () && file.isDirectory ();
    }

    public static Object getRecentProjectItem(AnActionEvent event) throws RuntimeException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> RecentProjectsWelcomeScreenActionBase$Companion = Class.forName ("com.intellij.openapi.wm.impl.welcomeScreen.projectActions.RecentProjectsWelcomeScreenActionBase$Companion");
        Constructor<?> CompanionConstructor = RecentProjectsWelcomeScreenActionBase$Companion.getDeclaredConstructor ();
        CompanionConstructor.setAccessible (true);
        Object companion = CompanionConstructor.newInstance ();
        Method getSelectedItem$intellijPlatformIdeImpl = RecentProjectsWelcomeScreenActionBase$Companion.getMethod ("getSelectedItem$intellij_platform_ide_impl", AnActionEvent.class);
        return getSelectedItem$intellijPlatformIdeImpl.invoke (companion, event);

    }

    public static  String  getProjectPath(AnActionEvent event) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Object invoke = getRecentProjectItem (event);
        Method getProjectPath = invoke.getClass ().getMethod ("getProjectPath");
        return String.valueOf (getProjectPath.invoke (invoke));
    }
}

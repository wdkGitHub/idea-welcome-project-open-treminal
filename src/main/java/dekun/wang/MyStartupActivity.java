package dekun.wang;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import dekun.wang.utils.ProjectViewState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * @author WangDeKun
 * <p>
 * <p>
 * Email :  wangdekunemail@gmail.com
 * <p>
 */

public class MyStartupActivity implements ProjectActivity {


    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        try {
            ProjectViewState.projectViewStateModified(project.getBasePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}


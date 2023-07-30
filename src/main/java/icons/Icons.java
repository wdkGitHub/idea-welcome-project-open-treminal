package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author WangDeKun
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html">SDK</a>
 * <br>
 */
public interface Icons {
    Icon TERMINAL_ICON = IconLoader.getIcon("/icons/terminal.png", Icons.class);
    Icon Markdown = IconLoader.getIcon("/icons/markdown.png", Icons.class);
}


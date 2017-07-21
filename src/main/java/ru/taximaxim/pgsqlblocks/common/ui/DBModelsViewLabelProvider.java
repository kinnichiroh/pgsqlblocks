package ru.taximaxim.pgsqlblocks.common.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
import ru.taximaxim.pgsqlblocks.TreeLabelProvider;
import ru.taximaxim.pgsqlblocks.modules.db.controller.DBController;
import ru.taximaxim.pgsqlblocks.utils.ImageUtils;
import ru.taximaxim.pgsqlblocks.utils.Images;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DBModelsViewLabelProvider extends TreeLabelProvider {

    private static final int BLOCKED_ICON_QUADRANT = IDecoration.TOP_RIGHT;
    private ConcurrentMap<String, ImageDescriptor> decoratorsMap = new ConcurrentHashMap<>();

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        DBController controller = (DBController) element;
        return getImage(controller);
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        DBController controller = (DBController) element;
        return controller.getModel().getName();
    }
    private Image getImage(DBController controller) {
        String imagePath = controller.getStatus().getStatusImage().getImageAddr();
        Image image = imagesMap.computeIfAbsent(imagePath,
                k -> new Image(null, getClass().getClassLoader().getResourceAsStream(k)));

        if (controller.isBlocked()) {
            String decoratorBlockedPath = Images.DECORATOR_BLOCKED.getImageAddr();
            ImageDescriptor decoratorBlockedImageDesc = decoratorsMap.computeIfAbsent(decoratorBlockedPath, path -> {
                Image blockedImage = new Image(null, getClass().getClassLoader().getResourceAsStream(path));
                return ImageDescriptor.createFromImage(blockedImage);
            });
            image = ImageUtils.decorateImage(image, decoratorBlockedImageDesc, BLOCKED_ICON_QUADRANT);
        }
        return image;
    }
}
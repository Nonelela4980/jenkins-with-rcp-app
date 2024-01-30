package wirk.secured.container.ui.utils;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import wirk.secured.container.ui.enums.FileType;
import wirk.secured.container.ui.providers.ViewLabelProvider;
public class ImageDescriptorGenerator {
	public ImageDescriptor generateImageDescriptor(FileType type) {
		String image = "";
		switch(type) {
			case WORD_DOCUMENT: image="word";
				break;
			case PDF: image="pdf";
				break;
			case POWER_POINT_DOCUMENT: image="ppt";
				break;
			case TEXT_FILE: image="txt";
				break;
			case ENV_FILE: image="env";
				break;
			case ZIP_FILE: image="zip";
			break;
			default: image="folder";
				break;
		}
        Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
        URL url = FileLocator.find(bundle, new Path("images/"+image+".png"), null);
        return ImageDescriptor.createFromURL(url);
	}
}

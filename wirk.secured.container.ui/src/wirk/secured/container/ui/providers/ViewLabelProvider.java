package wirk.secured.container.ui.providers;
import java.io.File;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import wirk.secured.container.ui.enums.FileType;
import wirk.secured.container.ui.utils.ImageDescriptorGenerator;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Image;
public class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {
	private ResourceManager resourceManager;
	private ImageDescriptorGenerator imageDescriptorGenerator;
    public ViewLabelProvider(ImageDescriptorGenerator imageDescriptorGenerator) {
        this.imageDescriptorGenerator = imageDescriptorGenerator;
    }
    
	@Override
	public StyledString getStyledText(Object element) {
        if(element instanceof File) {
            File file = (File) element;
            StyledString styledString = new StyledString(getFileName(file));
            String[] files = file.list();
            if (files != null) {
                styledString.append(" ( " + files.length + " ) ",
                        StyledString.COUNTER_STYLER);
            }
            return styledString;
        }
        return null;
	}
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof File) {
			if(((File)element).isDirectory()) {
				return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.FOLDER));
			}else {
				String extention = getFileExtention((File)element);
				switch(extention) {
					case "pdf": return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.PDF));	
					case "docx": return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.WORD_DOCUMENT));	
					case "zip": return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.ZIP_FILE));	
					case "txt": return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.TEXT_FILE));	
					case "ppt": return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.POWER_POINT_DOCUMENT));	
					case "env": return getResourceManager().createImage(imageDescriptorGenerator.generateImageDescriptor(FileType.ENV_FILE));	
					default: return super.getImage(element);
				}
			}
		}
		return super.getImage(element);
	}

	private String getFileExtention(File file) {
		String fileName = file.getName();
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex != -1) {
		    return fileName.substring(lastIndex + 1);
		}
		return "";
	}
	
    protected ResourceManager getResourceManager() {
        if(resourceManager == null) {
            resourceManager = new LocalResourceManager(JFaceResources.getResources());
        }
        return resourceManager;
    }

    private String getFileName(File file) {
        String name = file.getName();
        System.out.println(name);
        return name.isEmpty() ? file.getPath() : name;
    }
}

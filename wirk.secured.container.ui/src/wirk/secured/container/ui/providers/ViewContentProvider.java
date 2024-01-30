package wirk.secured.container.ui.providers;
import java.io.File;
import org.eclipse.jface.viewers.ITreeContentProvider;
public class ViewContentProvider implements ITreeContentProvider{
	@Override
	public Object[] getElements(Object inputElement) {
		return (File[]) inputElement;
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		File file = (File)parentElement;
		return file.listFiles();
	}
	@Override
	public Object getParent(Object element) {
		File file = (File)element;
		return file.getParent();
	}
	
	@Override
	public boolean hasChildren(Object element) {
		File file = (File)element;
		return file.isDirectory();
	}
}

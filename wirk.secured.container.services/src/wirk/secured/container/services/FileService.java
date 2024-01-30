package wirk.secured.container.services;
import java.io.File;
import org.osgi.service.component.annotations.Component;
import wirk.secured.container.model.external.services.IFileService;
@Component
public class FileService implements IFileService{
	@Override
	public File[] getFiles(String url) {
		File files = new File(url);
		if(files.isDirectory()) {
			return files.listFiles();
		}
		else
			throw new RuntimeException("Invalid URL entered");
	}
}

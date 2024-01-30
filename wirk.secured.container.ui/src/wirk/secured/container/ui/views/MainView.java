package wirk.secured.container.ui.views;

import java.text.DateFormat;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import wirk.secured.container.model.external.services.IFileService;
import wirk.secured.container.ui.providers.FileModifiedLabelProvider;
import wirk.secured.container.ui.providers.FileSizeLabelProvider;
import wirk.secured.container.ui.providers.ViewContentProvider;
import wirk.secured.container.ui.providers.ViewLabelProvider;
import wirk.secured.container.ui.utils.ImageDescriptorGenerator;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
public class MainView {
	private Text text;
	private Tree documentTree;
	private TreeViewer treeViewer;
	private  Button btnSearch;
	
	@Inject
	IFileService fileService;
	private static final DateFormat dateFormat = DateFormat.getDateInstance();
	private Tree tFilesDisplayer;
	private FormData fd_btnSearch;
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FormLayout());
		initViews(parent);
		
		TreeViewer treeViewer_1 = new TreeViewer(parent, SWT.BORDER);
		Tree tree = treeViewer_1.getTree();
		fd_btnSearch.right = new FormAttachment(tree, 0, SWT.RIGHT);
		FormData fd_tree = new FormData();
		fd_tree.bottom = new FormAttachment(100, -53);
		fd_tree.top = new FormAttachment(btnSearch, 10);
		fd_tree.left = new FormAttachment(tFilesDisplayer, 29);
		fd_tree.right = new FormAttachment(tFilesDisplayer, 195, SWT.RIGHT);
		tree.setLayoutData(fd_tree);
	}
	private void initViews(Composite parent) {
		Label lblNewLabel = new Label(parent, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 28);
		fd_lblNewLabel.left = new FormAttachment(0, 30);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Enter the path of the to view contents of");
		
		text = new Text(parent, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(lblNewLabel, 6);
		fd_text.left = new FormAttachment(0, 30);
		text.setLayoutData(fd_text);
		
		treeViewer = new TreeViewer(parent, SWT.BORDER);
		tFilesDisplayer = treeViewer.getTree();
		FormData fd_tFilesDisplayer = new FormData();
		fd_tFilesDisplayer.bottom = new FormAttachment(100, -53);
		fd_tFilesDisplayer.top = new FormAttachment(text, 12);
		fd_tFilesDisplayer.right = new FormAttachment(100, -230);
		fd_tFilesDisplayer.left = new FormAttachment(0, 30);
		tFilesDisplayer.setLayoutData(fd_tFilesDisplayer);
		
		btnSearch = new Button(parent, SWT.NONE);
		fd_text.right = new FormAttachment(btnSearch, -6);
		fd_btnSearch = new FormData();
		fd_btnSearch.top = new FormAttachment(0, 47);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("Check Files");		
		initializeTreeViewer(parent);
		
		btnSearch.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("Files at path $s".formatted(text.getText()));
				treeViewer.setInput(fileService.getFiles(text.getText()));
			}
		});
	}

	private void initializeTreeViewer(Composite parent) {
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.getTree().setHeaderVisible(true);
		
        TreeViewerColumn mainColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
        mainColumn.getColumn().setText("Name");
        mainColumn.getColumn().setWidth(300);
        mainColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(
                new ViewLabelProvider(new ImageDescriptorGenerator())));
        TreeViewerColumn modifiedColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
        modifiedColumn.getColumn().setText("Last Modified");
        modifiedColumn.getColumn().setWidth(300);
        modifiedColumn.getColumn().setAlignment(SWT.RIGHT);
        modifiedColumn
                .setLabelProvider(new DelegatingStyledCellLabelProvider(
                        new FileModifiedLabelProvider(dateFormat)));
        
        TreeViewerColumn fileSizeColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
        fileSizeColumn.getColumn().setText("Size");
        fileSizeColumn.getColumn().setWidth(300);
        fileSizeColumn.getColumn().setAlignment(SWT.RIGHT);
        fileSizeColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(
                new FileSizeLabelProvider()));
        generateMenu();
//        treeViewer.setInput(fileService.getFiles(null));
		/*
		 * treeViewer.addSelectionChangedListener(event->{ IStructuredSelection
		 * selection = viewer.getStructuredSelection();
		 * selectionService.setSelection(selection.toList());
		 * System.out.println("Selected Item: "+ selection.getFirstElement()); });
		 */
	}
	
	private void generateMenu() {
	    final Menu menu = new Menu(tFilesDisplayer);
	    tFilesDisplayer.setMenu(menu);
	    menu.addMenuListener(new MenuAdapter()
	    {
	        public void menuShown(MenuEvent e)
	        {
	            MenuItem[] items = menu.getItems();
	            for (int i = 0; i < items.length; i++)
	            {
	                items[i].dispose();
	            }
	            MenuItem newItem = new MenuItem(menu, SWT.NONE);
	            newItem.setText("Menu for " + tFilesDisplayer.getSelection()[0].getText());
	        }
	    });
	}
}

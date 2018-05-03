package com.datascope.ui.area.dialog;

import com.datascope.ui.area.callbacks.IAreaFileUploadCallback;
import com.datascope.ui.utils.common.FileUtilsWrapper;
import com.datascope.ui.utils.notifications.Notifications;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;

@Component
public class UploadAreaFileDialog extends Window implements Upload.Receiver {

    private static final String TMP_FILE_PREFIX = "area_file_";
    private static final String[] FILE_EXTENSIONS = new String[]{"png", "jpeg", "jpg", "pdf"};
    private static int UPLOAD_FILE_SIZE_LIMIT = 10 * 1024 * 1024; // 10 Mb


    @Value("${area.upload.name}")
    private String labelAreaName;

    @Value("${area.upload.file}")
    private String labelAreaFile;

    @Value("${area.upload.uploaddialog}")
    private String labelUploadDialog;

    @Value("${area.upload.uploading}")
    private String labelAreaUploading;

    @Value("${area.upload.creating}")
    private String labelAreaCreating;

    @Value("${area.upload.button.upload}")
    private String labelAreaButtonUpload;

    @Value("${area.upload.button.cancel}")
    private String labelAreaButtonCancel;


    private String fileName = "";
    private File file;

    private TextField tfAreaName;
    private Upload uploadComponent;
    private ProgressBar progressBarUpload;
    private ProgressBar progressBarCreateArea;
    private Button btnUpload;
    private Button btnClose;

    private IAreaFileUploadCallback callback = (areaName, areaFileName, fileData) -> {
    };

    private Notifications notifications;

    public UploadAreaFileDialog(Notifications notifications) {
        super();

        this.notifications = notifications;

        center();
    }

    @Override
    public void attach() {
        super.attach();
        setCaption(labelUploadDialog);

        setClosable(true);
        setModal(true);
        setResizable(false);

        createComponents();
        applyComponentLayout();
    }

    @Override
    public void close() {
        reset();
        super.close();
    }

    public void setAreaFileUploadCallback(IAreaFileUploadCallback callback) {
        if (Optional.ofNullable(callback).isPresent()) {
            this.callback = callback;
        }
    }

    private void createComponents() {
        tfAreaName = new TextField(labelAreaName);
        tfAreaName.setSizeFull();

        uploadComponent = new Upload();
        uploadComponent.setReceiver(this);
        uploadComponent.setCaption(labelAreaFile);
        uploadComponent.setImmediateMode(false);
        uploadComponent.setButtonCaption(null);

        uploadComponent.addChangeListener(event -> {
            String areaName = getFileNameWithoutExtension(event.getFilename());
            tfAreaName.setValue(areaName);
            fileName = event.getFilename();
        });

        uploadComponent.addStartedListener(event -> {
            if (event.getContentLength() > UPLOAD_FILE_SIZE_LIMIT) {
                uploadComponent.interruptUpload();
                notifications.error("areas.upload.filetobig");
                reset();
                return;
            }

            progressBarUpload.setValue(0);
            progressBarUpload.setVisible(true);
        });

        uploadComponent.addProgressListener((readBytes, contentLength) -> {
            if (contentLength != 0) {
                progressBarUpload.setValue(readBytes / contentLength);
            }
        });

        uploadComponent.addFailedListener(event -> {
            notifications.error("areas.upload.failed");
            reset();
        });

        uploadComponent.addSucceededListener(event -> {
            progressBarCreateArea.setVisible(true);
            uploadFinished();
        });

        progressBarUpload = new ProgressBar();
        progressBarUpload.setSizeFull();
        progressBarUpload.setIndeterminate(false);
        progressBarUpload.setCaption(labelAreaUploading);
        progressBarUpload.setVisible(false);

        progressBarCreateArea = new ProgressBar();
        progressBarCreateArea.setIndeterminate(true);
        progressBarCreateArea.setCaption(labelAreaCreating);
        progressBarCreateArea.setVisible(false);

        btnUpload = new Button(labelAreaButtonUpload, VaadinIcons.UPLOAD);
        btnUpload.addClickListener(event -> startUpload());

        btnClose = new Button(labelAreaButtonCancel, VaadinIcons.CLOSE);
        btnClose.addClickListener(event -> close());
    }

    private void applyComponentLayout() {
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.addComponent(btnUpload);
        bottomBar.addComponent(btnClose);
        bottomBar.setComponentAlignment(btnUpload, Alignment.MIDDLE_RIGHT);
        bottomBar.setComponentAlignment(btnClose, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(tfAreaName);
        verticalLayout.addComponent(uploadComponent);
        verticalLayout.addComponent(progressBarUpload);
        verticalLayout.addComponent(progressBarCreateArea);
        verticalLayout.addComponent(bottomBar);

        verticalLayout.setComponentAlignment(bottomBar, Alignment.MIDDLE_RIGHT);

        verticalLayout.setComponentAlignment(progressBarUpload, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(progressBarCreateArea, Alignment.MIDDLE_CENTER);

        setContent(verticalLayout);
    }

    private void reset() {
        uploadComponent.interruptUpload();
        fileName = "";
        tfAreaName.setValue("");

        progressBarUpload.setValue(0);
        progressBarUpload.setVisible(false);

        progressBarCreateArea.setVisible(false);

        FileUtilsWrapper.removeFile(file);
    }

    private void startUpload() {
        String areaName = tfAreaName.getValue().trim();
        try {
            assertAreaNameEmpty(areaName);
            assertFileNotSelected(fileName);
            assertWrongFileType(fileName);
            uploadComponent.submitUpload();
        } catch (Exception ignored) {
        }
    }

    private void uploadFinished() {
        if (Optional.ofNullable(file).isPresent()) {
            String areaName = tfAreaName.getValue().trim();
            String areaFileName = FilenameUtils.getName(fileName);
            callback.areaFileUploadFinished(areaName, areaFileName, FileUtilsWrapper.extractBytes(file));
            close();
        }
    }

    private String getFileNameWithoutExtension(String filePath) {
        return FilenameUtils.removeExtension(FilenameUtils.getName(filePath));
    }

    private void assertFileNotSelected(String fileName) throws Exception {
        if (fileName.isEmpty()) {
            notifications.error("areas.upload.filenotselected");
            throw new Exception();
        }
    }

    private void assertWrongFileType(String fileName) throws Exception {
        String fileNameLower = fileName.toLowerCase();
        for (String fileExtension : FILE_EXTENSIONS) {
            if (fileNameLower.endsWith(fileExtension)) {
                return;
            }
        }
        notifications.error("areas.upload.wrongfile");
        throw new Exception();
    }

    private void assertAreaNameEmpty(String areaName) throws Exception {
        if (areaName.isEmpty()) {
            notifications.error("areas.upload.notspecified");
            throw new Exception();
        }
    }

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {
        FileOutputStream outputStream = null;
        try {
            file = File.createTempFile(TMP_FILE_PREFIX, "");
            outputStream = new FileOutputStream(file);
        } catch (Exception ex) {
            notifications.error("areas.upload.filecreationfailed");
        }
        UI.getCurrent().setPollInterval(300);
        return outputStream;
    }
}

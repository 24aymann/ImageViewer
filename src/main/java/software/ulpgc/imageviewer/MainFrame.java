package software.ulpgc.imageviewer;

import software.ulpgc.imageviewer.control.Command;
import software.ulpgc.imageviewer.view.ImageDisplay;
import software.ulpgc.imageviewer.model.Image;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final SwingImageDisplay imageDisplay;
    private final Map<String, Command> commands;

    public MainFrame() throws HeadlessException {
        this.commands = new HashMap<>();

        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.add(imageDisplay = newImageDisplay());

        JScrollPane scrollPane = new JScrollPane(imageDisplay);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(utilityPanel(), BorderLayout.SOUTH);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private SwingImageDisplay newImageDisplay() {
        return new SwingImageDisplay(new SwingImageDeserializer());
    }

    private Component utilityPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(navigationalButton("←"));
        panel.add(navigationalButton("→"));
        return panel;
    }

    private Component navigationalButton(String buttonReference) {
        JButton button = new JButton(buttonReference);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(b -> {
            commands.get(buttonReference).execute();
        });
        return button;
    }

    public ImageDisplay getImageDisplay() {
        return imageDisplay;
    }

    public static MainFrame create() {
        return new MainFrame();
    }

    public MainFrame initWith(Image image) {
        imageDisplay.show(image);
        return this;
    }

    public MainFrame add(String buttonReference, Command command) {
        commands.put(buttonReference, command);
        return this;
    }
}

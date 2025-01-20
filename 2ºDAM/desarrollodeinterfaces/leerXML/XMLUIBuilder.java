import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLUIBuilder {
    public static void main(String[] args) {
        try {
            UIBuilder builder = new UIBuilder();
            // Usar getResource para cargar el XML desde resources
            String xmlPath = XMLUIBuilder.class.getClassLoader()
                                    .getResource("Interfaz.xml").getPath();
            JFrame frame = builder.buildFromXML(xmlPath);
            frame.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class UIBuilder {
    private Document doc;
    private UIComponentFactory factory;
    
    public JFrame buildFromXML(String xmlPath) throws Exception {
        factory = new UIComponentFactory();
        loadXMLDocument(xmlPath);
        return createMainWindow();
    }
    
    private void loadXMLDocument(String xmlPath) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(new File(xmlPath));
        doc.getDocumentElement().normalize();
    }
    
    private JFrame createMainWindow() {
        Element ventana = (Element) doc.getElementsByTagName("ventana").item(0);
        JFrame frame = new JFrame(ventana.getAttribute("titulo"));
        frame.setSize(500, 300); // Tamaño por defecto
        processComponents(ventana, frame.getContentPane());
        return frame;
    }

    private void processComponents(Element parent, Container container) {
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i) instanceof Element) {
                Element element = (Element) nodes.item(i);
                JComponent component = factory.createComponent(element);
                if (parent.getTagName().equals("panel")) {
                    String constraints = element.getAttribute("constraints");
                    if (!constraints.isEmpty() && container.getLayout() instanceof BorderLayout) {
                        container.add(component, constraints);
                    } else {
                        container.add(component);
                    }
                } else {
                    container.add(component);
                }
                
                if (element.getTagName().equals("panel")) {
                    processComponents(element, (Container) component);
                }
            }
        }
    }
}

class UIComponentFactory {
    public JComponent createComponent(Element element) {
        switch(element.getTagName()) {
            case "boton":
                return createButton(element);
            case "etiqueta":
                return createLabel(element);
            case "cajaTexto":
                return createTextField(element);
            case "panel":
                return createPanel(element);
            default:
                throw new IllegalArgumentException("Componente no soportado: " + element.getTagName());
        }
    }
    
    private JButton createButton(Element element) {
        JButton button = new JButton(element.getAttribute("texto"));
        return button;
    }
    
    private JLabel createLabel(Element element) {
        JLabel label = new JLabel(element.getAttribute("texto"));
        return label;
    }
    
    private JTextField createTextField(Element element) {
        JTextField textField = new JTextField(element.getAttribute("texto"), 20);
        return textField;
    }
    
    private JPanel createPanel(Element element) {
        JPanel panel = new JPanel();
        String layout = element.getAttribute("layout");
        panel.setLayout(createLayout(layout));
        return panel;
    }
    
    private LayoutManager createLayout(String layout) {
        switch(layout.toLowerCase()) {
            case "borderlayout":
                return new BorderLayout();
            case "flowlayout":
                return new FlowLayout();
            case "gridlayout":
                return new GridLayout(0, 1); // Una columna, filas automáticas
            default:
                return new FlowLayout();
        }
    }
}
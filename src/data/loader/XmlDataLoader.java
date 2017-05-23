package data.loader;

import api.entity.Group;
import api.entity.Root;
import model.tree.RootImpl;
import model.tree.StudentImpl;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

class XmlDataLoader extends FileDataLoader {
    XmlDataLoader(File file) {
        super(file);
    }

    @Override
    public Root loadData() {
        Root root = new RootImpl();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element rootElement = document.getDocumentElement();
            NodeList groupNodes = rootElement.getElementsByTagName("group");
            for (int i = 0; i < groupNodes.getLength(); i++) {
                Node groupNode = groupNodes.item(i);
                NamedNodeMap groupNodeAttributes = groupNode.getAttributes();
                Node nameNode = groupNodeAttributes.getNamedItem("name");
                String groupName = nameNode.getNodeValue();
                Group group = root.getOrAddGroup(groupName);
                NodeList studentNodes = groupNode.getChildNodes().item(1).getChildNodes();
                for (int j = 0; j < studentNodes.getLength(); j++) {
                    Node studentNode = studentNodes.item(j);
                    if (!"student".equals(studentNode.getNodeName())) {
                        continue;
                    }
                    NamedNodeMap studentNodeAttributes = studentNode.getAttributes();
                    Node headNode = studentNodeAttributes.getNamedItem("head");
                    boolean head = headNode != null && "true".equals(headNode.getNodeValue());
                    NodeList studentProperties = studentNode.getChildNodes();
                    String lastName = null;
                    String firstName = null;
                    String middleName = null;
                    int rating = -1;
                    for (int k = 0; k < studentProperties.getLength(); k++) {
                        Node propertyNode = studentProperties.item(k);
                        if ("surname".equals(propertyNode.getNodeName())) {
                            lastName = propertyNode.getTextContent();
                        } else if ("name".equals(propertyNode.getNodeName())) {
                            firstName = propertyNode.getTextContent();
                        } else if ("middleName".equals(propertyNode.getNodeName())) {
                            middleName = propertyNode.getTextContent();
                        } else if ("rating".equals(propertyNode.getNodeName())) {
                            rating = Integer.parseInt(propertyNode.getTextContent());
                        }
                    }
                    if (lastName == null || firstName == null || middleName == null || rating == -1) {
                        throw new RuntimeException("Некорректный формат");
                    }
                    group.addStudent(new StudentImpl(firstName, lastName, middleName, rating, group, head));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return root;
    }
}

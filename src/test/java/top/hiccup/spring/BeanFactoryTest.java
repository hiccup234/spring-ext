package top.hiccup.spring;

import org.w3c.dom.*;
import top.hiccup.spring.test.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactoryTest {

    private Map<String, Object> beanMap = new ConcurrentHashMap<>(32);

    public void init(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xml));
            Element root = document.getDocumentElement();
            NodeList nl = root.getChildNodes();
            // <bean>节点
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (!"bean".equals(node.getNodeName())) {
                    continue;
                }
                NamedNodeMap namedNodeMap = node.getAttributes();
                Node idNode = namedNodeMap.getNamedItem("id");
                Node clazzNode = namedNodeMap.getNamedItem("class");
                Class clazz = Class.forName(clazzNode.getTextContent());
                BeanInfo info = Introspector.getBeanInfo(clazz);
                PropertyDescriptor pd[] = info.getPropertyDescriptors();
                Method mSet = null;
                Object obj = clazz.newInstance();
                // <property>节点
                NodeList childNodeList = node.getChildNodes();
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    Node childNode = childNodeList.item(j);
                    if ("property".equals(childNode.getNodeName())) {
                        NamedNodeMap property = childNode.getAttributes();
                        String name = property.getNamedItem("name").getTextContent();
                        // <value>节点
                        NodeList valueNode = childNode.getChildNodes();
                        String value = valueNode.item(1).getTextContent();
                        for (int k = 0; k < pd.length; k++) {
                            if (pd[k].getName().equalsIgnoreCase(name)) {
                                mSet = pd[k].getWriteMethod();
                                // 要考虑类型转换
                                mSet.invoke(obj, value);
                            }
                        }
                    }
                }
                beanMap.put(idNode.getTextContent(), obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) {
        Object obj = beanMap.get(beanName);
        return obj;
    }

    public static void main(String[] args) {
        BeanFactoryTest factory = new BeanFactoryTest();
        factory.init("D:\\MyDev\\IDEA Workspace\\Hiccup\\spring-ext\\src\\test\\resources\\spring/beans.xml");
        User user = (User) factory.getBean("user");
        System.out.println("name = " + user.getName());
        System.out.println("age = " + user.getAge());
    }
}

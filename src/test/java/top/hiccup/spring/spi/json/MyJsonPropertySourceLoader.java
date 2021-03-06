package top.hiccup.spring.spi.json;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * MyJsonPropertySourceLoader
 *
 * @author wenhy
 * @date 2021/3/6
 */
public class MyJsonPropertySourceLoader implements PropertySourceLoader {

    @Override
    public String[] getFileExtensions() {
        return new String[]{"xyz"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = JSONObject.parseObject(sb.toString());
        List<PropertySource<?>> propertySources = new ArrayList<>();
        MapPropertySource mapPropertySource = new MapPropertySource(resource.getFilename(), json);
        propertySources.add(mapPropertySource);
        return propertySources;
    }

}

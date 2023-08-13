package lib.mylib.properties;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectConfigSet implements Iterable<ObjectConfig> {
    private String name;
    private List<ObjectConfig> configs = new LinkedList<ObjectConfig>();

    public ObjectConfigSet() {
    }

    public ObjectConfigSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjectConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ObjectConfig> configs) {
        this.configs = configs;
    }

    public void addObjectConfig(ObjectConfig config) {
        configs.add(config);
    }

    @Override
    public Iterator<ObjectConfig> iterator() {
        return configs.iterator();
    }

    @Override
    public String toString() {
        return "ObjectConfigSet [name=" + name + ", configs=" + configs + "]";
    }


}

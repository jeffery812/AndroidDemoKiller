package tang.max.com.demokiller.model;

/**
 * Created by zhihuitang on 2016-11-01.
 */

public class DemoEntity {
    private String mDescription;
    private Class mClassName;

    public DemoEntity(String description, Class aClass) {
        mDescription = description;
        mClassName = aClass;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Class getClassName() {
        return mClassName;
    }

    public void setClassName(Class className) {
        mClassName = className;
    }
}

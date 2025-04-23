package online.goudan.config;

import online.goudan.util.ThreadLocalUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Object o = ThreadLocalUtil.get("dynamic");
        if(o == null){
            o = "master";
        }
        return o;
    }
}

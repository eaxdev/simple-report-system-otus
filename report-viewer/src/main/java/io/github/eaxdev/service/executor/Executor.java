package io.github.eaxdev.service.executor;

import java.util.List;
import java.util.Map;

public interface Executor {

    List<Map<String, Object>> getData(String query);

    Integer getCount(String query);
}

package com.beiming.novel_crawler.spider.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * PipelineType
 */
public abstract class AbstractPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (getType() != null) {
            if (getType().equals(resultItems.get("type"))) {
                process(resultItems);
            }
        }
    }

    protected abstract void process(ResultItems resultItems);

    protected abstract String getType();
}

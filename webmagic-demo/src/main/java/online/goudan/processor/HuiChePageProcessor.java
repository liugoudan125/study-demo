package online.goudan.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author
 * @date 2023/5/8 17:25
 * @desc HuiChePageProcessor
 */
public class HuiChePageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36 Edg/113.0.1774.35");

    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().getDocument().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}

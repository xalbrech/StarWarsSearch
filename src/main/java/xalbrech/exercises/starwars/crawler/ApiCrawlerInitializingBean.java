package xalbrech.exercises.starwars.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xalbrech.exercises.starwars.StarWarsSearchApp;

@Component
public class ApiCrawlerInitializingBean implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(StarWarsSearchApp.class);

    @Autowired
    private ApiCrawler apiCrawler;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Populating search index....");
        apiCrawler.crawl();
        log.info("Population of search index completed.");
    }
}

package com.dbay.teddy;

import com.dbay.teddy.config.AppConfig;
import com.dbay.teddy.utils.TeddyConf;
import org.apache.commons.cli.*;
import org.apache.commons.io.IOUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Spring Boot 应用入口
 * @author AlexanderGuo
 */
@SpringBootApplication
@Import(AppConfig.class)
@MapperScan("com.dbay.teddy.mapper")
public class Application{
    private static final String PATH = "p";

    public static void main(String[] args) throws ParseException, IOException {

        Options options = new Options();

        options.addOption(PATH, true, "加载 Properties 文件");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(PATH)) {
            String path = cmd.getOptionValue(PATH);

            // 加载 Properties
            Properties properties = loadProperties(path);

            TeddyConf.setProperties(properties);
        } else {
            TeddyConf.setProperties(loadProperties("D:\\IdeaProjects\\GitHubProjects\\teddy\\conf\\teddy.properties"));
        }

        SpringApplication.run(Application.class, args);
    }

    private static Properties loadProperties(String path) throws IOException {
        InputStream in = new FileInputStream(path);
        Properties properties = new Properties();
        properties.load(in);
        IOUtils.closeQuietly(in);
        return properties;
    }

}

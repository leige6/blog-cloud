package com.leige.blog.config;

import com.leige.blog.template.CheckPermission;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/6/1  17:08
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class TldConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private FreeMarkerConfigurer configurer;
    @Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    private CheckPermission checkPermission;
    @PostConstruct
    public void setSharedVariable() throws TemplateException {
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setTimeFormat("HH:mm:ss");
        //下面三句配置的就是我自己的freemarker的自定义标签，在这里把标签注入到共享变量中去就可以在模板中直接调用了
        //<entry key="base" value="#{servletContext.contextPath}" />
        configuration.setSharedVariable("checkPermission", checkPermission);
        //setting配置
        configuration.setSetting("template_update_delay", "0");//模板更新时间，这里配置1秒更新一次，正式环境可以将值设大一点，提高效率,正式环境设置为3600秒
        configuration.setSetting("default_encoding", "UTF-8");
        configuration.setSetting("tag_syntax","auto_detect");//模版里标签的显示方式为<>或[] ,注意模版内标签显示方式不可混用。此处设置为自动匹配
        configuration.setSetting("number_format","0.######");
        configuration.setSetting("whitespace_stripping","true");//去掉多余的空格,非常有用
        configuration.setSetting("object_wrapper","freemarker.ext.beans.BeansWrapper");
    }
    @PostConstruct
    public void freeMarkerConfigurer() {
        List<String> tlds = new ArrayList<String>();
        tlds.add("/static/tags/security.tld");
        TaglibFactory taglibFactory = configurer.getTaglibFactory();
        taglibFactory.setClasspathTlds(tlds);
        if(taglibFactory.getObjectWrapper() == null) {
            taglibFactory.setObjectWrapper(configurer.getConfiguration().getObjectWrapper());
        }
    }
}

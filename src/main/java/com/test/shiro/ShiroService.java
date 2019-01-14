package com.test.shiro;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.urlFilter;
import com.test.service.urlFilterService;

@Service
public class ShiroService{

	@Autowired
	private urlFilterService urlFilterService;
	
	public Map<String, String> loadFilterChainDefinitions() {
		Map<String, String> filterMap = new LinkedHashMap<String, String>();
		List list = urlFilterService.getUrlFilter();
		for(int i=0;i<list.size();i++) {
			urlFilter urlfilter = (urlFilter) list.get(i);
			filterMap.put(urlfilter.getUrl(), urlfilter.getFilter());
		}
		System.out.println("权限加载完成！！");
        return filterMap;
    }
	
	
	public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		synchronized (shiroFilterFactoryBean) {
			AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
            	throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean
                    .setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            System.out.println("更新权限成功！！");
        }
    }
}
